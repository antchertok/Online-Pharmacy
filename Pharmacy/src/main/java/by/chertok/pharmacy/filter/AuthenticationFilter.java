package by.chertok.pharmacy.filter;

import by.chertok.pharmacy.command.CommandHolder;
import by.chertok.pharmacy.command.CommandProvider;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Filter for preventing the execution of inappropriate commands via URL requests
 */
@WebFilter (
        displayName = "authenticationFilter",
        urlPatterns = "/controller",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class AuthenticationFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(AuthenticationFilter.class);
    private static final String UNACCEPTABLE_COMMAND = "Unacceptable command";

    private EnumSet<CommandHolder> freeCommands;
    private EnumSet<CommandHolder> pharmacistCommands;
    private EnumSet<CommandHolder> customerCommands;
    private EnumSet<CommandHolder> doctorCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        freeCommands = EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.LOGIN);
        pharmacistCommands = EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.DELETE_DRUG);
        customerCommands = EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.CURRENT_ORDER);
        doctorCommands = EnumSet.range(CommandHolder.SEEK_DRUG, CommandHolder.CURRENT_ORDER);
        doctorCommands.addAll(EnumSet.range(CommandHolder.APPROVE_PRESCRIPTION, CommandHolder.LIST_PRESCRIPTIONS));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String commandName = request.getParameter(AttributeName.COMMAND);
        boolean isLegal = true;

        if(commandName != null && !commandName.isEmpty()){
            commandName = commandName.replace('-','_').toUpperCase();
            CommandHolder command = null;
            try {
                command = CommandHolder.valueOf(commandName);
            } catch (IllegalArgumentException e){
                LOGGER.info(UNACCEPTABLE_COMMAND);
                request.getSession().setAttribute(AttributeName.ERROR_MSG, UNACCEPTABLE_COMMAND);
                request.getRequestDispatcher(PageStorage.ERROR).forward(request, response);
            }
            User user = (User)request.getSession().getAttribute(AttributeName.USER);

            if(user != null){
                String role = user.getRole();

                if((role.equals(AttributeName.CUSTOMER_ROLE)
                        && !customerCommands.contains(command))
                        ||(role.equals(AttributeName.PHARMACIST_ROLE)
                        && !pharmacistCommands.contains(command))
                        ||(role.equals(AttributeName.DOCTOR_ROLE)
                        && !doctorCommands.contains(command))) {
                    isLegal = false;
                }
            } else if(!freeCommands.contains(command)){
                isLegal = false;
            }
        } else {
            isLegal = false;
        }

        if(isLegal){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            LOGGER.info(UNACCEPTABLE_COMMAND);
            request.getSession().setAttribute(AttributeName.ERROR_MSG, UNACCEPTABLE_COMMAND);
            request.getRequestDispatcher(PageStorage.ERROR).forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
