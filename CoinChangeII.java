import java.util.HashMap;
import java.util.Map;

/*
    You are given coins of different denominations and a total amount of money amount.
    Write a function to the number of different combinations that can sum up to that amount.

    Example 1:
    coins = [1,2] , amount = 5
    return 3
    Explanation:
    5 = 1 + 1 + 1 + 1 + 1 = 1 + 1 + 1 + 2 = 1 + 2 + 2
    Note:
    You may assume that you have an infinite number of each kind of coin.
 */
public class CoinChangeII {
    // Solution 1, dp, with space optimized to O(amount), TC: O(coins.length*amount)
    public int coinChange(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins)
            for (int j = coin; j <= amount; j++)
                dp[j] += dp[j - coin];
        return dp[amount];
    }
    // Solution 1 ends here
    // Solution 2, dp, TC: O(coins.length*amount), SC: O(coins.length*amount)
    public int coinChange2(int amount, int[] coins) {
        int len = coins.length;
        int[][] dp = new int[len + 1][amount+ 1];
        dp[0][0] = 1;
        for (int i = 1; i <= len; i++) {
            dp[i][0] = 1;
//            for (int j = 1; j < coins[i - 1]; j++) dp[i][j] = dp[i - 1][j];
//            for (int j = coins[i - 1]; j < amount; j++) dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i - 1]];
            for (int j = 1; j <= amount; j++)
                dp[i][j] = dp[i - 1][j] + (j >= coins[i - 1] ? dp[i][j - coins[i - 1]] : 0);
        }
        return dp[len][amount];
    }
    // Solution 2 ends here

    // Solution 3 starts here
    public int coinChange3(int amount, int[] coins) {
        // Write your solution here
        if (coins.length == 0) return amount == 0 ? 1 : 0;
        Map<Integer, Integer> map = new HashMap<>();
        // Map<amount, ways of making this amount>
        for (int i = 0; i * coins[0] <= amount; i++)
            map.put(i * coins[0], 1);

        for (int j = 1; j < coins.length; j++) {
            // j: which face value
            int coin = coins[j];
            Map<Integer, Integer> map2 = new HashMap<>();
            for (int amt0 : map.keySet())
                for (int i = 0; ; i++) {
                    int amt1 = i * coin + amt0;
                    if (amt1 > amount) break;
                    map2.put(amt1, map.get(amt0) + map2.getOrDefault(amt1, 0));
                }
            map = map2;
        }
        return map.getOrDefault(amount, 0);

    }
    // solution 3 ends here

    public static void main(String[] args) {
        CoinChangeII cc2 = new CoinChangeII();
        System.out.println(cc2.coinChange(10, new int[]{1, 2, 5})); // 10
        System.out.println(cc2.coinChange(11, new int[]{23})); // 0
        System.out.println(cc2.coinChange2(10, new int[]{1, 2, 5})); // 10
        System.out.println(cc2.coinChange2(11, new int[]{23})); // 0
        System.out.println(cc2.coinChange3(10, new int[]{1, 2, 5})); // 10
        System.out.println(cc2.coinChange3(11, new int[]{23})); // 0
    }

}
