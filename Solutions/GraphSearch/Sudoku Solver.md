# Sudoku Solver
[37. Sudoku Solver](https://leetcode.com/problems/sudoku-solver/)

## Solution 1: Basic DFS (14ms)
### Space and Time
TC: O(9^m*27), (m represents the number of blanks to be filled in)

SC: O(m)
```java
class Solution {
    public void solveSudoku(char[][] board) {
        solve(board);
    }

    public boolean solve(char[][] board) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '.') continue;
                for (char c = '1'; c <= '9'; c++) //trial. Try 1 through 9
                    if (valid(board, i, j, c)) {
                        board[i][j] = c; //Put c for this cell
                        if (solve(board)) return true; //If it's the solution return true
                        board[i][j] = '.'; //吃了吐
                    }
                return false;
            }
        return true;
    }

    private boolean valid(char[][] board, int r, int c, char num) {
        int a = r / 3 * 3, b = c / 3 * 3;
        for (int i = 0; i < 9; i++)
            if (board[r][i] == num || board[i][c] == num || board[a + i / 3][b + i % 3] == num)
                return false;
        return true;
    }
}
```

### Solution 1b (17ms)
#### Space
O(81)
```java
class Solution {
    public void solveSudoku(char[][] board) {
        dfs(board, 0);
    }

    private boolean dfs(char[][] board, int d) {
        if (d == 81) return true;
        int r = d / 9, c = d % 9;
        if (board[r][c] != '.') return dfs(board, d + 1);

        for (char num = '1'; num <= '9'; num++)
            if (valid(board, r, c, num)) {
                board[r][c] = num;
                if (dfs(board, d + 1)) return true;
                board[r][c] = '.';//吃了吐
            }
        return false;
    }

    private boolean valid(char[][] board, int r, int c, char num) {
        int a = r / 3 * 3, b = c / 3 * 3;
        for (int i = 0; i < 9; i++)
            if (board[r][i] == num || board[i][c] == num || board[a + i / 3][b + i % 3] == num)
                return false;
        return true;
    }
}
```

### Solution 1c (8ms)
#### Space
O(81)
```java
class Solution {
    public void solveSudoku(char[][] board) {
        dfs(board, 0);
    }

    private boolean dfs(char[][] board, int d) {
        if (d == 81) return true; //found solution
        int i = d / 9, j = d % 9;
        if (board[i][j] != '.') return dfs(board, d + 1);//prefill number skip

        boolean[] flag = new boolean[9];
        validate(board, i, j, flag);
        for (int k = 0; k < 9; k++) {
            if (flag[k]) {
                board[i][j] = (char) ('1' + k);
                if (dfs(board, d + 1)) return true;
            }
        }
        board[i][j] = '.'; //吃了🤮
        return false;
    }

    private void validate(char[][] board, int i, int j, boolean[] flag) {
        Arrays.fill(flag, true);
        for (int k = 0; k < 9; k++) {
            if (board[i][k] != '.') flag[board[i][k] - '1'] = false;
            if (board[k][j] != '.') flag[board[k][j] - '1'] = false;
            int r = i / 3 * 3 + k / 3;
            int c = j / 3 * 3 + k % 3;
            if (board[r][c] != '.') flag[board[r][c] - '1'] = false;
        }
    }
}
```

## Solution 2 空间换时间 (2ms)
### Space & Time
TC: O(9^m)

SC: O(81*3)
```java
class Solution {
    private boolean[][] rows;
    private boolean[][] cols;
    private boolean[][] boxes;

    public void solveSudoku(char[][] board) {
         rows = new boolean[9][9];
         cols = new boolean[9][9];
        boxes = new boolean[9][9];

        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] != '.') {
                    int i = board[r][c] - '1';
                    int b = r / 3 * 3 + c / 3;
                    rows[r][i] = cols[c][i] = boxes[b][i] = true;
                }

        dfs(board, 0);
    }


    private boolean dfs(char[][] board, int d) {
        if (d == 81) return true;
        int r = d / 9, c = d % 9;
        if (board[r][c] != '.') return dfs(board, d + 1);

        int b = r / 3 * 3 + c / 3; // box number
        for (int i = 0; i < 9; i++)
            if (!rows[r][i] && !cols[c][i] && !boxes[b][i]) {
                board[r][c] = (char) (i + '1');
                rows[r][i] = cols[c][i] = boxes[b][i] = true;
                if (dfs(board, d + 1)) return true;
                rows[r][i] = cols[c][i] = boxes[b][i] = false;
            }

        board[r][c] = '.';
        return false;
    }
}
```

## Solution 3 bit operation (3ms)
### Time and Space
TC: O(9^m)

SC: O(m+27)
```java
class Solution {
    static class Cell {
        int r, c, b; // the row, col and box number for each cell

        Cell(int r, int c, int b) {
            this.r = r;
            this.c = c;
            this.b = b;
        }
    }

    List<Cell> emptyCells = new ArrayList<>();
    int[] rows = new int[9], cols = new int[9], boxes = new int[9];
    public void solveSudoku(char[][] board) {
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
    }
    boolean dfs(char[][] board, int i) {
        if (i == emptyCells.size()) return true; // Check if we filled all empty cells?

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
}
```

## Solution 4: 李显龙 c Solution Java 版本
[李显龙Original 0ms c Code](https://drive.google.com/drive/u/0/folders/0B2G2LjIu7WbdfjhaUmVzc1lCR2hUdk5fZllCOHdtbFItbU5qYzdqZGVxdmlnRkJyYVQ4VU0?resourcekey=0-D2Vpv_-JlV4qo2aSDdGFLA)

[知乎](https://www.zhihu.com/question/30110719)
```java
class Solution {
    static final int ALL_ZEROS = 0;
    static final int ALL_ONES = 0x3fe;
    int[] row_bitmap, col_bitmap, cube_bitmap, entry, sequence;
    // Always points to the first empty cell's SQUARE index, which is stored in SEQUENCE
    int seq_start;
    // Utility arrays to store mapping from SQUARE to ROW/COLs/CUBES: e.g. 37 -> cube[1, 0], whose 1D index is 3;
    int[] square_to_row, square_to_col, square_to_cube;

    public void solveSudoku(char[][] board) {
        seq_start = 0;
        row_bitmap = new int[9];
        col_bitmap = new int[9];
        cube_bitmap = new int[9];
        entry = new int[81];
        sequence = new int[81];
        square_to_row = new int[81];
        square_to_col = new int[81];
        square_to_cube = new int[81];
        // Initialize all helping data structures
        // All digits are initially all available (marked by 1) in all rows/columns/cubes
        for (int i = 0; i < 9; i++)
            row_bitmap[i] = col_bitmap[i] = cube_bitmap[i] = ALL_ONES;
        // Sequence stores all SQUARE indices of all cells, with 0..start-1 being all filled cells, and the rest all empty
        // All cells initially all empty
        for (int i = 0; i < 81; i++)
            sequence[i] = i;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++) {
                // Mapping from SQUARE to I/J is also beneficial: avoid calculating I/J from SQUARE later
                int square = i * 9 + j;
                square_to_row[square] = i;
                square_to_col[square] = j;
                square_to_cube[square] = (i / 3) * 3 + j / 3;
            }
        // Fill in the given cells. Update the bitmaps at the same time
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                if (board[i][j] != '.') {
                    int square = i * 9 + j, val = board[i][j] - '0', valbit = 1 << val;
                    row_bitmap[i] &= ~valbit;
                    col_bitmap[j] &= ~valbit;
                    cube_bitmap[(i / 3) * 3 + j / 3] &= ~valbit;
                    entry[square] = valbit;
                    int seq_iter = seq_start;
                    // Compact non-empty cells to the front, and use SEQ_START to mark the first empty cell's position
                    while (seq_iter < 81 && sequence[seq_iter] != square)
                        seq_iter++;
                    swapSeq(seq_start++, seq_iter);
                }
        // main solver process
        boolean success = place(seq_start);
        assert success : "Unsolvable Puzzle!";
        // dump result back from ENTRY array to BOARD
        for (int s = 0; s < 81; s++) {
            int i = square_to_row[s], j = square_to_col[s];
            board[i][j] = (char) (Integer.numberOfTrailingZeros(entry[s]) + '0');
        }
    }

    boolean place(int seq_pos) {
        if (seq_pos >= 81)
            return true;
        int seq_next = nextPos(seq_pos);
        swapSeq(seq_pos, seq_next);
        int square = sequence[seq_pos], row_idx = square_to_row[square], col_idx = square_to_col[square], cube_idx = square_to_cube[square];
        int cell_bitmap = row_bitmap[row_idx] & col_bitmap[col_idx] & cube_bitmap[cube_idx];
        while (cell_bitmap > 0) {
            // Get each available bit/digit in order
            int next_digit_bit = cell_bitmap & -cell_bitmap;
            cell_bitmap &= ~next_digit_bit;
            entry[square] = next_digit_bit;
            // claim this DIGIT is used in row/column/cube
            row_bitmap[row_idx] &= ~next_digit_bit;
            col_bitmap[col_idx] &= ~next_digit_bit;
            cube_bitmap[cube_idx] &= ~next_digit_bit;

            if (place(seq_pos + 1))
                return true;

            // undo claims in the bitmaps
            row_bitmap[row_idx] |= next_digit_bit;
            col_bitmap[col_idx] |= next_digit_bit;
            cube_bitmap[cube_idx] |= next_digit_bit;
            entry[square] = ALL_ZEROS;
        }
        swapSeq(seq_pos, seq_next);
        return false;
    }

    // Find among empty cells the one with the smallest search space: least bits on its bitmap;
    int nextPos(int pos) {
        int min_idx = pos, min_digit_count = 100;
        for (int i = pos; i < 81; i++) {
            int square = sequence[i];
            // Number of bits in CELL_BITMAP is the number of digits that this cell can take
            int cell_bitmap = row_bitmap[square_to_row[square]] & col_bitmap[square_to_col[square]] & cube_bitmap[square_to_cube[square]];
            // Counts the bits, so you know how many digits this CELL can take: we want the minimum one
            int num_possible_digits = Integer.bitCount(cell_bitmap);
            if (num_possible_digits < min_digit_count) {
                min_idx = i;
                min_digit_count = num_possible_digits;
            }
        }
        return min_idx;
    }

    void swapSeq(int i, int j) {
        int tmp = sequence[i];
        sequence[i] = sequence[j];
        sequence[j] = tmp;
    }
}
```