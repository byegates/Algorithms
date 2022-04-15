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

import java.util.*;

public class KthSmallestWithOnly3_5_7AsFactors {
    public long kth(int k) {
        PriorityQueue<Long> pq = new PriorityQueue<>(k);
        Set<Long> set = new HashSet<>();
        long cur = 3 * 5 * 7;
        pq.offer(cur);
        set.add(cur);
        while (k-- > 1 && !pq.isEmpty()) { // each round we poll 1 and add max 3
            cur = pq.poll();
            for (int val : new int[]{3, 5, 7})
                enQ(pq, set, val * cur);
        }

        return pq.isEmpty() ? cur : pq.peek();
    }

    private void enQ(PriorityQueue<Long> pq, Set<Long> set, long val) {
        if (set.add(val)) pq.offer(val);
    }

    public long kth2(int k) { // SC: O(k), SC: O(k)
        long res = 3 * 5 * 7;

        Deque<Long> dq3 = new ArrayDeque<>();
        Deque<Long> dq5 = new ArrayDeque<>();
        Deque<Long> dq7 = new ArrayDeque<>();

        offer(res, dq3, dq5, dq7);

        while (k-- > 1 && !dq3.isEmpty() && !dq5.isEmpty() && !dq7.isEmpty()) { // add non-empty check only to mitigate IDE warning
            if (dq3.peekFirst() < dq5.peekFirst() && dq3.peekFirst() < dq7.peekFirst()) {
                res = dq3.pollFirst();
                offer(res, dq3, dq5, dq7);
            } else if (dq5.peekFirst() < dq3.peekFirst() && dq5.peekFirst() < dq7.peekFirst()) {
                res = dq5.pollFirst();
                offer(res, null, dq5, dq7);
            } else {
                res = dq7.pollFirst();
                dq7.offerLast(res * 7);
            }
        }

        return res;
    }

    private void offer(long res, Deque<Long> dq3, Deque<Long> dq5, Deque<Long> dq7) {
        if (dq3 != null) dq3.offerLast(res * 3);
        if (dq5 != null) dq5.offerLast(res * 5);
        dq7.offerLast(res * 7);
    }

    public static void main(String[] args) {
        KthSmallestWithOnly3_5_7AsFactors k357 = new KthSmallestWithOnly3_5_7AsFactors();
        for (int k = 1; k < 11; k++)
            System.out.println(k357.kth(k) + " vs " + k357.kth2(k));
    }
}
