package by.chertok.pharmacy.command.impl.common.all;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class ChangeLocaleCommand implements ICommand {

    /**
     * Replaces the current locale with a given or default, if given value is not
     * supported
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        String locale = wrapper.getRequestParameter(AttributeName.LOCALE);
        wrapper.setSessionAttribute(AttributeName.LOCALE, locale);
        return new Path(true, wrapper.getRequestParameter(AttributeName.CURRENT_URL));
    }
}
