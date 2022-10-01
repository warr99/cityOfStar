package dao;


import utils.ConnUtil;

import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 陈希冉
 * @version 1.0
 */

public abstract class BaseDAO<T> {
    protected Connection conn;
    protected PreparedStatement preparedStatement;
    protected ResultSet resultSet;
    private Class entityClass;

    public BaseDAO() {

        Type genericType = getClass().getGenericSuperclass();
        //ParameterizedType 参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        //获取到的<T>中的T的真实的类型
        Type actualType = actualTypeArguments[0];

        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    protected Connection getConn() throws Exception {
        return ConnUtil.getConn();
    }

    /**
     * 给预处理命令对象设置参数
     * @param preparedStatement
     * @param params
     * @throws SQLException
     */
    private void setParams(PreparedStatement preparedStatement, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
    }

    /**
     * 执行插入操作
     * @param sql
     * @param params
     * @return
     */
    protected int executeUpdate(String sql, Object... params) {
        boolean insertFlag = false;

        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            conn = getConn();
            if (insertFlag) {
                preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement = conn.prepareStatement(sql);
            }
            setParams(preparedStatement, params);
            int count = preparedStatement.executeUpdate();

            if (insertFlag) {
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return ((Long) resultSet.getLong(1)).intValue();
                }
            }
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 通过反射技术给obj对象的property属性赋propertyValue值
     * @param obj
     * @param property
     * @param propertyValue
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    private void setValue(Object obj, String property, Object propertyValue) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Class clazz = obj.getClass();

        //获取property这个字符串对应的属性名 ， 比如 "fid"  去找 obj对象中的 fid 属性
        Field field = clazz.getDeclaredField(property);
        if (field != null) {

            //获取当前字段的类型名称
            String typeName = field.getType().getName();
            //判断如果是自定义类型，则需要调用这个自定义类的带一个参数的构造方法，创建出这个自定义的实例对象，然后将实例对象赋值给这个属性
            if (isMyType(typeName)) {
                Class typeNameClass = Class.forName(typeName);
                Constructor constructor = typeNameClass.getDeclaredConstructor(Integer.class);
                propertyValue = constructor.newInstance(propertyValue);
            }
            field.setAccessible(true);
            field.set(obj, propertyValue);
        }
    }

    private static boolean isNotMyType(String typeName) {
        List<String> list = new ArrayList<>();
        list.add("java.lang.Integer");
        list.add("java.lang.String");
        list.add("java.util.Date");
        list.add("java.sql.Date");
        list.add("java.sql.Blob");
        list.add("java.lang.Double");
        return list.contains(typeName);
    }

    private static boolean isMyType(String typeName) {
        return !isNotMyType(typeName);
    }

    /**
     * 执行复杂查询，返回例如统计结果
     * @param sql
     * @param params
     * @return
     */
    protected Object[] executeComplexQuery(String sql, Object... params) {

        try {
            conn = getConn();
            preparedStatement = conn.prepareStatement(sql);
            setParams(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //获取结果集的列数
            int columnCount = resultSetMetaData.getColumnCount();
            Object[] columnValueArr = new Object[columnCount];
            //6.解析rs
            if (resultSet.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = resultSet.getObject(i + 1);
                    columnValueArr[i] = columnValue;
                }
                return columnValueArr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 执行查询，返回单个实体对象
     * @param sql
     * @param params
     * @return
     */
    protected T load(String sql, Object... params) {

        try {
            conn = getConn();
            preparedStatement = conn.prepareStatement(sql);
            setParams(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            //获取结果集的列数
            int columnCount = resultSetMetaData.getColumnCount();
            //6.解析rs
            if (resultSet.next()) {
                //构造一个需要返回的对象
                T entity = (T) entityClass.newInstance();
                Map<String, String> map = matchFiled(entity);
                for (int i = 0; i < columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);
                    setValue(entity, map.get(columnName), columnValue);
                }
                return entity;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;
    }

    /**
     * 执行查询，返回List
     * @param sql
     * @param params
     * @return
     */
    protected List<T> executeQuery(String sql, Object... params) {
        List<T> list = new ArrayList<>();

        try {
            conn = getConn();
            preparedStatement = conn.prepareStatement(sql);
            setParams(preparedStatement, params);
            resultSet = preparedStatement.executeQuery();

            //通过rs可以获取结果集的元数据
            //元数据：描述结果集数据的数据 , 简单讲，就是这个结果集有哪些列，什么类型等等

            ResultSetMetaData rsmd = resultSet.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            //6.解析rs
            while (resultSet.next()) {
                T entity = (T) entityClass.newInstance();
                Map<String, String> map = matchFiled(entity);
                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnLabel(i + 1);
                    Object columnValue = resultSet.getObject(i + 1);
                    setValue(entity, map.get(columnName), columnValue);
                }
                list.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return list;
    }

    /**
     * 将对象的字段与数据库的字段存入map中
     * @param po
     * @return
     */
    protected Map<String, String> matchFiled(T po) {
        Map<String, String> map = new HashMap<>(16);
        Class<?> clazz = po.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            //实体类上数据库真实字段名
            String value = field.getAnnotation(DbField.class).value();
            map.put(value, field.getName());
        }
        return map;
    }

}
