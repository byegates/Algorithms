/*
        Given three arrays sorted in ascending order.
        Pull one number from each array to form a coordinate <x,y,z> in a 3D space.
        Find the coordinates of the points that is k-th closest to <0,0,0>.

        We are using euclidean distance here.

        Assumptions
        The three given arrays are not null or empty, containing only non-negative numbers
        K >= 1 and K <= a.length * b.length * c.length
        Return

        a size 3 integer list, the first element should be from the first array,
        the second element should be from the second array and the third should be from the third array

        Examples
        A = {1, 3, 5}, B = {2, 4}, C = {3, 6}
        The closest is <1, 2, 3>, distance is sqrt(1 + 4 + 9)
        The 2nd closest is <3, 2, 3>, distance is sqrt(9 + 4 + 9)
*/


import java.util.*;

public class KthClosestPointTo000 {
    public List<Integer> closest(int[] a, int[] b, int[] c, int k) { // TC: O(klogk), SC: O(k)
        List<Integer> res = new ArrayList<>();
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>(2 * k, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                long d1 = distance(o1, a, b, c);
                long d2 = distance(o2, a, b, c);
                return Long.compare(d1, d2);
            }
        });

        Set<List<Integer>> visited = new HashSet<>();
        List<Integer> cur = Arrays.asList(0, 0, 0);
        visited.add(cur);
        pq.offer(cur);

        while (k-- > 0) {
            cur = pq.poll();
            List<Integer> next = Arrays.asList(cur.get(0) + 1, cur.get(1), cur.get(2));
            if (next.get(0) < a.length && visited.add(next)) pq.offer(next);
            next = Arrays.asList(cur.get(0), cur.get(1) + 1, cur.get(2));
            if (next.get(1) < b.length && visited.add(next)) pq.offer(next);
            next = Arrays.asList(cur.get(0), cur.get(1), cur.get(2) + 1);
            if (next.get(2) < c.length && visited.add(next)) pq.offer(next);
        }

        cur.set(0, a[cur.get(0)]);
        cur.set(1, b[cur.get(1)]);
        cur.set(2, c[cur.get(2)]);

        return cur;
    }

    private long distance(List<Integer> point, int[]a, int[] b, int[] c) {
        long dis = 0;
        dis += (long) a[point.get(0)] * a[point.get(0)];
        dis += (long) b[point.get(1)] * b[point.get(1)];
        dis += (long) c[point.get(2)] * c[point.get(2)];
        return dis;
    }

    public static void main(String[] args) {
        KthClosestPointTo000 k000 = new KthClosestPointTo000();
        System.out.println(k000.closest(new int[]{1, 2, 3}, new int[]{2, 4}, new int[]{1, 2}, 10)); // [2, 4, 2]
    }
}
