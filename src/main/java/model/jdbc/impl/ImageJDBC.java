package model.jdbc.impl;

import model.entity.Image;
import model.jdbc.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ImageJDBC extends JDBC implements Dao<Image> {

    ImageJDBC(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Image entity) {
        String query = "INSERT into images(src, image, article_id)" +
                " values (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getSrc());
            preparedStatement.setBytes(2, entity.getImage());
            preparedStatement.setInt(3, entity.getArticle_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Entity image " + entity.getArticle_id() + " exists");
        }
    }

    @Override
    public Integer getId(Image entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Image> readAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
