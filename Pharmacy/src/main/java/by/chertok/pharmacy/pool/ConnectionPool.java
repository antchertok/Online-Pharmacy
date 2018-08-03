package main.java.by.chertok.pharmacy.pool;

import main.java.by.chertok.pharmacy.exception.ConnectionPoolException;
import main.java.by.chertok.pharmacy.manager.ResourceManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static final int DEFAULT_POOL_SIZE = 5;
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private BlockingQueue<ProxyConnection> connections;

    private int poolSize;
    private String user;
    private String password;
    private String url;
    private String driverName;

    private ConnectionPool() throws ConnectionPoolException {
        ResourceManager manager = ResourceManager.getInstance();
        user = manager.getValue("db.user");
        password = manager.getValue("db.password");
        url = manager.getValue("db.url");
        driverName = manager.getValue("db.driver");
        try {
            poolSize = Integer.parseInt(manager.getValue("db.poolSize"));
        } catch (NumberFormatException e) {
            LOGGER.error("Failed to parse pool size. Default value was sat", e);
            poolSize = DEFAULT_POOL_SIZE;
        }
        try {
            initPool(poolSize);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error(e);
            throw new ConnectionPoolException("Failed to initialize connection pool", e);
        };
    }

    /**
     * Launches connection pool initialization
     */
    public static void poolStart() throws ConnectionPoolException{
        ConnectionPoolHolder.INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return ConnectionPoolHolder.INSTANCE;
    }

    public Connection getConnection() throws InterruptedException {
        return connections.take();
    }

    /**
     * Closes all current connections
     */
    public void destroy() {
        try {
            for (ProxyConnection connection : connections) {
                connection.closeConnection();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to close connection", e);
        }
    }

    private void initPool(int poolSize) throws ClassNotFoundException, SQLException {
        Locale.setDefault(Locale.ENGLISH);
        Class.forName(driverName);

        connections = new ArrayBlockingQueue<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            ProxyConnection proxyConnection = new ProxyConnection(DriverManager.getConnection(url, user, password));
            connections.offer(proxyConnection);
        }
    }

    /**
     * Returns connection into a blocking queue
     *
     * @param connection connection for return
     */
    public void closeConnection(ProxyConnection connection){
        connections.offer(connection);
    }

    private static class ConnectionPoolHolder {
        public static ConnectionPool INSTANCE;
    }
}