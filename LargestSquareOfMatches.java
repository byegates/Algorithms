/*
        Determine the largest square surrounded by a bunch of matches (each match is either horizontal or vertical),
        return the length of the largest square.

        The input is a matrix of points. Each point has one of the following values:

        0 - there is no match to its right or bottom.
        1 - there is a match to its right.
        2 - there is a match to its bottom.
        3 - there is a match to its right, and a match to its bottom.

        Assumptions

        The given matrix is guaranteed to be of size M * N, where M, N >= 0

        Examples

        {{3, 1, 1, 3, 0, 1, 1, 0},
         {2, 0, 0, 2, 0, 0, 0, 0},
         {3, 1, 3, 0, 0, 0, 0, 0},
         {2, 0, 2, 0, 0, 0, 0, 0},
         {1, 1, 0, 0, 0, 0, 0, 0}}

        The largest square has length of 2.
*/


public class LargestSquareOfMatches {

    int n, m;
    public int largestSquareOfMatches(int[][] mx) { // O(n*m*min(n,m)) SC: (n*m)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        n = mx.length; m = mx[0].length;

        int[][] right = new int[n+1][m+1]; // right to left, look back: i, j+1
        int[][] bottom = new int[n+1][m+1]; // bottom to top, look back: i+1, j

        int res = 0;
        for (int i = n - 1; i >= 0; i--)
            for (int j = m - 1; j >= 0; j--) {
                if (hasRight(mx[i][j])) right[i][j] = right[i][j+1] + 1;
                if (hasDown(mx[i][j])) bottom[i][j] = bottom[i+1][j] + 1;

                if (hasBoth(mx[i][j]))
                    for (int maxLen = Math.min(right[i][j], bottom[i][j]); maxLen >= 0; maxLen--)
                        if (right[i+maxLen][j] >= maxLen && bottom[i][j+maxLen] >= maxLen) {
                            if (maxLen > res) res = maxLen;
                            break;
                        }
            }

        return res;
    }

    private boolean hasRight(int val) {return (val & 0b1) != 0;}
    private boolean hasDown(int val) {return (val & 0b10) != 0;}
    private boolean hasBoth(int val) {return (val == 0b11);}
    // Solution 1 ends here

    // below are for debug/demo
    private int[][] getDown(int[][] mx) {
        n = mx.length; m = mx[0].length;
        int[][] M = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                M[i][j] = hasDown(mx[i][j]) ? 1 : 0;
        return M;
    }

    private int[][] getRight(int[][] mx) {
        n = mx.length; m = mx[0].length;
        int[][] M = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                M[i][j] = hasRight(mx[i][j]) ? 1 : 0;
        return M;
    }

    private void printMX(int[][] mx) {
        n = mx.length; m = mx[0].length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                System.out.print(j == m-1 ? String.format("%d", mx[i][j]) : String.format("%d, ", mx[i][j]));
            System.out.println();
        }
        System.out.println();
    }

    private void printRight(int[][] mx) {printMX(getRight(mx));}
    private void printDown(int[][] mx) {printMX(getDown(mx));}

    public static void main(String[] args) {
        LargestSquareOfMatches lsm = new LargestSquareOfMatches();

        int[][] mx = new int[][]{
                {3, 1, 1, 3, 0, 1, 1, 0},
                {2, 0, 0, 2, 0, 0, 0, 0},
                {3, 1, 3, 0, 0, 0, 0, 0},
                {2, 0, 2, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0},
        };
        
        int[][] mx2 = new int[][]{
                {2, 3, 3, 2},
                {3, 3, 3, 0},
                {3, 3, 3, 2},
                {3, 3, 3, 2},
                {1, 3, 1, 2},
                {3, 3, 3, 2},
                {1, 1, 3, 0},
                {3, 3, 3, 2},
                {3, 3, 3, 2},
                {1, 1, 1, 0},
        };

        System.out.println(lsm.largestSquareOfMatches(mx)); // 2
        System.out.println(lsm.largestSquareOfMatches(mx2)); // 2

        lsm.printRight(mx2); // just for fun
        lsm.printDown(mx2); // just for fun too
    }
}
