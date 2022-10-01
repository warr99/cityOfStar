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
@AllArgsConstructor
@NoArgsConstructor
public class PictureBookMusic {
    @DbField("music_id")
    private Integer musicId;
    @DbField("music_url")
    private String musicUrl;
    @DbField("picture_book_page_num")
    private Integer pageNum;
}
