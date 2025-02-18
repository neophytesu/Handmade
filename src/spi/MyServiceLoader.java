package spi;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class MyServiceLoader<S> {
    private final Class<S> service;
    private final List<S> providers = new ArrayList<>();
    private final ClassLoader classLoader;

    public static <S> MyServiceLoader<S> load(Class<S> service) {
        return new MyServiceLoader<>(service);
    }

    private MyServiceLoader(Class<S> service) {
        this.service = service;
        this.classLoader = Thread.currentThread().getContextClassLoader();
        doLoad();
    }

    private void doLoad() {
        try {
            Enumeration<URL> urls = classLoader.getResources("META-INF/services/" + service.getName());
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                System.out.println("File = " + url.getPath());
                URLConnection urlConnection = url.openConnection();
                urlConnection.setUseCaches(false);
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String className = bufferedReader.readLine();
                while (className != null) {
                    Class<?> clazz = Class.forName(className, false, classLoader);
                    if (service.isAssignableFrom(clazz)) {
                        Constructor<? extends S> constructor = (Constructor<? extends S>) clazz.getConstructor();
                        S instance = constructor.newInstance();
                        providers.add(instance);
                    }
                    className = bufferedReader.readLine();
                }
            }
        } catch (Exception e) {
            System.out.println("读取文件异常");
        }
    }

    public List<S> getProviders() {
        return providers;
    }
}
