package main.java.by.chertok.pharmacy.service;

import main.java.by.chertok.pharmacy.exception.ServiceException;

import java.util.List;
import java.util.Optional;

/**
 * Defines the basic functionality for accessing low-level data
 *
 * @param <T> type of entity
 */
public interface Service<T> {
    /**
     * Takes all entities with given type
     *
     * @return list filled with all available entities
     */
    List<T> readAll() throws ServiceException;

    /**
     * Takes an entity with given id if it exists
     *
     * @param id unique entity identifier
     * @return {@link Optional Optional} which can contain an entity
     */
    Optional<T> readById(long id) throws ServiceException;

    /**
     * Inserts given entity into data storage
     *
     * @param entity entity to insert
     * @return true if insertion was successful
     */
    boolean create(T entity) throws ServiceException;

    /**
     * Overwrites the data about given entity if it exists
     *
     * @param entity entity to update
     * @return true if update was successful
     */
    boolean update(T entity) throws ServiceException;

    /**
     * Removes an entity with a specified id from data storage
     *
     * @param id unique identifier of the entity for removing
     * @return
     */
    boolean delete(long id) throws ServiceException;
}
