package currents;

import java.util.concurrent.*;

public class CyclicBarrierExample1 {
    private static final int threadCount = 550;
    private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            Thread.sleep(1000);
            threadPool.execute(() -> {
                try {
                    test(threadId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        threadPool.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println("threadNum:" + threadNum + " is ready");
        try {
            cyclicBarrier.await(60, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("------CyclicBarrierException------");
        }
        System.out.println("threadNum:" + threadNum + " is finished");
    }
}
