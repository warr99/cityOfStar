package dao.impl;

import dao.BaseDAO;
import dao.api.PictureBookDAO;
import pojo.po.PictureBook;
import pojo.po.PictureBookImg;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class PictureBookDaoImpl extends BaseDAO<PictureBook> implements PictureBookDAO {

    @Override
    public Integer addPictureBook(String pictureBookName, Integer userId) {
        return executeUpdate("insert into tb_picture_book (user_id, picture_book_name) value (?,?)",
                userId, pictureBookName);
    }

    @Override
    public PictureBook getUserPictureBookByName(String pictureBookName, Integer userId) {
        return super.load("select * from tb_picture_book where picture_book_name = ? and user_id = ?", pictureBookName, userId);
    }

    @Override
    public Integer addPictureBookPImg(PictureBookImg pictureBookImg) {
        return null;
    }
}
