package currents;

public class ThreadLocalExample {
    private static ThreadLocal<Integer>threadLocal=ThreadLocal.withInitial(()->0);

    public static void main(String[] args) {
        Runnable task=()->{
            int value=threadLocal.get();
            value++;
            threadLocal.set(value);
            System.out.println(Thread.currentThread().getName()+" Value:"+threadLocal.get());
            threadLocal.remove();
            System.out.println(Thread.currentThread().getName()+" Value:"+threadLocal.get());
        };
        Thread thread1=new Thread(task,"thread1");
        Thread thread2=new Thread(task,"thread2");
        thread1.start();
        thread2.start();
    }
}
