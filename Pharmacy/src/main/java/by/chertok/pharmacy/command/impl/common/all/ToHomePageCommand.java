package by.chertok.pharmacy.command.impl.common.all;

import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToHomePageCommand implements ICommand {

    /**
     * Sends redirect to a home page
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        return new Path(false, PageStorage.START_PAGE);
    }
}
