package by.chertok.pharmacy.command.impl.common.all;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.UserService;
import by.chertok.pharmacy.util.encoder.Encoder;
import by.chertok.pharmacy.util.encoder.impl.EncoderMd5;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.validator.ParameterValidator;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class SignUpCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(SignUpCommand.class);
    private static final String FAIL = "Failed to create account";
    private static final String INVALID_DATA = "Invalid data";
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
        try {
            User user = new User(0);
            user.setLogin(wrapper.getRequestParameter(AttributeName.LOGIN).trim());
            user.setPassword(wrapper.getRequestParameter(AttributeName.PASSWORD).trim());
            user.setFirstName(wrapper.getRequestParameter(AttributeName.FIRST_NAME).trim());
            user.setLastName(wrapper.getRequestParameter(AttributeName.LAST_NAME).trim());
            user.setMail(wrapper.getRequestParameter(AttributeName.EMAIL).trim());
            Path path = new Path();
            path.setUrl(PageStorage.START_PAGE);

            if (new ParameterValidator().isUserValid(user)) {
                Encoder encoder = EncoderMd5.getInstance();
                user.setPassword(encoder.encode(user.getPassword()));

                if (userService.create(user)) {
                    user = userService.readByLoginPassword(user.getLogin(), user.getPassword()).get();
                    wrapper.setSessionAttribute(AttributeName.USER, user);
                    Order order = new Order(0);
                    order.setCustomerId(((User) wrapper.getSessionAttribute(AttributeName.USER)).getId());
                    wrapper.setSessionAttribute(AttributeName.ORDER, order);
                    wrapper.setSessionAttribute(AttributeName.DRUGS_ORDERED, 0);
                    wrapper.setSessionAttribute(AttributeName.TOTAL, 0.0);
                    path.setForward(false);
                } else {
                    wrapper.setSessionAttribute(AttributeName.ERROR_MSG, FAIL);
                    path.setForward(false);
                    path.setUrl(PageStorage.ERROR);
                }
            } else {
                wrapper.setRequestAttribute(AttributeName.ERROR_REG_MSG, INVALID_DATA);
                path.setForward(true);
            }
            return path;
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
