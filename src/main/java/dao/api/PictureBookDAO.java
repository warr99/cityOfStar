package dao.api;

import pojo.po.PictureBook;
import pojo.po.PictureBookImg;

/**
 * @author WARRIOR
 * @version 1.0
 */
public interface PictureBookDAO {


    /**
     * 新增绘本
     *
     * @param pictureBookName 绘本名字
     * @param userId          所属用户id
     * @return 受影响行数
     */
    public Integer addPictureBook(String pictureBookName, Integer userId);

    /**
     * 根据绘本名称获取某个用户的绘本
     *
     * @param pictureBookName 绘本名称
     * @param userId          用户id
     * @return 绘本对象
     */
    public PictureBook getUserPictureBookByName(String pictureBookName, Integer userId);


    /**
     * 为绘本添加图片
     *
     * @param pictureBookImg
     * @return
     */
    public Integer addPictureBookPImg(PictureBookImg pictureBookImg);
}
