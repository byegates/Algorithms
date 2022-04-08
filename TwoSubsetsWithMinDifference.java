/*
        Given a set of n integers, divide the set in two subsets of n/2 sizes each
        such that the difference of the sum of two subsets is as minimum as possible.
        Return the minimum difference(absolute value).

        Assumptions:
        The given integer array is not null, and it has length of >= 2.

        Examples:
        {1, 3, 2} can be divided into {1, 2} and {3}, the minimum difference is 0
*/

import java.util.ArrayList;
import java.util.List;

public class TwoSubsetsWithMinDifference {
    // method 1 starts here, using cur to hold cur result
    public int minDifference(int[] arr) { // TC: O(2^n), SC: O(n)
        int[] cur = new int[arr.length / 2];
        Min min = new Min();
        dfs(0, 0, cur, sum(arr), arr, min);
        return min.val;
    }

    private void dfs(int idx, int write, int[] cur, int sum, int[] arr, Min min) {
        if (write == cur.length) {
            min.val = Math.min(min.val, Math.abs(sum - sum(cur) * 2));
            return;
        }
        if (idx == arr.length) return;

        dfs(idx + 1, write, cur, sum, arr, min);
        cur[write] = arr[idx];
        dfs(idx + 1, write + 1, cur, sum, arr, min);
    }

    private int sum(int[] a) {
        int sum = 0;
        for (int v : a) sum += v;
        return sum;
    }

    static class Min {
        int val = Integer.MAX_VALUE;
    }
    // method 1 ends here

    // method 2 starts here, using ArrayList to hold the cur result
    public int minDifference2(int[] arr) { // TC: O(2^n), SC: O(n)
        List<Integer> cur = new ArrayList<>();
        Min min = new Min();
        dfs(0, cur, sum(arr), arr, min);
        return min.val;
    }

    private void dfs(int idx, List<Integer> cur, int sum, int[] arr, Min min) {
        if (cur.size() == arr.length / 2) {
            min.val = Math.min(min.val, Math.abs(sum - sum(cur) * 2));
            return;
        }
        if (idx == arr.length) return;

        dfs(idx + 1, cur, sum, arr, min);
        cur.add(arr[idx]);
        dfs(idx + 1, cur, sum, arr, min);
        cur.remove(cur.size() - 1);
    }

    private int sum(List<Integer> l) {
        int sum = 0;
        for (int v : l) sum += v;
        return sum;
    }
    // method 2 ends here

    public static void main(String[] args) {
        TwoSubsetsWithMinDifference tsmd = new TwoSubsetsWithMinDifference();
        int[] arr = {1,4,2,3};
        System.out.println(tsmd.minDifference(arr)); // 0
        System.out.println(tsmd.minDifference2(arr)); // 0
    }
}
