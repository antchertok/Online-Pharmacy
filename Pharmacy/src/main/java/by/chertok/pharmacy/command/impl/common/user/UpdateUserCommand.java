package by.chertok.pharmacy.command.impl.common.user;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.UserService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class UpdateUserCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(UpdateUserCommand.class);
    private static final String FAIL = "Failed to update profile";
    private UserService userService;

    public UpdateUserCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * If successful, updates information about given user
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            User user = (User) wrapper.getSessionAttribute(AttributeName.USER);
            user.setLogin(wrapper.getRequestParameter(AttributeName.LOGIN).trim());
            user.setFirstName(wrapper.getRequestParameter(AttributeName.FIRST_NAME).trim());
            user.setLastName(wrapper.getRequestParameter(AttributeName.LAST_NAME).trim());
            user.setMail(wrapper.getRequestParameter(AttributeName.EMAIL).trim());
            Path path = new Path();
            path.setUrl(PageStorage.PROFILE);

            if (userService.update(user)) {
                path.setForward(false);
            } else {
                wrapper.setSessionAttribute(AttributeName.UPDATE_INFO_MSG, FAIL);
                path.setForward(true);
            }
            return path;
        } catch (ServiceException e) {
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}