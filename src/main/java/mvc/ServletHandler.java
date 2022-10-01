package mvc;

import java.lang.annotation.*;

/**
 * @author 陈希冉
 * @version 1.0
 * 演示
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServletHandler {

    /**
     * 等价于urlPatterns注解
     * @return
     */
    String[] value();

    /**
     * Controller或其方法所处理的请求的访问路径
     * 注意，该功能实现时，并没有检查指定的url是否会重复。
     * 在使用时不要填写重复的url，否则可能会跳转到不理想的方法中。
     * 如果觉得有需要，可以自己改进
     * @return
     */
    String[] urlPatterns() default {};

    /**
     * 请求方式，默认支持4种常见的请求方式。HTTP请求一共有8个
     * @return
     */
    String[] type() default {RequestType.GET, RequestType.POST, RequestType.PUT, RequestType.DELETE};

}
