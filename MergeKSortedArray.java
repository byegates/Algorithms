/*
        Merge K sorted array into one big sorted array in ascending order.

        Assumptions
        The input arrayOfArrays is not null, none of the arrays is null either.
*/


import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MergeKSortedArray {

    static class Entry implements Comparable<Entry> {
        int i, j, v;
        Entry(int i, int j, int v) {
            this.i = i;
            this.j = j;
            this.v = v;
        }

        @Override
        public int compareTo(Entry e) {
            return Integer.compare(this.v, e.v);
        }
    }

    public int[] merge(int[][] aa) {
        // Write your solution here
        PriorityQueue<Entry> pq = new PriorityQueue<>(11);
        int length = 0;
        for (int i = 0; i < aa.length; i++) {
            int[] a = aa[i];
            length += a.length;
            if (a.length != 0) pq.offer(new Entry(i, 0, a[0]));
        }
        int[] res = new int[length];

        for (int i = 0;!pq.isEmpty();) {
            Entry cur = pq.poll();
            res[i++] = cur.v;
            if (++cur.j < aa[cur.i].length) {
                cur.v = aa[cur.i][cur.j];
                pq.offer(cur);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        MergeKSortedArray msa = new MergeKSortedArray();
        System.out.println(Arrays.toString(msa.merge(new int[][]{{3}, {1, 2, 3, 4, 5}}))); // [1, 2, 3, 3, 4, 5]
        System.out.println(Arrays.toString(msa.merge(new int[][]{{11, 12, 13, 14, 15}, {3}, {1, 2}, {4, 5, 6, 7}, {8, 9, 10}}))); // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
    }
}
