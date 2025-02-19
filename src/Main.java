import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put(i);
                    System.out.println("生产者添加元素: " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread consumer = new Thread(() -> {
            try {
                int count = 0;
                while (true) {
                    int element = queue.take();
                    System.out.println("消费者取出元素: " + element);
                    count++;
                    if (count == 10) {
                        break;
                    }
                }
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        countDownLatch.await();
        producer.interrupt();
        consumer.interrupt();
    }
}