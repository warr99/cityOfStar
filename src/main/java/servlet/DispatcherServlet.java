package servlet;

import com.alibaba.fastjson2.JSONObject;
import ioc.BeanFactory;
import mvc.RequestType;
import pojo.vo.ApiMsg;
import utils.GetParamUtil;
import utils.ResponseUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @author 陈希冉
 * @version 1.0
 * DispatcherServlet
 */


@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private BeanFactory beanFactory;
    private static final String HTML = ".html";
    private static final String JSP = ".jsp";
    private static final String JSON = "application/json";
    private static final String COMMON_FROM = "application/x-www-form-urlencoded";
    private static final String FILE_FROM = "multipart/form-data";

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext application = getServletContext();
        Object o = application.getAttribute("beanFactory");
        if (o != null) {
            beanFactory = (BeanFactory) o;
        } else {
            throw new RuntimeException("IOC容器获取失败");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doDispatch(req, resp);
    }

    protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        String[] split = uri.split("/");
        if (uri.contains(HTML) || uri.contains(JSP) || split.length < 1) {
            super.service(req, resp);
            return;
        }

        //获取 controller
        Object controllerBean = beanFactory.getBean(split[1]);
        if (controllerBean == null) {
            resp.setStatus(404);
            ResponseUtil.send(resp, ApiMsg.notFound("该controller不存在"));
            return;
        }

        //获取方法名,若没有方法名,默认调用 index
        String methodName = null;
        if (split.length < 3) {
            methodName = "index";
        } else {
            methodName = split[2];
        }
        try {
            Method[] declaredMethods = controllerBean.getClass().getDeclaredMethods();
            JSONObject params = null;
            for (Method method : declaredMethods) {
                if (methodName.equals(method.getName())) {
                    //获取参数
                    params = getParams(method, req);
                    //调用方法
                    Object invoke = doInvoke(method, controllerBean, req, resp, params);
                    ResponseUtil.send(resp, JSONObject.toJSONString(invoke));
                    return;
                }
            }
            //如果既没有指定要调用的方法,没有找到index方法,404
            resp.setStatus(404);
            ResponseUtil.send(resp, ApiMsg.notFound("找不到默认的index方法"));
            return;
        } catch (IllegalAccessException e) {
            System.err.println("反射的方法参数不匹配：" + e.getMessage());
            resp.setStatus(500);
            ResponseUtil.send(resp, ApiMsg.exception("反射的方法参数不匹配：" + e.getMessage()));
        } catch (InvocationTargetException e) {
            e.getCause().printStackTrace();
            resp.setStatus(500);
            ResponseUtil.send(resp, ApiMsg.exception("后端执行异常：" + e.getCause().getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            ResponseUtil.send(resp, ApiMsg.exception(e));
        }

    }

    protected Object doInvoke(Method method, Object controllerBean, HttpServletRequest req, HttpServletResponse resp, JSONObject params) throws InvocationTargetException, IllegalAccessException {
        method.setAccessible(true);
        Object invoke = method.invoke(controllerBean, req, resp, params);
        return invoke;
    }

    protected JSONObject getParams(Method method, HttpServletRequest req) {
        JSONObject params = null;

        // 获取请求参数
        String reqMethod = req.getMethod();
        if (RequestType.GET.equals(reqMethod)) {
            // GET请求从URL中获取参数
            params = GetParamUtil.getJsonByUrl(req);
        } else {
            // 其他请求，先判断是不是用json传参
            String contentType = req.getContentType();
            if (JSON.equals(contentType)) {
                // 参数通过json提交
                params = GetParamUtil.getJsonByJson(req);
            } else if (COMMON_FROM.equals(contentType)) {
                // 普通form表单提交
                params = GetParamUtil.getJsonByForm(req);
            } else if (contentType.contains(FILE_FROM)) {
                // 带有文件的from表单提交
                params = GetParamUtil.getFileJson(req);
            } else {
                throw new UnsupportedOperationException("暂不支持该请求方式");
            }

        }
        return params;
    }
}

