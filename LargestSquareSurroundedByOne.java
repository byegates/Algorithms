/*      Determine the largest square surrounded by 1s in a binary matrix (a binary matrix only contains 0 and 1),
        return the length of the largest square.

        Assumptions
        The given matrix is guaranteed to be of size N * M, where M, N >= 0
        Examples
        {{1, 0, 1, 1, 1},
         {1, 1, 1, 1, 1},
         {1, 1, 0, 1, 0},
         {1, 1, 1, 1, 1},
         {1, 1, 1, 0, 0}}

        The largest square surrounded by 1s has length of 3.
*/


public class LargestSquareSurroundedByOne {
    public int largestSquareSurroundedByOne(int[][] mx) { // TC: O(n*m*min(n,m)), SC: O(n*m)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        int n = mx.length, m = mx[0].length;

        int[][] M1 = new int[n + 1][m + 1]; // right to left, extra col & row to avoid boundary check in main loop
        int[][] M2 = new int[n + 1][m + 1]; //

        int max = mx[0][0];
        for (int i = n - 1; i >= 0; i--) // starting from 2nd bottom row to top
            for (int j = m - 1; j >= 0; j--) {// starting from 2nd right row to left
                M1[i][j] = mx[i][j] == 0 ? 0 : M1[i][j + 1] + 1;
                M2[i][j] = mx[i][j] == 0 ? 0 : M2[i + 1][j] + 1;
                // as we are filling M1 & M2 from bottom & right, we can/must iterate i,j as top left vertices of square
                for (int maxLen = Math.min(M1[i][j], M2[i][j]); maxLen > 0; maxLen--)
                    if (M1[i + maxLen - 1][j] >= maxLen && M2[i][j + maxLen - 1] >= maxLen) {
                        if (maxLen > max) max = maxLen;
                        break;
                    }
            }

        return max;
    }

    // Solution 2, normal-sized M1 and M2, no extra row & col
    public int noExtraRowCol(int[][] mx) {
        if (mx.length == 0 || mx[0].length == 0) return 0;
        int n = mx.length, m = mx[0].length;

        int[][] M1 = new int[n][m]; // right to left, extra col & row to avoid boundary check in main loop
        int[][] M2 = new int[n][m]; //

        int max = mx[0][0];
        for (int i = n - 1; i >= 0; i--) // starting from 2nd bottom row to top
            for (int j = m - 1; j >= 0; j--) {// starting from 2nd right row to left
                M1[i][j] = mx[i][j] == 0 ? 0 : (j + 1 >= m ? 0 : M1[i][j + 1]) + 1;
                M2[i][j] = mx[i][j] == 0 ? 0 : (i + 1 >= n ? 0 : M2[i + 1][j]) + 1;
                // as we are filling M1 & M2 from bottom & right, we can/must iterate i,j as top left vertices of square
                for (int maxLen = Math.min(M1[i][j], M2[i][j]); maxLen > 0; maxLen--)
                    if (M1[i + maxLen - 1][j] >= maxLen && M2[i][j + maxLen - 1] >= maxLen) {
                        if (maxLen > max) max = maxLen;
                        break;
                    }
            }

        return max;
    }

    public static void main(String[] args) {
        int[][] mx = new int[][] {
                {1,1,1,1,1,1,0,1,1,1,0,0,1,1,1,0,0,1},
                {0,0,0,1,0,1,1,0,1,1,1,1,0,1,0,1,0,1},
                {1,1,1,1,1,1,0,1,1,1,0,1,1,1,0,1,1,1},
                {1,1,1,0,1,0,0,1,0,0,1,1,1,1,1,1,1,1},
                {1,0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1},
                {0,0,0,0,0,1,1,1,1,0,1,1,1,1,1,1,0,1},
                {0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,1,1,1},
                {1,1,1,1,1,1,0,1,0,1,1,1,0,0,0,0,0,1},
                {0,1,0,0,1,1,0,0,0,1,0,0,0,0,0,1,1,1},
                {0,1,1,1,0,1,1,1,0,1,1,1,1,1,0,0,0,1},
                {1,1,0,1,1,1,1,1,1,1,0,1,1,1,0,1,1,1},
        };
        int [][]mx2 = new int[][]{
                {1, 0, 1, 1, 1},
                {1, 1, 1, 1, 1},
                {1, 1, 0, 1, 0},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0},};

        LargestSquareSurroundedByOne lss = new LargestSquareSurroundedByOne();
        System.out.println(lss.largestSquareSurroundedByOne(mx)); // 5
        System.out.println(lss.largestSquareSurroundedByOne(mx2)); // 3
        System.out.println(lss.noExtraRowCol(mx)); // 5
        System.out.println(lss.noExtraRowCol(mx2)); // 3
    }
}
