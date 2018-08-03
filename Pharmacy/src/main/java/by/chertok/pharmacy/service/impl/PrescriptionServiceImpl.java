package main.java.by.chertok.pharmacy.service.impl;

import main.java.by.chertok.pharmacy.dao.PrescriptionDao;
import main.java.by.chertok.pharmacy.entity.Prescription;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.PrescriptionService;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PrescriptionServiceImpl implements PrescriptionService {

    private static final Logger LOGGER = Logger.getLogger(PrescriptionServiceImpl.class);
    private static final String ERR_MSG = "Operation failed";
    private PrescriptionDao prescriptionDao;

    public PrescriptionServiceImpl(PrescriptionDao prescriptionDao) {
        this.prescriptionDao = prescriptionDao;
    }

    @Override
    public List<Prescription> readAll() throws ServiceException {
        try{
            return prescriptionDao.readAll();
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public Optional<Prescription> readById(long id) throws ServiceException{
        try{
            return prescriptionDao.readById(id);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Optional.empty();
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean create(Prescription entity) throws ServiceException{
        try{
            return prescriptionDao.create(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean update(Prescription entity) throws ServiceException{
        try{
            return prescriptionDao.update(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException{
        try{
            return prescriptionDao.delete(id) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean checkAvailability(long drugId, long customerId) throws ServiceException{
        try{
            return prescriptionDao.checkAvailability(drugId, customerId);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public List<Prescription> readByDoctorId(long doctorId) throws ServiceException{
        try{
            return prescriptionDao.readByDoctorId(doctorId);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }
}
