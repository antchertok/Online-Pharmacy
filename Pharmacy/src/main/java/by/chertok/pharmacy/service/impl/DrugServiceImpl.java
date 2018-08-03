package main.java.by.chertok.pharmacy.service.impl;

import main.java.by.chertok.pharmacy.dao.DrugDao;
import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import main.java.by.chertok.pharmacy.exception.ServiceException;
import main.java.by.chertok.pharmacy.service.DrugService;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DrugServiceImpl implements DrugService {

    private static final Logger LOGGER = Logger.getLogger(DrugServiceImpl.class);
    private static final String ERR_MSG = "Operation failed";
    private DrugDao drugDao;

    public DrugServiceImpl(DrugDao drugDao) {
        this.drugDao = drugDao;
    }

    @Override
    public List<Drug> readAll() throws ServiceException {
        try{
            return drugDao.readAll();
        } catch(DaoException e){//TODO запилить processDaoException(logger, throwable) throws ServiceException
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public Optional<Drug> readById(long id) throws ServiceException{
        try {
            return drugDao.readById(id);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Optional.empty();
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public boolean create(Drug entity) throws ServiceException{
        try{
            return drugDao.create(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }

    }

    @Override
    public boolean update(Drug entity) throws ServiceException{
        try{
            return drugDao.update(entity) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }

    }

    @Override
    public boolean delete(long id) throws ServiceException{
        try{
            return drugDao.delete(id) > 0;
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            throw new ServiceException(ERR_MSG);
        }

    }

    @Override
    public List<Drug> readByName(String drugName) throws ServiceException{
        try{
            return drugDao.readByName(drugName);
        } catch(DaoException e){
            LOGGER.error(e.getMessage());
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public List<Drug> readForPage(String drugName, int pageNumber, int elements) throws ServiceException{
        try{
            return drugDao.readForPage(drugName, pageNumber, elements);
        } catch (DaoException e) {
            LOGGER.error(e.getMessage(), e);
            if(e.getCause() instanceof EmptyResultException){
                return Collections.EMPTY_LIST;
            }
            throw new ServiceException(ERR_MSG);
        }
    }

    @Override
    public int getAmountOfRecords(String name) throws ServiceException{
        try{
            return drugDao.getAmountOfRecords(name);
        }catch (DaoException e){
            LOGGER.error(e);
            throw new ServiceException("Records were not counted", e);
        }
    }
}
