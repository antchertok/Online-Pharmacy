package by.chertok.pharmacy.command.impl.customer;

import by.chertok.pharmacy.command.resources.AttributeName;
import by.chertok.pharmacy.command.ICommand;
import by.chertok.pharmacy.command.resources.PageStorage;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.OrderService;
import by.chertok.pharmacy.util.path.Path;
import by.chertok.pharmacy.util.wrapper.Wrapper;
import org.apache.log4j.Logger;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ListOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ListOrderCommand.class);
    private static final String FAIL = "Nothing was found";
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
            long userId = ((User) wrapper.getSessionAttribute(AttributeName.USER)).getId();
            Set<Order> orders = new LinkedHashSet<>();
            orders.addAll(orderService.readByUserId(userId));

            if (!orders.isEmpty()) {
                wrapper.setRequestAttribute(AttributeName.ORDERS, orders);
            } else {
                wrapper.setRequestAttribute(AttributeName.NO_ORDER_MSG, FAIL);
            }
            return new Path(true, PageStorage.PROFILE);
        } catch(ServiceException e){
            LOGGER.error(e);
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
