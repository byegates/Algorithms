/*
        Find the length of longest common subsequence of two given strings.

        Assumptions
        The two given strings are not null
        Examples
        S = “abcde”, T = “cbabdfe”, the longest common subsequence of s and t is {‘a’, ‘b’, ‘d’, ‘e’}, the length is 4.

*/


public class LongestCommonSubsequence {
    public int longest(String s, String t) {
        int n = s.length(), m = t.length();
        int[][] M = new int[n + 1][m + 1];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                if (s.charAt(i) == t.charAt(j)) M[i+1][j+1] = M[i][j] + 1;
                else M[i+1][j+1] = Math.max(M[i][j+1], M[i+1][j]);

        System.out.println(getLongestCommonSubSeq(s, M));
        return M[n][m];
    }

    public String getLongestCommonSubSeq(String s, int[][] M) {
        StringBuilder sb = new StringBuilder();
        int rows = M.length, cols = M[0].length;
        for (int i = 0; i < rows - 1; i++)
            for (int j = 0; j < cols - 1; j++)
                if (M[i][j] + 1 == M[i+1][j+1] && rows - i == cols - j)
                    sb.append(s.charAt(i));

        return sb.toString();
    }

    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        lcs.longest("apple", "apppppppppple");
        //System.out.println();
        lcs.longest("tsla", "apple");
        //System.out.println();
        lcs.longest("tatat", "tctct");
        //System.out.println();
        lcs.longest("coconut", "cobanua");
        //System.out.println();
    }
}
