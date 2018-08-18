package by.chertok.pool;

import by.chertok.pharmacy.exception.ConnectionPoolException;
import by.chertok.pharmacy.pool.ConnectionPool;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import java.sql.Connection;

public class ConnectionPoolTest {
    @Before
    public void initConnectionPool() throws ConnectionPoolException {
        ConnectionPool.poolStart();
    }

    @After
    public void destroyConnectionPool(){
        ConnectionPool.getInstance().destroy();
    }

    @Test
    public void getConnectionPoolTest(){
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Assert.assertNotNull(connectionPool);
    }

    @Test
    public void getConnectionTest() throws ConnectionPoolException{
        Connection connection = ConnectionPool.getInstance().getConnection();
        Assert.assertNotNull(connection);
    }
}
