package main.java.by.chertok.pharmacy.controller;


import main.java.by.chertok.pharmacy.command.CommandProvider;
import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = Logger.getLogger(FrontController.class); //LOGGER

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
        System.out.println(request.getContextPath());
        Wrapper wrapper = new Wrapper();
        wrapper.extract(request);
        ICommand command = CommandProvider.getCommandByName(wrapper.getRequestParameter("command"));
        Path path = command.execute(wrapper);
        wrapper.updateRequest(request);

        if (path.isForward()) {
            request.getRequestDispatcher(path.getUrl()).forward(request, response);
        } else {
            response.sendRedirect(path.getUrl());
        }
    }
}