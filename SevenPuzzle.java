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
import resources.ConsoleColors.*;

import static resources.ConsoleColors.*;

public class SevenPuzzle {
    static class Board {
        public final static int rows = 2, cols = 4;
        private final int[][] board = new int[rows][cols];
        public int i0, j0, i1, j1;
        public Board(){}
        public Board (int[] vals) {
            for (int i = 0; i < rows; i++)
                System.arraycopy(vals, i * cols, board[i], 0, cols);
                // board[i][j] = vals[i * cols + j];
        }

        public boolean swap(int i, int j) { // must put index for zero at i1 and j1
            if (outOfBound(i, j)) return false;
            this.i1 = i0;
            this.j1 = j0;
            board[i0][j0] = board[i][j];
            board[i][j] = 0;
            i0 = i;
            j0 = j;
            return true;
        }

        public boolean outOfBound(int i, int j) {
            return i < 0 || j < 0 || i >= rows || j >= cols;
        }

        @Override
        public int hashCode() {
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

        public Board clone() {
            Board b = new Board();
            for (int i = 0; i < rows; i++)
                System.arraycopy(board[i], 0, b.board[i], 0, cols);
                //b.board[i][j] = board[i][j];
            b.i0 = i0;
            b.j0 = j0;
            b.i1 = i1;
            b.j1 = j1;
            return b;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                sb.append('[');
                for (int j = 0; j < cols; j++) {
                    if (i == i0 && j == j0)
                        sb.append(RED).append(board[i][j]).append(RESET);
                    else if (i == i1 && j == j1)
                        sb.append(CYAN).append(board[i][j]).append(RESET);
                    else sb.append(board[i][j]);
                    if (j != cols - 1) sb.append(", ");
                }
                sb.append("]\n");
            }

            return new String(sb);
        }
    }

    enum Move {
        L(-1, 0), R(1, 0), U(0, 1), D(0, -1);
        final int di, dj;
        Move(int di, int dj) {
            this.di = di;
            this.dj = dj;
        }

        public int i(int i) {
            return i + di;
        }

        public int j(int j) {
            return j + dj;
        }
    }

    public int numOfSteps(int[] values) {
        Queue<Board> q = new ArrayDeque<>();
        Map<Board, Integer> map = new HashMap<>();

        Board start = new Board(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        q.offer(start);
        map.put(start, 0);

        while (!q.isEmpty()) {
            Board cur = q.poll();
            int step = map.get(cur);
            int i0 = cur.i0, j0 = cur.j0;

            for (Move move : Move.values()) {
                if (!cur.swap(move.i(i0), move.j(j0))) continue;
                if (!map.containsKey(cur)) {
                    Board next = cur.clone();
                    q.offer(next);
                    map.put(next, step + 1);
                }
                cur.swap(i0, j0); // swap back
            }
        }

        return map.getOrDefault(new Board(values), -1);
    }

    public static void main(String[] args) {
        SevenPuzzle sp = new SevenPuzzle();
        System.out.println(sp.numOfSteps(new int[]{1, 0, 2, 3, 4, 5, 6, 7}));
        System.out.println(sp.numOfSteps(new int[]{3, 6, 0, 7, 1, 2, 4, 5}));
        System.out.println(sp.numOfSteps(new int[]{6, 7, 3, 5, 4, 2, 1, 0}));
    }
}
