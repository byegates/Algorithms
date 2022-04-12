/*
        Find the Kth the smallest number s such that
        s = 3 ^ x * 5 ^ y * 7 ^ z, x > 0 and y > 0 and z > 0, x, y, z are all integers.

        Assumptions
        K >= 1

        Examples
        the smallest is 3 * 5 * 7 = 105
        the 2nd smallest is 3 ^ 2 * 5 * 7 = 315
        the 3rd smallest is 3 * 5 ^ 2 * 7 = 525
        the 5th smallest is 3 ^ 3 * 5 * 7 = 945
*/

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class KthSmallestWithOnly3_5_7AsFactors {
    public long kth(int k) {
        PriorityQueue<Long> pq = new PriorityQueue<>(k);
        Set<Long> set = new HashSet<>();
        long start = 3 * 5 * 7;
        pq.offer(start);
        set.add(start);
        while (k-- > 1) {
            long cur = pq.poll();
            for (int val : new int[]{3, 5, 7})
                enQ(pq, set, val * cur);
        }

        return pq.peek();
    }

    private void enQ(PriorityQueue<Long> pq, Set<Long> set, long val) {
        if (set.add(val)) pq.offer(val);
    }

    public static void main(String[] args) {
        KthSmallestWithOnly3_5_7AsFactors k357 = new KthSmallestWithOnly3_5_7AsFactors();
        for (int k = 1; k < 10; k++)
            System.out.println(k357.kth(k));
    }
}
