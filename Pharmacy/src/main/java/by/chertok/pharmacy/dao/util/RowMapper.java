package by.chertok.pharmacy.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This interface defines a method for converting a single
 * line from util into a new entity.
 *
 * @param <T>
 */
@FunctionalInterface
public interface RowMapper<T> {
    T mapRow(ResultSet resultSet) throws SQLException;
}
