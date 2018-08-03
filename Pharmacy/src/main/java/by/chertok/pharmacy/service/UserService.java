package main.java.by.chertok.pharmacy.service;

import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.ServiceException;

import java.util.Optional;

public interface UserService extends Service<User> {
//    boolean askPrescExtension(long id);//replaced with createPrescription
//    List<User> readByRole(String role);//
//    boolean denyPrescExtention(long PrescId, boolean prescExcept);//replaced with updatePrescription (same about approvePrescription)

    /**
     * Returns {@link Optional Optional} which may contain user object if it
     * was found
     *
     * @param userLogin    user's login for search
     * @param userPassword user's password for search
     * @return Optional with user object if it was found
     */
    Optional<User> readByLoginPassword(String userLogin, String userPassword) throws ServiceException;

}
