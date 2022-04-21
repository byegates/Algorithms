/*
        Given an m * n matrix of non-negative integers representing the height of each unit cell in a continent,
        the "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom edges.

        Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or lower.

        Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
        The order of returned grid coordinates does not matter.

        Example:
        Given the following 4 * 4 matrix:
        Pacific   ~   ~    ~   ~

        ~      1    2    2   (3)   *

        ~      3    2    3   (4)   *

        ~      2    4   (5)   3   *

        ~      (6)  (7)   1   4   *

        *    *    *    *  Atlantic

        Output: [0,3],[1,3],[2,2],[3,0],[3,1]
*/


import java.util.*;

public class PacificAtlanticFlow {
    public List<List<Integer>> pacificAtlantic(int[][] mx) {
        int rows = mx.length, cols = mx[0].length;
        int[][] M = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];
        Queue<List<Integer>> q = new ArrayDeque<>();

        for (int i = 0; i < rows; i++) enQ(i, 0, q, visited, M);
        for (int j = 1; j < cols; j++) enQ(0, j, q, visited, M);
        bfs(q, visited, M, mx, rows, cols);

        visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) enQ(i, cols - 1, q, visited, M);
        for (int j = 0; j < cols - 1; j++) enQ(rows - 1, j, q, visited, M);
        bfs(q, visited, M, mx, rows, cols);

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (M[i][j] == 2) res.add(Arrays.asList(i, j));
        return res;
    }

    private void bfs(Queue<List<Integer>> q, boolean[][] visited, int[][] M, int[][] mx, int rows, int cols) {
        while (!q.isEmpty()) {
            List<Integer> p = q.poll();

            for (int[] dir : new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, }) {
                int i2 = p.get(0) + dir[0];
                int j2 = p.get(1) + dir[1];
                if (i2 < 0 || j2 < 0 || i2 >= rows || j2 >= cols || visited[i2][j2] || mx[p.get(0)][p.get(1)] > mx[i2][j2]) continue;
                enQ(i2, j2, q, visited, M);
            }
        }
    }

    private void enQ(int i, int j, Queue<List<Integer>> q, boolean[][] visited, int[][] M) {
        visited[i][j] = true;
        q.offer(Arrays.asList(i, j));
        M[i][j]++;
    }

    public static void main(String[] args) {
        PacificAtlanticFlow paf = new PacificAtlanticFlow();
        int[][] mx = new int[][] {
                {12, 11, 12, 10, 10, 12, 12,  7,  0}, // 0
                {15,  9,  8,  9, 10, 10,  3,  4, 11},
                { 1,  2,  0,  5, 15,  3, 12,  2,  5},
                { 2, 12,  9,  8,  9,  9, 15,  9, 16},  // 3
                { 0, 11,  9, 15, 10, 11,  4,  8, 10},
                { 0,  6, 10,  3,  3,  5,  2, 12, 15},
                { 5,  7, 12,  9, 14, 14, 15,  7,  2},  // 6
                { 9, 14, 14,  3,  2,  7, 15,  0,  1},
                { 7,  3,  7,  6,  1, 13, 10, 12,  0},
                { 7, 13,  2,  4,  8, 10,  9, 14, 14},  // 9
                { 1,  8,  9, 11,  2, 12,  3,  7,  7},
                {14, 14,  9,  9,  4,  7, 14, 13,  3},
                { 1,  3,  7,  1,  2,  6,  4, 13, 16},  // 12
                { 0, 15,  7,  0, 12, 16, 15, 12, 16},
                { 8, 15, 11,  2,  5, 10,  9,  8, 11},
                {12,  1, 11, 15, 11,  5, 10, 14,  0}}; // 15
        System.out.println(paf.pacificAtlantic(mx)); // [[0, 5], [0, 6], [0, 7], [0, 8], [1, 8], [13, 1], [14, 1], [14, 2], [15, 0], [15, 2], [15, 3]]
        int[][] mx2 = new int[][] {
                {1, 2, 2, 3},
                {3, 2, 3, 4},
                {2, 4, 5, 3},
                {6, 7, 1, 4},
        };
        System.out.println(paf.pacificAtlantic(mx2)); // [[0, 3], [1, 3], [2, 2], [3, 0], [3, 1]]
    }

}
