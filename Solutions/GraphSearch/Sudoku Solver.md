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