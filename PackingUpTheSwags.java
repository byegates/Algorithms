/*
    Our company is going to distribute swags at the recruiting event. We will put the swags into square-shaped boxes.
    Each box has to be completely filled so that the swags wouldn't break during transportation.
    For example, a box can contain 1 swag, 4 swags, 9 swags, etc. (The boxes can be sufficiently large.)
    Given the number of swags, what is the minimum number of boxes to pack them up?

    Example #1
    Input: 4
    Output: 1 (just one 2x2 box)

    Example #2
    Input: 10
    Output: 2 (one 3x3 box and one 1x1 box)

    Analysis:
    1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    1 2 3 1 2 3 4 2 1  2  3  3  2  3  4  1  2  2  3  2
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PackingUpTheSwags { // TC: O(n^1.5), SC: O(n)
    // dp solution just to get the min number
    public int minBoxes2(int n) {
        int[] M = new int[n + 1];
        Arrays.fill(M, n);
        M[0] = 0;
        M[1] = 1;

        for (int i = 2; i <= n; i++)
            for (int j = 1; j * j <= i; j++)
                M[i] = Math.min(M[i], 1 + M[i - j * j]);

        return M[n];
    }

    // dp solution to the actual best solution, the size of the solution will be the number of boxes used
    public List<List<Integer>> minBoxes(int n) {
        int[] dp = new int[n + 1];
        List<List<Integer>> res = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) res.add(new ArrayList<>(4));
        dp[1] = 1;
        res.get(1).add(1);

        for (int i = 2; i <= n; i++) {
            dp[i] = i + 1; // to make sure when n = 2 or 3, the logic to create the result list will also work
            for (int j = 1; ; j++) {
                int sq = j * j;
                if (sq > i) break;

                int preIdx = i - sq;
                int cur = 1 + dp[preIdx];
                if (cur < dp[i]) {
                    dp[i] = cur;
                    List<Integer> curList = new ArrayList<>(res.get(preIdx)); // get a deep copy of previous result
                    curList.add(j); // attach current index
                    res.set(i, curList); // set current index
                }
            }
        }

        return res;
    }

    public void printResults(List<Integer> list) {
        Collections.sort(list);
        int max = list.get(list.size() - 1);
        List<List<Integer>> res = minBoxes(max);
        for (int i : list)
            printResult(res.get(i), i);
    }

    public void printResult(List<Integer> res, int i) {
        int size = res.size();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int x : res)
            sb.append(String.format("%d, ", x * x));
        sb.append("]");
        System.out.printf("%7d : %d, %-17s, %s\n", i, size, res, sb);

    }

    public static void main(String[] args) {
        PackingUpTheSwags pts = new PackingUpTheSwags();
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) list.add(i);
        list.addAll(Arrays.asList(99, 999, 2567, 9999, 84637, 99999, 943287, 999999, 1999999));
        pts.printResults(list);
    }
}

// dfs TC: n^(n^.5)