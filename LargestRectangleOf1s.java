/*
        Determine the largest rectangle of 1s in a binary matrix (a binary matrix only contains 0 and 1),
        return the area.

        Assumptions

        The given matrix is not null and has size of M * N, M >= 0 and N >= 0
        Examples
        { {0, 0, 0, 0},
        {1, 1, 1, 1},
        {0, 1, 1, 1},
        {1, 0, 1, 1} }

        the largest rectangle of 1s has area of 2 * 3 = 6

        Very good explanation:
        https://stackoverflow.com/questions/11481868/largest-rectangle-of-1s-in-2d-binary-matrix
*/


import java.util.Arrays;
import java.util.PriorityQueue;

public class LargestRectangleOf1s {

    public int dp(int[][] mx) { // TC: O(n^2), SC: O(n)
        if (mx.length == 0) return 0;

        int m = mx.length, n = mx[0].length, maxArea = 0;
        int[] left = new int[n], right = new int[n], height = new int[n]; // height is # of consecutive 1 from top
        Arrays.fill(right, n - 1);

        for (int[] row : mx) {
            int rB = n - 1;
            for (int j = n - 1; j >= 0; j--)
                if (row[j] == 1) right[j] = Math.min(right[j], rB);
                else {
                    right[j] = n - 1; // do we really need to do this?
                    rB = j - 1;
                }

            int lB = 0;
            for (int j = 0; j < n; j++)
                if (row[j] == 1) {
                    left[j] = Math.max(left[j], lB);
                    height[j]++;
                    maxArea = Math.max(maxArea, height[j] * (right[j] - left[j] + 1));
                } else {
                    height[j] = 0;
                    left[j] = 0;
                    lB = j + 1;
                }
        }
        return maxArea;
    }

    private int getArea(int length, int width) {
        return length * width;
    }

    private int[][] right(int[][] mx, int m, int n) {
        int[][] M = new int[n][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = m - 1; j >= 0; j--) {
                if (mx[i][j] == 1) {
                    M[i][j] = M[i][j + 1] + 1;
                }
            }
        }
        return M;
    }


    public int halfDP(int[][] mx) {
        if (mx.length == 0) return 0;
        int n = mx.length, m = mx[0].length;

        int[][] right = right(mx, m, n); // from right left for longest consecutive 1s

        int max = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                for (int l = right[i][j]; l >= 0; l--) {
                    int row = i;
                    while (row < n && right[row][j] >= l) row++;
                    max = Math.max(max, getArea(row - i, l));
                }
        return max;
    }

    public static void main(String[] args) {
        int[][] mx = new int[][]{
                {1, 0, 0, 1, 1, 0, 1, 1},
                {1, 0, 0, 0, 0, 1, 0, 0},
                {0, 1, 1, 1, 0, 0, 1, 1},
                {0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1, 1, 1, 0}
        };

        int[][] mx2 = new int[][]{
                {0, 0, 0, 0},
                {1, 1, 1, 1},
                {0, 1, 1, 1},
                {1, 0, 1, 1} };

        LargestRectangleOf1s lro1 = new LargestRectangleOf1s();
        System.out.println(lro1.dp(mx2)); // 6

        System.out.println(lro1.dp(mx)); // 8
        System.out.println(lro1.halfDP(mx)); // 8

    }

}

