package by.chertok.pharmacy.service;

import by.chertok.pharmacy.entity.Prescription;
import by.chertok.pharmacy.exception.ServiceException;

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

    /**
     * Gets all prescriptions addressed to given doctor from data storage
     *
     * @param doctorId doctor, whose prescriptions are to be given
     * @return List with prescriptions addressed to doctor with given id
     */
    List<Prescription> readByDoctorId(long doctorId) throws ServiceException;
}
