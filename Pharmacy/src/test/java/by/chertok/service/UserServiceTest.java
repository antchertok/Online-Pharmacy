package by.chertok.service;

import by.chertok.pharmacy.dao.impl.UserDaoImpl;
import by.chertok.pharmacy.dao.util.JdbcHelper;
import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.ConnectionPoolException;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.pool.ConnectionPool;
import by.chertok.pharmacy.service.impl.UserServiceImpl;
import by.chertok.pharmacy.util.encoder.Encoder;
import by.chertok.pharmacy.util.encoder.impl.EncoderMd5;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.util.Optional;

public class UserServiceTest {
    private UserServiceImpl userService;
    private Encoder encoder;

    @Before
    public void initUserService() throws ConnectionPoolException {
        ConnectionPool.poolStart();
        userService = new UserServiceImpl(new UserDaoImpl(new JdbcHelper(ConnectionPool.getInstance())));
        encoder = new EncoderMd5();
    }

    @After
    public void cleanUserService() {
        ConnectionPool.getInstance().destroy();
        userService = null;
        encoder = null;
    }

    @Test
    public void checkLoginAndPassword() throws ServiceException {
        Optional<User> user = userService.readByLoginPassword("Second", encoder.encode("qwerty2"));
        Assert.assertTrue(user.isPresent());
    }
}
