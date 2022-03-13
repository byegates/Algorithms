//Write a program to solve a Sudoku puzzle by filling the empty cells.
//
//        A sudoku solution must satisfy all of the following rules:
//
//        Each of the digits 1-9 must occur exactly once in each row.
//        Each of the digits 1-9 must occur exactly once in each column.
//        Each of the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
//        The '.' character indicates empty cells.

//Input:
// board = [["5","3",".",".","7",".",".",".","."],
//          ["6",".",".","1","9","5",".",".","."],
//          [".","9","8",".",".",".",".","6","."],
//          ["8",".",".",".","6",".",".",".","3"],
//          ["4",".",".","8",".","3",".",".","1"],
//          ["7",".",".",".","2",".",".",".","6"],
//          [".","6",".",".",".",".","2","8","."],
//          [".",".",".","4","1","9",".",".","5"],
//          [".",".",".",".","8",".",".","7","9"]]
//
//Output:  [["5","3","4","6","7","8","9","1","2"],
//          ["6","7","2","1","9","5","3","4","8"],
//          ["1","9","8","3","4","2","5","6","7"],
//          ["8","5","9","7","6","1","4","2","3"],
//          ["4","2","6","8","5","3","7","9","1"],
//          ["7","1","3","9","2","4","8","5","6"],
//          ["9","6","1","5","3","7","2","8","4"],
//          ["2","8","7","4","1","9","6","3","5"],
//          ["3","4","5","2","8","6","1","7","9"]]

import java.util.ArrayList;
import java.util.List;

import static resources.ConsoleColors.*;
import static resources.SudokuBoards.Easy1;
import static resources.SudokuBoards.MAKER;

public class Sudoku {

    static class Cell {
        int r, c, b; // the row, col and box number for each cell
        Cell(int r, int c, int b) {
            this.r = r;
            this.c = c;
            this.b = b;
        }
    }

    private static int depth;
    static List<Cell> emptyCells = new ArrayList<>(); // search space, int[] for interview, class is better
    static int[] row = new int[9]; // for each row what numbers have been used
    static int[] col = new int[9]; // for each col what numbers have been used
    static int[] box = new int[9]; // for each box what numbers have been used

    public static void solveSudoku(char[][] board) {
        depth = 0;
        // initialization
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++) {
                Cell cell = new Cell(r, c, boxNumber(r, c));
                if (board[r][c] == '.') emptyCells.add(cell);
                else setBitUsed(cell, 1 << (board[r][c] - '1'));
            }

        dfs(board, 0);
        System.out.printf("\n" + BLUE + "Depth of Search: " + RED_BOLD_BRIGHT + "%d" + RESET + '\n', depth);
    }

    private static boolean dfs(char[][] board, int d) {
        if (d == emptyCells.size()) return true;
        depth++;
        Cell cell = emptyCells.get(d);
        for (int i = 0; i < 9; i++) {
            if (cellUsed(cell, i)) continue;
            int[] beforeStatus = new int[]{row[cell.r], col[cell.c], box[cell.b]};
            board[cell.r][cell.c] = (char)(i + '1');
            setBitUsed(cell, 1 << i);
            if (dfs(board, d + 1)) return true;
            recoverBeforeStatus(cell, beforeStatus); // recover before status before try next branch
        }
        return false;
    }

    private static void recoverBeforeStatus(Cell cell, int[] beforeStatus) {
        row[cell.r] = beforeStatus[0];
        col[cell.c] = beforeStatus[1];
        box[cell.b] = beforeStatus[2];
    }

    private static boolean cellUsed(Cell cell, int i) {
        return used(row[cell.r], i) || used(col[cell.c], i) || used(box[cell.b], i);
    }
    private static boolean used(int x, int i) {
        return ((x >> i) & 1) == 1;
    }

    private static int boxNumber(int c, int r) {
        return r / 3 * 3 + c / 3;
    }

    private static void setBitUsed(Cell cell, int bitUsed) {
        row[cell.r] |= bitUsed;
        col[cell.c] |= bitUsed;
        box[cell.b] |= bitUsed;
    }
// TC: 9^81 → 9^n where n is the number of empty Cells
// SC: 3 * n + 27 + 81, what do you say this is…

    private static void printSudoku(char[][] board, char[][] b2) {

        System.out.print("\n");
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == b2[r][c]) System.out.printf(RED_BOLD_BRIGHT + "%s  " + RESET, board[r][c]);
                else System.out.printf("%s  ", board[r][c]);

                if ((c + 1) % 3 == 0 && c != 8) System.out.print(CYAN + "|  " + RESET);
            }
            System.out.println();

            if ((r + 1) % 3 == 0 && r != 8) System.out.print(CYAN + "-------------------------------\n" + RESET);
        }
        System.out.println();
    }

    private static void printSudokuAsArray(char[][] board, char[][] b2) {

        System.out.print("\n{\n");
        for (int r = 0; r < board.length; r++) {
            System.out.print("        {");
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == b2[r][c]) System.out.printf(RED_BOLD_BRIGHT + "'%s', " + RESET, board[r][c]);
                else System.out.printf("'%s', ", board[r][c]);

                if ((c + 1) % 3 == 0 && c != 8) System.out.print("  ");
            }
            System.out.print("},\n");

            if ((r + 1) % 3 == 0 && r != 8) System.out.print("\n");
        }
        System.out.print("}\n\n");

    }

    public static void main(String[] args) {
        // solve a sudoku and print the comparison
        char[][] board = MAKER;
        char[][] b2 = new char[board.length][];
        for (int i = 0; i < board.length; i++)
            b2[i] = board[i].clone();

        solveSudoku(board);
        printSudoku(board, b2);
        printSudokuAsArray(board, b2);
    }

}
