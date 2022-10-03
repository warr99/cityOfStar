package controller;

import com.alibaba.fastjson2.JSONObject;
import pojo.po.User;
import pojo.vo.ApiMsg;
import service.PictureBookService;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author WARRIOR
 * @version 1.0
 */
public class PictureBookController {
    PictureBookService pictureBookService = null;
    UserService userService = null;

    public ApiMsg addPictureBookImg(HttpServletRequest req, HttpServletResponse resp, JSONObject params) {
        Integer pictureBookId = params.getInteger("pictureBookId");
        String savePath = params.getString("file1");
        String position = params.getString("position");
        Integer pageNum = params.getInteger("pageNum");
        boolean b = pictureBookService.addPictureBookImg(pictureBookId, savePath, position, pageNum);
        if (!b) {
            return ApiMsg.exception("添加失败");
        }
        return new ApiMsg<>();
    }

    public ApiMsg addPictureBook(HttpServletRequest req, HttpServletResponse resp, JSONObject params) {
        Integer userId = params.getInteger("userId");
        String pictureBookName = params.getString("pictureBookName");
        User user = userService.getUserById(userId);
        if (user == null) {
            return ApiMsg.notFound("用户不存在");
        }
        boolean b = pictureBookService.addPictureBook(userId, pictureBookName);
        if (!b) {
            return ApiMsg.alreadyExist("绘本名重复");
        }
        return new ApiMsg<>();
    }

}
