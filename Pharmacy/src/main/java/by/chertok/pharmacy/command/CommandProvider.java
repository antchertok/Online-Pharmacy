package by.chertok.pharmacy.command;

/**
 * Class responsible for extradition appropriate implementation of interface
 * {@link ICommand ICommand}
 */
public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public ICommand getCommandByName(String commandName) {
        String commandType = convertCommand(commandName);
        return CommandHolder.valueOf(commandType).getCommand();
    }

    private String convertCommand(String commandName) {
        return commandName.replace('-', '_').toUpperCase();
    }
}
