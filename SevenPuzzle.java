/*
        Given eight cards with number 0, 1, 2, ..7 on them, the cards are placed in two rows with 4 cards in each row.
        In each step only card 0 could swap with one of its adjacent(top, right, bottom, left) card.
        Your goal is to make all cards placed in order like this:

        0 1 2 3
        4 5 6 7

        Find the minimum number of steps from the given state to the final state.
        If there is no way to the final state, then return -1.
        The state of cards is represented by an array of integer,
        for example [0,1,2,3,4,5,6,7] where the first four numbers are in the first row from left to right
        while the others are placed in the second row from left to right.

        Example:
        Input: [4,1,2,3,5,0,6,7]       Output: 2

        Explanation:
        Initial state is:
        4 1 2 3
        5 0 6 7
        First swap 0 with 5, then the state is:
        4 1 2 3
        0 5 6 7
        Then swap 0 with 4, then we get the final state:
        0 1 2 3
        4 5 6 7
*/

import java.util.*;
import static resources.ConsoleColors.*;

public class SevenPuzzle {
    static class Board {
        public final static int rows = 2, cols = 4;
        private final int[][] board = new int[rows][cols];
        public int steps;
        public Cell c0; // Coordinates of 0 on current board
        public Map<Board, Board> pathMap = new HashMap<>();
        public Board() {}
        public Board (int[] input) {
            for (int i = 0; i < rows; i++)
                System.arraycopy(input, i * cols, board[i], 0, cols); // for (int j = 0; j < cols; j++) board[i][j] = input[i * cols + j];
            setZeroIndex();
        }

        public void setZeroIndex() {
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    if (board[i][j] == 0)
                        c0 = new Cell(i, j);
        }

        public boolean swap(Cell p) { // must put index for zero at i1 and j1
            if (outOfBound(p)) return false;
            board[c0.i][c0.j] = board[p.i][p.j];
            board[p.i][p.j] = 0;
            c0.i = p.i;
            c0.j = p.j;
            steps++;
            return true;
        }

        public boolean outOfBound(Cell p) {
            return p.i < 0 || p.j < 0 || p.i >= rows || p.j >= cols;
        }

        @Override
        public int hashCode() {// [0, 1, 2, 3, 4, 5, 6, 7] ==> (int) 1,234,567
            int code = 0;
            for (int[] row : board)
                for (int val : row)
                    code = code * 10 + val;
            return code;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) return true;
            if (!(o instanceof Board b)) return false;
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < cols; j++)
                    if (board[i][j] != b.board[i][j])
                        return false;
            return true;
        }

        public Board cloneBoard() {
            Board b = new Board();
            for (int i = 0; i < rows; i++)
                System.arraycopy(board[i], 0, b.board[i], 0, cols); // for (int j = 0; j < cols; j++) b.board[i][j] = board[i][j];
            b.c0 = cloneZero();
            b.pathMap = pathMap;
            b.steps = steps;
            return b;
        }

        public Cell cloneZero() {
            return new Cell(c0.i, c0.j);
        }

        public void printSteps() {
            int step = 0;
            Board cur = this;
            System.out.printf("\n%d :\n%s", 0, cur);
            while (step++ < steps) {
                Board next = pathMap.get(cur);
                System.out.printf("%d :\n%s", step, next.toString(cur.c0));
                cur = next;
            }
        }

        public String toString() {
            return toString(c0);
        }

        public String toString(Cell c1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                sb.append('[');
                for (int j = 0; j < cols; j++) {
                    if (i == c0.i && j == c0.j)
                        sb.append(RED).append(board[i][j]).append(RESET); // always print 0 as red
                    else if (i == c1.i && j == c1.j) // print what was switched with 0 as cyan
                        sb.append(CYAN).append(board[i][j]).append(RESET);
                    else sb.append(board[i][j]);
                    if (j != cols - 1) sb.append(", ");
                }
                sb.append("]\n");
            }

            return sb.toString();
        }
    }

    static class Cell {
        int i, j;
        Cell (int i, int j) {
            this.i = i;
            this.j = j;
        }
    }

    enum Move { // four directions we could possibly move
        U(-1, 0), D(1, 0), L(0, 1), R(0, -1),
        //DA1(1,1), DA2(1, -1), DA3(-1, 1), DA4(-1,-1), // to move on diagonals
        ;
        final int di, dj; // delta i, delta j
        Move(int di, int dj) {
            this.di = di;
            this.dj = dj;
        }

        public Cell next(Cell c) {
            return new Cell(c.i + di, c.j + dj);
        }
    }

    public Board solve(int[] input) {
        Queue<Board> q = new ArrayDeque<>();
        Set<Board> set = new HashSet<>(); // if we want all solutions we can actually save Map<Board, Board>

        Board end = new Board(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        Board start = new Board(input);
        if (end.equals(start)) return end;

        Map<Board, Board> map = end.pathMap;
        q.offer(end);
        set.add(end);

        while (!q.isEmpty()) {
            Board cur = q.poll();

            for (Move move : Move.values()) {

                Board next = cur.cloneBoard(); // Better clone first to avoid redundant swap back, which also messes up steps in iList/jList
                if (!next.swap(move.next(cur.c0))) continue;
                if (set.contains(next)) continue;

                q.offer(next);
                set.add(next);
                map.put(next, cur);

                if (next.equals(start))
                    return next;

            }
        }

        return null;
    }

    public int numOfSteps(int[] input) {
        Board b = solve(input);
        return b == null ? -1 : b.steps;
    }

    public static void main(String[] args) {
        SevenPuzzle sp = new SevenPuzzle();
        Board res1 = sp.solve(new int[]{1, 2, 3, 0, 4, 5, 6, 7});
        // System.out.println(res1.steps); // 3
        res1.printSteps();

        Board res2 = sp.solve(new int[]{1, 0, 3, 7, 4, 6, 2, 5});
        res2.printSteps();
        // System.out.println(res2.steps); // 11
        Board res3 = sp.solve(new int[]{3, 6, 0, 7, 1, 2, 4, 5});
        res3.printSteps();
        // System.out.println(res3.steps); // 22
        // Board res4 = sp.solve(new int[]{5, 1, 2, 3, 4, 0, 6, 7});
        // res4.printAll();
        // System.out.println(res4.numOfSteps()); // 0
        System.out.println();
        int[] noSolution = new int[]{6, 7, 3, 5, 4, 2, 1, 0};
        System.out.println(sp.numOfSteps(noSolution)); // -1
    }
}
