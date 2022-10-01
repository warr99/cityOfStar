package trans;


import utils.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author 陈希冉
 * @version 1.0
 * 处理事务
 */


public class TransactionManager {



    /**
     * 开启事务
     * @throws SQLException
     */
    public static void beginTrans() throws SQLException {
        ConnUtil.getConn().setAutoCommit(false);
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public static void commit() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.closeConn();
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public static void rollback() throws SQLException {
        Connection conn = ConnUtil.getConn();
        conn.rollback();
        ConnUtil.closeConn();
    }
}
