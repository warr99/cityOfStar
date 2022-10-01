package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WARRIOR
 * @version 1.0
 * 动态图片
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoryImg {
    @DbField("img_id")
    private Integer storyImgId;
    @DbField("story_id")
    private Story story;
    @DbField("img_url")
    private String imgUrl;
    @DbField("img_order")
    private Integer imgOrder;
}
