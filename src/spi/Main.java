package spi;

public class Main {
    public static void main(String[] args) {
        LoggerService service = LoggerService.getService();
        service.info("Hello World!");
        service.debug("Hello World!");
    }
}
