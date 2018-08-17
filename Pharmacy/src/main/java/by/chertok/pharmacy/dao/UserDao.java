package by.chertok.pharmacy.dao;

import by.chertok.pharmacy.entity.User;
import by.chertok.pharmacy.exception.DaoException;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    /**
     * Returns {@link Optional Optional} which may contain user object if it
     * was found
     *
     * @param userLogin    user's login for search
     * @param userPassword user's password for search
     * @return Optional with user object if it was found
     */
    Optional<User> readByLoginPassword(String userLogin, String userPassword)
            throws DaoException;

    /**
     * Returns {@link Optional Optional} which may contain doctor if it
     * was found
     *
     * @param firstName doctor's first name
     * @param lastName  doctor's last name
     * @return Optional with user object if it was found
     */
    Optional<User> getDocByName(String firstName, String lastName)
            throws DaoException;
}
