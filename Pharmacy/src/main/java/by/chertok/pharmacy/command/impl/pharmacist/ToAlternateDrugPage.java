package main.java.by.chertok.pharmacy.command.impl.pharmacist;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class ToAlternateDrugPage implements ICommand {

    @Override
    public Path execute(Wrapper wrapper) {
        wrapper.setRequestAttribute("drugId", wrapper.getRequestParameter("drugId"));
        wrapper.setRequestAttribute("name", wrapper.getRequestParameter("name"));
        wrapper.setRequestAttribute("price", wrapper.getRequestParameter("price"));
        wrapper.setRequestAttribute("prescription", wrapper.getRequestParameter("prescription"));
        wrapper.setRequestAttribute("dose", wrapper.getRequestParameter("dose"));
        wrapper.setRequestAttribute("alternating", "true");
        return new Path(true, Pages.ALTERNATING_DRUGS);
    }
}
