package spi;

public class Logback implements Logger {
    @Override
    public void info(String msg) {
        System.out.println("Logback info: " + msg);
    }

    @Override
    public void debug(String msg) {
        System.out.println("Logback debug: " + msg);
    }
}
