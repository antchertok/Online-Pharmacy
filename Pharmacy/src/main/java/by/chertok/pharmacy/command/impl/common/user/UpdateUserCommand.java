package main.java.by.chertok.pharmacy.command.impl.common.user;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.UserService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

public class UpdateUserCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(UpdateUserCommand.class);
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
        try{
            User user = new User(Integer.parseInt(wrapper.getRequestParameter("id")));
            user.setLogin(wrapper.getRequestParameter("login").trim());
            user.setPassword(((User)wrapper.getSessionAttribute("user")).getPassword());
            user.setFirstName(wrapper.getRequestParameter("firstName").trim());
            user.setLastName(wrapper.getRequestParameter("lastName").trim());
            user.setMail(wrapper.getRequestParameter("email").trim());
            Path path = new Path();
            path.setUrl(Pages.START_PAGE);

            if (userService.update(user)) {
//            request.getRequestDispatcher("index.jsp").forward(request, response);
                path.setForward(false);
            } else {
                wrapper.setSessionAttribute("updateInfoMsg", "Failed to update profile");
                path.setForward(true);
            }

            return path;
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}