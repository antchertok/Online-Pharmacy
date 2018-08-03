package main.java.by.chertok.pharmacy.dao.impl;

import main.java.by.chertok.pharmacy.dao.PrescriptionDao;
import main.java.by.chertok.pharmacy.dao.util.JdbcHelper;
import main.java.by.chertok.pharmacy.dao.util.RowMapper;
import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
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
    private static final String CHECK_AVAILABILITY = "SELECT prescription.id, valid_until, prescription.doctor_id, name, first_name, last_name, approved, drug_id, customer_id FROM prescription LEFT JOIN drug USING(drug_id) LEFT JOIN user ON customer_id = user.id WHERE drug_id = ? AND customer_id = ? AND approved = 1";
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
        try {
            return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Prescription> readById(long id) throws DaoException{
        try {
            return Optional.of((Prescription) jdbcHelper.queryForObject(READ_BY_ID, new Object[]{id}, rowMapper));
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public List<Prescription> readByDoctorId(long id) throws DaoException{
        try {
            return jdbcHelper.queryForList(READ_BY_DOCTOR_ID, new Object[]{id}, rowMapper);
        } catch (EmptyResultException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public int create(Prescription entity) throws DaoException{
        Object[] parameters = new Object[]{
                entity.getValidUntil(),
                entity.getDoctorId(),//TODO insert personal doctor's id
                entity.getDrugId(),
                entity.getCustomerId(),
                null
        };
        return jdbcHelper.executeUpdate(INSERT, parameters);
    }

    @Override
    public int update(Prescription entity) throws DaoException{
        Object[] parameters = new Object[]{
                entity.isApproved() ? LocalDateTime.now().plusMonths(1) : entity.getValidUntil(),
                entity.isApproved() ? 1 : 0,
                entity.getId(),
        };
        return jdbcHelper.executeUpdate(UPDATE, parameters);
    }

    @Override
    public int delete(long id) {
        throw new UnsupportedOperationException();//TODO CHECK BELOW
//        String deleteQuery = "DELETE FROM prescription WHERE id = ?";
//        return jdbcHelper.executeUpdate(deleteQuery, new Object[]{id}) > 0;
    }

    @Override
    public boolean checkAvailability(long drugId, long customerId) throws DaoException{
        try {
            return Optional.of((Prescription) jdbcHelper.queryForObject(CHECK_AVAILABILITY, new Object[]{drugId, customerId}, rowMapper)).isPresent();
        } catch (EmptyResultException e) {//TODO PERHAPS IT'S BETTER TO REPLACE WITH NULL INDICATION
            //TODO DO SMTH!
            return false;
        }
    }
}
