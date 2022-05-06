/*
Left of Center: 1, left vs right sum: 11 vs  8, Array: [4, 7, 0, 3, 4, 1]
Left of Center: 1, left vs right sum: 11 vs  8, Array: [4, 7, 0, 3, 4, 1]
Left of Center: 3, left vs right sum: 17 vs 15, Array: [5, 1, 7, 4, 8, 3, 4]
Left of Center: 3, left vs right sum: 17 vs 15, Array: [5, 1, 7, 4, 8, 3, 4]
Left of Center: 1, left vs right sum: 12 vs 15, Array: [7, 5, 7, 4, 2, 2]
Left of Center: 1, left vs right sum: 12 vs 15, Array: [7, 5, 7, 4, 2, 2]
Left of Center: 0, left vs right sum:  4 vs  8, Array: [4, 8, 0]
Left of Center: 0, left vs right sum:  4 vs  8, Array: [4, 8, 0]
Left of Center: 1, left vs right sum: 14 vs 11, Array: [9, 5, 7, 4]
Left of Center: 1, left vs right sum: 14 vs 11, Array: [9, 5, 7, 4]
Left of Center: 1, left vs right sum:  7 vs  5, Array: [2, 5, 5]
Left of Center: 1, left vs right sum:  7 vs  5, Array: [2, 5, 5]
Left of Center: 0, left vs right sum:  9 vs 15, Array: [9, 9, 6]
Left of Center: 0, left vs right sum:  9 vs 15, Array: [9, 9, 6]
Left of Center: 2, left vs right sum: 18 vs 18, Array: [3, 6, 9, 2, 8, 3, 5]
Left of Center: 2, left vs right sum: 18 vs 18, Array: [3, 6, 9, 2, 8, 3, 5]
Left of Center: 3, left vs right sum: 17 vs 13, Array: [1, 8, 3, 5, 9, 4]
Left of Center: 3, left vs right sum: 17 vs 13, Array: [1, 8, 3, 5, 9, 4]
Left of Center: 0, left vs right sum:  8 vs 13, Array: [8, 7, 1, 3, 2, 0]
Left of Center: 0, left vs right sum:  8 vs 13, Array: [8, 7, 1, 3, 2, 0]
Left of Center: 4, left vs right sum: 22 vs 17, Array: [5, 1, 5, 3, 8, 4, 5, 1, 7]
Left of Center: 4, left vs right sum: 22 vs 17, Array: [5, 1, 5, 3, 8, 4, 5, 1, 7]
Left of Center: 5, left vs right sum: 24 vs 23, Array: [4, 9, 1, 7, 0, 3, 7, 6, 1, 9]
Left of Center: 5, left vs right sum: 24 vs 23, Array: [4, 9, 1, 7, 0, 3, 7, 6, 1, 9]
Left of Center: 1, left vs right sum:  9 vs  9, Array: [8, 1, 9]
Left of Center: 1, left vs right sum:  9 vs  9, Array: [8, 1, 9]
Left of Center: 2, left vs right sum: 19 vs 18, Array: [9, 7, 3, 4, 6, 4, 4]
Left of Center: 2, left vs right sum: 19 vs 18, Array: [9, 7, 3, 4, 6, 4, 4]
Left of Center: 0, left vs right sum:  8 vs 10, Array: [8, 5, 4, 1, 0]
Left of Center: 0, left vs right sum:  8 vs 10, Array: [8, 5, 4, 1, 0]
Left of Center: 3, left vs right sum: 15 vs 16, Array: [1, 5, 8, 1, 9, 0, 0, 2, 0, 5]
Left of Center: 3, left vs right sum: 15 vs 16, Array: [1, 5, 8, 1, 9, 0, 0, 2, 0, 5]
Left of Center: 2, left vs right sum: 10 vs 10, Array: [0, 8, 2, 6, 4]
Left of Center: 2, left vs right sum: 10 vs 10, Array: [0, 8, 2, 6, 4]
Left of Center: 1, left vs right sum: 11 vs 11, Array: [8, 3, 3, 8, 0]
Left of Center: 1, left vs right sum: 11 vs 11, Array: [8, 3, 3, 8, 0]
Left of Center: 3, left vs right sum: 18 vs 15, Array: [3, 0, 6, 9, 1, 2, 9, 3]
Left of Center: 3, left vs right sum: 18 vs 15, Array: [3, 0, 6, 9, 1, 2, 9, 3]
Left of Center: 4, left vs right sum: 21 vs 19, Array: [3, 4, 2, 6, 6, 9, 6, 4, 0]
Left of Center: 4, left vs right sum: 21 vs 19, Array: [3, 4, 2, 6, 6, 9, 6, 4, 0]
*/

import java.util.Arrays;

public class CenterOfGravity {
    // two pointer solution, TC: O(n), SC: O(1)
    public int center2(int[] a) {
        int l = 0, r = a.length - 1;
        int diff = a[r] - a[l];
        while (l < r - 1)
            if (diff < 0) diff += a[--r];
            else diff -= a[++l];

        return l;
    }

    // prefixSum, TC: O(n) + O(log(n)) ==> O(n), SC: O(n)
    public int center(int[] a) {
        if (a == null || a.length < 2) return -1; // invalid case
        if (a.length == 2) return 0; // return the left index, the right index will be left + 1
        int[] prefixSum = new int[a.length];
        prefixSum[0] = a[0];
        for (int i = 1; i < a.length; i++)
            prefixSum[i] = prefixSum[i-1] + a[i];

        return closest(prefixSum, 0, a.length - 1, prefixSum[a.length - 1] / 2);
    }

    private int closest(int[] a, int l, int r, int target) { // 1st occur if equal
        while (l < r - 1) {
            int m = l + (r - l) / 2;
            if (a[m] < target) l = m;
            else r = m;
        }
        return target - a[l] < a[r] - target ? l : r;
    }

    private static int sum(int[] a, int l, int r) {
        int sum = 0;
        for (int i = l; i <= r; i++) sum += a[i];
        return sum;
    }

    private static void print(int[] a, int left) {
        int leftSum = sum(a, 0, left);
        int totalSum = sum(a, 0, a.length - 1);
        int rightSum = totalSum - leftSum;
        System.out.printf("Left of Center: %d, left vs right sum: %2d vs %2d, Array: %s\n", left, leftSum, rightSum, Arrays.toString(a));
    }

    // produce some random tests
    private static void test(CenterOfGravity sol) {
        int len = (int) (Math.random() * 8) + 3;
        int[] a = new int[len];
        for (int i = 0; i < len; i++)
            a[i] = (int) (Math.random() * 10);
        int left = sol.center(a);
        print(a, left);
        print(a, sol.center2(a));
    }

    public static void main(String[] args) {
        CenterOfGravity sol = new CenterOfGravity();
        for (int i = 0; i < 10; i++) test(sol);
    }
}
