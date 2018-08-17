package by.chertok.pharmacy.pool;

import by.chertok.pharmacy.exception.ConnectionPoolException;
import by.chertok.pharmacy.manager.DatabaseResourceManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static volatile ConnectionPool instance;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String URL_KEY = "db.url";
    private static final String DRIVER_KEY = "db.driver";
    private static final String POOL_SIZE_KEY = "db.poolSize";
    private static final String INITIALIZING_ERROR_MSG = "Failed to initialize connection pool";

    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> givenConnections;

    private int poolSize;
    private String user;
    private String password;
    private String url;
    private String driverName;

    private ConnectionPool() throws ConnectionPoolException {
        DatabaseResourceManager manager = DatabaseResourceManager.getInstance();
        user = manager.getValue(USER_KEY);
        password = manager.getValue(PASSWORD_KEY);
        url = manager.getValue(URL_KEY);
        driverName = manager.getValue(DRIVER_KEY);
        poolSize = Integer.parseInt(manager.getValue(POOL_SIZE_KEY));

        try {
            initPool();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error(e);
            throw new ConnectionPoolException(INITIALIZING_ERROR_MSG, e);
        }
    }

    /**
     * Launches connection pool initialization
     */
    public static void poolStart() throws ConnectionPoolException {
        instance = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public Connection getConnection() throws InterruptedException {
        ProxyConnection connection = availableConnections.take();
        givenConnections.offer(connection);
        return connection;
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
                connection.closeConnection();
            }
            for (ProxyConnection connection : givenConnections) {
                connection.closeConnection();
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    private void initPool() throws ClassNotFoundException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
        Class.forName(driverName);

        availableConnections = new ArrayBlockingQueue<>(poolSize);
        givenConnections = new ArrayBlockingQueue<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            ProxyConnection proxyConnection = new ProxyConnection(DriverManager.getConnection(url, user, password));
            availableConnections.offer(proxyConnection);
        }
    }
}