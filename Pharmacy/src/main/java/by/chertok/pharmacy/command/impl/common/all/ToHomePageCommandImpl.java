package main.java.by.chertok.pharmacy.command.impl.common.all;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToHomePageCommandImpl implements ICommand {

    /**
     * Sends redirect to a home page
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper){
        return new Path(false, Pages.START_PAGE);
    }
}
