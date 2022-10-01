package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureBookImg {
    @DbField("picture_book_img_id")
    private Integer pictureBookImgId;
    @DbField("page")
    private Integer page;
    @DbField("position")
    private String position;
    @DbField("picture_book_img_url")
    private String pictureBookImgUrl;
    @DbField("picture_book_id")
    private PictureBook pictureBook;
}
