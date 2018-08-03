package main.java.by.chertok.pharmacy.service;

import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.exception.ServiceException;

import java.util.List;

public interface PrescriptionService extends Service<Prescription> {
    /**
     * Checks if given drug is available for a certain user by verification his
     * prescription
     *
     * @param drugId     drug identifier for it's availability verification
     * @param customerId id of the customer for whom drug is checking
     * @return true if the prescription exists and is approved
     */
    boolean checkAvailability(long drugId, long customerId) throws ServiceException;
    List<Prescription> readByDoctorId(long doctorId) throws ServiceException;
}
