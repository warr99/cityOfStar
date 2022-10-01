package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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
    private Integer checkState;
    @DbField("story_like")
    private Integer likeNum;

    public Story(Integer storyId, User user, Integer storyStatus, String storyContext, Date publishTime, Integer checkState, Integer likeNum) {
        this.storyId = storyId;
        this.user = user;
        this.storyStatus = storyStatus;
        this.storyContext = storyContext;
        this.publishTime = publishTime;
        this.checkState = checkState;
        this.likeNum = likeNum;
    }

    private List<StoryImg> storyRelatedImgList;

}
