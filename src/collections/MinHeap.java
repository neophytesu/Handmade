package collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MinHeap<E> {
    private List<E> list;
    private Comparator<E> comparator;

    public MinHeap() {
        list = new ArrayList<>();
    }

    public MinHeap(Comparator<E> comparator) {
        list = new ArrayList<>();
        this.comparator = comparator;
    }

    public MinHeap(E[] arr) {
        list = new ArrayList<>(Arrays.asList(arr));
        heapify();
    }

    public MinHeap(int capacity) {
        list = new ArrayList<>(capacity);
    }

    public MinHeap(int capacity, Comparator<E> comparator) {
        list = new ArrayList<>(capacity);
        this.comparator = comparator;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    private int parentIndex(int childIndex) {
        if (childIndex == 0) {
            throw new IllegalArgumentException();
        }
        return (childIndex - 1) / 2;
    }

    private int leftIndex(int index) {
        return 2 * index + 1;
    }

    private int rightIndex(int index) {
        return 2 * index + 2;
    }

    public void add(E e) {
        list.add(e);
        siftUp(list.size() - 1);
    }

    private void siftUp(int index) {
        if (comparator != null) {
            while (index > 0 && comparator.compare(list.get(parentIndex(index)), list.get(index)) > 0) {
                E tmpElement = list.get(index);
                list.set(index, list.get(parentIndex(index)));
                list.set(parentIndex(index), tmpElement);
                index = parentIndex(index);
            }
        } else {
            while (index > 0 && ((Comparable) list.get(parentIndex(index))).compareTo(list.get(index)) > 0) {
                E tmpElement = list.get(index);
                list.set(index, list.get(parentIndex(index)));
                list.set(parentIndex(index), tmpElement);
                index = parentIndex(index);
            }
        }
    }

    public E poll() {
        if (list == null || list.isEmpty()) {
            return null;
        }
        E ret = list.getFirst();
        list.set(0, list.getLast());
        list.removeLast();
        siftDown(0);
        return ret;
    }

    private void heapify() {
        for (int i = parentIndex(list.size() - 1); i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftDown(int index) {
        if (comparator != null) {
            while (leftIndex(index) < list.size()) {
                int cmpIdx = leftIndex(index);
                if (rightIndex(index) < list.size() && comparator.compare(list.get(leftIndex(index)), list.get(rightIndex(index))) > 0) {
                    cmpIdx = rightIndex(index);
                }
                if (comparator.compare(list.get(index), list.get(cmpIdx)) <= 0) {
                    break;
                }
                E tmpElement = list.get(cmpIdx);
                list.set(cmpIdx, list.get(index));
                list.set(index, tmpElement);
                index = cmpIdx;
            }
        } else {
            while (leftIndex(index) < list.size()) {
                int cmpIdx = leftIndex(index);
                if (rightIndex(index) < list.size() && ((Comparable) list.get(leftIndex(index))).compareTo(list.get(rightIndex(index))) > 0) {
                    cmpIdx = rightIndex(index);
                }
                if (((Comparable) list.get(index)).compareTo(list.get(cmpIdx)) <= 0) {
                    break;
                }
                E tmpElement = list.get(cmpIdx);
                list.set(cmpIdx, list.get(index));
                list.set(index, tmpElement);
                index = cmpIdx;
            }
        }
    }

    public E peek() {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.getFirst();
    }
}
