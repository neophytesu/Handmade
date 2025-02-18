package spring.ioc;

public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location)throws Exception;
}
