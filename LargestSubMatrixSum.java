/*      Given a matrix that contains integers, find the sub matrix with the largest sum. Return the sum of the sub matrix.

        Assumptions
        The given matrix is not null and has size of M * N, where M >= 1 and N >= 1
        Examples
        { {1, -2, -1, 4},
          {1, -1,  1, 1},
          {0, -1, -1, 1},
          {0,  0,  1, 1} }

        the largest sub matrix sum is (-1) + 4 + 1 + 1 + (-1) + 1 + 1 + 1 = 7.
*/


import java.util.Arrays;

class LargestSubMatrixSum {
    public int largest(int[][] mx) { // TC: O(n^2*2n) → O(n^3), SC: O(n)
        int n = mx.length, m = mx[0].length;

        int res = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) { // from row i
            int[] prefixSum = new int[m]; // a row which stores prefix sum for each column
            for (int j = i; j < n; j++) // to row j
                res = Math.max(res, max(prefixSum(prefixSum, mx[j])));
        }

        return res;
    }

    private int[] prefixSum(int[] prefixSum, int[] curRow) { // O(n)
        for (int i = 0; i < curRow.length; i++) prefixSum[i] += curRow[i];
        return prefixSum;
    }

    private int max(int[] A) { // O(n)
        int max = A[0], cur = A[0];
        for (int i = 1; i < A.length; i++) {
            cur = Math.max(cur, 0) + A[i];
            if (cur > max) max = cur;
        }
        return max;
    }

    public static void main(String[] args) {

        LargestSubMatrixSum lss = new LargestSubMatrixSum();
        int[][] mx = new int[][]{
                {1, -2, -1, 4},
                {1, -1,  1, 1},
                {0, -1, -1, 1},
                {0,  0,  1, 1} };
        System.out.println(lss.largest(new int[][]{{1}})); // 1
        System.out.println(lss.largest(mx)); // 7
    }

}
