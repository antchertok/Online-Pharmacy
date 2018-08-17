package by.chertok.pharmacy.dao.util;

import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.EmptyResultException;
import by.chertok.pharmacy.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Class allowing to unify handling util requests
 */
public class JdbcHelper {

    private ConnectionPool connectionPool;
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String EMPTY_RESULT_MSG = "No data matches in database...";
    private static final String FAILED_QUERY_MSG = "Failed to execute query";
    private static final String FAILED_TO_LOAD_MSG = "Failed to load objects from result set";
    private static final String FAILED_TO_ROLL_BACK_MSG = "Failed to roll back";
    private static final String FAILED_FINISH_TRANSACTION_MSG = "Transaction wasn't finished";
    private static final String INVALID_AMOUNT_OF_PARAMETERS_MSG = "Invalid amount of arguments";

    public JdbcHelper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Method for making UPDATE, DELETE and INSERT requests to util
     *
     * @param sqlRequest string containing request to util without parameters
     *                   taken from gin entity
     * @param parameters array of parameters with a certain order for insertion
     *                   them into the request
     * @return the number of affected rows in the util after successful
     * execution of request
     * @throws DaoException if failed to execute any stage
     */
    public int executeUpdate(String sqlRequest, Object[] parameters) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            return buildStatement(statement, parameters).executeUpdate();
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(FAILED_QUERY_MSG, e);
        }
    }

    /**
     * Method for making UPDATE, DELETE and INSERT requests to util
     *
     * @param sqlRequests array of strings describing sql requests
     * @param parameters  array of parameters to insert into sql request
     * @return the number of executed updates
     * @throws DaoException if failed to execute any stage
     * @throws SQLException if failed to close connection or statement
     */
    public int executeMassiveUpdate(String[] sqlRequests, Object[][] parameters) throws DaoException, SQLException {
        if (sqlRequests.length != parameters.length) {
            throw new DaoException(INVALID_AMOUNT_OF_PARAMETERS_MSG);
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            int i = 0;
            for (; i < sqlRequests.length; i++) {
                statement = connection.prepareStatement(sqlRequests[i]);
                buildStatement(statement, parameters[i]).executeUpdate();
            }
            connection.commit();

            return i;
        } catch (InterruptedException | SQLException e) {
            if (connection != null) {
                rollback(connection);
            }
            throw new DaoException(FAILED_FINISH_TRANSACTION_MSG, e);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * Method for making SELECT requests to util expecting to get a set of
     * entities in result
     *
     * @param sqlRequest string containing request to util without parameters
     *                   taken from gin entity
     * @param parameters array of parameters with a certain order for insertion
     *                   them into the request
     * @param rowMapper  a certain implementation of interface {@link RowMapper
     *                   RowMapper} for assembling an entity from a single line from
     *                   {@link ResultSet ResultSet}
     * @return {@link ArrayList ArrayList} filled with entities from ResultSet
     * @throws DaoException if failed to execute any stage
     */
    public List queryForList(String sqlRequest, Object[] parameters, RowMapper rowMapper)
            throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            ResultSet resultSet = buildStatement(statement, parameters).executeQuery();
            return mappingRows(resultSet, rowMapper);
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(FAILED_TO_LOAD_MSG, e);
        }
    }

    /**
     * Method for making SELECT requests to util expecting to get a single
     * entity in result
     *
     * @param sqlRequest string containing request to util without parameters
     *                   taken from gin entity
     * @param parameters array of parameters with a certain order for insertion
     *                   them into the request
     * @param rowMapper  a certain implementation of interface {@link RowMapper
     *                   RowMapper} for assembling an entity from a single line
     *                   from {@link ResultSet ResultSet}
     * @return {@link ArrayList ArrayList} filled with entities from ResultSet
     * @throws DaoException if failed to execute any stage
     */
    public Optional queryForObject(String sqlRequest, Object[] parameters, RowMapper rowMapper)
            throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = buildStatement(statement, parameters).executeQuery();

            return resultSet.next() ? Optional.of(rowMapper.mapRow(resultSet)) : Optional.empty();
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(FAILED_TO_LOAD_MSG, e);
        }
    }

    /**
     * Method for making SELECT requests to util expecting to get a set of
     * entities in result
     *
     * @param sqlRequest string containing request to util without parameters
     *                   taken from gin entity
     * @return {@link ArrayList ArrayList} filled with entities from ResultSet
     * @throws EmptyResultException if nothing was found
     * @throws DaoException         if failed to execute any stage
     */
    public int queryForInt(String sqlRequest) throws DaoException, EmptyResultException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new EmptyResultException(EMPTY_RESULT_MSG);
            }
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(FAILED_QUERY_MSG, e);
        }
    }

    /**
     * Method for filling list of entities using data from {@link ResultSet ResultSet}
     * extracting with a certain {@link RowMapper RowMapper}
     *
     * @param resultSet ResultSet given from successfully executed request
     * @param rowMapper a certain implementation of RowMapper for extracting
     *                  entities from ResultSet
     * @return ArrayList filled with entities
     * @throws SQLException if sudden fault has happened
     */
    private List mappingRows(ResultSet resultSet, RowMapper rowMapper)
            throws SQLException {

        if (resultSet.next()) {
            List<Object> mappedObjectsList = new ArrayList<>();
            mappedObjectsList.add(rowMapper.mapRow(resultSet));

            while (resultSet.next()) {
                mappedObjectsList.add(rowMapper.mapRow(resultSet));
            }
            return mappedObjectsList;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Method for filling gaps in {@link PreparedStatement PreparedStatement} formed
     * on the basis of string with SQL request given formerly
     *
     * @param statement  PreparedStatement without specification of transmitted
     *                   parameters
     * @param parameters array that contains parameters for statement with appropriate
     *                   order
     * @return filled PreparedStatement
     * @throws SQLException if failed to build statement
     */
    private PreparedStatement buildStatement(PreparedStatement statement, Object[] parameters)
            throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] instanceof Number) {
                statement.setString(i + 1, String.valueOf(parameters[i]));
            } else
                if (parameters[i] instanceof LocalDateTime) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
                String formattedString = ((LocalDateTime) parameters[i]).format(formatter);
                statement.setString(i + 1, formattedString);
            } else {
                statement.setString(i + 1, (String) parameters[i]);
            }
        }
        return statement;
    }

    private void rollback(Connection connection) throws DaoException {
        try {
            connection.rollback();
        } catch (SQLException e) {
            throw new DaoException(FAILED_TO_ROLL_BACK_MSG, e);
        }
    }
}
