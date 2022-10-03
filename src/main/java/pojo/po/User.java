package pojo.po;

import dao.DbField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author WARRIOR
 * @version 1.0
 * User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @DbField("user_id")
    private Integer userId;
    @DbField("user_name")
    private String username;
    @DbField("user_phone")
    private String phoneNumber;
    @DbField("user_password")
    private String password;
    @DbField("user_head")
    private String headPortrait;
    @DbField("ip_id")
    private UserIp userIp;
    @DbField("user_sex")
    private String userSex;
    @DbField("user_status")
    private String userStatus;

    private List<Story> userStoryList;
    private List<PictureBook> pictureBookList;


    public User(Integer userId) {
        this.userId = userId;
    }

    public User(Integer userId, String username, String phoneNumber, String password, String headPortrait, String userSex, String userStatus) {
        this.userId = userId;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.headPortrait = headPortrait;
        this.userSex = userSex;
        this.userStatus = userStatus;
    }


    public User(String username, String phoneNumber, String password) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
