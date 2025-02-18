package aqs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class MyLock {
    AtomicInteger state = new AtomicInteger(0);
    Thread owner = null;
    AtomicReference<Node> head = new AtomicReference<>(new Node());
    AtomicReference<Node> tail = new AtomicReference<>(head.get());

    void lock() {
        if (state.get() == 0) {
            if (state.compareAndSet(0, 1)) {
                System.out.println(Thread.currentThread().getName() + "直接获得锁");
                owner = Thread.currentThread();
                return;
            }
        }else {
            if (owner==Thread.currentThread()) {
                System.out.println(Thread.currentThread().getName()+"拿到了重入锁，当前重入次数为"+state.getAndIncrement());
                return;
            }
        }

        Node current = new Node();
        current.thread = Thread.currentThread();
        while (true) {
            Node currentTail = tail.get();
            if (tail.compareAndSet(currentTail, current)) {
                System.out.println(Thread.currentThread().getName() + "成功加入链表尾部");
                current.pre = currentTail;
                currentTail.next = current;
                break;
            }
        }
        while (true) {
            if (current.pre == head.get() && state.compareAndSet(0, 1)) {
                owner = Thread.currentThread();
                head.set(current);
                current.pre.next = null;
                current.pre = null;
                System.out.println(Thread.currentThread().getName() + "被唤醒之后，成功获得锁");
                return;
            }
            LockSupport.park();
        }
    }

    void unlock() {
        if (Thread.currentThread() != owner) {
            throw new IllegalStateException("当前线程并没有锁,不能解锁");
        }
        int i=state.get();
        if (i>1){
            state.set(i-1);
            System.out.println(Thread.currentThread().getName()+"解锁了重入锁，当前重入次数为"+ (i - 1));
            return;
        }
        if (i<=0){
            throw new IllegalStateException("重入锁解锁错误！");
        }
        Node headNode = head.get();
        Node next = headNode.next;
        state.set(0);
        if (next != null) {
            System.out.println(Thread.currentThread().getName() + "释放锁，唤醒" + next.thread.getName());
            LockSupport.unpark(next.thread);
        }

    }

    class Node {
        Node pre;
        Node next;
        Thread thread;
    }
}
