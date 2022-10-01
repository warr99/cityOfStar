package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author WARRIOR
 * @version 1.0
 * Story
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Story {
    @DbField("story_id")
    private Integer storyId;
    @DbField("user_id")
    private User user;
    @DbField("story_status")
    private Integer storyStatus;
    @DbField("story_context")
    private String storyContext;
    @DbField("story_time")
    private Date publishTime;
    @DbField("story_check")
    protected Integer checkState;
    @DbField("story_like")
    protected Integer likeNum;

}
