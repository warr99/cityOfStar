package ioc;

/**
 * @author 陈希冉
 * @version 1.0
 * 演示
 */
public interface BeanFactory {
    /**
     * 根据id获取对应的bean
     * @param id
     * @return
     */
    Object getBean(String id);
}
