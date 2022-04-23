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

    Sample Results:
   Input  #boxes    boxes used          Space of each box
      1 : 1,               [1],                     [1, ]
      2 : 2,            [1, 1],                  [1, 1, ]
      3 : 3,         [1, 1, 1],               [1, 1, 1, ]
      4 : 1,               [2],                     [4, ]
      5 : 2,            [1, 2],                  [1, 4, ]
      6 : 3,         [1, 1, 2],               [1, 1, 4, ]
      7 : 4,      [1, 1, 1, 2],            [1, 1, 1, 4, ]
      8 : 2,            [2, 2],                  [4, 4, ]
      9 : 1,               [3],                     [9, ]
     10 : 2,            [1, 3],                  [1, 9, ]
     11 : 3,         [1, 1, 3],               [1, 1, 9, ]
     12 : 3,         [2, 2, 2],               [4, 4, 4, ]
     13 : 2,            [2, 3],                  [4, 9, ]
     14 : 3,         [1, 2, 3],               [1, 4, 9, ]
     15 : 4,      [1, 1, 2, 3],            [1, 1, 4, 9, ]
     16 : 1,               [4],                    [16, ]
     17 : 2,            [1, 4],                 [1, 16, ]
     18 : 2,            [3, 3],                  [9, 9, ]
     19 : 3,         [1, 3, 3],               [1, 9, 9, ]
     20 : 2,            [2, 4],                 [4, 16, ]
     21 : 3,         [1, 2, 4],              [1, 4, 16, ]
     22 : 3,         [2, 3, 3],               [4, 9, 9, ]
     23 : 4,      [1, 2, 3, 3],            [1, 4, 9, 9, ]
     24 : 3,         [2, 2, 4],              [4, 4, 16, ]
     25 : 1,               [5],                    [25, ]
     26 : 2,            [1, 5],                 [1, 25, ]
     27 : 3,         [1, 1, 5],              [1, 1, 25, ]
     28 : 4,      [1, 1, 1, 5],           [1, 1, 1, 25, ]
     29 : 2,            [2, 5],                 [4, 25, ]
     30 : 3,         [1, 2, 5],              [1, 4, 25, ]
     99 : 3,         [1, 7, 7],             [1, 49, 49, ]
    999 : 4,     [1, 1, 6, 31],         [1, 1, 36, 961, ]
   2567 : 4,    [1, 3, 21, 46],       [1, 9, 441, 2116, ]
   9999 : 4,    [1, 1, 14, 99],       [1, 1, 196, 9801, ]
  84637 : 3,     [3, 188, 222],       [9, 35344, 49284, ]
  99999 : 4,  [1, 1, 171, 266],    [1, 1, 29241, 70756, ]
 943287 : 4,   [1, 2, 21, 971],     [1, 4, 441, 942841, ]
 999999 : 4,  [1, 1, 194, 981],   [1, 1, 37636, 962361, ]
1999999 : 4, [1, 2, 437, 1345], [1, 4, 190969, 1809025, ]
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
    public List<List<Integer>> minBoxes(List<Integer> list, int n) {
        int[]  dp = new int[n + 1];
        int[] pre = new int[n + 1];
        int[] cur = new int[n + 1];
         dp[1] = 1;
        cur[1] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i] = i + 1; // to make sure when n = 2 or 3, dp[i] is updated, so cur[i] and pre[i] is updated
            for (int j = 1; ; j++) {
                int sq = j * j; // avoid calculate j * j twice
                if (sq > i) break;

                int preIdx = i - sq;
                int temp = 1 + dp[preIdx];
                if (temp < dp[i]) {
                    dp[i] = temp;
                    cur[i] = j;
                    pre[i] = preIdx;
                }
            }
        }

        return createResults(pre, cur, list);
    }

    public List<List<Integer>> createResults(int[] pre, int[] cur, List<Integer> list) {
        List<List<Integer>> res = new ArrayList<>(list.size());
        for (int x : list) res.add(createResult(pre, cur, x));
        return res;
    }

    public List<Integer> createResult(int[] pre, int[] cur, int x) {
        List<Integer> res = new ArrayList<>(4); // after reviewing results, it looks like the max size is 4
        for (int idx = x; idx > 0; idx = pre[idx])
            res.add(cur[idx]);
        return res;
    }

    public void printResults(List<Integer> list) {
        Collections.sort(list);
        int max = list.get(list.size() - 1);
        List<List<Integer>> res = minBoxes(list, max);
        for (int i = 0; i < list.size(); i++)
            printResult(res.get(i), list.get(i));
    }

    public void printResult(List<Integer> res, int i) {
        int size = res.size();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int x : res)
            sb.append(String.format("%d, ", x * x));
        sb.append("]");
        System.out.printf("%7d : %d, %17s, %25s\n", i, size, res, sb);

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