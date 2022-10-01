package filters;

import trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author 陈希冉
 * @version 1.0
 * 演示
 */

//@WebFilter(urlPatterns = {"/*"})
//public class OpenSessionInViewFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        try {
//            TransactionManager.beginTrans();
//            //放行
//            filterChain.doFilter(servletRequest, servletResponse);
//            TransactionManager.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                TransactionManager.rollback();
//            } catch (SQLException sqlException) {
//                sqlException.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
