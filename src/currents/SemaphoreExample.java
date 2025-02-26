package currents;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final int threadCount=550;

    public static void main(String[] args) {
        ExecutorService threadPool= Executors.newFixedThreadPool(300);
        final Semaphore semaphore=new Semaphore(20);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum=i;
            threadPool.execute(()->{
                try {
                    semaphore.acquire();
                    test(threadNum);
                    semaphore.release();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
        System.out.println("All tasks completed.");
    }
    public static void test(int threadNum) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("threadNum="+threadNum);
        Thread.sleep(1000);
    }
}
