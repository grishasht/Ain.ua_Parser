package model.jdbc.impl;

import model.entity.Category;
import model.jdbc.Dao;
import model.jdbc.mappers.CategoryMapper;
import model.jdbc.mappers.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class CategoryJDBC extends JDBC implements Dao<Category> {
    CategoryJDBC(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Category entity) {
        String query = "INSERT into categories(name, href) values (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getRef());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Entity category " + entity.getName() + " exists");
        }
    }

    @Override
    public List<Category> readAll() {
        String query = "select * from categories";
        List<Category> categories = new LinkedList<>();
        Mapper<Category> categoryMapper = new CategoryMapper();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                categories.add(categoryMapper.getEntity(resultSet, 1, 2, 3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Integer getId(Category entity) {
        String query = "select id from categories where name='" + entity.getName() + "'";
        Mapper<Category> categoryMapper = new CategoryMapper();


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer id = null;
            while(resultSet.next()) {
                id = categoryMapper.getId(resultSet);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
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
