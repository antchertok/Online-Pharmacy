package by.chertok.pharmacy.dao.impl;

import by.chertok.pharmacy.dao.DrugDao;
import by.chertok.pharmacy.dao.util.JdbcHelper;
import by.chertok.pharmacy.dao.util.RowMapper;
import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.EmptyResultException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DrugDaoImpl implements DrugDao {

    private JdbcHelper jdbcHelper;
    private RowMapper<Drug> rowMapper;
    private static final Logger LOGGER = Logger.getLogger(DrugDaoImpl.class);
    private static final String COUNTING_RECORDS_ERR_MSG = "Failed to count records";
    private static final String READ_ALL = "SELECT drug_id, name, dose, prescription, price FROM drug WHERE availability = 1";
    private static final String READ_BY_ID = "SELECT drug_id, name, dose, prescription, price FROM drug WHERE drug_id = ?";
    private static final String INSERT = "INSERT INTO drug (name, dose, prescription, price, availability) VALUES (?,?,?,?,1)";
    private static final String DELETE = "UPDATE drug SET availability = 0 WHERE drug_id = ?";
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
        return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
    }

    @Override
    public Optional<Drug> readById(long id) throws DaoException {
        return jdbcHelper.queryForObject(READ_BY_ID, new Object[]{id}, rowMapper);
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
    public List<Drug> readForPage(String drugName, Integer pageNumber, Integer elements) throws DaoException {

        int offset = elements * (pageNumber - 1);
        return jdbcHelper.queryForList(PAGE_FIRST + drugName + PAGE_SEC + elements
                + PAGE_LAST + offset, new Object[]{}, rowMapper);
    }

    @Override
    public int getAmountOfRecords(String name) throws DaoException {
        String query = GET_AMOUNT_OF_RECORDS + name + READ_BY_NAME_LAST;
        try {
            return jdbcHelper.queryForInt(query);
        } catch (EmptyResultException e) {
            LOGGER.error(e);
            throw new DaoException(COUNTING_RECORDS_ERR_MSG, e);
        }
    }
}