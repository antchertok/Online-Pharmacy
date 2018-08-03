package main.java.by.chertok.pharmacy.service.impl;

import main.java.by.chertok.pharmacy.dao.UserDao;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.UserService;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final String ERR_MSG = "Operation failed";
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> readByLoginPassword(String userLogin, String userPassword) throws ServiceException {
        try{
            return userDao.readByLoginPassword(userLogin, userPassword);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Optional.empty();
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public List<User> readAll() throws ServiceException{
        try{
            return userDao.readAll();
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public Optional<User> readById(long id) throws ServiceException{
        try{
            return userDao.readById(id);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Optional.empty();
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean create(User entity) throws ServiceException{
        try{
            return userDao.create(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean update(User entity) throws ServiceException{
        try{
            return userDao.update(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException{
        try{
            return userDao.delete(id) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

}
