package model.jdbc.impl;

import model.entity.Article;
import model.entity.Category;
import model.entity.Image;
import model.jdbc.ConnectionPool;
import model.jdbc.Dao;
import model.jdbc.DaoFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class FactoryJDBC extends DaoFactory {
    @Override
    public Dao<Article> createArticleDao() {
        return new ArticleJDBC(getConnection());
    }

    @Override
    public Dao<Category> createCategoryDao() {
        return new CategoryJDBC(getConnection());
    }

    @Override
    public Dao<Image> createImageDao() {
        return new ImageJDBC(getConnection());
    }

    private Connection getConnection() {
        try {
            return ConnectionPool.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
