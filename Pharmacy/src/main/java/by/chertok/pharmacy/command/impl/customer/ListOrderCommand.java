package main.java.by.chertok.pharmacy.command.impl.customer;

import main.java.by.chertok.pharmacy.command.ICommand;
import main.java.by.chertok.pharmacy.command.Pages;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.OrderService;
import main.java.by.chertok.pharmacy.util.road.Path;
import main.java.by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.List;

public class ListOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ListOrderCommand.class);
    private OrderService orderService;

    public ListOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * If successful, sets the list of orders made by the current user
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try{
            long userId = ((User) wrapper.getSessionAttribute("user")).getId();
            List<Order> orders = orderService.readByUserId(userId);

            if (!orders.isEmpty()) {
//            request.setAttribute("orders", orders);
                wrapper.setRequestAttribute("orders", orders);
            } else {
                wrapper.setRequestAttribute("errorMsg", "Nothing was found");
            }
            return new Path(true, Pages.PROFILE);
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
