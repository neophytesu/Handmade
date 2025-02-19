package priorityqueue;

public interface Queue<E> {
    int size();

    boolean isEmpty();

    boolean offer(E e);

    E poll();

    E peek();
}
