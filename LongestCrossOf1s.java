/*      Given a matrix that contains only 1s and 0s, find the largest cross which contains only 1s,
        with the same arm lengths and the four arms joining at the central point.

        Return the arm length of the largest cross.

        Assumptions
        The given matrix is not null, has size of N * M, N >= 0 and M >= 0.
        Examples

        { {0, 0, 0, 0},
          {1, 1, 1, 1},
          {0, 1, 1, 1},
          {1, 0, 1, 1} }

        the largest cross of 1s has arm length 2.
*/

import java.util.Arrays;

public class LongestCrossOf1s {
    // Solution 1 Easiest to read
    public int largest(int[][] mx) { // TC: 5*n*m--> O(n*m), SC: 4*n*m --> O(n*m)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        int n = mx.length, m = mx[0].length;

        int[][] M1 = createM1(mx, new int[n][m], n, m); // left to right
        int[][] M2 = createM2(mx, new int[n][m], n, m); // right to left
        int[][] M3 = createM3(mx, new int[n][m], n, m); // top to bottom
        int[][] M4 = createM4(mx, new int[n][m], n, m); // bottom to top

        int largest = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                largest = Math.max(min(new int[]{M1[i][j], M2[i][j], M3[i][j], M4[i][j]}), largest);

        return largest;
    }

    private int min(int[] A) { // take in all 4 values as an int[] and return the smallest of them
        int smallest = A[0];
        for (int i = 1; i < A.length; i++) if (A[i] < smallest) smallest = A[i];
        return smallest;
    }

    private int[][] createM1(int[][] mx, int[][] M, int n, int m) {
        for (int i = 0; i < n; i++) M[i][0] = mx[i][0]; // fill left column

        for (int i = 0; i < n; i++) // 2nd from the left to the right columns
            for (int j = 1; j < m; j++)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i][j - 1] + 1;
        return M;
    }

    private int[][] createM2(int[][] mx, int[][] M, int n, int m) {
        for (int i = 0; i < n; i++) M[i][m - 1] = mx[i][m - 1]; // fill right column

        for (int i = 0; i < n; i++) // 2nd from the right to the left columns
            for (int j = m - 2; j >= 0; j--)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i][j + 1] + 1;
        return M;
    }

    private int[][] createM3(int[][] mx, int[][] M, int n, int m) {
        for (int j = 0; j < m; j++) M[0][j] = mx[0][j]; // fill top row

        for (int i = 1; i < n; i++) // from 2nd row to the bottom
            for (int j = 0; j < m ; j++)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i - 1][j] + 1;
        return M;
    }

    private int[][] createM4(int[][] mx, int[][] M, int n, int m) {
        for (int j = 0; j < m; j++) M[n - 1][j] = mx[n - 1][j]; // fill bottom row

        for (int i = n - 2; i >= 0; i--) // 2nd from bottom to the top
            for (int j = 0; j < m ; j++)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i + 1][j] + 1;
        return M;
    }
    // Solution 1 ends here

    // Solution 2, with merge
    public int withMerge(int[][] mx) { // TC: 5*n*m--> O(n*m), SC: 3*n*m --> O(n*m)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        int n = mx.length, m = mx[0].length;

        return merge(leftUp(mx, n, m), rightDown(mx, n, m), n, m);
    }

    private int merge(int[][] A, int[][] B, int n, int m) {
        int max = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                A[i][j] = Math.min(A[i][j], B[i][j]);
                if (A[i][j] > max) max = A[i][j];
            }
        return max;
    }

    private int[][] leftUp(int[][] mx, int n, int m) {
        int[][] M1 = new int[n][m]; // from left to right
        int[][] M2 = new int[n][m]; // from Top to bottom
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (mx[i][j] == 1) {
                    M1[i][j] = j < 1 ? 1 : M1[i][j-1] + 1;
                    M2[i][j] = i < 1 ? 1 : M2[i-1][j] + 1;
                }

        merge(M1, M2, n, m);
        return M1;
    }

    private int[][] rightDown(int[][] mx, int n, int m) {
        int[][] M1 = new int[n][m]; // from right to left
        int[][] M2 = new int[n][m]; // from bottom to Top
        for (int i = n - 1; i >= 0; i--)
            for (int j = m - 1; j >= 0; j--)
                if (mx[i][j] == 1) {
                    M1[i][j] = j+1 >= m ? 1 : M1[i][j+1] + 1;
                    M2[i][j] = i+1 >= n ? 1 : M2[i+1][j] + 1;
                }

        merge(M1, M2, n, m);
        return M1;
    }
    // Solution 2 ends here

    // Solution 3 mor merges, less space
    public int lowSpace(int[][] mx) { // TC: 5*n*m--> O(n*m), SC: 4*n*m --> O(n*m)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        int n = mx.length, m = mx[0].length;

        int[][] M = createM1(mx, new int[n][m], n, m); // left to right
        merge(M, createM2(mx, new int[n][m], n, m), n, m); // right to left, then merge
        merge(M, createM3(mx, new int[n][m], n, m), n, m); // top to bottom, then merge
        return merge(M, createM4(mx, new int[n][m], n, m), n, m); // bottom to top, then merge
    }
    // Solution 3 ends here

    public static void main(String[] args) {
        LongestCrossOf1s lco1 = new LongestCrossOf1s();
        int[][] mx = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {1, 0, 1, 1}, };
        System.out.println(lco1.largest(mx)); // 2

        int[][] mx2 = new int[][]{
                {1, 1, 0, 1, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 1, 1, 1},
                {1, 1, 0, 1, 0},
                {0, 0, 0, 1, 1} };
        System.out.println(lco1.withMerge(mx2)); // 1

        int[][] mx3 = new int[][]{
                {1,1,1,0,1},
                {1,0,1,1,1},
                {1,1,1,1,1},
                {1,0,1,1,0},
                {0,0,1,1,0}};
        System.out.println(lco1.lowSpace(mx3)); // 3
    }
}
