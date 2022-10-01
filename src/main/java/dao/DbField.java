package dao;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DbField {

    /**
     * 该成员变量对应的数据库表中的字段名
     */
    String value();
}
