package model.jdbc.mappers;

import model.entity.Article;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArticleMapper implements Mapper<Article> {
    @Override
    public Article getEntity(ResultSet resultSet, int... index) {
        try {
            Article article = new Article();
            article.setId(resultSet.getInt(index[0]));
            article.setName(resultSet.getString(index[1]));
            article.setHref(resultSet.getString(index[2]));
            article.setContains(resultSet.getString(index[3]));
            article.setCategoryId(resultSet.getInt(index[4]));
            return article;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
