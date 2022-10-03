package dao.api;

import pojo.po.User;

/**
 * @author WARRIOR
 * @version 1.0
 */
public interface UserDAO {

    /**
     * 根据手机号码（账号）和密码获取用户信息
     * @param phoneNumber 手机号码（账号）
     * @param password 密码
     * @return User or Null
     */
    public User getUser(String phoneNumber, String password);

    /**
     * 创建新用户
     * @param user
     * @return 受影响行数
     */
    public int addUser(User user);

    /**
     * 根据手机号码获取用户信息
     * @param phoneNumber
     * @return
     */
    public User getUserByPhoneNumber(String phoneNumber);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    public User getUserByUsername(String username);

    /**
     * 根据id获取用户
     * @param userId
     * @return
     */
    public User getUserById(Integer userId);
}
