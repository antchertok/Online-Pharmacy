package by.chertok.pharmacy.command.impl.common.user;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class LogOutCommand implements ICommand {

    /**
     * Removes the user object from the session
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        wrapper.setSessionAttribute(AttributeName.USER, null);
        wrapper.setSessionAttribute(AttributeName.TOTAL, 0.0);
        return new Path(false, PageStorage.START_PAGE);
    }
}
