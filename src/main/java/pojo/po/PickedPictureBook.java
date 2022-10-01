package pojo.po;

import dao.DbField;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class PickedPictureBook {
    @DbField("picked_picture_book_id")
    private Integer pickedPictureBookId;
    @DbField("picture_book_id")
    private PictureBook pictureBook;
}
