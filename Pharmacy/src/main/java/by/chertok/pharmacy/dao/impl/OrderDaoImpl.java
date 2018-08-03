package main.java.by.chertok.pharmacy.dao.impl;

import main.java.by.chertok.pharmacy.dao.OrderDao;
import main.java.by.chertok.pharmacy.dao.util.JdbcHelper;
import main.java.by.chertok.pharmacy.dao.util.RowMapper;
import main.java.by.chertok.pharmacy.entity.Order;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private JdbcHelper jdbcHelper;
    private RowMapper<Order> rowMapper;
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);
    private static long currentId;
    private static Order currentOrder;
    private static final String READ_ALL = "SELECT order_id, order_date, pharmacist_id, customer_id, drug_id, amount FROM order LEFT JOIN order_m2m_drug USING (order_id) ORDER BY order_id";
    private static final String READ_BY_ORDER_ID = "SELECT order_id, order_date, pharmacist_id, customer_id, drug_id, amount FROM order_m2m_drug LEFT JOIN order USING (order_id) WHERE order_id = ?";
    private static final String INSERT = "INSERT INTO mydb.order (order_date, pharmacist_id, customer_id) VALUES (?,?,?)";
    private static final String DELETE = "DELETE FROM order_m2m_drug LEFT JOIN order USING (order_id) WHERE order_id = ?";
    private static final String READ_BY_USER_ID = "SELECT order_id, order_date, pharmacist_id, customer_id, drug_id, amount FROM order_m2m_drug LEFT JOIN mydb.order USING (order_id) WHERE customer_id = ? ORDER BY order_id";
    private static final String ADD_DRUG_TO_ORDER = "INSERT INTO order_m2m_drug (order_id, drug_id, amount)  VALUES ((SELECT MAX(order_id) FROM mydb.order),?, ?)";

    public OrderDaoImpl(JdbcHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
        rowMapper = resultSet -> {

            if (resultSet.getLong("order_id") != currentId) {
                currentId = resultSet.getLong("order_id");
                currentOrder = new Order(resultSet.getLong("order_id"));
//                currentOrder.setOrderDate(LocalDateTime.ofInstant(resultSet.getDate("order_date").toInstant(), ZoneId.systemDefault()));
                currentOrder.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
                currentOrder.setPharmacistId(resultSet.getLong("pharmacist_id"));
                currentOrder.setCustomerId(resultSet.getLong("customer_id"));
                currentOrder.addDrug(resultSet.getLong("drug_id"),
                        resultSet.getInt("amount"));
            } else {
//                if (currentOrder == null) {
//                    currentOrder = new Order(resultSet.getLong("order_id"));
//                }
                currentOrder.addDrug(resultSet.getLong("drug_id"),
                        resultSet.getInt("amount"));
            }
            return currentOrder;
        };
    }

    @Override
    public List<Order> readAll() throws DaoException {
        try {
            return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Order> readById(long id) throws DaoException {
        try {
            return Optional.of((Order) jdbcHelper.queryForObject(READ_BY_ORDER_ID, new Object[]{id}, rowMapper));
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int create(Order entity) throws DaoException {//TODO REMAKE
        Map<Long, Integer> drugs = entity.getDrugs();
        String[] sqlRequests = new String[drugs.size() + 1];
        Object[][] parameters = new Object[sqlRequests.length][];
        sqlRequests[0] = INSERT;
        parameters[0] = new Object[]{entity.getOrderDate(), entity.getPharmacistId(), entity.getCustomerId()};

        int i;
        for (i = 1; i < sqlRequests.length; i++) {
            sqlRequests[i] = ADD_DRUG_TO_ORDER;
        }

        i = 1;
        for (Long drugId : drugs.keySet()) {
            parameters[i++] = new Object[]{drugId, drugs.get(drugId)};
        }

        try{
            return jdbcHelper.executeTransaction(sqlRequests,parameters);
        } catch (SQLException e){
            LOGGER.error(e);
            throw new DaoException("Failed to release resources", e);
        }
    }

    @Override
    public int update(Order entity) {
        throw new UnsupportedOperationException();//TODO: CAN I DO THAT?
//        String updateQuery = "";
//        return jdbcHelper.executeUpdate(updateQuery, new Object[]{}) > 0;
    }

    @Override
    public int delete(long id) throws DaoException {
        return jdbcHelper.executeUpdate(DELETE, new Object[]{id});
    }

    @Override
    public List<Order> readByUserId(long userId) throws DaoException {
        try {
            return jdbcHelper.queryForList(READ_BY_USER_ID, new Object[]{userId}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    private boolean addDrug(long drugId, int amount) throws DaoException {
        return jdbcHelper.executeUpdate(ADD_DRUG_TO_ORDER, new Object[]{drugId, amount}) > 0;
    }
}
