package main.java.by.chertok.pharmacy.dao.util;

import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import main.java.by.chertok.pharmacy.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class allowing to unify handling util requests
 */
public class JdbcHelper {

    private ConnectionPool connectionPool;

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
     * @throws DaoException
     */
    public int executeUpdate(String sqlRequest, Object[] parameters) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            return buildStatement(statement, parameters).executeUpdate();
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(e);
        }
    }

//    public int executeManyUpdates(String[] sqlRequests, Object[][] parameters) throws DaoException{
//        if(sqlRequests.length != parameters.length){
//            throw new DaoException("Invalid amount of arguments");
//        }
//
//        try (Connection connection = connectionPool.getConnection()){
//            PreparedStatement statement = connection.prepareStatement(sqlRequests[0]);
//            buildStatement(statement, parameters[0]);
//
//            for(int i = 1; i < sqlRequests.length; i++){
//                statement.addBatch();
//            }
//
//        }catch (InterruptedException | SQLException e){
//            throw new DaoException(e);
//        }
//    }


    //TODO ЗАПИЛИТЬ
    /**
     * Method for making UPDATE, DELETE and INSERT requests to util
     *
     * @param sqlRequests
     * @param parameters
     * @return the number of affected rows in the util after successful
     * execution of request
     * @throws DaoException
     */
    public int executeTransaction(String[] sqlRequests, Object[][] parameters) throws DaoException, SQLException{
        if(sqlRequests.length != parameters.length){
            throw new DaoException("Invalid amount of arguments");
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            for(int i = 0; i < sqlRequests.length; i++){
                statement = connection.prepareStatement(sqlRequests[i]);
                System.out.println(i);
                buildStatement(statement, parameters[i]).executeUpdate();
            }
            connection.commit();

            return 1;
        } catch (InterruptedException | SQLException e) {
            if(connection != null){
                rollback(connection);
            }
            e.printStackTrace();
            throw new DaoException("Transaction wasn't finished ", e);
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(statement != null) {
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
     * @throws EmptyResultException
     * @throws DaoException
     */
    public List queryForList(String sqlRequest, Object[] parameters, RowMapper rowMapper)
            throws EmptyResultException, DaoException {
        List<Object> mappedObjectsList = new ArrayList<>();//TODO CHECK IT ONCE AGAIN

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {

            ResultSet resultSet = buildStatement(statement, parameters).executeQuery();
            return mappingRows(resultSet, rowMapper);
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();//TODO delete
            throw new DaoException("Failed to load objects from result set", e);
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
     * @throws EmptyResultException
     * @throws DaoException
     */
    public Object queryForObject(String sqlRequest, Object[] parameters, RowMapper rowMapper)
            throws EmptyResultException, DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlRequest)) {
            ResultSet resultSet = buildStatement(statement, parameters).executeQuery();

            if (resultSet.next()) {
                return rowMapper.mapRow(resultSet);
            } else {
                throw new EmptyResultException("No matches in data base...");
            }

        } catch (InterruptedException | SQLException e) {
            throw new DaoException("Failed to load object from result set", e);
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
     * @throws EmptyResultException
     * @throws DaoException
     */
    private List mappingRows(ResultSet resultSet, RowMapper rowMapper)
            throws EmptyResultException, SQLException {

        if (!resultSet.next()) {
            throw new EmptyResultException("No matches in data base...");
        } else {
            List<Object> mappedObjectsList = new ArrayList<>();
            mappedObjectsList.add(rowMapper.mapRow(resultSet));

            while (resultSet.next()) {
                mappedObjectsList.add(rowMapper.mapRow(resultSet));
            }
            return mappedObjectsList;
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
     * @throws SQLException
     * @throws DaoException
     */
    private PreparedStatement buildStatement(PreparedStatement statement, Object[] parameters)
            throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
//            statement.setString(i+1, (String) parameters[i]);
            if (parameters[i] instanceof Number) {
                statement.setString(i + 1, String.valueOf(parameters[i]));
            } else if (parameters[i] instanceof LocalDateTime) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                String formattedString = ((LocalDateTime) parameters[i]).format(formatter);
                statement.setString(i + 1, formattedString);
            } else {
                statement.setString(i + 1, (String) parameters[i]);
            }
        }
        return statement;
    }

    private void rollback(Connection connection) throws DaoException{
        try{
            connection.rollback();
        }catch (SQLException e){
            throw new DaoException("Failed to roll back", e);
        }
    }
}
