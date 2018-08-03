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

import java.time.LocalDateTime;

public class ApproveOrderCommand implements ICommand {
    private static final Logger LOGGER = Logger.getLogger(ApproveOrderCommand.class);
    private OrderService orderService;

    public ApproveOrderCommand(OrderService orderService){
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
        try{
            Order order = (Order)wrapper.getSessionAttribute("order");
            order.setOrderDate(LocalDateTime.now());
            Path path = new Path();
            path.setUrl(Pages.PROFILE);

            if(orderService.create(order)){
                wrapper.setRequestAttribute("infoOrder", "Success");
                order = new Order(0);
                order.setCustomerId(((User)wrapper.getSessionAttribute("user")).getId());
                wrapper.setSessionAttribute("order", order);
                wrapper.setSessionAttribute("drugsOrdered", 0);
                wrapper.setSessionAttribute("total", 0.0);
                path.setForward(false);
                return path;
            } else {
                wrapper.setRequestAttribute("infoOrder", "Failure");
                path.setForward(true);
                return path;
            }
        } catch(ServiceException e){
            LOGGER.error(e.getMessage());
            wrapper.setSessionAttribute("errMsg", e.getMessage());
            return new Path(false, Pages.ERROR);
        }
    }
}
