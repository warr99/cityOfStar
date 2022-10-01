package controller;

import com.alibaba.fastjson2.JSONObject;
import pojo.po.User;
import pojo.vo.ApiMsg;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 陈希冉
 * @version 1.0
 * 演示
 */
public class UserController {
    UserService userService = null;
    public ApiMsg<User> login(HttpServletRequest req, HttpServletResponse resp, JSONObject params) throws Exception {
        String username = params.getString("username");
        String password = params.getString("password");
        if ("".equals(username) || "".equals(password)) {
            return ApiMsg.paramsError("请求参数缺失");
        }
        System.out.println("username = " + username + ", password = " + password);
        if("111".equals(username)&&"666".equals(password)) {
            User user = new User();
            return new ApiMsg<>(user);
        }
        return ApiMsg.userError("用户名或密码错误");
    }
}