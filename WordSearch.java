/*
        Given a 2D board and a word, find if the word exists in the grid.
        The word can be constructed from letters of sequentially adjacent cell,
        where "adjacent" cells are those horizontally or vertically neighboring.
        The same letter cell may not be used more than once.

        Input: board = [
        [“ABCE”],
        [“SFCS”],
        [“ADEE”]
        ]

        Output: Word = “ABCCED”   return true
        Word = “SEE”      return true
        Word = “ABCB”      return false
*/

public class WordSearch {
    public final int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}, };
    private int rows, cols;
    public boolean isWord(char[][] board, String s) {
        if ((rows = board.length) == 0 || (cols = board[0].length) == 0) return false;

        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (dfs(0, s.toCharArray(), i, j, board, visited)) return true;

        return false;
    }

    private boolean dfs(int idx, char[] a, int i, int j, char[][] board, boolean[][] visited) {
        if (idx == a.length) return true;
        if (i < 0 || j < 0 || i >= rows || j >= cols || visited[i][j] || board[i][j] != a[idx])
            return false;

        visited[i][j] = true;
        for (int[] dir : dirs)
            if (dfs(idx + 1, a, i + dir[0], j + dir[1], board, visited)) return true;
        visited[i][j] = false;

        return false;
    }

    public static void main(String[] args) {
        WordSearch ws = new WordSearch();
        char[][] b0 = new char[][] {
                {'A'},
        };
        System.out.println(ws.isWord(b0, "A")); // true
        char[][] b1 = new char[][] {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        System.out.println(ws.isWord(b1,"ABCCED")); // true
        System.out.println(ws.isWord(b1,"SEE")); // true
        System.out.println(ws.isWord(b1,"ABCB")); // false
    }
}
