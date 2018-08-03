package main.java.by.chertok.pharmacy.listener;

import main.java.by.chertok.pharmacy.dao.util.JdbcHelper;
import main.java.by.chertok.pharmacy.exception.ConnectionPoolException;
import main.java.by.chertok.pharmacy.instance.DaoInstance;
import main.java.by.chertok.pharmacy.instance.ServiceInstance;
import main.java.by.chertok.pharmacy.pool.ConnectionPool;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener for creating and initializing of {@link ConnectionPool connection pool}
 * and auxiliary classes {@link JdbcHelper JdbcHelper}, {@link DaoInstance
 * DaoInstance} and {@link ServiceInstance ServiceInstance}
 */
@WebListener
public class ConnectionPoolInitializerListener implements ServletContextListener {
    private static final Logger LOGGER
            = Logger.getLogger(ConnectionPoolInitializerListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            ConnectionPool.poolStart();
            JdbcHelper jdbcHelper = new JdbcHelper(ConnectionPool.getInstance());
            DaoInstance.initDaoInstance(jdbcHelper);
            ServiceInstance.initServiceInstance(DaoInstance.getInstance());
        }catch (ConnectionPoolException e){
            LOGGER.error("Connection pool hasn't been initialized");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool.getInstance().destroy();
    }
}
