package main.java.by.chertok.pharmacy.command.impl.customer;//package main.java.by.anton.apteka.command.impl.oldimpl;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.service.OrderService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;

public class DenyOrderCommand implements ICommand {
    private OrderService orderService;

    public DenyOrderCommand(OrderService orderService){
        this.orderService = orderService;
    }

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
        order.setCustomerId(((User)wrapper.getSessionAttribute("user")).getId());
        wrapper.setSessionAttribute("order", order);
        wrapper.setSessionAttribute("drugsOrdered", 0);
        wrapper.setSessionAttribute("total", 0.0);

        return new Path(false, Pages.PROFILE);
    }
}

