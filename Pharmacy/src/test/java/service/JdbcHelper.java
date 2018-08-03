package test.java.service;

import main.java.by.chertok.pharmacy.dao.util.RowMapper;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.pool.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcHelper {

    int executeUpdate(String sql, Object[] parameters) throws SQLException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

//            return buildStatement(statement, parameters).executeUpdate();
            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i, parameters[i].toString());
            }
            return statement.executeUpdate();
        } catch (InterruptedException e) {
            throw new SQLException(e);
        }

    }

    Optional executeQuery(String sql, RowMapper rowMapper, Object[] parameters) throws SQLException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

//            ResultSet resultSet = buildStatement(statement, parameters).executeQuery();
//            return resultSet.next() ? Optional.of(rowMapper.mapRow(resultSet)) : Optional.empty();
            for (int i = 0; i < parameters.length; i++) {
                statement.setString(i, parameters[i].toString());
            }
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? Optional.of(rowMapper.mapRow(resultSet)) : Optional.empty();
        } catch (InterruptedException e) {
            throw new SQLException(e);
        }
    }

    List executeQueryList(String sql, RowMapper rowMapper, Object[] parameters) throws SQLException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

//            ResultSet resultSet = buildStatement(statement, parameters).executeQuery();
//            return mappingRows(resultSet, rowMapper);
            for (int i = 0; i < parameters.length; i++){
                statement.setString(i, parameters[i].toString());
            }
            ResultSet resultSet = statement.executeQuery();

            if(!resultSet.next()){
                return Collections.EMPTY_LIST;
            }
            List mappedEntities = new ArrayList();
            Drug drug = new Drug(resultSet.getLong(1));
            drug.setDose(resultSet.getInt("dose"));
            drug.setPrescription(resultSet.getInt("prescription"));
            drug.setPrice(resultSet.getDouble("price"));
            drug.setName(resultSet.getString("name"));
            mappedEntities.add(drug);

            while(resultSet.next()){
                drug = new Drug(resultSet.getLong("drugId"));
                drug.setName(resultSet.getString("name"));
                drug.setPrescription(resultSet.getInt("prescription"));
                drug.setDose(resultSet.getInt("dose"));
                drug.setPrice(resultSet.getDouble("price"));

                mappedEntities.add(drug);
            }
            return mappedEntities;
        } catch (InterruptedException e) {
            throw new SQLException(e);
        }
    }

    List mappingRows(ResultSet resultSet, RowMapper rowMapper) throws SQLException {
        if (!resultSet.next()) {
            return Collections.EMPTY_LIST;
        } else {
            List result = new ArrayList();
            result.add(rowMapper.mapRow(resultSet));

            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }
            return result;
        }

    }

    PreparedStatement buildStatement(PreparedStatement statement, Object[] parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            statement.setString(i, (String) parameters[i]);
        }
        return statement;
    }
}
