/*
        Given a non-negative integer array representing the heights of a list of adjacent bars. Suppose each bar has a width of 1. Find the largest amount of water that can be trapped in the histogram.

        Assumptions
        The given array is not null
        Examples
        { 2, 1, 3, 2, 4 }, the amount of water can be trapped is 1 + 1 = 2 (at index 1, 1 unit of water can be trapped and index 3, 1 unit of water can be trapped)

*/

public class MaxWaterTrappedI {

    public int maxTrapped(int[] arr) { // TC: O(n), SC: O(1)
        int res = 0, n = arr.length;
        if (n == 0) return res;

        int lmax = arr[0], rmax = arr[n - 1];
        for (int l = 1, r = n - 2; l <= r;) {
            if (lmax < rmax) {
                if (arr[l] > lmax) lmax = arr[l];
                res += Math.max(0, lmax - arr[l++]);
            } else {
                if (arr[r] > rmax) rmax = arr[r];
                res += Math.max(0, rmax - arr[r--]);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        MaxWaterTrappedI mwt1 = new MaxWaterTrappedI();
        System.out.println(mwt1.maxTrapped(new int[]{5,3,2,1,4,6})); // 10
        System.out.println(mwt1.maxTrapped(new int[]{3, 2, 1})); // 0
        System.out.println(mwt1.maxTrapped(new int[]{1,2,3,4,4,4,5})); // 0
    }

}
