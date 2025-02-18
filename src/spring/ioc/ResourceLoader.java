package spring.ioc;

import java.net.URL;

public class ResourceLoader {
    public Resource getResource(String path) {
        URL resource=this.getClass().getClassLoader().getResource(path);
        return new UrlResource(resource);
    }
}
