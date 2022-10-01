package pojo.po;

import dao.DbField;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class UserIp {
    @DbField("ip_id")
    private Integer UserIpId;
    @DbField("user_id")
    private User user;



}
