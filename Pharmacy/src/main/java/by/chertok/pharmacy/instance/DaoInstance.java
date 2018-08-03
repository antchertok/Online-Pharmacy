package main.java.by.chertok.pharmacy.instance;

import main.java.by.chertok.pharmacy.dao.*;
import main.java.by.chertok.pharmacy.dao.impl.*;
import main.java.by.chertok.pharmacy.dao.util.JdbcHelper;
import main.java.by.chertok.pharmacy.pool.ConnectionPool;

/**
 * Auxiliary class providing access to all kinds of DAO
 */
public class DaoInstance {
    private UserDao userDao;
    private DrugDao drugDao;
    private OrderDao orderDao;
    private PrescriptionDao prescriptionDao;

    private DaoInstance(JdbcHelper jdbcHelper){
        userDao = new UserDaoImpl(jdbcHelper);
        drugDao = new DrugDaoImpl(jdbcHelper);
        orderDao = new OrderDaoImpl(jdbcHelper);
        prescriptionDao = new PrescriptionDaoImpl(jdbcHelper);
    }

    public static synchronized DaoInstance getInstance() {
        if (DaoInstanceHolder.INSTANCE == null) {
            initDaoInstance(new JdbcHelper(ConnectionPool.getInstance()));
        }
        return DaoInstanceHolder.INSTANCE;
    }

    public static synchronized void initDaoInstance(JdbcHelper jdbcHelper) {
        DaoInstanceHolder.INSTANCE = new DaoInstance(jdbcHelper);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public DrugDao getDrugDao() {
        return drugDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public PrescriptionDao getPrescriptionDao() {
        return prescriptionDao;
    }

    private static class DaoInstanceHolder {
        private static DaoInstance INSTANCE;
    }
}
