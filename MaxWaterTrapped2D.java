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
    static class Cell implements Comparable<Cell>{
        int i, j, height;
        boolean visited;
        Cell (int i, int j, int height) {
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
        int rows, cols, res = 0;
        if ((rows = mx.length) < 3 || (cols = mx[0].length) < 3) return 0;
        Cell[][] cells = createCellMatrix(mx, rows, cols);

        PriorityQueue<Cell> pq = new PriorityQueue<>();
        fillBorders(cells, pq, rows, cols);

        while (!pq.isEmpty()) {
            Cell cur = pq.poll();
            for (Cell neighbor : getAllNeighbors(cur, cells, new ArrayList<>())) {
                res += Math.max(cur.height - neighbor.height, 0);
                neighbor.height = Math.max(cur.height, neighbor.height);
                neighbor.visited = true;
                pq.offer(neighbor);
            }
        }

        return res;
    }

    private List<Cell> getAllNeighbors(Cell cur, Cell[][] cells, List<Cell> neighbors){
        int[] x = {cur.i + 1, cur.i - 1, cur.i, cur.i};
        int[] y = {cur.j, cur.j, cur.j + 1, cur.j - 1};

        for (int i = 0; i < x.length; i++) {
            try {
                if (!cells[x[i]][y[i]].visited) neighbors.add(cells[x[i]][y[i]]);
            } catch (IndexOutOfBoundsException ignored) {}
        }

        return neighbors;
    }

    private void fillBorders(Cell[][] cells, PriorityQueue<Cell> pq, int rows, int cols) {
        for (int j = 0; j < cols; j++) {
            fillBorder(cells, pq, 0, j);
            fillBorder(cells, pq,rows - 1, j);
        }
        for (int i = 1; i < rows - 1; i++) {
            fillBorder(cells, pq, i, 0);
            fillBorder(cells, pq, i, cols - 1);
        }
    }

    private void fillBorder(Cell[][] cells, PriorityQueue<Cell> pq, int i, int j) {
        cells[i][j].visited = true;
        pq.offer(cells[i][j]);
    }

    private Cell[][] createCellMatrix(int[][] mx, int rows, int cols) {
        Cell[][] cells = new Cell[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                cells[i][j] = new Cell(i, j, mx[i][j]);

        return cells;
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

