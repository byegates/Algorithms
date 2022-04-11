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
        public int i0, j0;
        public List<Integer> iList, jList;
        public Board(){}
        public Board (int[] vals) {
            for (int i = 0; i < rows; i++)
                System.arraycopy(vals, i * cols, board[i], 0, cols);
                // for (int j = 0; j < cols; j++)
                    // board[i][j] = vals[i * cols + j];
            iList = new ArrayList<>();
            jList = new ArrayList<>();
        }

        public boolean swap(int i, int j) { // must put index for zero at i1 and j1
            if (outOfBound(i, j)) return false;
            iList.add(i0);
            jList.add(j0);
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
                // for (int j = 0; j < cols; j++)
                    // b.board[i][j] = board[i][j];
            b.i0 = i0;
            b.j0 = j0;
            b.iList = new ArrayList<>(iList);
            b.jList = new ArrayList<>(jList);
            return b;
        }

        public int steps() {return iList.size();}

        public void printAll() {
            System.out.println();
            List<Integer> iL = new ArrayList<>(iList); // need new one, otherwise it will cause dead loop
            List<Integer> jL = new ArrayList<>(jList);
            iL.add(i0); // to print start status, first swap will be from i0, j0 to i0, j0
            jL.add(j0);
            Board b = clone(); // as we are printing while swap the steps backwards, better do it with new board
            b.iList = new ArrayList<>();
            b.jList = new ArrayList<>();
            for (int k = iL.size() - 1; k >= 0; k--) {
                b.swap(iL.get(k), jL.get(k));
                System.out.printf("%d :\n", iL.size() - k - 1);
                System.out.print(b);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rows; i++) {
                sb.append('[');
                for (int j = 0; j < cols; j++) {
                    if (i == i0 && j == j0)
                        sb.append(RED).append(board[i][j]).append(RESET); // always print 0 as red
                    else if (i == iList.get(iList.size() - 1) && j == jList.get(jList.size() - 1)) // print what was switched with 0 as cyan
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

    public Board solve(int[] vals) {
        Queue<Board> q = new ArrayDeque<>();
        Set<Board> set = new HashSet<>(); // if we want all solutions we can actually save Map<Board, Board>

        Board end = new Board(new int[]{0, 1, 2, 3, 4, 5, 6, 7});
        Board start = new Board(vals);
        if (end.equals(start)) return end;

        q.offer(end);
        set.add(end);

        while (!q.isEmpty()) {
            Board cur = q.poll();
            for (Move move : Move.values()) {
                Board next = cur.clone(); // Better clone first to avoid redundant swap back, which also messes up steps in iList/jList
                if (!next.swap(move.i(next.i0), move.j(next.j0))) continue;
                if (next.equals(start)) return next;
                if (!set.contains(next)) {
                    q.offer(next);
                    set.add(next);
                }
            }
        }

        return null;
    }

    public int numOfSteps(int[] vals) {
        Board b = solve(vals);
        return b == null ? -1 : b.iList.size();
    }

    public static void main(String[] args) {
        SevenPuzzle sp = new SevenPuzzle();
        Board res1 = sp.solve(new int[]{1, 2, 3, 0, 4, 5, 6, 7});
        res1.printAll();//System.out.println(res1.steps()); // 3

        Board res2 = sp.solve(new int[]{1, 0, 3, 7, 4, 6, 2, 5});
        res2.printAll();//System.out.println(res2.steps()); // 11
        Board res3 = sp.solve(new int[]{3, 6, 0, 7, 1, 2, 4, 5});
        res3.printAll();//System.out.println(res3.steps()); // 22
        System.out.println();
        int[] noSolution = new int[]{6, 7, 3, 5, 4, 2, 1, 0};
        System.out.println(sp.numOfSteps(noSolution));
    }
}
