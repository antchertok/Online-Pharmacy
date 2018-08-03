package main.java.by.chertok.pharmacy.command.impl.common.all;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

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
        String locale = wrapper.getRequestParameter("locale");
        wrapper.setSessionAttribute("locale", locale);
        return new Path(true, wrapper.getRequestParameter("currentUrl"));
    }
}
