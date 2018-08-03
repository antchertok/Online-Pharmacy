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

public class SignUpCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);
    private UserService userService;

    public SignUpCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * If successful, adds a new account into util and sets user object into
     * the session
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try{
            User user = new User(0);
            user.setLogin(wrapper.getRequestParameter("login").trim());
            user.setPassword((String)(wrapper.getRequestAttribute("password")));
            user.setFirstName(wrapper.getRequestParameter("firstName").trim());
            user.setLastName(wrapper.getRequestParameter("lastName").trim());
            user.setMail(wrapper.getRequestParameter("email").trim());
            Path path = new Path();
            path.setUrl(Pages.START_PAGE);

            if (userService.create(user)) {
                user = userService.readByLoginPassword(user.getLogin(), user.getPassword()).get();
                wrapper.setSessionAttribute("user", user);
                Order order = new Order(0);
                order.setCustomerId(((User)wrapper.getSessionAttribute("user")).getId());
                wrapper.setSessionAttribute("order", order);
                wrapper.setSessionAttribute("drugsOrdered", 0);
                wrapper.setSessionAttribute("total", 0.0);
                path.setForward(false);
                return path;
            } else {
                wrapper.setRequestAttribute("errorRegMsg", "Invalid data");
                path.setForward(true);
                return path;
            }
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
