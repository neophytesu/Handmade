package spring.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AutowireCapableBeanFactory extends AbstractBeanFactory{
        protected void applyPropertyValues(Object bean,BeanDefinition mbd)throws Exception{
            if (bean instanceof BeanFactoryAware){
                ((BeanFactoryAware)bean).setBeanFactory(this);
            }
            for (PropertyValue propertyValue : mbd.getPropertyValues().getPropertyValues()) {
                Object value=propertyValue.value();
                if (value instanceof BeanReference){
                    BeanReference beanReference=(BeanReference)value;
                    value=getBean(beanReference.name());
                }
                try {
                    Method declaredMethod=bean.getClass().getDeclaredMethod(
                            "set"+propertyValue.name().substring(0,1).toUpperCase()
                            +propertyValue.name().substring(1),value.getClass()
                    );
                    declaredMethod.setAccessible(true);
                    declaredMethod.invoke(bean,value);
                }catch (NoSuchMethodException e){
                    Field declaredField=bean.getClass().getDeclaredField(propertyValue.name());
                    declaredField.setAccessible(true);
                    declaredField.set(bean,value);
                }
            }
        }
}
