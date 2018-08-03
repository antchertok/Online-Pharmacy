package main.java.by.chertok.pharmacy.filter;

import main.java.by.chertok.pharmacy.command.CommandProvider;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.util.md5.Encoder;
import main.java.by.chertok.pharmacy.util.md5.impl.EncoderMd5;
import main.java.by.chertok.pharmacy.util.validator.ParameterValidator;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter for encoding passwords coming with commands "Login" and "Sign Up"
 */
@WebFilter(
        displayName = "authenticationFilter",
        urlPatterns = "/controller",
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class EncodingFilter implements Filter {

    private Encoder encoder;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoder = new EncoderMd5();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        String command = CommandProvider.convertCommand(request.getParameter("command"));

        if(command.equals("LOGIN")){
            String password = request.getParameter("password");
            request.setAttribute("password", encoder.encode(password));
        } else if (command.equals("SIGN_UP") || command.equals("UPDATE_USER")){
            User user = new User(0);
            user.setLogin(request.getParameter("login").trim());
            user.setPassword(request.getParameter("password").trim());
            user.setFirstName(request.getParameter("firstName").trim());
            user.setLastName(request.getParameter("lastName").trim());
            user.setMail(request.getParameter("email").trim());

            if(ParameterValidator.isUserValid(user)){
                request.setAttribute("password", encoder.encode("password"));
            } else {
                String errMsg = "Access Denied. Wrong login and/or password.";
                request.setAttribute("accessDeniedMessage", errMsg);
                response.sendRedirect("login.jsp");
            }
        }

        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
