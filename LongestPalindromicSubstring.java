/*
    Given a string S, find the longest palindromic substring in S.
    Assumptions
    There exists one unique longest palindromic substring.
    The input S is not null.
    Examples
    Input:     "abbc"
    Output:  "bb"

    Input:     "abcbcbd"
    Output:  "bcbcb"
 */
public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        int l = 0, r = 0, n = s.length();
        if (n == 0) return "";

        boolean[][] dp = new boolean[n][n]; // [i, j] in string is palindrome

        for (int j = 0; j < n; j++)
            for (int i = 0; i <= j; i++) // fix position of j first, then check all substring [i, j]
               if (s.charAt(i) == s.charAt(j) && (i + 1 >= j || dp[i+1][j-1])) {
                    dp[i][j] = true;
                    if (j - i > r - l) { // longer Palindromic Substring found
                        r = j;
                        l = i;
                    }
                }

        return s.substring(l, r + 1);
    }

    public static void main(String[] args) {
        LongestPalindromicSubstring lps = new LongestPalindromicSubstring();
        System.out.println(lps.longestPalindrome("abcbcbd").equals("bcbcb"));
        System.out.println(lps.longestPalindrome("bcbccaacdca").equals("acdca"));
        System.out.println(lps.longestPalindrome("dbdcbdbabbacd").equals("abba"));
        System.out.println(lps.longestPalindrome("").equals(""));
    }

}
