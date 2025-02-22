import java.util.Properties;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 获取系统属性
        Properties properties = System.getProperties();

        // 打印所有系统属性及其值
        properties.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}