package main.java.by.chertok.pharmacy.command;


import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

/**
 * Defines the interface for implementing the "Command" pattern
 */
public interface ICommand {

    Path execute(Wrapper wrapper);
}
