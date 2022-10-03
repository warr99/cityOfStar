package service;

import dao.api.PictureBookDAO;
import pojo.po.PictureBook;
import pojo.po.User;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class PictureBookService {
    PictureBookDAO pictureBookDAO = null;


    public PictureBook getPictureBookByName(String pictureBookName, Integer userId) {
        return pictureBookDAO.getUserPictureBookByName(pictureBookName, userId);
    }

    public boolean addPictureBook(Integer userId, String pictureBookName) {
        PictureBook pictureBook = getPictureBookByName(pictureBookName, userId);
        if (pictureBook != null) {
            return false;
        }
        Integer row = pictureBookDAO.addPictureBook(pictureBookName, userId);
        if (row == 0) {
            return false;
        }
        return true;
    }
}
