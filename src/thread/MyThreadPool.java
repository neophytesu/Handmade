package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {
    BlockingQueue<Runnable> blockingQueue;
    private final int corePoolSize;
    private final int maximumPoolSize;
    private final int supportTimeOut;
    private final TimeUnit supportTimeUnit;
    private final int coreTimeOut;
    private final TimeUnit coreTimeUnit;
    private final RejectHandle rejectHandle;
    private boolean isShutdown = false;

    public MyThreadPool(int corePoolSize, int maximumPoolSize, int supportTimeOut, TimeUnit supportTimeUnit, int coreTimeOut, TimeUnit coreTimeUnit,BlockingQueue<Runnable> blockingQueue,  RejectHandle rejectHandle) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.supportTimeOut = supportTimeOut;
        this.supportTimeUnit = supportTimeUnit;
        this.blockingQueue = blockingQueue;
        this.coreTimeOut = coreTimeOut;
        this.coreTimeUnit = coreTimeUnit;
        this.rejectHandle = rejectHandle;
    }

    List<Thread> coreList = new ArrayList<>();
    List<Thread> supportList = new ArrayList<>();

    void shutdown() {
        isShutdown = true;

    }

    void execute(Runnable command) {
        if (isShutdown) {
            return;
        }
        if (coreList.size() < corePoolSize) {
            Thread thread = new CoreThread();
            coreList.add(thread);
            thread.start();
        }
        if (blockingQueue.offer(command)) {
            return;
        }
        if (coreList.size() + supportList.size() < maximumPoolSize) {
            Thread thread = new SupportThread();
            supportList.add(thread);
            thread.start();
        }
        if (!blockingQueue.offer(command)) {
            rejectHandle.reject(command, this);
        }
    }

    class CoreThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable command = blockingQueue.poll(coreTimeOut,coreTimeUnit);
                    if (command!=null){
                        command.run();
                    }
                    if (isShutdown){
                        break;
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    class SupportThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable command = blockingQueue.poll(supportTimeOut, supportTimeUnit);
                    if (command == null) {
                        break;
                    }
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            supportList.remove(Thread.currentThread());
            System.out.println(Thread.currentThread().getName() + "线程结束了！");

        }
    }
}
