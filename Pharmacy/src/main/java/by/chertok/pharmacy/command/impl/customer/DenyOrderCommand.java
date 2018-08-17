package by.chertok.pharmacy.command.impl.customer;//package main.java.by.anton.apteka.command.impl.oldimpl;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;

public class DenyOrderCommand implements ICommand {
    /**
     * If successful, deletes current order from session, replacing it with a
     * new empty order
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        Order order = new Order(0);
        order.setCustomerId(((User) wrapper.getSessionAttribute(AttributeName.USER)).getId());
        wrapper.setSessionAttribute(AttributeName.ORDER, order);
        wrapper.setSessionAttribute(AttributeName.DRUGS_ORDERED, 0);
        wrapper.setSessionAttribute(AttributeName.TOTAL, 0.0);

        return new Path(false, wrapper.getRequestParameter(AttributeName.CURRENT_URL));
    }
}

