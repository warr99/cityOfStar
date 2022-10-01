package ioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈希冉
 * @version 1.0
 * 创建beanMap(),并实现依赖注入
 */
public class ClassPathApplicationContext implements BeanFactory {
    private Map<String, Object> beanMap = new HashMap<>();
    private String path = "applicationContext.xml";

    public ClassPathApplicationContext() {
        this("applicationContext.xml");
    }

    public ClassPathApplicationContext(String path) {
        if ("".equals(path) || path == null) {
            throw new RuntimeException("ioc容器的配置文件没有指令");
        }
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //创建bean的实例化对象，并放入beanMap()中
        try {
            //获取配置文件中所有的bean标签 beanNodeList
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                //获取单个node
                Node node = beanNodeList.item(i);
                //如果是元素节点
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) node;
                    //获取id和对应的className
                    String id = beanElement.getAttribute("id");
                    String className = beanElement.getAttribute("class");
                    //利用反射创建bean实例
                    Object o = Class.forName(className).newInstance();
                    //将bean对象保存到map容器中
                    beanMap.put(id, o);
                }
            }
            //组装bean之间的依赖关系
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node node = beanNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) node;
                    String id = beanElement.getAttribute("id");
                    //获取每一个bean的子节点列表
                    NodeList childNodeList = beanElement.getChildNodes();
                    for (int j = 0; j < childNodeList.getLength(); j++) {
                        Node childNode = childNodeList.item(j);
                        //如果该bean的实例化对象存在依赖关系
                        if (childNode.getNodeType() == Node.ELEMENT_NODE && "property".equals(childNode.getNodeName())) {
                            Element propertyElement = (Element) childNode;
                            //获取property名和对应的实例名
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            //找到property对应的实例
                            Object refObj = beanMap.get(propertyRef);
                            //将 refObj 设置到当前 bean 对应的实例的 property 属性上去
                            Object beanObj = beanMap.get(id);
                            Class beanClass = beanObj.getClass();
                            Field propertyField = beanClass.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj, refObj);
                        }
                    }

                }
            }
        } catch (ParserConfigurationException | SAXException | IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
