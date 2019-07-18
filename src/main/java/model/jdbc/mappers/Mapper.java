package model.jdbc.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<T> {
    T getEntity(ResultSet resultSet, int... index);

    default Integer getId(ResultSet resultSet){
        try {
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
