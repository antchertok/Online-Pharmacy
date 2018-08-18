package by.chertok.pharmacy.controller;

import by.chertok.pharmacy.command.CommandProvider;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet representing application controller
 */
@WebServlet(
        urlPatterns = "/controller"
)
public class FrontController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Wrapper wrapper = new Wrapper();
        wrapper.extract(request);
        CommandProvider provider = CommandProvider.getInstance();
        ICommand command = provider.getCommandByName(request.getParameter(AttributeName.COMMAND));
        Path path = command.execute(wrapper);
        wrapper.updateRequest(request);

        if (path.isForward()) {
            request.getRequestDispatcher(path.getUrl()).forward(request, response);
        } else {
            response.sendRedirect(path.getUrl());
        }
    }
}