package model.service;

import model.entity.Category;
import model.jdbc.Dao;
import model.jdbc.DaoFactory;

import java.util.List;

public class CategoryService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private Dao<Category> categoryDao = daoFactory.createCategoryDao();

    public List<Category> getCategories(){
        return categoryDao.readAll();
    }

    public void saveCategory(Category category){
        categoryDao.create(category);
    }

    public Integer getCategoryId(Category category){
        return categoryDao.getId(category);
    }
}
