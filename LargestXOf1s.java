/*
        Given a matrix that contains only 1s and 0s, find the largest X shape which contains only 1s,
        with the same arm lengths and the four arms joining at the central point.
        Return the arm length of the largest X shape.

        Assumptions
        The given matrix is not null, has size of N * M, N >= 0 and M >= 0.

        Examples
        { {0, 0, 0, 0},
          {1, 1, 1, 1},
          {0, 1, 1, 1},
          {1, 0, 1, 1} }
        The largest X of 1s has arm length 2.
*/


public class LargestXOf1s {
    int n, m;
    public int largest(int[][] mx) { // TC: 5*n*m → O(n*m), SC: 3*n*m → O(n*m)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        n = mx.length;
        m = mx[0].length;

        return merge(leftUp(mx), rightDown(mx));
    }

    private int merge(int[][] A, int[][] B) {
        int max = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                A[i][j] = Math.min(A[i][j], B[i][j]);
                if (A[i][j] > max) max = A[i][j];
            }

        return max;
    }

    private int[][] leftUp(int[][] mx) {
        int[][] left = new int[n][m];
        int[][] up = new int[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (mx[i][j] == 1) {
                    left[i][j] = i == 0 || j == 0 ? 1 : left[i-1][j-1] + 1;
                    up[i][j] = i == 0 || j == m-1 ? 1 : up[i-1][j+1] + 1;
            }

        merge(left, up);
        return left;
    }

    private int[][] rightDown(int[][] mx) {
        int[][] right = new int[n][m];
        int[][] down = new int[n][m];
        for (int i = n - 1; i >= 0; i--)
            for (int j = m - 1; j >= 0; j--)
                if (mx[i][j] == 1) {
                    right[i][j] = i == n-1 || j == m-1 ? 1 : right[i+1][j+1] + 1;
                    down[i][j] = i == n-1 || j == 0 ? 1 : down[i+1][j-1] + 1;
            }

        merge(right, down);
        return right;
    }
    // Solution 1 ends here

    // Solution 2 with fewer matrices used starts here, but uses merge method from above
    public int lowerSpace(int[][] mx) { // TC: O(m*n), SC: O(m*n) (but use less matrix)
        if (mx.length == 0 || mx[0].length == 0) return 0;
        n = mx.length;
        m = mx[0].length;

        int[][] M = M1(mx, new int[n][m]); // top left to bottom right (i-1, j-1)
        merge(M, M2(mx, new int[n][m])); // bottom left to top right (i+1, j-1)
        merge(M, M3(mx, new int[n][m])); // top right to bottom left (i-1,j+1)

        return merge(M, M4(mx, new int[n][m])); // bottom right to top left (i+1,j+1)
    }

    private int[][] M1(int[][] mx, int[][] M) {
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (mx[i][j] == 1) M[i][j] = getVal(M, i-1, j-1) + 1;
        return M;
    }

    private int[][] M2(int[][] mx, int[][] M) {
        for (int i = n-1; i >= 0; i--)
            for (int j = 0; j < m; j++)
                if (mx[i][j] == 1) M[i][j] = getVal(M, i+1, j-1) + 1;
        return M;
    }

    private int[][] M3(int[][] mx, int[][] M) {
        for (int i = 0; i < n; i++)
            for (int j = m - 1; j >= 0; j--)
                if (mx[i][j] == 1) M[i][j] = getVal(M, i-1, j+1) + 1;
        return M;
    }


    private int[][] M4(int[][] mx, int[][] M) {
        for (int i = n-1; i >= 0; i--)
            for (int j = m-1; j >= 0; j--)
                if (mx[i][j] == 1) M[i][j] = getVal(M, i+1, j+1) + 1;
        return M;
    }

    private int getVal(int[][] M, int i, int j){
        return i < 0 || j < 0 || i >= n || j >= m ? 0 : M[i][j];
    }
    // Solution 2 ends here

    public static void main(String[] args) {
        int[][] mx = new int[][] {
                {1,0,0,0,0,0,0,0,1},
                {0,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,1,0,0},
                {0,0,0,1,0,1,0,0,0},
                {0,0,0,0,1,0,0,0,0},
                {0,0,0,1,0,1,0,0,0},
                {0,0,1,0,0,0,1,0,0},
                {0,1,0,0,0,0,0,1,0},
                {1,0,0,0,0,0,0,0,1},
        };
        int[][] mx2 = new int[][] {
                {0,1,1,0,1},
                {1,1,1,1,0},
                {0,0,1,0,1},
        };
        LargestXOf1s lx1 = new LargestXOf1s();
        System.out.println(lx1.largest(mx)); // 3
        System.out.println(lx1.largest(mx2)); // 2
        System.out.println(lx1.lowerSpace(mx)); // 3
        System.out.println(lx1.lowerSpace(mx2)); // 2
    }
}
