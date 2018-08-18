package by.chertok.pharmacy.command.impl.customer;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.command.impl.common.all.LogInCommand;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.UserService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.Optional;

public class SetDoctorCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(LogInCommand.class);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Failed to set doctor";
    private UserService userService;

    public SetDoctorCommand(UserService userService) {
        this.userService = userService;
    }

    /**
     * Connects doctor with user (if doctor's ID not equals to the user's one)
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        User user = (User) wrapper.getSessionAttribute(AttributeName.USER);

        if (user != null) {
            try {
                String doctorFirstName = wrapper.getRequestParameter(AttributeName.DOCTOR_FIRST_NAME);
                String doctorLastName = wrapper.getRequestParameter(AttributeName.DOCTOR_LAST_NAME);
                Optional<User> doctor = userService.getDocByName(doctorFirstName, doctorLastName);

                if (doctor.isPresent() && doctor.get().getId() != user.getId()) {
                    user.setDoctorId(doctor.get().getId());
                    userService.update(user);
                    wrapper.setRequestAttribute(AttributeName.DOCTOR_MSG, SUCCESS);
                } else {
                    wrapper.setRequestAttribute(AttributeName.DOCTOR_MSG, FAIL);
                }

                return new Path(true, wrapper.getRequestParameter(AttributeName.CURRENT_URL));
            } catch (ServiceException e) {
                LOGGER.error(e.getMessage());
                wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
                return new Path(false, PageStorage.ERROR);
            }
        }


        return null;
    }
}
