package currents;

import java.util.concurrent.locks.ReentrantLock;

public class MyReentrantLock {
    Thread t = new Thread() {
        @Override
        public void run() {
            ReentrantLock r = new ReentrantLock();
            r.lock();
            System.out.println("lock() : lock count :" + r.getHoldCount());
            interrupt();
            System.out.println("Current thread is interrupted");
            r.tryLock();
            System.out.println("tryLock() on interrupted lock count :" + r.getHoldCount());
            try {
                System.out.println("Current thread is interrupted:" + Thread.currentThread().isInterrupted());
                r.lockInterruptibly();
                System.out.println("lockInterruptible() -- Not executable statement" + r.getHoldCount());
            } catch (InterruptedException e) {
                r.lock();
                System.out.println("Error");
            } finally {
                r.unlock();
            }
            System.out.println("lockInterruptible() not able to Acquire lock: lock count :" + r.getHoldCount());
            r.unlock();
            System.out.println("lock count : " + r.getHoldCount());
            r.unlock();
            System.out.println("lock count : " + r.getHoldCount());
        }
    };

    public static void main(String[] args) {
        MyReentrantLock m = new MyReentrantLock();
        m.t.start();
    }
}
