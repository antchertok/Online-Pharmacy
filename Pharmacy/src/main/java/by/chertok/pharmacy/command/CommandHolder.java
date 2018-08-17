package by.chertok.pharmacy.command;

import by.chertok.pharmacy.command.impl.common.all.*;
import by.chertok.pharmacy.command.impl.common.user.*;
import by.chertok.pharmacy.command.impl.customer.*;
import by.chertok.pharmacy.command.impl.doctor.*;
import by.chertok.pharmacy.command.impl.pharmacist.*;
import by.chertok.pharmacy.instance.ServiceKeeper;
import by.chertok.pharmacy.util.encoder.impl.EncoderMd5;

/**
 * Enumeration containing all possible commands with their implementations as an
 * appropriate field
 */
public enum CommandHolder {

    //all
    SEEK_DRUG(new SeekDrugsCommand(ServiceKeeper.getInstance().getDrugService())),
    SIGN_UP(new SignUpCommand(ServiceKeeper.getInstance().getUserService(), new EncoderMd5())),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    HOME_PAGE(new ToHomePageCommand()),
    LOGIN(new LogInCommand(ServiceKeeper.getInstance().getUserService(), new EncoderMd5())),

    //users only
    LOGOUT(new LogOutCommand()),
    PROFILE(new ToProfileCommand()),
    ALTERNATE_PROFILE(new ToProfileAlternating()),
    TO_SETTING_DOCTOR(new ToSettingDoctorCommand()),
    SET_DOCTOR(new SetDoctorCommand(ServiceKeeper.getInstance().getUserService())),
    UPDATE_USER(new UpdateUserCommand(ServiceKeeper.getInstance().getUserService())),
    DELETE_USER(new DeleteUserCommand(ServiceKeeper.getInstance().getUserService())),

    LIST_ORDERS(new ListOrderCommand(ServiceKeeper.getInstance().getOrderService())),
    APPROVE_ORDER(new ApproveOrderCommand(ServiceKeeper.getInstance().getOrderService())),
    ADD_TO_CARD(new AddToCardCommand(ServiceKeeper.getInstance().getDrugService(),
            ServiceKeeper.getInstance().getPrescriptionService())),
    REMOVE_FROM_CART(new RemoveDrugFromCartCommand()),
    REQUEST_PRESCRIPTION(new RequestPrescriptionCommand(ServiceKeeper.getInstance().getPrescriptionService())),
    DENY_ORDER(new DenyOrderCommand()),
    CURRENT_ORDER(new CurrentOrderCommand(ServiceKeeper.getInstance().getDrugService())),

    //pharmacists only
    ADD_DRUG(new AddDrugCommand(ServiceKeeper.getInstance().getDrugService())),
    UPDATE_DRUG(new UpdateDrugCommand(ServiceKeeper.getInstance().getDrugService())),
    TO_ALTERNATING_DRUG(new ToAlternateDrugPage()),
    TO_ADD_DRUG(new ToAddDrugPage()),
    DELETE_DRUG(new DeleteDrugCommand(ServiceKeeper.getInstance().getDrugService())),

    //doctors only
    APPROVE_PRESCRIPTION(new ApprovePrescriptionCommand(ServiceKeeper.getInstance().getPrescriptionService())),
    DENY_PRESCRIPTION(new DenyPrescriptionCommand(ServiceKeeper.getInstance().getPrescriptionService())),
    LIST_PRESCRIPTIONS(new ListPrescriptionCommand(ServiceKeeper.getInstance().getPrescriptionService()));

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