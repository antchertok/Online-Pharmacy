package by.chertok.pharmacy.service.impl;

import by.chertok.pharmacy.dao.OrderDao;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.OrderService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
    private static final String GETTING_ORDER_ERR_MSG = "Failed to get order";
    private static final String CREATING_ORDER_ERR_MSG = "Failed to make order";
    private static final String UPDATING_ORDER_ERR_MSG = "Failed to update order";
    private static final String DELETING_ORDER_ERR_MSG = "Failed to delete order";
    private static final String COUNTING_DRUGS_ERR_MSG = "Failed to count records";
    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> readAll() throws ServiceException {
        try{
            return orderDao.readAll();
        } catch(DaoException e){
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_ORDER_ERR_MSG, e);
        }
    }

    @Override
    public Optional<Order> readById(long id) throws ServiceException{
        try{
            return orderDao.readById(id);
        } catch(DaoException e){
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_ORDER_ERR_MSG, e);
        }
    }

    @Override
    public boolean create(Order entity) throws ServiceException{
        try {
            return orderDao.create(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(CREATING_ORDER_ERR_MSG, e);
        }
    }

    @Override
    public boolean update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) throws ServiceException{
        try{
            return orderDao.delete(id) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(DELETING_ORDER_ERR_MSG, e);
        }
    }

    @Override
    public List<Order> readByUserId(long userId) throws ServiceException{
        try{
            return orderDao.readByUserId(userId);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(GETTING_ORDER_ERR_MSG, e);
        }
    }
}
