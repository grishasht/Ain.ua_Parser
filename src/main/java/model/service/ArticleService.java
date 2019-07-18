package model.service;

import model.entity.Article;
import model.jdbc.Dao;
import model.jdbc.DaoFactory;

import java.util.List;

public class ArticleService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private Dao<Article> articleDao = daoFactory.createArticleDao();

    public List<Article> getArticles(){
        return articleDao.readAll();
    }

    public void saveArticle(Article article){
        articleDao.create(article);
    }

    public Integer getArticleId(Article article){
        return articleDao.getId(article);
    }
}
