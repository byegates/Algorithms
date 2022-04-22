/*
    There is an array of positive integers, in which each integer represents a piece of Pizza’s size,
    you and your friend take turns to pick pizza from either end of the array.
    The winner is the one who gets larger total sum of all pizza or whoever starts first if there is a tie.
    Return whether you will win the game if you start first.

    Example:
    Input: [2,1,100,3]
    Output: True

    Explanation: To win the game, you pick 2 first, then your friend will pick either 1 or 3, after that you could pick 100.
    In the end you could get 2 + 100 = 102, while your friend could only get 1 + 3 = 4.
 */

public class CanIWin {
    public boolean canWin(int[] p) {
        if (p.length == 0) return true;

        int len = p.length;
        int[][] dp = new int[len][len];
        for (int diff = 0; diff < len; diff++)
            for (int i = 0; i + diff < len; i++) {
                int j = i + diff;
                if (i == j) dp[i][j] = p[i];
                else if (j == i + 1) dp[i][j] = Math.max(p[i], p[j]);
                else {
                    int pi = p[i] + Math.min(dp[i + 2][j], dp[i + 1][j - 1]);
                    int pj = p[j] + Math.min(dp[i + 1][j - 1], dp[i][j - 2]);
                    dp[i][j] = Math.max(pi, pj);
                }
            }

        int sum = 0;
        for (int value : p) sum += value;
        return dp[0][len - 1] >= sum - dp[0][len - 1];
    }

    public static void main(String[] args) {
        CanIWin cw = new CanIWin();
        int[] a1 = {1, 2, 100, 4, 3, 5, 6};
        System.out.println(cw.canWin(a1)); //false
        int[] a2 = {55, 77, 73, 72, 9, 86, 35, 25, 53, 73, 100, 88, 89};
        System.out.println(cw.canWin(a2)); // false
        int[] a3 = {7, 98, 5, 80, 35, 76};
        System.out.println(cw.canWin(a3)); // true
        int[] a4 = {49,71,6,56,13,50,68,56,63,25,20,80,44,66,48,4,24};
        System.out.println(cw.canWin(a4)); // false
        int[] a5 = {18,74,89,72,90,84,63,8,2,20,99,88,5};
        System.out.println(cw.canWin(a5)); // false
    }
}
