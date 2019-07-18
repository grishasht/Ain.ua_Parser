package model.jdbc.impl;

import model.entity.Article;
import model.jdbc.Dao;
import model.jdbc.mappers.ArticleMapper;
import model.jdbc.mappers.Mapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ArticleJDBC extends JDBC implements Dao<Article> {

    ArticleJDBC(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Article entity) {
        String query = "INSERT into articles(name, href, text_data, category_id)" +
                " values (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getHref());
            preparedStatement.setString(3, entity.getContains());
            preparedStatement.setInt(4, entity.getCategoryId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Entity article " + entity.getName() + " exists");
        }
    }

    @Override
    public List<Article> readAll() {
        String query = "select * from articles";
        List<Article> articles = new LinkedList<>();
        Mapper<Article> articleMapper = new ArticleMapper();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                articles.add(articleMapper.getEntity(resultSet, 1, 2, 3, 4, 5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    public Integer getId(Article entity) {
        String query = "select id from articles where name='" + entity.getName() + "'";
        Mapper<Article> articleMapper = new ArticleMapper();


        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer id = null;
            while(resultSet.next()) {
                id = articleMapper.getId(resultSet);
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
