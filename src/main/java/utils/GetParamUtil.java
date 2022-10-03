package utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author wtk
 * @description 从HttpServletRequest中获取参数
 */
public class GetParamUtil {
    /**
     * 获取json参数，保存在JSONObject对象中
     *
     * @param req
     * @return
     */
    public static JSONObject getJsonByJson(HttpServletRequest req) {
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                    req.getInputStream(), StandardCharsets.UTF_8));
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null) {
                responseStrBuilder.append(inputStr);
            }
            JSONObject resultJson = JSONObject.parseObject(responseStrBuilder.toString());
            return resultJson;
        } catch (Exception e) {
            System.err.println("解析json 失败 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过url获取参数，存到json中
     *
     * @param req
     * @return
     */
    public static JSONObject getJsonByUrl(HttpServletRequest req) {
        try {
            String urlParams = req.getQueryString();
            if ("".equals(urlParams) || urlParams == null) {
                System.err.println("getJsonByUrl 无参数");
                return null;
            }
            //切割 & ，得到一个个键值对
            String[] params = urlParams.split("&");
            JSONObject json = new JSONObject();
            for (String p : params) {
                //切割键值对，存到json中
                String[] kv = p.split("=");
                json.put(kv[0], kv[1]);
            }
            return json;
        } catch (Exception e) {
            System.err.println("解析json 失败 " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过form表单获取参数，封装为对象。可能会遗漏数据
     *
     * @param req
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObjByForm(HttpServletRequest req, Class<T> clazz) {
        try {
            JSONObject json = new JSONObject();
            for (Field field : clazz.getDeclaredFields()) {
                String key = field.getName();
                String parameter = req.getParameter(key);
                json.put(key, parameter);
            }
            return json.toJavaObject(clazz);
        } catch (Exception ex) {
            System.err.println("解析 formData 失败。Message：" + ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 通过表单获取数据，保存在json中
     *
     * @param req
     * @return
     */
    public static JSONObject getJsonByForm(HttpServletRequest req) {
        JSONObject json = new JSONObject();
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            json.put(entry.getKey(), entry.getValue()[0]);
        }
        return json;
    }

    /**
     * 通过from表单获取参数封装到JSONObject中,
     * 将上传的文件(图片)保存到本地,并将形如"file1":"b2a573a86a2c4fb49dd7a920feae4dd0.jpeg"格式的信息封装到JSONObject中一并返回
     *
     * @param request
     * @return
     */

    public static JSONObject getFileJson(HttpServletRequest request) {
        JSONObject json = new JSONObject();
        Integer fileNum = 0;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        try {
            List<FileItem> fileList = upload.parseRequest(request);
            for (FileItem fileItem : fileList) {
                if (fileItem.isFormField()) {
                    String key = fileItem.getFieldName();
                    String value = fileItem.getString();
                    json.put(key, value);
                    //普通表单数据
                } else {
                    //文件类型数据
                    String[] contentType = fileItem.getContentType().split("/");
                    String savePath = "D:\\program\\upload\\" + UuidUtil.getHashCode() + "." + contentType[1];
                    System.out.println("save at:" + savePath);
                    InputStream inputStream = fileItem.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(savePath);
                    byte[] buff = new byte[1024];
                    int readLen = 0;
                    while ((readLen = inputStream.read(buff)) > 0) {
                        outputStream.write(buff, 0, readLen);
                    }
                    fileNum = fileNum + 1;
                    inputStream.close();
                    outputStream.close();
                    json.put("file" + fileNum.toString(), UuidUtil.getHashCode() + "." + contentType[1]);
                }
            }
        } catch (Exception e) {
            System.err.println("解析 form-data 失败。Message：" + e.getMessage());
            e.printStackTrace();
        }
        return json;
    }

}
