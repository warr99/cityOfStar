package service;

import dao.api.PictureBookDAO;
import pojo.po.PictureBook;
import pojo.po.PictureBookImg;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class PictureBookService {
    PictureBookDAO pictureBookDAO = null;


    public PictureBook getUserPictureBookByName(String pictureBookName, Integer userId) {
        return pictureBookDAO.getUserPictureBookByName(pictureBookName, userId);
    }

    public PictureBook getPictureBookById(Integer pictureBookId) {
        return pictureBookDAO.getPictureBookById(pictureBookId);
    }

    public boolean addPictureBook(Integer userId, String pictureBookName) {
        PictureBook pictureBook = getUserPictureBookByName(pictureBookName, userId);
        if (pictureBook != null) {
            return false;
        }
        Integer row = pictureBookDAO.addPictureBook(pictureBookName, userId);
        if (row == 0) {
            return false;
        }
        return true;
    }

    public boolean addPictureBookImg(Integer pictureBookId, String savePath, String position, Integer pageNum) {
        PictureBook pictureBook = getPictureBookById(pictureBookId);
        if (pictureBook == null) {
            return false;
        }
        Integer rowNum = pictureBookDAO.addPictureBookImg(new PictureBookImg(null, pageNum, position, savePath, pictureBook));
        if (rowNum.equals(0)) {
            return false;
        }
        return true;
    }

}
