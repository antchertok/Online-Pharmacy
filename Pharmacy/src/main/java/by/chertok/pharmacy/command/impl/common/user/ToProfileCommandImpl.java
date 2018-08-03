package main.java.by.chertok.pharmacy.command.impl.common.user;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToProfileCommandImpl implements ICommand {

    /**
     * Sends redirect to the profile page
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper){
        return new Path(false, Pages.PROFILE);
    }
}
