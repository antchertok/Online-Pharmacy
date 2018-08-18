package by.chertok.pharmacy.instance;

import by.chertok.pharmacy.dao.*;
import by.chertok.pharmacy.dao.impl.*;
import by.chertok.pharmacy.dao.util.JdbcHelper;
import by.chertok.pharmacy.pool.ConnectionPool;

/**
 * Auxiliary class providing access to all kinds of DAO
 */
public class DaoKeeper {
    private UserDao userDao;
    private DrugDao drugDao;
    private OrderDao orderDao;
    private PrescriptionDao prescriptionDao;

    private DaoKeeper(JdbcHelper jdbcHelper) {
        userDao = new UserDaoImpl(jdbcHelper);
        drugDao = new DrugDaoImpl(jdbcHelper);
        orderDao = new OrderDaoImpl(jdbcHelper);
        prescriptionDao = new PrescriptionDaoImpl(jdbcHelper);
    }

    public static DaoKeeper getInstance() {
        return DaoInstanceHolder.INSTANCE;
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
        private static final DaoKeeper INSTANCE = new DaoKeeper(new JdbcHelper(ConnectionPool.getInstance()));
    }
}