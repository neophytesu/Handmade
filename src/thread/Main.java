package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        MyThreadPool myThreadPool = new MyThreadPool(2,4,1, TimeUnit.SECONDS,1, TimeUnit.MINUTES,new ArrayBlockingQueue<>(2),new DiscardRejectHandle());
        for (int i = 0; i < 5; i++) {
            final int fi=i;
            myThreadPool.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName()+" "+fi);
            });
            if (i==2){
                myThreadPool.shutdown();
            }
        }
        System.out.println("主线程没有被阻塞");
    }
}
