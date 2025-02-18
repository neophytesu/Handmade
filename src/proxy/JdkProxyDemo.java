package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface MyService {
    void server();
}

class MyServiceImpl implements MyService {
    @Override
    public void server() {
        System.out.println("serving...");
    }
}

class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before method: " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("After method: " + method.getName());
        return result;
    }
}

public class JdkProxyDemo {
    public static void main(String[] args) {
        MyService service = new MyServiceImpl();
        MyService proxy = (MyService) Proxy.newProxyInstance(
                service.getClass().getClassLoader(),
                service.getClass().getInterfaces(),
                new MyInvocationHandler(service)
        );
        proxy.server();
    }
}
