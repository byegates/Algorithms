//Determine the largest square of 1s in a binary matrix (a binary matrix only contains 0 and 1), return the length of the largest square.
//
//        Assumptions
//
//        The given matrix is not null and guaranteed to be of size N * N, N >= 0
//        Examples
//
//        { {0, 0, 0, 0},
//
//        {1, 1, 1, 1},
//
//        {0, 1, 1, 1},
//
//        {1, 0, 1, 1}}
//
//        the largest square of 1s has length of 2


public class LargestSquareOfOnes {

    public int largest(int[][] mx) { // TC: O(n^2), SC: O(n)
        int n = mx.length;
        if (n == 0) return 0;
        int m = mx[0].length;
        if (m == 0) return 0;


        int[] dp = new int[m];
        int res = 0;
        for (int j = 0; j < m; j++) {
            dp[j] = mx[0][j];
            if (dp[j] > res) res = dp[j];
        }

        for (int i = 1; i < n; i++) {
            int left = mx[i][0], corner = mx[i - 1][0];
            for (int j = 1; j < m; j++) {
                int nextCorner = dp[j];
                dp[j] =  mx[i][j] == 0 ? 0 : Math.min(Math.min(left, corner), dp[j]) + 1;
                if (dp[j] > res) res = dp[j];
                corner = nextCorner;
                left = dp[j];
            }
        }

        return res;
    }

    public int MByNSpace(int[][] mx) { // TC: O(m*n), SC: O(m*n)
        int n = mx.length;
        if (n == 0) return 0;
        int m = mx[0].length;
        if (m == 0) return 0;

        int[][] dp = new int[n][m];
        int res = 0;
        for (int j = 0; j < m; j++) res = largest(dp, 0, j, mx[0][j], res);
        for (int i = 0; i < n; i++) res = largest(dp, i, 0, mx[i][0], res);

        for (int j = 1; j < m; j++)
            for (int i = 1; i < n; i++)
                if (mx[i][j] == 0) dp[i][j] = 0;
                else
                    res = largest(dp, i, j, Math.min(dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1]))+1, res);

        return res;
    }

    private int largest(int[][] dp, int i, int j, int val, int longest) {
        dp[i][j] = val;
        return Math.max(val, longest);
    }

    public static void main(String[] args) {
        LargestSquareOfOnes lso = new LargestSquareOfOnes();
        int[][] mx1 = new int[][]{
              {1,1,1,1},
              {1,1,1,1},
              {0,1,1,1},
              {1,1,1,1},
        };
        System.out.println(lso.MByNSpace(mx1)); // 3
        int[][] mx2 = new int[][]{
                {1,0,0},
                {0,0,0},
                {0,0,0},
        };
        System.out.println(lso.MByNSpace(mx2)); // 1
        int[][] mx3 = new int[][]{
                {0, 0, 1, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1},
                {1, 0, 1, 1, 1},
        };
        System.out.println(lso.MByNSpace(mx3)); //2
        int[][] mx4 = new int[][]{
                {1,1,1,1},
                {1,1,1,1},
                {1,1,1,1},
                {1,1,1,1},
        };
        System.out.println(lso.largest(mx4)); // 4
    }
}
