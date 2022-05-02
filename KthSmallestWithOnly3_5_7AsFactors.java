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

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class KthSmallestWithOnly3_5_7AsFactors {
    static class Helper implements Comparable<Helper>{
        public Long val;
        int i, j, k;

        public Helper(Long val, int i, int j, int k) {
            this.val = val;
            this.i = i;
            this.j = j;
            this.k = k;
        }

        public Helper next(int e) {
            Helper next = cloneHelper();
            switch (e) {
                case 3: next.i++;break;
                case 5: next.j++;break;
                case 7: next.k++;break;
                default: ;
            }
            next.val *= e;
            return next;
        }

        public Helper cloneHelper() {
            return new Helper(val, i, j, k);
        }

        @Override
        public int compareTo(@NotNull KthSmallestWithOnly3_5_7AsFactors.Helper o) {
            return Long.compare(this.val, o.val);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Helper helper = (Helper) o;
            return val.equals(helper.val);
        }

        @Override
        public int hashCode() {
            return Objects.hash(val);
        }

        public String toString() {
            return String.format("%5d(%d, %d, %d)", val, i, j, k);
        }
    }

    public void kthHelper(int k) {
        PriorityQueue<Helper> pq = new PriorityQueue<>(k);
        Set<Helper> set = new HashSet<>();
        Helper cur = new Helper(3 * 5 * 7L, 1, 1, 1);
        pq.offer(cur);
        set.add(cur);
        for (;k > 1 && !pq.isEmpty(); k--) { // each round we poll 1 and add max 3
            cur = pq.poll();
            System.out.println(cur);
            for (int val : new int[]{3, 5, 7})
                enQHelper(pq, set, cur, val);
        }
        //return pq.isEmpty() ? cur : pq.peek();
    }

    private void enQHelper(PriorityQueue<Helper> pq, Set<Helper> set, Helper cur, int val) {
        Helper next = cur.next(val);
        if (set.add(next)) pq.offer(next);
    }


    public long kth(int k) {
        PriorityQueue<Long> pq = new PriorityQueue<>(k);
        Set<Long> set = new HashSet<>();
        long cur = 3 * 5 * 7;
        pq.offer(cur);
        set.add(cur);
        for (;k > 1 && !pq.isEmpty(); k--) { // each round we poll 1 and add max 3
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

    public void kth2b(int k) { // SC: O(k), SC: O(k)
        Helper cur = new Helper(3 * 5 * 7L, 1, 1, 1);

        Deque<Helper> dq3 = new ArrayDeque<>();
        Deque<Helper> dq5 = new ArrayDeque<>();
        Deque<Helper> dq7 = new ArrayDeque<>();

        offerHelper(cur, dq3, dq5, dq7);

        while (k-- > 1 && !dq3.isEmpty() && !dq5.isEmpty() && !dq7.isEmpty()) { // add non-empty check only to mitigate IDE warning
            if (dq3.peekFirst().val < dq5.peekFirst().val && dq3.peekFirst().val < dq7.peekFirst().val) {
                cur = dq3.pollFirst();
                offerHelper(cur, dq3, dq5, dq7);
            } else if (dq5.peekFirst().val < dq3.peekFirst().val && dq5.peekFirst().val < dq7.peekFirst().val) {
                cur = dq5.pollFirst();
                offerHelper(cur, null, dq5, dq7);
            } else {
                cur = dq7.pollFirst();
                dq7.offerLast(cur.next(7));
            }
        }
    }

    private void offerHelper(Helper cur, Deque<Helper> dq3, Deque<Helper> dq5, Deque<Helper> dq7) {
        if (dq3 != null) dq3.offerLast(cur.next(3));
        if (dq5 != null) dq5.offerLast(cur.next(5));
        dq7.offerLast(cur.next(7));
    }

    public static void main(String[] args) {
        KthSmallestWithOnly3_5_7AsFactors k357 = new KthSmallestWithOnly3_5_7AsFactors();
        int k = 20;
        System.out.println(k357.kth(k) + " vs " + k357.kth2(k));
        k357.kthHelper(k);
        System.out.println();
        k357.kth2b(k);
    }
}
