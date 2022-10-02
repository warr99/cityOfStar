package dao.impl;

import dao.BaseDAO;
import dao.api.UserDAO;
import pojo.po.User;
import utils.MD5Util;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class UserDaoImpl extends BaseDAO<User> implements UserDAO {

    @Override
    public User getUser(String phoneNumber, String password) {
        return super.load("select * from tb_user where user_phone = ? and user_password = ? ", phoneNumber, MD5Util.encode(password));
    }

    @Override
    public int addUser(User user) {

        return executeUpdate("insert into tb_user (user_name, user_phone, user_password) value (?,?,?)",
                user.getUsername(),
                user.getPhoneNumber(),
                MD5Util.encode(user.getPassword()));

    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) {
        return super.load("select * from tb_user where user_phone = ? ", phoneNumber);
    }

    @Override
    public User getUserByUsername(String username) {
        return super.load("select * from tb_user where user_name = ? ", username);
    }
}
