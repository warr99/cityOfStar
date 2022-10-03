package controller;

import com.alibaba.fastjson2.JSONObject;
import pojo.po.User;
import pojo.vo.ApiMsg;
import service.UserService;
import utils.MD5Util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class UserController {
    UserService userService = null;

    public ApiMsg<User> login(HttpServletRequest req, HttpServletResponse resp, JSONObject params) throws Exception {
        String phoneNumber = params.getString("phoneNumber");
        String password = params.getString("password");

        if ("".equals(phoneNumber) || "".equals(password)) {
            return ApiMsg.paramsError("请求参数缺失");
        }
        System.out.println(MD5Util.encode(password));
        User user = userService.login(phoneNumber, password);
        if (user != null) {
            Cookie currentUser = new Cookie("currentUser", user.getUserId().toString());
            resp.addCookie(currentUser);
            return new ApiMsg<>(user);
        }
        return ApiMsg.userError("用户名或密码错误");
    }


    public ApiMsg register(HttpServletRequest req, HttpServletResponse resp, JSONObject params) {
        String username = params.getString("username");
        String phoneNumber = params.getString("phoneNumber");
        String password = params.getString("password");
        boolean phoneExited = userService.isPhoneExited(phoneNumber);
        if (phoneExited) {
            return ApiMsg.alreadyExist("该账号已存在，请更换注册手机号码");
        }
        boolean usernameExited = userService.isUsernameExited(username);
        if (usernameExited) {
            return ApiMsg.alreadyExist("该用户名已被占用");
        }
        boolean b = userService.register(username, phoneNumber, password);
        if (b) {
            return ApiMsg.ok("注册成功");
        }
        return ApiMsg.exception("因未知原因注册失败");
    }
}
