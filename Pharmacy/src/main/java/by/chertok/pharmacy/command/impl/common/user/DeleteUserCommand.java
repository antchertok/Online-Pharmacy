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

public class DeleteUserCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteUserCommand.class);
    private static final String FAILED_TO_DELETE_MSG = "Failed to delete account";
    private UserService userService;

    public DeleteUserCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * If successful, deletes the account from util
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            long userId = ((User) wrapper.getSessionAttribute(AttributeName.USER)).getId();
            Path path = new Path();

            if (userService.delete(userId)) {
                wrapper.setSessionAttribute(AttributeName.USER, null);
                path.setForward(false);
                path.setUrl(PageStorage.START_PAGE);
                return path;
            } else {
                wrapper.setRequestAttribute(AttributeName.ERROR_MSG, FAILED_TO_DELETE_MSG);
                path.setForward(true);
                path.setUrl(PageStorage.PROFILE);
                return path;
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
