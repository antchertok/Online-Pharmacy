package main.java.by.chertok.pharmacy.dao.impl;

import main.java.by.chertok.pharmacy.dao.DrugDao;
import main.java.by.chertok.pharmacy.dao.util.JdbcHelper;
import main.java.by.chertok.pharmacy.dao.util.RowMapper;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import main.java.by.chertok.pharmacy.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class DrugDaoImpl implements DrugDao {

    private JdbcHelper jdbcHelper;
    private RowMapper<Drug> rowMapper;
    private static final Logger LOGGER = Logger.getLogger(DrugDaoImpl.class);
    private static final String READ_ALL = "SELECT drug_id, name, dose, prescription, price FROM drug WHERE availability = 1";
    private static final String READ_BY_ID = "SELECT drug_id, name, dose, prescription, price FROM drug WHERE drug_id = ?";
    private static final String INSERT = "INSERT INTO drug (name, dose, prescription, price, availability) VALUES (?,?,?,?,1)";
    private static final String DELETE = "UPDATE drug SET availability = 0 WHERE drug_id = ?";
    private static final String READ_BY_NAME_FIRST = "SELECT drug_id, name, dose, prescription, price FROM drug WHERE name LIKE \"%";
    private static final String READ_BY_NAME_LAST = "%\" AND availability = 1";
    private static final String UPDATE = "UPDATE drug SET name = ?, dose = ?, prescription = ?, price = ? WHERE drug_id = ?";
    private static final String PAGE_FIRST = "SELECT drug_id, name, dose, prescription, price FROM drug WHERE name LIKE \"%";
    private static final String PAGE_SEC = "%\" AND availability = 1 LIMIT ";
    private static final String PAGE_LAST = " OFFSET ";
    private static final String GET_AMOUNT_OF_RECORDS = "SELECT COUNT(drug_id) FROM drug WHERE name LIKE \"%";

    public DrugDaoImpl(JdbcHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
        rowMapper = resultSet -> {
            Drug drug = new Drug(resultSet.getLong("drug_id"));
            drug.setName(resultSet.getString("name"));
            drug.setDose(resultSet.getInt("dose"));
            drug.setPrescription(resultSet.getInt("prescription"));
            drug.setPrice(resultSet.getDouble("price"));

            return drug;
        };
    }

    @Override
    public List<Drug> readAll() throws DaoException {
        try {
            return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Drug> readById(long id) throws DaoException {
        try {
            return Optional.of((Drug) jdbcHelper.queryForObject(READ_BY_ID, new Object[]{id}, rowMapper));
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int create(Drug entity) throws DaoException {
        Object[] parameters = new Object[]{
                entity.getName(),
                entity.getDose(),
                entity.getPrescription(),
                entity.getPrice()
        };
        return jdbcHelper.executeUpdate(INSERT, parameters);
    }

    @Override
    public int update(Drug entity) throws DaoException {
        Object[] parameters = new Object[]{
                entity.getName(),
                entity.getDose(),
                entity.getPrescription(),
                entity.getPrice(),
                entity.getId()
        };
        return jdbcHelper.executeUpdate(UPDATE, parameters);
    }

    @Override
    public int delete(long id) throws DaoException {
        return jdbcHelper.executeUpdate(DELETE, new Object[]{id});
    }

    @Override
    public List<Drug> readByName(String drugName) throws DaoException {
        try {
            String readByNameQuery = READ_BY_NAME_FIRST + drugName + READ_BY_NAME_LAST;
            return jdbcHelper.queryForList(readByNameQuery, new Object[]{}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Nothing was found", e);
        }
    }

    @Override
    public List<Drug> readForPage(String drugName, Integer pageNumber, Integer elements) throws DaoException {//Если кол-во эл-тов в списке меньше чем elements, значит последняя страница
        try {
            int offset = elements * (pageNumber - 1);
            return jdbcHelper.queryForList(PAGE_FIRST + drugName + PAGE_SEC + elements
                    + PAGE_LAST + offset, new Object[]{}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Nothing was found", e);
        }
    }

    @Override
    public int getAmountOfRecords(String name) throws DaoException {
        String query = GET_AMOUNT_OF_RECORDS + name + READ_BY_NAME_LAST;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : -1;
        } catch (InterruptedException | SQLException e) {
            LOGGER.error(e);
            throw new DaoException("Failed to count records", e);
        }
    }
}
