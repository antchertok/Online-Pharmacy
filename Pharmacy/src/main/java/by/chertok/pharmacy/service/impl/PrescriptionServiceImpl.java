package by.chertok.pharmacy.service.impl;

import by.chertok.pharmacy.dao.PrescriptionDao;
import by.chertok.pharmacy.entity.Prescription;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.PrescriptionService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger LOGGER = Logger.getLogger(PrescriptionServiceImpl.class);
    private static final String GETTING_PRESC_ERR_MSG = "Failed to get prescription";
    private static final String CREATING_PRESC_ERR_MSG = "Failed to create new prescription";
    private static final String UPDATING_PRESC_ERR_MSG = "Failed to update prescription";
    private static final String DELETING_PRESC_ERR_MSG = "Failed to delete prescription";
    private static final String CHECKING_PRESC_ERR_MSG = "Failed to check drug's availability";
    private PrescriptionDao prescriptionDao;

    public PrescriptionServiceImpl(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    @Override
    public List<Prescription> readAll() throws ServiceException {
        try {
            return prescriptionDao.readAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_PRESC_ERR_MSG, e);
        }
    }

    @Override
    public Optional<Prescription> readById(long id) throws ServiceException {
        try {
            return prescriptionDao.readById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_PRESC_ERR_MSG, e);
        }
    }

    @Override
    public boolean create(Prescription entity) throws ServiceException {
        try {
            return prescriptionDao.create(entity) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(CREATING_PRESC_ERR_MSG, e);
        }
    }

    @Override
    public boolean update(Prescription entity) throws ServiceException {
        try {
            return prescriptionDao.update(entity) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(UPDATING_PRESC_ERR_MSG, e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return prescriptionDao.delete(id) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(DELETING_PRESC_ERR_MSG, e);
        }
    }

    @Override
    public boolean checkAvailability(long drugId, long customerId)
            throws ServiceException {
        try {
            return prescriptionDao.checkAvailability(drugId, customerId);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(CHECKING_PRESC_ERR_MSG, e);
        }
    }

    @Override
    public List<Prescription> readByDoctorId(long doctorId)
            throws ServiceException {
        try {
            return prescriptionDao.readByDoctorId(doctorId);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_PRESC_ERR_MSG, e);
        }
    }
}
