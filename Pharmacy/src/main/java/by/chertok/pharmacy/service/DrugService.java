package main.java.by.chertok.pharmacy.service;

import main.java.by.chertok.pharmacy.entity.Drug;
import main.java.by.chertok.pharmacy.exception.ServiceException;

import java.util.List;

public interface DrugService extends Service<Drug> {
    /**
     * Returns all drugs which names contain given string
     *
     * @param drugName string which should be in drug's name
     * @return list with all relevant drugs
     */
    List<Drug> readByName(String drugName) throws ServiceException;

    List<Drug> readForPage(String drugName, int pageNumber, int elements) throws ServiceException;

    int getAmountOfRecords(String name) throws ServiceException;
}
