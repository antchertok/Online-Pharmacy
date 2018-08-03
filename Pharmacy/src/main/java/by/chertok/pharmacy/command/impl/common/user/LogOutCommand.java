package main.java.by.chertok.pharmacy.command.impl.common.user;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class LogOutCommand implements ICommand {

    /**
     * Removes the user object from the session
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper){
        wrapper.setSessionAttribute("user", null);
        wrapper.setSessionAttribute("total", 0.0);
        return new Path(true, Pages.START_PAGE);
//        request.getSession().removeAttribute("user");
//        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
