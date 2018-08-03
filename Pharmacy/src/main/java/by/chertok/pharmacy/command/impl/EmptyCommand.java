package main.java.by.chertok.pharmacy.command.impl;


import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class EmptyCommand implements ICommand {

    @Override
    public Path execute(Wrapper wrapper){
        wrapper.setSessionAttribute("errMsg", "Unknown command");
        return new Path(false, Pages.ERROR);
    }
}
