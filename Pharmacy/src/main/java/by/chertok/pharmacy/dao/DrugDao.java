package main.java.by.chertok.pharmacy.dao;

import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.DaoException;

import java.util.List;

public interface DrugDao extends Dao<Drug> {

    /**
     * Returns all drugs which names contain given string
     *
     * @param drugName string which should be in drug's name
     * @return list with all relevant drugs
     */
    List<Drug> readByName(String drugName) throws DaoException;

    List<Drug> readForPage(String drugName, Integer pageNumber, Integer elements) throws DaoException;

    int getAmountOfRecords(String name) throws DaoException;
}
