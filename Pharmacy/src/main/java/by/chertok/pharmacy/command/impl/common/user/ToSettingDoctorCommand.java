package by.chertok.pharmacy.command.impl.common.user;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToSettingDoctorCommand implements ICommand {

    /**
     * Makes user able to set doctor
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        wrapper.setRequestAttribute(AttributeName.SETTING_DOCTOR, true);
        return new Path(true, wrapper.getRequestParameter(AttributeName.CURRENT_URL));
    }
}
