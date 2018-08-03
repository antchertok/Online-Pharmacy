package main.java.by.chertok.pharmacy.dao;


import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDao extends Dao<Prescription> {
    /**
     * Checks if given drug is available for a certain user
     *
     * @param drugId     drug identifier for it's availability verification
     * @param customerId id of the customer for whom drug is checking
     * @return true if the prescription exists and is approved
     */
    boolean checkAvailability(long drugId, long customerId) throws DaoException;
    List<Prescription> readByDoctorId(long doctorId) throws DaoException;
}
