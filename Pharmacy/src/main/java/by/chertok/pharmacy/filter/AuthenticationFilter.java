package main.java.by.chertok.pharmacy.filter;

import main.java.by.chertok.pharmacy.command.CommandHolder;
import main.java.by.chertok.pharmacy.command.CommandProvider;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter for preventing the execution of inappropriate commands via URL requests
 */
//TODO: CLEAN IT
@WebFilter (
        displayName = "authenticationFilter",
        urlPatterns = "/controller",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class AuthenticationFilter implements Filter {
    private EnumSet<CommandHolder> freeCommands;
    private EnumSet<CommandHolder> pharmacistCommands;
    private EnumSet<CommandHolder> customerCommands;
    private EnumSet<CommandHolder> doctorCommands;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        freeCommands = EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.LOGIN); //TODO pause
//        userCommands = EnumSet.range(CommandHolder.LOGOUT, CommandHolder.DELETE_USER);
        pharmacistCommands = EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.CURRENT_ORDER);
        customerCommands = EnumSet.range(CommandHolder.LIST_ORDERS, CommandHolder.CURRENT_ORDER);
        customerCommands.addAll(EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.DELETE_USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
//         Check if user has enough access level
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String command = CommandProvider.convertCommand(request.getParameter("command"));
        User user = (User)request.getSession().getAttribute("user");
        String page = null;

        if(command != null && !command.isEmpty()){

            if(user != null){
                if(user.getRole().equals("customer")
                        && !customerCommands.contains(CommandHolder.valueOf(command))){
                    page = Pages.ERROR;
                }
                if (user.getRole().equals("pharmacist")
                        && !pharmacistCommands.contains(CommandHolder.valueOf(command))){
                    page = Pages.ERROR;
                }
            } else if(!freeCommands.contains(CommandHolder.valueOf(command))){
                page = Pages.ERROR;
            }

            if(page != null){
                request.setAttribute("errMsg", "Invalid access level");//TODO НЕ ПОКАЗЫВАЕТ
                response.sendRedirect(page);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
