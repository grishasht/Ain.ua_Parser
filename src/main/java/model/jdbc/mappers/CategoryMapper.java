package model.jdbc.mappers;

import model.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryMapper implements Mapper<Category>{
    @Override
    public Category getEntity(ResultSet resultSet, int... index) {
        try {
            Category category = new Category();
            category.setId(resultSet.getInt(index[0]));
            category.setName(resultSet.getString(index[1]));
            category.setRef(resultSet.getString(index[2]));
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
