package service;


import dao.api.UserDAO;
import pojo.po.User;

/**
 * @author WARRIOR
 * @version 1.0
 * 演示
 */
public class UserService {
    UserDAO userDAO = null;

    public User login(String phoneNumber, String password) {
        User user = userDAO.getUser(phoneNumber, password);
        return user;
    }

    public boolean register(String username, String phoneNumber, String password) {
        User user = new User(username, phoneNumber, password);
        int affectedRowNum = userDAO.addUser(user);
        if (affectedRowNum > 0) {
            return true;
        }
        return false;
    }

    public boolean isPhoneExited(String phoneNumber) {
        User user = userDAO.getUserByPhoneNumber(phoneNumber);
        if (user == null) {
            return false;
        }
        return true;
    }

    public boolean isUsernameExited(String username) {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }
}
