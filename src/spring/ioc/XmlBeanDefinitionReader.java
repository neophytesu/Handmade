package spring.ioc;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader{
    public XmlBeanDefinitionReader(ResourceLoader resourceLoader) {
        super(resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws Exception {
        InputStream inputStream=getResourceLoader().getResource(location).getInputStream();
        doLoadBeanDefinitions(inputStream);
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws Exception {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder=factory.newDocumentBuilder();
        Document doc=builder.parse(inputStream);
        registerBeanDefinitions(doc);
        inputStream.close();
    }

    private void registerBeanDefinitions(Document doc) {
        Element root=doc.getDocumentElement();
        parseBeanDefinitions(root);
    }

    private void parseBeanDefinitions(Element root) {
        NodeList n1=root.getChildNodes();
        for (int i = 0; i < n1.getLength(); i++) {
            Node node=n1.item(i);
            if (node instanceof Element){
                Element ele=(Element)node;
                processBeanDefinition(ele);
            }
        }
    }
    protected void processBeanDefinition(Element ele) {
        String name=ele.getAttribute("id");
        String className=ele.getAttribute("class");
        BeanDefinition beanDefinition=new BeanDefinition();
        processProperty(ele,beanDefinition);
        beanDefinition.setBeanClassName(className);
        getRegistry().put(name,beanDefinition);
    }

    private void processProperty(Element ele, BeanDefinition beanDefinition) {
        NodeList propertyNode=ele.getElementsByTagName("property");
        for (int i = 0; i < propertyNode.getLength(); i++) {
            Node node=propertyNode.item(i);
            if (node instanceof Element){
                Element propertyEle=(Element) node;
                String name=propertyEle.getAttribute("name");
                String value=propertyEle.getAttribute("value");
                if (value !=null && !value.isEmpty()){
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name,value));
                }else {
                    String ref=propertyEle.getAttribute("ref");
                    if (ref==null|| ref.isEmpty()){
                        throw new IllegalArgumentException("Configuration problem: <property> element for property '"
                                + name + "' must specify a ref or value");
                    }
                    BeanReference beanReference=new BeanReference(ref,null);
                    beanDefinition.getPropertyValues().addPropertyValue(new PropertyValue(name,beanReference));
                }
            }
        }
    }
}
