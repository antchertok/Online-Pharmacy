package by.chertok.pharmacy.service;

import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.exception.ServiceException;

import java.util.List;

public interface OrderService extends Service<Order> {
    /**
     * Returns all orders made by a certain user
     *
     * @param userId id of user whose orders are to be found
     * @return list filled with all orders made by this account
     */
    List<Order> readByUserId(long userId) throws ServiceException;
}
