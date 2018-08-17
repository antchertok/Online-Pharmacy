package by.chertok.pharmacy.dao;

import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.exception.DaoException;

import java.util.List;
import java.util.Map;

public interface OrderDao extends Dao<Order> {
    /**
     * Returns all orders made by a certain user
     *
     * @param userId id of user whose orders are to be found
     * @return list filled with all orders made by this account
     */
    List<Order> readByUserId(long userId) throws DaoException;
}
