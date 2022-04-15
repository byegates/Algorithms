package util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;


class HeapTest {
    Integer[] A1 = new Integer[]{8 , 7, 4, 2, 9, 5, 3, 1, 0, 6};
    Integer[] A2 = new Integer[]{7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};

    int[] pqToIntArray(PriorityQueue<Integer> pq) {
        int[] res = new int[pq.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = pq.poll();
        return res;
    }
    int[] pqToIntArray(Heap pq) {
        int[] res = new int[pq.size()];
        for (int i = 0; i < res.length; i++)
            res[i] = pq.poll();
        return res;
    }

//    @Test
//    void peek() {assertEquals(pq1.peek(), mh1.peek());}
//    @Test
//    void size() {assertEquals(pq1.size(), mh1.size());}

    @Test
    void poll() {assertArrayEquals(pqToIntArray(new PriorityQueue<>(Arrays.asList(A1))), pqToIntArray(new Heap(A1)));}

    @Test
    void poll2() {assertArrayEquals(pqToIntArray(new PriorityQueue<>(Arrays.asList(A2))), pqToIntArray(new Heap(A2)));}

    @Test
    void offer() {
        PriorityQueue<Integer> pq2 = new PriorityQueue<>(Arrays.asList(A2));
        Heap mh2 = new Heap(A2);
        pq2.poll();mh2.poll();
        pq2.offer(-5);mh2.offer(-5);
        assertArrayEquals(pqToIntArray(pq2), pqToIntArray(mh2));
    }

    @Test
    void update() {
        PriorityQueue<Integer> pq2 = new PriorityQueue<>(Arrays.asList(A2));
        Heap mh2 = new Heap(A2);
        mh2.poll();pq2.poll();
        pq2.offer(-5);mh2.offer(-5);
        assertArrayEquals(pqToIntArray(pq2), pqToIntArray(mh2));
    }

    @Test
    void HeapSort1() {
        Heap heap = new Heap(A1, Comparator.reverseOrder()); // need max heap for heap sort
        Integer[] A1b = Arrays.copyOf(A1, A1.length);
        Arrays.sort(A1b);
        heap.heapsort();
        assertArrayEquals(A1b, heap.array());
    }

    @Test
    void HeapSort2() {
        Heap heap = new Heap(A2, Comparator.reverseOrder());// need max heap for heap sort
        Integer[] A2b = Arrays.copyOf(A2, A2.length);
        Arrays.sort(A2b);
        heap.heapsort();
        assertArrayEquals(A2b, heap.array());
    }

    @Test
    void isEmpty() {
    }

    @Test
    void isFull() {
    }
}