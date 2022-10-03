package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PictureBook {
    @DbField("picture_book_id")
    private Integer pictureBookId;
    @DbField("user_id")
    private User user;
    @DbField("picture_book_name")
    private String pictureBookName;
    @DbField("picture_book_page_size")
    private Integer pageSize;
    private List<PictureBookImg> pictureBookImgList;
    private List<PictureBookMusic> pictureBookMusicList;

    public PictureBook(Integer pictureBookId, User user, String pictureBookName, Integer pageSize) {
        this.pictureBookId = pictureBookId;
        this.user = user;
        this.pictureBookName = pictureBookName;
        this.pageSize = pageSize;
    }
}
