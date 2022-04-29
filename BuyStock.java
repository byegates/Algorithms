import java.util.Arrays;

public class BuyStock {
    /*
    Buy Stock 1
    Given an array of positive integers representing a stock’s price on each day.
    On each day you can only make one operation: either buy or sell one unit of stock,
    and you can make at most 1 transaction. Determine the maximum profit you can make.

    Assumptions
    The given array is not null and is length of at least 2.
    Examples
    {2, 3, 2, 1, 4, 5}, the maximum profit you can make is 5 - 1 = 4
     */
    public int maxProfit1(int[] prices) { // TC: O(n), SC: O(1)
        int min = prices[0];
        int res = 0;
        for (int x : prices)
            if (x <= min) min = x;
            else res = Math.max(res, x - min);

        return res;
    }

    // solution 2, get maxProfit, between indices, this is a helper method for problem buy stock III
    public int mp1(int[] prices, int l, int r) { // TC: O(n), SC: O(1)
        if (l >= prices.length) return 0;
        int min = prices[l], res = 0;
        for (int i = l + 1; i <= r; i++)
            if (prices[i] <= min) min = prices[i];
            else res = Math.max(res, prices[i] - min);
        return res;
    }
    // solution 2 ends here

    /*
    Buy Stock II
    Given an array of positive integers representing a stock’s price on each day.
    On each day you can only make one operation: either buy or sell one unit of stock,
    you can make as many transactions you want, but at any time you can only hold at most one unit of stock.
    Determine the maximum profit you can make.

    Assumptions
    The give array is not null and is length of at least 2
    Examples
    {2, 3, 2, 1, 4, 5}, the maximum profit you can make is (3 - 2) + (5 - 1) = 5
     */

    public int maxProfit2(int[] prices) { // TC: O(n), SC: O(1)
        int res = 0;
        for (int i = 1; i < prices.length; i++)
            if (prices[i] > prices[i - 1]) res += prices[i] - prices[i - 1];
        return res;
    }

/*
    Given an array of positive integers representing a stock’s price on each day.
    On each day you can only make one operation: either buy or sell one unit of stock at any time you can only hold at
    most one unit of stock, and you can make at most 2 transactions in total. Determine the maximum profit you can make.

    Assumptions
    The give array is not null and is length of at least 2
    Examples
    {2, 3, 2, 1, 4, 5, 2, 11}, the maximum profit you can make is (5 - 1) + (11 - 2) = 13
*/

    // solution 1 start here (re-use mp1 from before)
    public int maxProfit3a(int[] prices) { // TC: O(n^2), SC: O(1)
        int res = 0;
        for (int i = 1; i < prices.length; i++)
            res = Math.max(res, mp1(prices, 0, i) + mp1(prices, i, prices.length - 1));
        return res;
    }
    // solution 1 ends here

    // solution 2 using state machine, TC O(n); Generalized solution refer to below Solution 1 for Buy Stock IV
    public int maxProfit3b(int[] prices) {
        if (prices.length == 0) return 0;
        int s1 = -prices[0], s2, s3, s4;
        s2 = s3 = s4 = Integer.MIN_VALUE;

        for (int i = 1; i < prices.length; i++) {
            s1 = Math.max(s1, -prices[i]); // 1st buy
            s2 = Math.max(s2, s1 + prices[i]); // 1st sell
            s3 = Math.max(s3, s2 - prices[i]); // 2nd buy
            s4 = Math.max(s4, s3 + prices[i]); // 2nd sell
        }

        return Math.max(0, s4);
    }
    // solution 2 ends here

    /*
    Buy Stock IV
    Given an array of integers representing a stock’s price on each day. On each day you can only make one operation:
    either buy or sell one unit of stock, and at any time you can only hold at most one unit of stock,
    and you can make at most K transactions in total. Determine the maximum profit you can make.
    Assumptions
    The give array is not null and is length of at least 2
    Examples
    {2, 3, 2, 1, 4, 5, 2, 11}, K = 3, the maximum profit you can make is (3 - 2) + (5 - 1) + (11 - 2)= 14
     */

    // Solution 1 using generalized state machine
    public int maxProfit4b(int[] prices, int k) { // TC: O(k*n), SC: O(k)
        if (prices.length == 0 || k == 0) return 0;
        if (k >= prices.length / 2) return maxProfit2(prices); // this means you can do as many transactions as you want
        int[] states = new int[k * 2 + 1];
        Arrays.fill(states, Integer.MIN_VALUE); // after each buy, value could be negative, so we can't use 0 as default here
        states[0] = 0; // this is added to avoid making states[0] = - prices[0] as a standalone logic, now this is accomplished by states[idx1] logic below
        states[1] = - prices[0];

        for (int i = 1; i < prices.length; i++)
            for (int j = 0; j < k; j++) {
                int idx1 = j * 2 + 1;
                int idx2 = idx1 + 1;
                states[idx1] = Math.max(states[idx1], states[idx1 - 1] - prices[i]);
                states[idx2] = Math.max(states[idx2], states[idx2 - 1] + prices[i]);
            }

        return Math.max(0, states[2 * k]);
    }
    // solution 1 ends here

    // Solution 2, dp, TC: O(k*n), SC: O(k*n)
    public int maxProfit4(int[] prices, int k) {
        int len = prices.length;
        if ( len == 0 || k == 0) return 0;
        if (k >= len / 2) return maxProfit2(prices);

        int[][] dp = new int[k + 1][len];
        for (int i = 1; i <= k; i++) {
            int balance = - prices[0];
            for (int j = 1; j < len; j++) {
                dp[i][j] = Math.max(dp[i][j - 1], balance + prices[j]);
                balance = Math.max(balance, dp[i - 1][j - 1] - prices[j]);
            }
        }
        return dp[k][len - 1];
    }
    // solution 2 ends here

    // Solution 2, dp, TC: O(2*k*n), SC: O(2n), not very good
    public int maxProfit4c(int[] prices, int k) {
        int len = prices.length;
        if ( len == 0 || k == 0) return 0;
        if (k >= len / 2) return maxProfit2(prices);

        int[][] dp = new int[2][len];
        for (int i = 1; i <= k; i++) {
            int balance = - prices[0];
            for (int j = 1; j < len; j++) {
                dp[1][j] = Math.max(dp[1][j - 1], prices[j] + balance);
                balance = Math.max(balance, dp[0][j - 1] - prices[j]);
            }
            System.arraycopy(dp[1], 0, dp[0], 0, len);
        }
        return dp[1][len - 1];
    }
    // solution 2 ends here

    public static void main(String[] args) {
        BuyStock bs = new BuyStock();
        System.out.println(bs.maxProfit4b(new int[]{1, 7, 3, 8}, 2)); // 11
        System.out.println(bs.maxProfit1(new int[]{6, 4, 8, 2, 7, 1, 3})); // 5
        System.out.println(bs.maxProfit2(new int[]{5, 1, 2, 3, 7, 2, 5, 1, 3})); // 11
        System.out.println(bs.maxProfit3a(new int[]{1, 7, 3, 8})); // 11
        System.out.println(bs.maxProfit3b(new int[]{1, 7, 3, 8})); // 11
        System.out.println(bs.maxProfit4b(new int[]{3, 4, 1, 2, 6, 2, 3, 5, 1, 7, 3, 8}, 4)); // 19
        System.out.println(bs.maxProfit4(new int[]{3, 4, 1, 2, 6, 2, 3, 5, 1, 7, 3, 8}, 4)); // 19
        System.out.println(bs.maxProfit4c(new int[]{3, 4, 1, 2, 6, 2, 3, 5, 1, 7, 3, 8}, 4)); // 19
        System.out.println(bs.maxProfit4(new int[]{2, 3, 2, 1, 4, 5, 2, 11}, 3)); // 14
    }
}
