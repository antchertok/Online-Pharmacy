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

import java.time.LocalDateTime;

public class ApproveOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ApproveOrderCommand.class);
    private static final String SUCCESS = "Success";
    private static final String FAIL = "Failed to make order";
    private OrderService orderService;

    public ApproveOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * If successful, adds formed order to util with current time, replacing
     * it with a new empty order
     *
     * @param wrapper an object containing attributes and parameters from request
     *                and session
     * @return an object that contains url and option whether to send redirect or
     * to go forward
     */
    @Override
    public Path execute(Wrapper wrapper) {
        try {
            Order order = (Order) wrapper.getSessionAttribute(AttributeName.ORDER);
            order.setOrderDate(LocalDateTime.now());
            Path path = new Path();
            path.setUrl(wrapper.getRequestParameter(AttributeName.CURRENT_URL));

            if (orderService.create(order)) {
                wrapper.setRequestAttribute(AttributeName.INFO_ORDER, SUCCESS);
                order = new Order(0);
                order.setCustomerId(((User) wrapper.getSessionAttribute(AttributeName.USER)).getId());
                wrapper.setSessionAttribute(AttributeName.ORDER, order);
                wrapper.setSessionAttribute(AttributeName.DRUGS_ORDERED, 0);
                wrapper.setSessionAttribute(AttributeName.TOTAL, 0.0);
                path.setForward(false);
                return path;
            } else {
                wrapper.setRequestAttribute(AttributeName.INFO_ORDER, FAIL);
                path.setForward(true);
                return path;
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute(AttributeName.ERROR_MSG, e.getMessage());
            return new Path(false, PageStorage.ERROR);
        }
    }
}
