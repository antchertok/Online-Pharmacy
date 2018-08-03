package main.java.by.chertok.pharmacy.command;


import main.java.by.chertok.pharmacy.command.impl.common.all.*;
import main.java.by.chertok.pharmacy.command.impl.common.user.*;
import main.java.by.chertok.pharmacy.command.impl.customer.*;
import main.java.by.chertok.pharmacy.command.impl.doctor.ApprovePrescriptionCommand;
import main.java.by.chertok.pharmacy.command.impl.doctor.DenyPrescriptionCommand;
import main.java.by.chertok.pharmacy.command.impl.doctor.ListPrescriptionCommand;
import main.java.by.chertok.pharmacy.command.impl.pharmacist.AddDrugCommand;
import main.java.by.chertok.pharmacy.command.impl.pharmacist.DeleteDrugCommand;
import main.java.by.chertok.pharmacy.command.impl.pharmacist.ToAlternateDrugPage;
import main.java.by.chertok.pharmacy.command.impl.pharmacist.UpdateDrugCommand;
import main.java.by.chertok.pharmacy.instance.ServiceInstance;

/**
 * Enumeration containing all possible commands with their implementations as an
 * appropriate field
 */
public enum CommandHolder {

    SEEK_DRUG(new SeekDrugsCommand(ServiceInstance.getInstance().getDrugService())),
    SIGN_UP(new SignUpCommand(ServiceInstance.getInstance().getUserService())),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    HOME_PAGE(new ToHomePageCommandImpl()),
    LOGIN(new LogInCommand(ServiceInstance.getInstance().getUserService())),

    LOGOUT(new LogOutCommand()),
    PROFILE(new ToProfileCommandImpl()),
    ALTERNATE_PROFILE(new ToProfileAlternating()),
    UPDATE_USER(new UpdateUserCommand(ServiceInstance.getInstance().getUserService())),
    DELETE_USER(new DeleteUserCommand(ServiceInstance.getInstance().getUserService())),

    ADD_DRUG(new AddDrugCommand(ServiceInstance.getInstance().getDrugService())),
    UPDATE_DRUG(new UpdateDrugCommand(ServiceInstance.getInstance().getDrugService())),
    TO_ALTERNATING_DRUG(new ToAlternateDrugPage()),
    DELETE_DRUG(new DeleteDrugCommand(ServiceInstance.getInstance().getDrugService())),

    LIST_ORDERS(new ListOrderCommand(ServiceInstance.getInstance().getOrderService())),
    APPROVE_ORDER(new ApproveOrderCommand(ServiceInstance.getInstance().getOrderService())),
    ADD_TO_CARD(new AddToCardCommand(ServiceInstance.getInstance().getDrugService(),
            ServiceInstance.getInstance().getPrescriptionService())),
    REMOVE_FROM_CART(new RemoveDrugFromCartCommand()),
    REQUEST_PRESCRIPTION(new RequestPrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService(),
            ServiceInstance.getInstance().getDrugService())),
    DENY_ORDER(new DenyOrderCommand(ServiceInstance.getInstance().getOrderService())),
    CURRENT_ORDER(new CurrentOrderCommand(ServiceInstance.getInstance().getDrugService())),

    APPROVE_PRESCRIPTION(new ApprovePrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService())),
    DENY_PRESCRIPTION(new DenyPrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService())),
    LIST_PRESCRIPTIONS(new ListPrescriptionCommand(ServiceInstance.getInstance().getPrescriptionService()));

    private ICommand command;

    CommandHolder(ICommand command) {
        this.command = command;
    }

    /**
     * Gives corresponding implementation of a specific command
     * @return a certain implementation of {@link ICommand ICommand} interface
     */
    public ICommand getCommand() {
        return command;
    }
}