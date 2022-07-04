import java.util.ArrayList;
import java.util.List;

/*
    Given a 2D array A[8][8] with all integer numbers if we take a number a[i][j],
    then we cannot take its 8 neighboring cells. How should we take the numbers to make their sum as large as possible.
    Assumptions
    The given matrix is 8 * 8
 */
public class LargestSumOfValidNumbers {
    public int largestSum(int[][] mx) {
        int k = 8;
        List<Integer> configs = validConfigs(k);
        int[][] dp = new int[k][configs.size()];

        for (int i = 0; i < k; i++)
            for (int j = 0; j < configs.size(); j++) {
                dp[i][j] = Integer.MIN_VALUE;
                if (i == 0) dp[i][j] = sum(mx[i], configs.get(j));
                else
                    for (int l = 0; l < configs.size(); l++)
                        if (noConflict(configs.get(j), configs.get(l)))
                            dp[i][j] = Math.max(dp[i][j], dp[i - 1][l] + sum(mx[i], configs.get(j)));
            }

        int res = dp[k-1][0];
        for (int i = 1; i < configs.size(); i++) res = Math.max(res, dp[k-1][i]);

        return res;
    }

    // calc sum of selected field in array using bitwise flag in config
    private int sum(int[] a, int config) {
        int sum = 0;
        for (int i = 0; i < a.length; i++)
            if (((config >>> i) & 1) != 0)
                sum += a[i];
        return sum;
    }

    // if ith bit is 1 in c1, then ith, (i-1)th and (i+1)th bit in c2 can't be 1
    private boolean noConflict(int c1, int c2) {
        return (c1 & c2) == 0 && ((c1 << 1) & c2) == 0 && (c1 & (c2 << 1)) == 0;
    }

    // get all possible configs using dfs, each represented as an int (of which only 8 bits used by us)
    // we make sure no adjacent bit is chosen for the lowest 8 bits
    private List<Integer> validConfigs(int k) {
        List<Integer> configs = new ArrayList<>();
        helper(configs, 0, k, 0);
        return configs;
    }

    private void helper(List<Integer> configs, int idx, int k, int cur) {
        configs.add(cur);
        for (int i = idx; i < k; i++)
            helper(configs, i + 2, k, cur | (1 << i));
    }

    public static void main(String[] args) {
        LargestSumOfValidNumbers lsvn = new LargestSumOfValidNumbers();
        int[][] mx = new int[][] {{133, 319, 475, 144, 318, 887, 256, 152}, {941, 947, 766, 137, 157, 901, 858, 254}, {928, 501, 454, 642, 168, 772, 89, 952}, {260, 853, 814, 132, 770, 407, 928, 305}, {560, 202, 657, 493, 110, 563, 982, 549}, {650, 161, 651, 829, 304, 107, 770, 983}, {732, 434, 403, 605, 805, 355, 415, 998}, {873, 236, 753, 679, 629, 131, 262, 58}};
        System.out.println(lsvn.largestSum(mx));;
    }
}
