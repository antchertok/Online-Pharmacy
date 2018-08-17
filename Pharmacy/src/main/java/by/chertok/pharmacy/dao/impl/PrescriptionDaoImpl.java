package by.chertok.pharmacy.dao.impl;

import by.chertok.pharmacy.dao.PrescriptionDao;
import by.chertok.pharmacy.dao.util.JdbcHelper;
import by.chertok.pharmacy.dao.util.RowMapper;
import by.chertok.pharmacy.entity.Prescription;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.EmptyResultException;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PrescriptionDaoImpl implements PrescriptionDao {

    private JdbcHelper jdbcHelper;
    private RowMapper<Prescription> rowMapper;
    private static final Logger LOGGER = Logger.getLogger(PrescriptionDaoImpl.class);
    private static final String READ_ALL = "SELECT prescription.id, valid_until, prescription.doctor_id, name, first_name, last_name, approved, drug_id, customer_id FROM prescription LEFT JOIN drug USING(drug_id) LEFT JOIN user ON customer_id = user.id WHERE approved IS NULL";
    private static final String READ_BY_DOCTOR_ID = "SELECT prescription.id, valid_until, prescription.doctor_id, name, first_name, last_name, approved, drug_id, customer_id FROM prescription LEFT JOIN drug USING(drug_id) LEFT JOIN user ON customer_id = user.id WHERE prescription.doctor_id = ? AND approved IS NULL";
    private static final String READ_BY_ID = "SELECT prescription.id, valid_until, prescription.doctor_id, name, first_name, last_name, approved, drug_id, customer_id FROM prescription LEFT JOIN drug USING(drug_id) LEFT JOIN user ON customer_id = user.id WHERE prescription.id = ?";
    private static final String CHECK_AVAILABILITY = "SELECT prescription.id, valid_until, prescription.doctor_id, name, first_name, last_name, approved, drug_id, customer_id FROM prescription LEFT JOIN drug USING(drug_id) LEFT JOIN user ON customer_id = user.id WHERE drug_id = ? AND customer_id = ? AND approved = 1 AND NOW() < valid_until";
    //    private static final String CHECK_AVAILABILITY = "SELECT id, valid_until, doctor_id, drug_id, customer_id, approved FROM prescription WHERE drug_id = ? AND customer_id = ?";
    private static final String INSERT = "INSERT INTO prescription (valid_until, doctor_id, drug_id, customer_id, approved) VALUES (?,?,?,?,?)";
    private static final String UPDATE = "UPDATE prescription SET valid_until = ?, approved = ? WHERE id = ?";

    public PrescriptionDaoImpl(JdbcHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
        rowMapper = resultSet -> {
            Prescription prescription = new Prescription(resultSet.getLong("id"));
            prescription.setValidUntil(resultSet.getTimestamp("valid_until").toLocalDateTime());
            prescription.setDoctorId(resultSet.getLong("doctor_id"));
            prescription.setDrugId(resultSet.getLong("drug_id"));
            prescription.setCustomerId(resultSet.getLong("customer_id"));
            prescription.setDrugName(resultSet.getString("name"));
            prescription.setCustomerFirstName(resultSet.getString("first_name"));
            prescription.setCustomerLastName(resultSet.getString("last_name"));
            prescription.setApproved(resultSet.getBoolean("approved"));//MAYBE INT BETTER?!

            return prescription;
        };
    }

    @Override
    public List<Prescription> readAll() throws DaoException {
        return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
    }

    @Override
    public Optional<Prescription> readById(long id) throws DaoException {
        return jdbcHelper.queryForObject(READ_BY_ID, new Object[]{id}, rowMapper);
    }

    @Override
    public List<Prescription> readByDoctorId(long id) throws DaoException {
        return jdbcHelper.queryForList(READ_BY_DOCTOR_ID, new Object[]{id}, rowMapper);
    }

    @Override
    public int create(Prescription entity) throws DaoException {
        Object[] parameters = new Object[]{
                entity.getValidUntil(),
                entity.getDoctorId(),
                entity.getDrugId(),
                entity.getCustomerId(),
                null
        };
        return jdbcHelper.executeUpdate(INSERT, parameters);
    }

    @Override
    public int update(Prescription entity) throws DaoException {
        Object[] parameters = new Object[]{
                entity.isApproved() ? LocalDateTime.now().plusMonths(1) : entity.getValidUntil(),
                entity.isApproved() ? 1 : 0,
                entity.getId(),
        };
        return jdbcHelper.executeUpdate(UPDATE, parameters);
    }

    @Override
    public int delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean checkAvailability(long drugId, long customerId) throws DaoException {
        return jdbcHelper.queryForObject(CHECK_AVAILABILITY, new Object[]{drugId, customerId}, rowMapper).isPresent();
    }
}
