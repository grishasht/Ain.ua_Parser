package model.jdbc.mappers;

import model.entity.Image;

import java.sql.ResultSet;

public class ImageMapper implements Mapper<Image>{
    @Override
    public Image getEntity(ResultSet resultSet, int... index) {
        return null;
    }
}
