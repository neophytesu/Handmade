package aqs;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class MyLock {
    AtomicBoolean flag = new AtomicBoolean(false);
    Thread owner=null;
    void lock() {
        while (true) {
            if (flag.compareAndExchange(false, true)) {
                owner=Thread.currentThread();
                return;
            }
            LockSupport.park();
        }
    }

    void unlock() {
        if (Thread.currentThread()==owner){
            throw  new IllegalStateException("当前线程并没有锁,不能解锁");
        }
        while (true) {
            if (flag.compareAndExchange(true, false)) {
                return;
            }
        }
    }
}
