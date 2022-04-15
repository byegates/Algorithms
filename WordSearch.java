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


import java.util.*;

public class WordSearch {
    int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}, };
    int rows, cols;
    public boolean isWord(char[][] board, String s) { // TC: O(m*n*4^len), sc: O(m*n+l)
        if ((rows = board.length) == 0 || (cols = board[0].length) == 0)
            return false;

        char[] word = s.toCharArray();
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (dfs(0, board, word, i, j, visited))
                    return true;

        return false;
    }

    private boolean dfs(int idx, char[][] board, char[] word, int i, int j, Set<Integer> visited) {
        if (idx == word.length)
            return true;

        int encode = i * cols + j;
        if (i < 0 || j < 0 || i >= rows || j >= cols || visited.contains(encode) || board[i][j] != word[idx])
            return false;
        
        visited.add(encode);
        for (int[] direction : directions) {
            if (dfs(idx + 1, board, word, i + direction[0], j + direction[1], visited))
                return true;
        }
        visited.remove(encode);
        
        return false;
    }

    public static void main(String[] args) {
        WordSearch ws = new WordSearch();
        char[][] b1 = new char[][] {
                {'A','B','C','E'},
                {'S','F','C','S'},
                {'A','D','E','E'}
        };
        System.out.println(ws.isWord(b1,"ABCCED"));
        System.out.println(ws.isWord(b1,"SEE"));
        System.out.println(ws.isWord(b1,"ABCB"));
    }
}
