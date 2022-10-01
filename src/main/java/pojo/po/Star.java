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
public class Star {
    @DbField("star_id")
    private Integer starId;
    @DbField("story_id")
    private Integer storyId;

}
