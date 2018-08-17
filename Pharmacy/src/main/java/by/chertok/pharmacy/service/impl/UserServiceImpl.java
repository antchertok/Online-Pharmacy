package by.chertok.pharmacy.service.impl;

import by.chertok.pharmacy.dao.UserDao;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.UserService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    private static final String GETTING_USER_ERR_MSG = "Failed to get user";
    private static final String CREATING_USER_ERR_MSG = "Failed to create new account";
    private static final String UPDATING_USER_ERR_MSG = "Failed to update account";
    private static final String DELETING_USER_ERR_MSG = "Failed to delete account";
    private static final String GETTING_DOCTOR_ERR_MSG = "Failed to get doctor";
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<User> readByLoginPassword(String userLogin, String userPassword)
            throws ServiceException {
        try {
            return userDao.readByLoginPassword(userLogin, userPassword);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_USER_ERR_MSG, e);
        }
    }

    @Override
    public List<User> readAll() throws ServiceException {
        try {
            return userDao.readAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_USER_ERR_MSG, e);
        }
    }

    @Override
    public Optional<User> readById(long id) throws ServiceException {
        try {
            return userDao.readById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_USER_ERR_MSG, e);
        }
    }

    @Override
    public boolean create(User entity) throws ServiceException {
        try {
            return userDao.create(entity) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(CREATING_USER_ERR_MSG, e);
        }
    }

    @Override
    public boolean update(User entity) throws ServiceException {
        try {
            return userDao.update(entity) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(UPDATING_USER_ERR_MSG, e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return userDao.delete(id) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(DELETING_USER_ERR_MSG, e);
        }
    }

    @Override
    public Optional<User> getDocByName(String firstName, String lastName)
            throws ServiceException {
        try {
            return userDao.getDocByName(firstName, lastName);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_DOCTOR_ERR_MSG, e);
        }
    }
}
