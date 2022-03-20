/*
        Given a string, a partitioning of the string is a palindrome partitioning if every substring of the partition
        is a palindrome. Determine the fewest cuts needed for a palindrome partitioning of a given string.

        Assumptions

        The given string is not null
        Examples

        “a | babbbab | bab | aba” is a palindrome partitioning of “ababbbabbababa”.

        The minimum number of cuts needed is 3.

*/

public class MinimumCutsForPalindromes {
    //common method shared by multiple solutions
    public int minCuts(String s) {
        if (s.length() <= 1) return 0;
        return minCuts(s.toCharArray(), s.length());
    }

    //Improved dp solution
    public int minCuts(char[] A, int n) {// TC: O(n), SC: O(n*n)
        int[] cuts = new int[n]; // result memorization
        boolean[][] pal = new boolean[n][n]; // check whether substring is pal or not

        for (int i = 0; i < n; i++) {
            cuts[i] = i;
            for (int j = 0; j <= i; j++)
                if (A[i] == A[j] && (j+1>i-1 || pal[j+1][i-1])) {
                    pal[j][i] = true;
                    cuts[i] = j == 0 ? 0 : Math.min(cuts[i], cuts[j-1] + 1);
                }
        }
        return cuts[n - 1];
    }

    // Naive DP solution, slow
    public int naive(char[] A, int n) {
        int[] M = new int[n];

        for (int i = 0; i < n; i++) {
            M[i] = i;
            for (int j = 0; j <= i; j++)
                if (isPalindrome(A, j, i))
                    M[i] = j == 0 ? 0 : Math.min(M[i], M[j-1] + 1);
        }

        return M[n - 1];
    }

    private boolean isPalindrome(char[] A, int l, int r) {
        while (l <= r) if (A[l++] != A[r--]) return false;
        return true;
    }
    //Naive dp solution ends here

    //Naive dp solution 2, which uses isPalindrome method above too
    public int naive2(char[] A, int n) { // TC: O(n), SC: O(n)
        int[] M = new int[n + 1];
        M[0] = -1;

        for (int i = 1; i <= n; i++) {
            M[i] = i-1;
            for (int j = 0; j < i; j++)
                if (isPalindrome(A, j, i - 1))
                    M[i] = Math.min(M[i], M[j] + 1);
        }

        return M[n];
    }
    // Naive solution 2 ends here

    public static void main(String[] args) {
        MinimumCutsForPalindromes mcp = new MinimumCutsForPalindromes();
        String s1 = "aaaaaabbabb";
        System.out.println(mcp.minCuts(s1)); // 1
        String s2 = "ababbbabbababa";
        System.out.println(mcp.minCuts(s2)); // 3
        System.out.println(mcp.naive(s1.toCharArray(), s1.length())); // 1
        System.out.println(mcp.naive(s2.toCharArray(), s2.length())); // 3
        System.out.println(mcp.naive2(s1.toCharArray(), s1.length())); // 1
        System.out.println(mcp.naive2(s2.toCharArray(), s2.length())); // 3
    }
}
