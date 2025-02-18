package spring.ioc;

public interface BeanFactory {
    Object getBean(String name)throws Exception;
}
