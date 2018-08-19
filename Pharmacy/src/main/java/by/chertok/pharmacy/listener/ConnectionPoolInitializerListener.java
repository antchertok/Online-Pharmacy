package by.chertok.pharmacy.listener;

import by.chertok.pharmacy.dao.util.JdbcHelper;
import by.chertok.pharmacy.exception.ConnectionPoolException;
import by.chertok.pharmacy.instance.DaoKeeper;
import by.chertok.pharmacy.instance.ServiceKeeper;
import by.chertok.pharmacy.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener for creating and initializing of {@link ConnectionPool connection pool}
 * and auxiliary classes {@link JdbcHelper JdbcHelper}
 */
@WebListener
public class ConnectionPoolInitializerListener implements ServletContextListener {
    private static final Logger LOGGER
            = Logger.getLogger(ConnectionPoolInitializerListener.class);
    private static final String POOL_INIT_ERROR
            = "Connection pool hasn't been initialized";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.poolStart();
        }catch (ConnectionPoolException e){
            LOGGER.error(POOL_INIT_ERROR, e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().destroy();
    }
}
