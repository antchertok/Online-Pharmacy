package by.chertok.pharmacy.dao.impl;

import by.chertok.pharmacy.dao.OrderDao;
import by.chertok.pharmacy.dao.util.JdbcHelper;
import by.chertok.pharmacy.dao.util.RowMapper;
import by.chertok.pharmacy.entity.Order;
import by.chertok.pharmacy.exception.ConnectionPoolException;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {

    private JdbcHelper jdbcHelper;
    private RowMapper<Order> rowMapper;
    private static final Logger LOGGER = Logger.getLogger(OrderDaoImpl.class);
    private static long currentId;
    private static Order currentOrder;
    private static final String READ_ALL = "SELECT order_id, order_date, customer_id, total, drug_id, amount FROM order LEFT JOIN order_m2m_drug USING (order_id) ORDER BY order_id";
    private static final String READ_BY_ORDER_ID = "SELECT order_id, order_date, customer_id, total, drug_id, amount FROM order_m2m_drug LEFT JOIN order USING (order_id) WHERE order_id = ?";
    private static final String DELETE = "DELETE FROM order_m2m_drug LEFT JOIN order USING (order_id) WHERE order_id = ?";
    private static final String READ_BY_USER_ID = "SELECT order_id, order_date, customer_id, total, drug_id, amount FROM order_m2m_drug LEFT JOIN mydb.order USING (order_id) WHERE customer_id = ? ORDER BY order_id";
    private static final String INSERT = "INSERT INTO mydb.order (order_date, customer_id, total) VALUES (?,?,?)";
    private static final String ADD_DRUG_TO_ORDER = "INSERT INTO order_m2m_drug (order_id, drug_id, amount)  VALUES ((SELECT MAX(order_id) FROM mydb.order),?, ?)";
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String TRANSACTION_FAILED_MSG = "Transaction wasn't finished";
    private static final String FAILED_TO_ROLL_BACK_MSG = "Failed to roll back transaction";

    public OrderDaoImpl(JdbcHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;

        rowMapper = resultSet -> {
            if (resultSet.getLong("order_id") != currentId) {
                currentId = resultSet.getLong("order_id");
                currentOrder = new Order(resultSet.getLong("order_id"));
                currentOrder.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
                currentOrder.setCustomerId(resultSet.getLong("customer_id"));
                currentOrder.setTotal(resultSet.getDouble("total"));
                currentOrder.addDrug(resultSet.getLong("drug_id"),
                        resultSet.getInt("amount"));
            } else {
                currentOrder.addDrug(resultSet.getLong("drug_id"),
                        resultSet.getInt("amount"));
            }
            return currentOrder;
        };
    }

    @Override
    public List<Order> readAll() throws DaoException {
        return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
    }

    @Override
    public Optional<Order> readById(long id) throws DaoException {
        return jdbcHelper.queryForObject(READ_BY_ORDER_ID, new Object[]{id}, rowMapper);
    }

    @Override
    public int create(Order entity) throws DaoException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            statement = connection.prepareStatement(INSERT);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
            String formattedString = entity.getOrderDate().format(formatter);
            statement.setString(1, formattedString);
            statement.setLong(2, entity.getCustomerId());
            statement.setDouble(3, entity.getTotal());
            statement.executeUpdate();

            statement = connection.prepareStatement(ADD_DRUG_TO_ORDER);
            Map<Long, Integer> drugs = entity.getDrugs();

            for (Long drugId : drugs.keySet()) {
                statement.setLong(1, drugId);
                statement.setInt(2, drugs.get(drugId));
                statement.addBatch();
            }

            int operations = statement.executeBatch().length;

            connection.commit();
            return operations;
        } catch (SQLException | ConnectionPoolException e) {
            LOGGER.error(e);
            if (connection != null) {
                rollback(connection);
            }
            throw new DaoException(TRANSACTION_FAILED_MSG);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    @Override
    public int update(Order entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int delete(long id) throws DaoException {
        return jdbcHelper.executeUpdate(DELETE, new Object[]{id});
    }

    @Override
    public List<Order> readByUserId(long userId) throws DaoException {
        return jdbcHelper.queryForList(READ_BY_USER_ID, new Object[]{userId}, rowMapper);
    }

    private void rollback(Connection connection) throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(FAILED_TO_ROLL_BACK_MSG, e);
        }
    }
}
