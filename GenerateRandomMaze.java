/*
        Randomly generate a maze of size N * N (where N = 2K + 1) whose corridor and wall’s width are both 1 cell.
        For each pair of cells on the corridor, there must exist one and only one path between them.
        (Randomly means that the solution is generated randomly, and whenever the program is executed,
        the solution can be different.). The wall is denoted by 1 in the matrix and corridor is denoted by 0.

        Assumptions
        N = 2K + 1 and K >= 0
        the top left corner must be corridor
        there should be as many corridor cells as possible
        for each pair of cells on the corridor, there must exist one and only one path between them

        Examples
2
        N = 5, one possible maze generated is
        0  0  0  1  0
        1  1  0  1  0
        0  1  0  0  0
        0  1  1  1  0
        0  0  0  0  0
*/

import java.util.*;

public class GenerateRandomMaze {
    public int[][] maze(int n) { // TC: , SC:
        int[][] maze = new int[n][n]; // n = 2 * k + 1, k is a non-neg int

        for (int[] row : maze) Arrays.fill(row, 1); // init the whole thing as walls
        maze[0][0] = 0; // start with 1st cell as corridor

        generate(maze, 0, 0);
        return maze;
    }

    private void generate(int[][] maze, int x, int y) {
        ArrayList<Move> directions = new ArrayList<>(Arrays.asList(Move.values()));
        Collections.shuffle(directions);

        for (Move mv : directions) {
            int x2 = mv.x(x, 2);
            int y2 = mv.y(y, 2);
            if (isValidWall(maze, x2, y2)) {
                maze[mv.x(x, 1)][mv.y(y, 1)] = 0;
                maze[x2][y2] = 0;
                //printMX(maze);
                generate(maze, x2, y2);
            }
        }

    }

    private boolean isValidWall(int[][] maze, int x, int y) {
        return x >= 0 && x < maze.length && y >= 0 && y < maze[0].length && maze[x][y] == 1;
    }

    enum Move {
        N(-1, 0), S(1, 0), W(0, -1), E(0, 1);

        final int dx, dy;

        Move(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int x(int x, int cells) {return x + cells * dx;}
        public int y(int y, int cells) {return y + cells * dy;}

    }

    public void printMX(int[][] mx) {
        for (int[] row : mx)
            System.out.println(Arrays.toString(row));
        System.out.println();
    }

    public static int countMX(int[][] mx, int val) {
        int count = 0;
        for (int[] row : mx)
            for (int i : row)
                if (i == val) count++;
        return count;
    }

    public static void main(String[] args) {
        GenerateRandomMaze grm = new GenerateRandomMaze();
        for (int k = 2; k < 8; k++) {
            int[][] mx = grm.maze(2 * k + 1);
            System.out.printf("k: %d, size: %2d, #of ones: %3d(%d^2*2), #of zeros: %3d\n", k, 2*k+1, countMX(mx, 1), k, countMX(mx, 0));
        }

        System.out.println();

        for (int i = 0; i < 5; i++) {
            int n = 5;
            int[][] mx = grm.maze(n);
            System.out.printf("size: %2d, #of ones: %3d, #of zeros: %3d\n", n, countMX(mx, 1), countMX(mx, 0));
            grm.printMX(mx);
        }
    }
}
