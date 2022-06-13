package util;

import java.util.Arrays;
import java.util.Comparator;

public class Heap {
    private Integer[] A;
    private int size;
    private Comparator<Integer> C = Integer::compareTo;
    //    private Comparator<Integer> C = new Comparator<Integer>() {
//        @Override
//        public int compare(Integer i1, Integer i2) {
//            return Integer.compare(i1, i2);}
//    };
    public Heap(Integer[] A, Comparator<Integer> C) { // constructor from int[]
        if (A == null || A.length <= 1) throw new IllegalArgumentException("Input Array can't be null or empty or 1");
        this.A = A;
        this.size = A.length;
        if (C != null) this.C = C;
        heapify();
    }
    public Heap(Integer[] A) {this(A, null);}

    public Heap(int cap) {// constructor start with empty array with cap
        if (cap <= 1) throw new IllegalArgumentException("Cap must be greater than 1");
        A = new Integer[cap];
        size = 0;
    }

    private void heapify() {
        for (int i = size / 2 - 1; i >= 0; i--) percolateDown(i);
    }

    private void percolateUp(int idx) {
        while (idx > 0) {
            int p = (idx - 1) / 2; // parent index
            if (C.compare(A[idx], A[p]) < 0) swap(idx, p);
            else break;
            idx = p;
        }

    }
    private void percolateDown(int idx) {
        while (true) {
            int candidate = 2 * idx + 1; // left child index, assume it's the candidate (smaller one for min heap, larger one for max heap)
            if (candidate >= size) break;
            int r = candidate + 1; //r: right child steps
            if (r < size && C.compare(A[r], A[candidate]) < 0) candidate = r;
            if (C.compare(A[idx], A[candidate]) > 0) swap(idx, candidate);
            else break;
            idx = candidate;
        }
    }

    public Integer peek() {
        return isEmpty() ? null : A[0];
    }

    public Integer poll() {
        if (isEmpty()) return null;
        swap(0, --size);
        percolateDown(0);
        return A[size];
    }
    public void offer(int e) {
        if (size == A.length) A = Arrays.copyOf(A, (int)(size * 1.5));
        A[size++] = e;
        percolateUp(size - 1);
    }
    public int update(int idx, int e) {
        if (idx < 0 || idx >= size) throw new IllegalArgumentException("Index must be between 0 and Heap size - 1");
        int res = A[idx];
        A[idx] = e;
        if (C.compare(e, res) < 0) percolateUp(idx);
        else percolateDown(idx);
        return res;
    }
    private void swap(int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public void heapsort(Integer[] A) {
        if (A == null || A.length <= 1) return;
        Integer[] B = this.A; // save the original heap
        this.A = A;
        this.size = A.length;
        heapify();
        heapsort();
        this.A = B;
        this.size = B.length;
    }
    public void heapsort() {
        int savedSize = size;
        while (size > 1) poll();
        size = savedSize;
    }

    public Integer[] array() {return Arrays.copyOf(A, size);}
    public String toString() {return Arrays.toString(A);}
    public int size() {return size;}
    public int cap() {return A.length;}
    public boolean isEmpty() {return size == 0;}
    public boolean isFull() {return size == A.length;}
}
