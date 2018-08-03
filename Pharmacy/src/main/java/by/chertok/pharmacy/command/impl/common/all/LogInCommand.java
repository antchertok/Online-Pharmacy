package main.java.by.chertok.pharmacy.command.impl.common.all;


import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.UserService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class LogInCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class);
    private UserService userService;

    public LogInCommand(UserService userService){
        this.userService = userService;
    }

    /**
     * If successful, sets the user object corresponding to given login and password
     * into {@link Wrapper wrapper} for the further transmitting it into the session
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    public Path execute(Wrapper wrapper) {
        try{
            String login = wrapper.getRequestParameter("login");
            String password = (String)wrapper.getRequestAttribute("password");
            Optional<User> user = userService.readByLoginPassword(login, password);
            Path path = new Path();
            path.setForward(true);

            if(user.isPresent()){
                wrapper.setSessionAttribute("user", user.get());
                Order order = new Order(0);
                order.setCustomerId(((User)wrapper.getSessionAttribute("user")).getId());
                wrapper.setSessionAttribute("order", order);
                wrapper.setSessionAttribute("drugsOrdered", 0);
                wrapper.setSessionAttribute("total", 0.0);
                path.setUrl(Pages.START_PAGE);
            } else {
                String errMsg = "Access Denied. Wrong login and/or password.";
                wrapper.setRequestAttribute("accessDeniedMessage", errMsg);
                path.setUrl(Pages.LOGIN);
            }

            return path;
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}