package main.java.by.chertok.pharmacy.dao.impl;
;
import main.java.by.chertok.pharmacy.dao.UserDao;
import main.java.by.chertok.pharmacy.dao.util.JdbcHelper;
import main.java.by.chertok.pharmacy.dao.util.RowMapper;
import main.java.by.chertok.pharmacy.entity.User;
import main.java.by.chertok.pharmacy.exception.DaoException;
import main.java.by.chertok.pharmacy.exception.EmptyResultException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private JdbcHelper jdbcHelper;
    private RowMapper<User> rowMapper;
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);
    private static final String READ_ALL = "SELECT id, login, password, `e-mail`, role, first_name, last_name, speciality, doctor_id FROM user";
    private static final String READ_BY_ID = "SELECT id, login, password, `e-mail`, role, first_name, last_name, speciality, doctor_id FROM user WHERE id = ?";
    private static final String INSERT = "INSERT INTO user (login, password, `e-mail`, role, first_name, last_name) VALUES (?,?,?,\"customer\",?,?)";
    private static final String DELETE = "DELETE FROM user WHERE id = ?";
    private static final String UPDATE = "UPDATE user SET login = ?, password = ?, `e-mail` = ?, role = ?, first_name = ?, last_name = ?, speciality = ?, doctor_id = ? WHERE id = ?";
    private static final String READ_BY_LOGIN_AND_PASS = "SELECT id, login, password, `e-mail`, role, first_name, last_name, speciality, doctor_id FROM user WHERE login = ? AND password = ?";

    public UserDaoImpl(JdbcHelper jdbcHelper) {
        this.jdbcHelper = jdbcHelper;
        rowMapper = (resultSet) -> {
            User user = new User(resultSet.getInt("id"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setMail(resultSet.getString("e-mail"));
            user.setRole(resultSet.getString("role"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setSpeciality(resultSet.getString("speciality"));
            user.setDoctorId(resultSet.getLong("doctor_id"));

            return user;
        };
    }

    @Override
    public List<User> readAll() throws DaoException {
        try {
            return jdbcHelper.queryForList(READ_ALL, new Object[]{}, rowMapper);
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> readById(long id) throws DaoException{
        try {
            return Optional.of((User) jdbcHelper.queryForObject(READ_BY_ID, new Object[]{}, rowMapper));
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }

    @Override
    public int create(User entity) throws DaoException{
        Object[] parameters = new Object[]{
                entity.getLogin(),
                entity.getPassword(),
                entity.getMail(),
                entity.getFirstName(),
                entity.getLastName(),
        };
        return jdbcHelper.executeUpdate(INSERT, parameters);
    }

    @Override
    public int update(User entity) throws DaoException{
        String updateUser = "UPDATE user SET login = ?, password = ?, `e-mail` = ?, role = ?, first_name = ?, last_name = ?, speciality = ?, doctor_id = ? WHERE id = ?";
        Object[] parameters = new Object[]{
                entity.getLogin(),
                entity.getPassword(),
                entity.getMail(),
                entity.getRole(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getSpeciality(),
                entity.getDoctorId(),
                entity.getId()
        };
        return jdbcHelper.executeUpdate(UPDATE, parameters);
    }

    @Override
    public int delete(long id) throws DaoException{
        return jdbcHelper.executeUpdate(DELETE, new Object[]{id});
    }

    @Override
    public Optional<User> readByLoginPassword(String userLogin, String userPassword) throws DaoException{
        try {
            return Optional.of((User) jdbcHelper.queryForObject(READ_BY_LOGIN_AND_PASS, new Object[]{userLogin, userPassword}, rowMapper));
        } catch (EmptyResultException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(e);
        }
    }
}
