package by.chertok.pharmacy.command;

import org.apache.log4j.Logger;

/**
 * Class responsible for extradition appropriate implementation of interface
 * {@link ICommand ICommand}
 */
public class CommandProvider {

    public ICommand getCommandByName(String commandName) {
        String commandType = convertCommand(commandName);
        return CommandHolder.valueOf(commandType).getCommand();
    }

    /**
     * Auxiliary method for preparing command from request before getting
     * corresponding implementation
     * @param commandName string that contains required command
     * @return command name in appropriate format
     */
    public String convertCommand(String commandName) {
        return commandName.replace('-', '_').toUpperCase();
    }
}
