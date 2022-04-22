/*
        There is a wooden stick with length L >= 1, we need to cut it into pieces,
        where the cutting positions are defined in an int array A.
        The positions are guaranteed to be in ascending order in the range of [1, L - 1].
        The cost of each cut is the length of the stick segment being cut.
        Determine the minimum total cost to cut the stick into the defined pieces.

        Examples
        L = 10, A = {2, 4, 7}, the minimum total cost is 10 + 4 + 6 = 20 (cut at 4 first then cut at 2 and cut at 7)
*/

public class CuttingWood {

    public int minCost(int[] cuts, int length) {
        int n;
        if (cuts == null || (n = cuts.length) == 0) return 0;

        int[] a = new int[n += 2];
        a[n - 1] = length;
        for (int i = 1; i < n - 1; i++) a[i] = cuts[i - 1];
        int[][] M = new int[n][n];

        for (int diff = 2; diff < n; diff++)
            for (int i = 0; i + diff < n; i++) {
                int j = i + diff;
                int curCutCost = a[j] - a[i];
                M[i][j] = Integer.MAX_VALUE;
                for (int k = i + 1; k < j; k++)
                    M[i][j] = Math.min(M[i][j], curCutCost + M[i][k] + M[k][j]);
            }

        return M[0][n-1];
    }

    public static void main(String[] args) {
        CuttingWood cw = new CuttingWood();
        System.out.println(cw.minCost(new int[]{3,16,19,28,37,44,47,48,51,52,62}, 67)); // 229
    }
}
