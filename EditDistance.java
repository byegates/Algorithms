//Given two strings of alphanumeric characters, determine the minimum number of Replace, Delete, and Insert operations
// needed to transform one string into the other.
//
//        Assumptions
//        Both strings are not null
//
//        Examples
//        string one: “sigh”, string two : “asith”
//        the edit distance between one and two is 2 (one insert “a” at front then replace “g” with “t”).
//

// in all complexity analysis: m is s1.length(), n is s2.length()
public class EditDistance {
    public int editDistance(String s1, String s2) { // Space is only O(n)
        int len1 = s1.length();
        int len2 = s2.length();
        if (len2 == 0) return len1;

        int[] dp = new int[len2 + 1];
        // filling row 0 of imaging matrix
        for (int j = 0; j <= len2; j++) dp[j] = j;

        // real business, filling row 1 to len1 of imaging matrix
        for (int i = 1; i <= len1; i++) {
            int left = i, diagonal = i - 1;
            for (int j = 1; j <= len2; j++) {
                int prevDiagonal = diagonal;
                diagonal = dp[j];
                int fromDiagonal = prevDiagonal + (s1.charAt(i - 1) == s2.charAt(j - 1) ? 0 : 1);
                dp[j] = Math.min(fromDiagonal, Math.min(left, dp[j]) + 1);
                left = dp[j];
            }
        }

        return dp[len2];
    }// TC: O(m*n), SC: O(n)

    public int MByNSpace(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();

        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i <= len1; i++) dp[i][0] = i;
        for (int j = 0; j <= len2; j++) dp[0][j] = j;

        for (int i = 1; i <= len1; i++)
            for (int j = 1; j <= len2; j++)
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) dp[i][j] = dp[i - 1][j - 1];
                else dp[i][j] = Math.min(Math.min(dp[i][j-1], dp[i-1][j]), dp[i-1][j-1]) + 1;

        return dp[len1][len2];
    } // TC: O(m*n), SC: O(m*n)

    public int recursion(String s1, String s2) {
        return editDistance(s1.toCharArray(), 0, s2.toCharArray(), 0);
    }

    public int editDistance(char[] A, int i, char[] B, int j) {
        if (i == A.length) return B.length - j;
        if (j == B.length) return A.length - i;

        int rep = editDistance(A, i + 1, B, j + 1);
        if (A[i] == B[j]) return rep;
        int del = editDistance(A, i + 1, B, j);
        int ins = editDistance(A, i, B, j + 1);
        return Math.min(Math.min(rep, del), ins) + 1;
    } // TC: 3^(m + n), SC: m + n


    public static void main(String[] args) {
        EditDistance ed = new EditDistance();
        System.out.println(ed.editDistance("ab", "dbbabc")); // 4
        System.out.println(ed.editDistance("", "")); // 0
        System.out.println(ed.editDistance("dbbabc", "")); // 6
        System.out.println(ed.editDistance("", "dbbabc")); // 6
        System.out.println(ed.MByNSpace("ab", "dbbabc")); // 4
        System.out.println(ed.MByNSpace("", "")); // 0
        System.out.println(ed.MByNSpace("dbbabc", "")); // 6
        System.out.println(ed.MByNSpace("", "dbbabc")); // 6
        System.out.println(ed.recursion("ab", "dbbabc")); // 4
        System.out.println(ed.recursion("", "")); // 0
        System.out.println(ed.recursion("dbbabc", "")); // 6
        System.out.println(ed.recursion("", "dbbabc")); // 6
    }
}