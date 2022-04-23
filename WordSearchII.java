/*
        Given a 2D board and a list of words from the dictionary, find all words in the board.
        Each word must be constructed from letters of sequentially adjacent cell,
        where "adjacent" cells are those horizontally or vertically neighboring.
        The same letter cell may not be used more than once in a word.

        For example,
        Given words = ["oath","pea","eat","rain"] and board =
        [
        ['o','a','a','n'],
        ['e','t','a','e'],
        ['i','h','k','r'],
        ['i','f','l','v']
        ]
        Return ["eat","oath"].

        Note:
        You may assume that all inputs are consist of lowercase letters a-z.
*/


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Trie.TrieNode;

public class WordSearchII {
    static final int[][] dirs = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public List<String> findWords(char[][] board, String[] words) {
        int rows, cols;
        if ((rows = board.length) == 0 || (cols = board[0].length) == 0)
            return new ArrayList<>();

        Set<String> set = new HashSet<>();
        TrieNode root = buildTrie(words);
        boolean[][] visited = new boolean[rows][cols];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                dfs(board, i, j, root, sb, set, visited);

        return new ArrayList<>(set);
    }

    private void dfs(char[][] board, int i, int j, TrieNode root, StringBuilder sb, Set<String> set, boolean[][] visited) {
        try {
            if (visited[i][j]) return;
        } catch (IndexOutOfBoundsException pruning) {return;}

        char c = board[i][j];
        int idx = c - 'a';
        if ((root = root.children[idx]) == null) return;

        sb.append(c);
        if (root.isWord)
            set.add(sb.toString());

        visited[i][j] = true;
        for (int[] dir : dirs)
            dfs(board, i + dir[0], j + dir[1], root, sb, set, visited);
        visited[i][j] = false;
        sb.setLength(sb.length() - 1);

    }

    private TrieNode buildTrie(String[] words) {
        TrieNode root = new TrieNode();
        for (String s : words) {
            TrieNode cur = root;
            for (int i = 0; i < s.length(); i++) {
                int idx = s.charAt(i) - 'a';
                if (cur.children[idx] == null)
                    cur.children[idx] = new TrieNode();
                cur = cur.children[idx];
            }
            cur.isWord = true;
        }
        return root;
    }

    public static void main(String[] args) {
        WordSearchII ws2 = new WordSearchII();
        char[][] b1 = new char[][] {
                {'b','c','e','e','d'},
                {'b','b','e','a','e'},
                {'e','b','c','c','a'},
                {'a','c','e','c','c'},
                {'a','b','c','d','c'},
        };
        String[] w1 = new String[]{"accacd","caea","dcd","acede","ceedd","aa","eeacda","bb"};
        System.out.println(ws2.findWords(b1, w1)); // [bb, aa, caea]
        char[][] b2 = new char[][] {
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}
        };
        String[] w2 = new String[]{"oath","pea","eat","rain"};
        System.out.println(ws2.findWords(b2, w2)); // [oath, eat]

    }
}
