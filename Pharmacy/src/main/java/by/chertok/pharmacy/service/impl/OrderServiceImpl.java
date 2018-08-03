package main.java.by.chertok.pharmacy.service.impl;


import main.java.by.chertok.pharmacy.dao.OrderDao;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.OrderService;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = Logger.getLogger(OrderServiceImpl.class);
    private static final String ERR_MSG = "Operation failed";
    private OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> readAll() throws ServiceException {
        try{
            return orderDao.readAll();
        } catch(DaoException e){//TODO запилить processDaoException(logger, throwable)
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public Optional<Order> readById(long id) throws ServiceException{
        try{
            return orderDao.readById(id);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Optional.empty();
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean create(Order entity) throws ServiceException{
        try {
            return orderDao.create(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean update(Order entity) throws ServiceException{
        try{
            return orderDao.update(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException{
        try{
            return orderDao.delete(id) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public List<Order> readByUserId(long userId) throws ServiceException{
        try{
            return orderDao.readByUserId(userId);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }
}
