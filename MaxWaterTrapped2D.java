/*
        Given a non-negative integer 2D array representing the heights of bars in a matrix.
        Suppose each bar has length and width of 1. Find the largest amount of water that can be trapped in the matrix.
        The water can flow into a neighboring bar if the neighboring bar's height is smaller than the water's height.
        Each bar has 4 neighboring bars to the left, right, up and down direction.

        Assumptions
        The given matrix is not null and has size of M * N, where M > 0 and N > 0,
        all the values are non-negative integers in the matrix.

        Examples
        { { 2, 3, 4, 2 },
          { 3, 1, 2, 3 },
          { 4, 3, 5, 4 } }

        the amount of water can be trapped is 3,
        at position (1, 1) there is 2 units of water trapped,
        at position (1, 2) there is 1 unit of water trapped.
*/

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class MaxWaterTrapped2D {

    static class Cell implements Comparable<Cell> {
        int i, j, height;

        Cell(int i, int j, int height) {
            this.i = i;
            this.j = j;
            this.height = height;
        }

        @Override
        public int compareTo(Cell c) {
            return Integer.compare(this.height, c.height);
        }
    }

    public int maxTrapped(int[][] mx) {
        int rows, cols;
        if ((rows = mx.length) < 3 || (cols = mx[0].length) < 3) return 0;

        int res = 0;
        PriorityQueue<Cell> pq = new PriorityQueue<>();
        boolean[][] visited = new boolean[rows][cols];
        processBorder(mx, visited, pq, rows, cols);

        while (!pq.isEmpty()) {
            Cell cur = pq.poll(); // cur will be the shortest boarder and the limiting factor
            List<Cell> neighbors = allNeighbors(cur, mx, new ArrayList<>());
            for (Cell neighbor : neighbors) {
                if (visited[neighbor.i][neighbor.j]) continue;
                res += Math.max(cur.height - neighbor.height, 0); // Any inner neighbor lower than the shortest holds water
                neighbor.height = Math.max(cur.height, neighbor.height); // any inner neighbor higher will become new boarder
                pq.offer(neighbor);
                visited[neighbor.i][neighbor.j] = true;
            }
        }
        return res;
    }

    private void processBorder(int[][] mx, boolean[][] visited, PriorityQueue<Cell> pq, int rows, int cols) {
        for (int j = 0; j < cols; j++) fillBorder(mx, visited, pq, 0, j, rows - 1, j);
        for (int i = 1; i < rows - 1; i++) fillBorder(mx, visited, pq, i, 0, i, cols - 1);
    }

    private void fillBorder(int[][] mx, boolean[][] visited, PriorityQueue<Cell> pq, int i1, int j1, int i2, int j2) {
        pq.offer(new Cell(i1, j1, mx[i1][j1]));
        visited[i1][j1] = true;
        pq.offer(new Cell(i2, j2, mx[i2][j2]));
        visited[i2][j2] = true;
    }

    private List<Cell> allNeighbors(Cell cur, int[][] mx, List<Cell> allNeighbors) {
        int[] x = new int[] {cur.i + 1, cur.i - 1, cur.i, cur.i};
        int[] y = new int[] {cur.j, cur.j, cur.j + 1, cur.j - 1};
        for (int i = 0; i < x.length; i++)
            try {
                allNeighbors.add(new Cell(x[i], y[i], mx[x[i]][y[i]]));
            }
            catch (ArrayIndexOutOfBoundsException ignored){}

        return allNeighbors;
    }

    public static void main(String[] args) {
        MaxWaterTrapped2D mwt2 = new MaxWaterTrapped2D();
        int[][] mx = {
                {5, 8, 7, 7},
                {5, 2, 1, 5},
                {7, 1, 7, 1},
                {8, 9, 6, 9},
                {9, 8, 9, 9}
        };
        System.out.println(mwt2.maxTrapped(mx)); // 12

        int[][] mx2 = {
                { 2, 3, 4, 2 },
                { 3, 1, 2, 3 },
                { 4, 3, 5, 4 }
        };
        System.out.println(mwt2.maxTrapped(mx2)); // 3

    }
}

