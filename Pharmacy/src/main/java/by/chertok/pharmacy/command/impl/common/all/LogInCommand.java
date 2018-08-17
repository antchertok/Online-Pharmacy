package by.chertok.pharmacy.command.impl.common.all;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.UserService;
import by.chertok.pharmacy.util.encoder.Encoder;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class LogInCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class);

    private static final String ACCESS_DENIED_MSG = "Access Denied. Wrong login and/or password.";
    private UserService userService;
    private Encoder encoder;

    public LogInCommand(UserService userService, Encoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
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
        try {
            String login = wrapper.getRequestParameter(AttributeName.LOGIN);
            String password = encoder.encode(wrapper.getRequestParameter(AttributeName.PASSWORD).trim());
            Optional<User> user = userService.readByLoginPassword(login, password);
            Path path = new Path();
            path.setForward(true);

            if (user.isPresent()) {
                wrapper.setSessionAttribute(AttributeName.USER, user.get());
                Order order = new Order(0);
                order.setCustomerId(((User) wrapper.getSessionAttribute(AttributeName.USER)).getId());
                wrapper.setSessionAttribute(AttributeName.ORDER, order);
                wrapper.setSessionAttribute(AttributeName.DRUGS_ORDERED, 0);
                wrapper.setSessionAttribute(AttributeName.TOTAL, 0.0);
                path.setUrl(PageStorage.START_PAGE);
            } else {
                wrapper.setRequestAttribute(AttributeName.ACCESS_DENIED, ACCESS_DENIED_MSG);
                path.setUrl(PageStorage.LOGIN);
            }

            return path;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}