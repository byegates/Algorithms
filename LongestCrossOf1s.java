//Given a matrix that contains only 1s and 0s, find the largest cross which contains only 1s,
// with the same arm lengths and the four arms joining at the central point.
//
//        Return the arm length of the largest cross.
//
//        Assumptions
//        The given matrix is not null, has size of N * M, N >= 0 and M >= 0.
//        Examples
//
//        { {0, 0, 0, 0},
//          {1, 1, 1, 1},
//          {0, 1, 1, 1},
//          {1, 0, 1, 1} }
//
//        the largest cross of 1s has arm length 2.

public class LongestCrossOf1s {
    public int largest(int[][] mx) { // TC: 5*O(n^2), SC: 4*O(n^2)
        int m = mx.length;
        if (m == 0) return 0;
        int n = mx[0].length;
        if (n == 0) return 0;

        int[][] M1 = createM1(mx, new int[m][n], m, n);
        int[][] M2 = createM2(mx, new int[m][n], m, n);
        int[][] M3 = createM3(mx, new int[m][n], m, n);
        int[][] M4 = createM4(mx, new int[m][n], m, n);

        int largest = 0;
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                largest = Math.max(min(new int[]{M1[i][j], M2[i][j], M3[i][j], M4[i][j]}), largest);

        return largest;
    }

    private int min(int[] A) {
        int smallest = A[0];
        for (int i = 1; i < A.length; i++) if (A[i] < smallest) smallest = A[i];
        return smallest;
    }

    private int[][] createM1(int[][] mx, int[][] M, int m, int n) {
        for (int i = 0; i < m; i++) M[i][0] = mx[i][0];

        for (int i = 0; i < m; i++)
            for (int j = 1; j < n; j++)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i][j - 1] + 1;
        return M;
    }

    private int[][] createM2(int[][] mx, int[][] M, int m, int n) {
        for (int i = 0; i < m; i++) M[i][n - 1] = mx[i][n - 1];

        for (int i = 0; i < m; i++)
            for (int j = n - 2; j >= 0; j--)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i][j + 1] + 1;
        return M;
    }

    private int[][] createM3(int[][] mx, int[][] M, int m, int n) {
        for (int j = 0; j < n; j++) M[0][j] = mx[0][j];

        for (int i = 1; i < m; i++)
            for (int j = 0; j <n ; j++)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i - 1][j] + 1;
        return M;
    }

    private int[][] createM4(int[][] mx, int[][] M, int m, int n) {
        for (int j = 0; j < n; j++) M[m - 1][j] = mx[m - 1][j];

        for (int i = m - 2; i >= 0; i--)
            for (int j = 0; j < n ; j++)
                M[i][j] = mx[i][j] == 0 ? 0 : M[i + 1][j] + 1;
        return M;
    }

    public static void main(String[] args) {
        LongestCrossOf1s lco1 = new LongestCrossOf1s();
        int[][] mx = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {1, 0, 1, 1}, };
        System.out.println(lco1.largest(mx)); // 2
    }
}
