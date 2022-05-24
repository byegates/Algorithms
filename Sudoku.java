//Write a program to solve a Sudoku puzzle by filling the empty cells.
//
//        A sudoku solution must satisfy all the following rules:
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
import static resources.SudokuBoards.*;

public class Sudoku {

    int depth;
    List<Cell> emptyCells = new ArrayList<>(); // search space, int[] for interview, class is better
    int[]  rows = new int[9]; // for each row what numbers have been used
    int[]  cols = new int[9]; // for each col what numbers have been used
    int[] boxes = new int[9]; // for each box what numbers have been used

    public static void main(String[] args) {
        // solve a sudoku and print the comparison
        char[][] board = YUANHAO;
        char[][] b2 = new char[board.length][];
        for (int i = 0; i < board.length; i++) b2[i] = board[i].clone();

        Sudoku sudoku = new Sudoku();

        sudoku.solveSudoku(b2);
        sudoku.printSudoku(b2, board);
        sudoku.printSudokuAsArray(b2, board);

        char[][] b3 = YUANHAO;
        boolean isValid = sudoku.isValidSudoku(b3);

        sudoku.printValidSudoku(b3);
        sudoku.printRes(isValid);
    }

    public void solveSudoku(char[][] board) {
        depth = 0;
        // initialization
        for (int row = 0; row < 9; row++)
            for (int col = 0; col < 9; col++) {
                Cell c = new Cell(row, col, (row / 3) * 3 + (col / 3));
                if (board[row][col] == '.') emptyCells.add(c);
                else {
                    int val = board[row][col] - '1';
                     rows[row] |= 1 << val;
                     cols[col] |= 1 << val;
                    boxes[c.b] |= 1 << val;
                }
            }

        dfs(board, 0);
        System.out.printf("\n" + BLUE + "Depth of Search: " + RED_BOLD_BRIGHT + "%,d" + RESET + '\n', depth);
    }

    private boolean dfs(char[][] board, int i) {
        if (i == emptyCells.size()) return true;
        depth++;
        Cell c = emptyCells.get(i);
        for (int val = 0; val < 9; ++val) {
            if (
                (( rows[c.r] >> val) & 1) == 1 ||
                (( cols[c.c] >> val) & 1) == 1 ||
                ((boxes[c.b] >> val) & 1) == 1
            )
                continue; // skip if that value is existed!
            board[c.r][c.c] = (char) ('1' + val);
            int oldRow = rows[c.r], oldCol = cols[c.c], oldBox = boxes[c.b]; // backup old values
             rows[c.r] |= 1 << val;
             cols[c.c] |= 1 << val;
            boxes[c.b] |= 1 << val;
            if (dfs(board, i + 1)) return true;
             rows[c.r] = oldRow;
             cols[c.c] = oldCol;
            boxes[c.b] = oldBox; // 吃了🤮
        }
        return false;
    }

// TC: 9^81 → 9^n where n is the number of empty Cells
// SC: 3 * n + 27 + 81, what do you say this is…

// above are solution for Sudoku Solver only
// below are for Sudoku board validations

    public boolean isValidSudoku(char[][] board) {
        int N = 9;
        int[] row = new int[N], col = new int[N], box = new int[N];

        for (int r = 0; r < N; r++)
            for (int c = 0; c < N; c++) {
                if (board[r][c] == '.') continue;
                int i = board[r][c] - '1';
                int b = (r / 3) * 3 + (c / 3);
                if (
                    ((row[r] >> i) & 1) == 1 ||
                    ((col[c] >> i) & 1) == 1 ||
                    ((box[b] >> i) & 1) == 1
                ) return false;
                row[r] |= 1 << i;
                col[c] |= 1 << i;
                box[b] |= 1 << i;
            }
        return true;
    }

    private void printRes(boolean isValid) {
        String s = (isValid) ? "" : "NOT ";
        System.out.print(RED_BOLD_BRIGHT + "\n\n" + s + "VALID" + RESET);
    }

    private void printValidSudoku(char[][] board) {

        System.out.print("\n");
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                String coloredNumber = switch (board[r][c]) {
                    case '1' -> RED              + board[r][c] + "  " + RESET;
                    case '2' -> GREEN            + board[r][c] + "  " + RESET;
                    case '3' -> YELLOW           + board[r][c] + "  " + RESET;
                    case '4' -> BLUE             + board[r][c] + "  " + RESET;
                    case '5' -> PURPLE           + board[r][c] + "  " + RESET;
                    case '6' -> CYAN             + board[r][c] + "  " + RESET;
                    case '7' -> BLACK            + board[r][c] + "  " + RESET;
                    case '8' -> WHITE            + board[r][c] + "  " + RESET;
                    case '9' -> CYAN_BOLD_BRIGHT + board[r][c] + "  " + RESET;
                    default -> ".  ";
                };
                System.out.print(coloredNumber);

                if ((c + 1) % 3 == 0 && c != 8) System.out.print("|  ");
            }
            System.out.print("\n");

            if ((r + 1) % 3 == 0 && r != 8) System.out.print("-------------------------------\n");
        }
    }

    private void printSudoku(char[][] board, char[][] b2) {

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

    private void printSudokuAsArray(char[][] board, char[][] b2) {

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

    static class Cell {
        int r, c, b; // the row, col and box number for each cell

        Cell(int r, int c, int b) {
            this.r = r;
            this.c = c;
            this.b = b;
        }
    }

}
