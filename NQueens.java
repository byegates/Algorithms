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
import java.util.List;

public class NQueens {
    public static List<List<Integer>> nqueens(int n) {
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

    private static List<Integer> toList(int[] cur) {
        List<Integer> l = new ArrayList<>(cur.length);
        for (int i : cur) l.add(i);
        return l;
    }

    // TC: n! * 1 ? n, SC: O(n)

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

    public static void main(String[] args) {
        printNQueens(nqueens(4));
    }
}
