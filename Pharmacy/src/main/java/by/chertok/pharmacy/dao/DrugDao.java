package by.chertok.pharmacy.dao;

import by.chertok.pharmacy.entity.Drug;
import by.chertok.pharmacy.exception.DaoException;

import java.util.List;

public interface DrugDao extends Dao<Drug> {

    /**
     * Returns some drugs (quantity depends on page's properties) which names contain given string
     *
     * @param drugName   string which should be in drug's name
     * @param pageNumber the number of page
     * @param elements   how many drugs are shown on each page
     * @return list with corresponding length containing relevant drugs
     * @throws DaoException if operation hasn't finished successfully
     */
    List<Drug> readForPage(String drugName, Integer pageNumber, Integer elements) throws DaoException;

    /**
     * Returns number of drugs with relevant names
     *
     * @param name part of drug's name
     * @return amount of drugs corresponding to "name" recorded in data storage
     * @throws DaoException if operation hasn't finished successfully
     */
    int getAmountOfRecords(String name) throws DaoException;
}
