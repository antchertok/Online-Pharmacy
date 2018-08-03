package main.java.by.chertok.pharmacy.command.impl.common.user;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.UserService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class DeleteUserCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteUserCommand.class);
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
        try{
            long userId = Long.parseLong(wrapper.getRequestParameter("id"));
            Path path = new Path();

            if (userService.delete(userId)) {
//            request.getSession().removeAttribute("user");
                wrapper.setSessionAttribute("user", null);
                path.setForward(false);
                path.setUrl(Pages.START_PAGE);
                return path;
            } else {
//            request.setAttribute("errMsg", "Failed to delete account");
                wrapper.setRequestAttribute("errMsg", "Failed to delete account");
                path.setForward(true);
                path.setUrl(Pages.PROFILE);
                return path;
            }
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
