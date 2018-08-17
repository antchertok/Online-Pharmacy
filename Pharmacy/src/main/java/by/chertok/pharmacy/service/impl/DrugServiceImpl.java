package by.chertok.pharmacy.service.impl;

import by.chertok.pharmacy.dao.DrugDao;
import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.exception.DaoException;
import by.chertok.pharmacy.exception.ServiceException;
import by.chertok.pharmacy.service.DrugService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DrugServiceImpl implements DrugService {

    private static final Logger LOGGER = Logger.getLogger(DrugServiceImpl.class);
    private static final String GETTING_DRUG_ERR_MSG = "Failed to get drug";
    private static final String CREATING_DRUG_ERR_MSG = "Failed to create new drug";
    private static final String UPDATING_DRUG_ERR_MSG = "Failed to update drug";
    private static final String DELETING_DRUG_ERR_MSG = "Failed to delete drug";
    private static final String COUNTING_DRUGS_ERR_MSG = "Failed to count records";
    private DrugDao drugDao;

    public DrugServiceImpl(DrugDao drugDao) {
        this.drugDao = drugDao;
    }

    @Override
    public List<Drug> readAll() throws ServiceException {
        try {
            return drugDao.readAll();
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(GETTING_DRUG_ERR_MSG, e);
        }
    }

    @Override
    public Optional<Drug> readById(long id) throws ServiceException {
        try {
            return drugDao.readById(id);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(GETTING_DRUG_ERR_MSG, e);
        }
    }

    @Override
    public boolean create(Drug entity) throws ServiceException {
        try {
            return drugDao.create(entity) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(CREATING_DRUG_ERR_MSG, e);
        }
    }

    @Override
    public boolean update(Drug entity) throws ServiceException {
        try {
            return drugDao.update(entity) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(UPDATING_DRUG_ERR_MSG, e);
        }
    }

    @Override
    public boolean delete(long id) throws ServiceException {
        try {
            return drugDao.delete(id) > 0;
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new ServiceException(DELETING_DRUG_ERR_MSG, e);
        }
    }

    @Override
    public List<Drug> readForPage(String drugName, int pageNumber, int elements)
            throws ServiceException {
        try {
            return drugDao.readForPage(drugName, pageNumber, elements);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(GETTING_DRUG_ERR_MSG, e);
        }
    }

    @Override
    public int getAmountOfRecords(String name) throws ServiceException {
        try {
            return drugDao.getAmountOfRecords(name);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(COUNTING_DRUGS_ERR_MSG, e);
        }
    }
}
