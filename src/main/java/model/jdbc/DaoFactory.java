package model.jdbc;

import model.entity.Article;
import model.entity.Category;
import model.entity.Image;
import model.jdbc.impl.FactoryJDBC;

public abstract class DaoFactory {

    public abstract Dao<Article> createArticleDao();

    public abstract Dao<Category> createCategoryDao();

    public abstract Dao<Image> createImageDao();

    private static class SingletonHolder {
        public static final DaoFactory INSTANCE = new FactoryJDBC();
    }

    public static DaoFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
