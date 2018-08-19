package by.chertok.pharmacy.pool;

import by.chertok.pharmacy.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static final String FILE_NAME = "db";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String URL_KEY = "db.url";
    private static final String DRIVER_KEY = "db.driver";
    private static final String POOL_SIZE_KEY = "db.poolSize";
    private static final String INITIALIZING_ERROR_MSG = "Connection pool hasn't been initialized";
    private static final String GET_CONN_ERROR_MSG = "Failed to get connection";

    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> givenConnections;

    private int poolSize;
    private String user;
    private String password;
    private String url;
    private String driverName;

    private ConnectionPool() {
        ResourceBundle bundle = ResourceBundle.getBundle(FILE_NAME, Locale.ENGLISH);
        user = bundle.getString(USER_KEY);
        password = bundle.getString(PASSWORD_KEY);
        url = bundle.getString(URL_KEY);
        driverName = bundle.getString(DRIVER_KEY);
        poolSize = Integer.parseInt(bundle.getString(POOL_SIZE_KEY));
    }

    /**
     * Launches connection pool initialization
     */
    public static void poolStart() throws ConnectionPoolException {
        getInstance().initPool();
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    public Connection getConnection() throws ConnectionPoolException {
        if(availableConnections == null || givenConnections == null){
            throw new ConnectionPoolException(INITIALIZING_ERROR_MSG);
        }
        try {
            ProxyConnection connection = availableConnections.take();
            givenConnections.offer(connection);
            return connection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(GET_CONN_ERROR_MSG, e);
        }
    }

    /**
     * Returns connection into a blocking queue
     *
     * @param connection connection for return
     */
    public void closeConnection(ProxyConnection connection) {
        availableConnections.offer(connection);
        givenConnections.remove(connection);
    }

    /**
     * Closes all current availableConnections
     */
    public void destroy() {
        try {
            for (ProxyConnection connection : availableConnections) {
                connection.reallyClose();
            }
            for (ProxyConnection connection : givenConnections) {
                connection.reallyClose();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    private void initPool() throws ConnectionPoolException {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName(driverName);
            availableConnections = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                ProxyConnection proxyConnection = new ProxyConnection(DriverManager.getConnection(url, user, password));
                availableConnections.offer(proxyConnection);
            }

            givenConnections = new ArrayBlockingQueue<>(poolSize);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e);
            throw new ConnectionPoolException(INITIALIZING_ERROR_MSG, e);
        }

    }

    private static class ConnectionPoolHolder {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }
}