package model.service;

import model.entity.Image;
import model.jdbc.Dao;
import model.jdbc.DaoFactory;

public class ImageService {
    private DaoFactory daoFactory = DaoFactory.getInstance();
    private Dao<Image> imageDao = daoFactory.createImageDao();

    public void saveImage(Image image){
        imageDao.create(image);
    }
}
