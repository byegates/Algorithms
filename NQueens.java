//Get all valid ways of putting N Queens on an N * N chessboard so that no two Queens threaten each other.
//
//        Assumptions
//        N > 0
//        Return
//
//        A list of ways of putting the N Queens
//        Each way is represented by a list of the Queen's y index for x indices of 0 to (N - 1)
//        Example
//
//        N = 4, there are two ways of putting 4 queens:
//
//        [1, 3, 0, 2] --> the Queen on the first row is at y index 1, the Queen on the second row is at y index 3,
//        the Queen on the third row is at y index 0 and the Queen on the fourth row is at y index 2.
//
//        [2, 0, 3, 1] --> the Queen on the first row is at y index 2, the Queen on the second row is at y index 0,
//        the Queen on the third row is at y index 3 and the Queen on the fourth row is at y index 1.

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NQueens {
    // default solution
    public static List<List<Integer>> nqueens(int n) { // TC: n! * n, SC: O(1)
        List<List<Integer>> res = new ArrayList<>();
        int[] cur = new int[n];
        nQueens(0, cur, res);
        return res;
    }

    private static void nQueens(int row, int[] cur, List<List<Integer>> res) {
        if (row == cur.length) {
            res.add(toList(cur));
            return;
        }

        for (int col = 0; col < cur.length; col++) {
            if (!valid(row, col, cur)) continue;
            cur[row] = col;
            nQueens(row + 1, cur, res); // no need to reset current level value as it'll be overridden in int[]
        }
    }

    private static boolean valid(int row, int col, int[] cur) {
        for (int prevRow = 0; prevRow < row; prevRow++)
            if (cur[prevRow] == col || Math.abs(cur[prevRow] - col) == row - prevRow)
                return false;
        return true;
    }

    // this method is used by both the default solution and solution 2
    private static List<Integer> toList(int[] cur) {
        List<Integer> l = new ArrayList<>(cur.length);
        for (int i : cur) l.add(i);
        return l;
    }

    // Solution 2 with set storages(using a helper class), for demonstration purpose only
    public static List<List<Integer>> nQueens(int n) { // TC: n!, SC: O(n)
        List<List<Integer>> res = new ArrayList<>();
        nQueens(0, new Helper(n), res);
        return res;
    }

    private static void nQueens(int row, Helper H, List<List<Integer>> res) {
        if (row == H.cur.length) {
            res.add(toList(H.cur));
            return;
        }

        for (int col = 0; col < H.cur.length; col++) {
            if (H.invalid(row, col)) continue;
            H.add(row, col);
            nQueens(row + 1, H, res);
            H.remove(row, col);
        }
    }

    static class Helper{

        Set<Integer> cols;
        Set<Integer> diag1;
        Set<Integer> diag2;
        int[] cur;

        Helper(int n) {
            cols = new HashSet<>(n);
            diag1 = new HashSet<>(n);
            diag2 = new HashSet<>(n);
            cur = new int[n];
        }

        void add(int r, int c) {
            cur[r] = c;
            cols.add(c);
            diag1.add(c + r);
            diag2.add(c - r);
        }

        void remove(int r, int c) {
            cols.remove(c);
            diag1.remove(c + r);
            diag2.remove(c - r);
        }

        boolean invalid(int r, int c) {return cols.contains(c) || diag1.contains(c + r) || diag2.contains(c - r);}
    }
    // Solution 2 ends here

    // some print methods for demo only
    private static void printQueens(List<Integer> l) {
        System.out.println();
        System.out.println(l);
        System.out.println();
        for (int x = 0; x < l.size(); x++) {
            for (int y = 0; y < l.size(); y++) {
                String token = l.get(x) == y ? String.format("%s", y) : "-";
                System.out.printf("[%s]", token);
            }
            System.out.println();
        }
    }

    private static void printNQueens(List<List<Integer>> res){for (List<Integer> l : res) printQueens(l);}

    private static void printDiags(int n) {
        System.out.println();
        printDiagsHelper(1, n);
        System.out.println("\n---------------------------------------\n");
        printDiagsHelper(-1, n);
    }

    private static void printDiagsHelper(int sign, int n) {
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++)
                System.out.printf("%2d|", (row + col * sign));
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        printNQueens(nqueens(4));
        System.out.println();
        //printNQueens(withStorage(4));
        System.out.println(nqueens(5));
        System.out.println(nQueens(5));
        printDiags(5);
    }
}
