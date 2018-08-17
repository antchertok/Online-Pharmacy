package by.chertok.pharmacy.command;

import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

/**
 * Defines the interface for implementing the "Command" pattern
 */
public interface ICommand {

    Path execute(Wrapper wrapper);
}
