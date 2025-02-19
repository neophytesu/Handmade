package collections;

import java.util.concurrent.DelayQueue;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        delayQueue.add(new DelayedTask(2000, () -> System.out.println("Task 2")));
        delayQueue.add(new DelayedTask(1000, () -> System.out.println("Task 1")));
        delayQueue.add(new DelayedTask(3000, () -> System.out.println("Task 3")));
        while (!delayQueue.isEmpty()){
            DelayedTask task=delayQueue.take();
            task.execute();
        }
    }
}
