package main.java.by.chertok.pharmacy.command;


import main.java.by.chertok.pharmacy.command.impl.EmptyCommand;

/**
 * Class responsible for extradition appropriate implementation of interface
 * {@link ICommand ICommand}
 */
public class CommandProvider {

    private CommandProvider() {
    }
//TODO УБРАТЬ ПРОВЕРКИ КОМАНД В ФИЛЬТР
    public static ICommand getCommandByName(String commandName) {
        if (commandName == null || commandName.isEmpty()) {
            return new EmptyCommand();
        }

//        switch (commandName){
//            case "add-drug":
//                return new AddDrugCommand(ServiceInstance.getInstance().getDrugService());
//            case "delete-user":
//                return new DeleteUserCommand(ServiceInstance.getInstance().getUserService());
//            case "list-orders":
//                return new ListOrderCommand(ServiceInstance.getInstance().getOrderService());
//            case "remove-drug":
//                return new RemoveDrugFromCartCommand();
//            case "seek-drug":
//                return new SeekDrugsCommand(ServiceInstance.getInstance().getDrugService());
//            case "sign-up":
//                return new SignUpCommand(ServiceInstance.getInstance().getUserService());
//            case "update-drug":
//                return new UpdateDrugCommand(ServiceInstance.getInstance().getDrugService());
//            case "update-user":
//                return new UpdateUserCommand(ServiceInstance.getInstance().getUserService());
//            case "login":
//                return new LogInCommand(ServiceInstance.getInstance().getUserService());
//            case "logout":
//                return new LogOutCommand();
//            case "change-locale":
//                return new ChangeLocaleCommand();
//            case "approve-order":
//                return new ApproveOrderCommand(ServiceInstance.getInstance().getOrderService());
//            case "add-to-card":
//                return new AddToCardCommand(ServiceInstance.getInstance().getDrugService(),
//                        ServiceInstance.getInstance().getPrescriptionService());
//            case "current-order":
//                return new CurrentOrderCommand(ServiceInstance.getInstance().getDrugService());
//            case "delete-drug":
//                return new DeleteDrugCommand(ServiceInstance.getInstance().getDrugService());
//            case "home-page":
//                return new ToHomePageCommandImpl();
//            case "profile":
//                return new ToProfileCommandImpl();
//            case "list-prescriptions":
//                return new ListPrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService());
//            case "approve-prescription":
//                return new ApprovePrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService());
//            case "deny-prescription":
//                return new DenyPrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService());
//            case "request-prescription":
//                return new RequestPrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService(),
//                        ServiceInstance.getInstance().getDrugService());
//            case "to-alternating":
//                return new ToAlternateDrugPage();
//            case "alternate-profile":
//                return new ToProfileAlternating();
//            case "remove-from-cart":
//                return new RemoveDrugFromCartCommand();
//            default:
//                return new EmptyCommand();
//        }

        String commandType = convertCommand(commandName);

        try {
            return CommandHolder.valueOf(commandType).getCommand();
        } catch (IllegalArgumentException e) {
            return new EmptyCommand();
        }
    }

    /**
     * Auxiliary method for preparing command from request before getting
     * corresponding implementation
     * @param commandName string that contains required command
     * @return command name in appropriate format
     */
    public static String convertCommand(String commandName) {//TODO CHECK IF NULL
        return commandName
                .replace('-', '_')
                .toUpperCase();
    }
}
