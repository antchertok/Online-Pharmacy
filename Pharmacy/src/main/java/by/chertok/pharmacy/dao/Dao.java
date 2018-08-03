package main.java.by.chertok.pharmacy.dao;

import main.java.by.chertok.pharmacy.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * Defines basic methods for providing an access to util
 *
 * @param <T> type of entity used in specific DAO
 */
public interface Dao<T> {//для прескрипшн:
    /**
     * Takes all entities with given type from util
     *
     * @return list filled with all available entities
     */
    List<T> readAll() throws DaoException;

    /**
     * Takes an entity with given id if it exists
     *
     * @param id unique entity identifier
     * @return {@link Optional Optional} which can contain an entity
    */
    Optional<T> readById(long id) throws DaoException;

    /**
     * Inserts given entity into util
     *
     * @param entity entity to insert
     * @return the number of affected rows in the util if execution was
     * successful
     */
    int create(T entity) throws DaoException;

    /**
     * Updates the data about given entity if it exists
     *
     * @param entity entity to update
     * @return the number of affected rows in the util if execution was
     * successful
     */
    int update(T entity) throws DaoException;

    /**
     * Removes an entity with a specified id from util
     *
     * @param id unique identifier of the entity for removing
     * @return the number of affected rows in the util if execution was
     * successful
     */
    int delete(long id) throws DaoException;
}