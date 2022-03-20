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
                    left[i][j] = i < 1 || j < 1 ? 1 : left[i-1][j-1] + 1;
                    up[i][j] = i < 1 || j+1 >= m ? 1 : up[i-1][j+1] + 1;
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
                    right[i][j] = i+1 >= n || j+1 >= m ? 1 : right[i+1][j+1] + 1;
                    down[i][j] = i +1 >= n || j < 1 ? 1 : down[i+1][j-1] + 1;
            }

        merge(right, down);
        return right;
    }

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
    }
}
