package by.chertok.pharmacy.command.impl.pharmacist;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToAlternateDrugPage implements ICommand {

    /**
     * Sends to drugs alternating page to update drug
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        wrapper.setRequestAttribute(AttributeName.DRUG_ID, wrapper.getRequestParameter(AttributeName.DRUG_ID));
        wrapper.setRequestAttribute(AttributeName.NAME, wrapper.getRequestParameter(AttributeName.NAME));
        wrapper.setRequestAttribute(AttributeName.PRICE, wrapper.getRequestParameter(AttributeName.PRICE));
        wrapper.setRequestAttribute(AttributeName.PRESCRIPTION, wrapper.getRequestParameter(AttributeName.PRESCRIPTION));
        wrapper.setRequestAttribute(AttributeName.DOSE, wrapper.getRequestParameter(AttributeName.DOSE));
        wrapper.setRequestAttribute(AttributeName.ALTERNATING, true);
        return new Path(true, PageStorage.ALTERNATING_DRUGS);
    }
}
