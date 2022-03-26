/*
        Given an integer k, arrange the sequence of integers [1, 1, 2, 2, 3, 3, ...., k - 1, k - 1, k, k], such that the output integer array satisfy this condition:
        Between each two i's, they are exactly i integers (for example: between the two 1s, there is one number, between the two 2's there are two numbers).
        If there does not exist such sequence, return null.

        Assumptions:
        k is guaranteed to be > 0
        Examples:

        k = 3, The output = { 2, 3, 1, 2, 1, 3 }.
*/

import java.util.Arrays;

public class KeepDistanceForIdenticalElements {
    public int[] keepDistance(int k) { // TC: O(n!), SC: O(n)
        int[] A = new int[2 * k];
        return dfs(A, k) ? A : null;
    }

    private boolean dfs(int[] A, int k) {
        if (k == 0) return true;

        for (int i = 0; i < A.length - k - 1; i++) {
            if (A[i] != 0 || A[i + k + 1] != 0) continue;
            A[i] = k;
            A[i + k + 1] = k;
            if (dfs(A, k - 1)) return true;
            A[i] = 0;
            A[i + k + 1] = 0;
        }

        return false;
    }

    public static void main(String[] args) {
        KeepDistanceForIdenticalElements kd4ie = new KeepDistanceForIdenticalElements();
        for (int i = 0; i < 13; i++)
            System.out.printf("%2d : %s\n",i , Arrays.toString(kd4ie.keepDistance(i)));

    }
}
