/*
        Given an integer number, return all possible combinations of the factors that can multiply to the target number.

        Example

        Give A = 24
        since 24 = 2 x 2 x 2 x 3
        = 2 x 2 x 6
        = 2 x 3 x 4
        = 2 x 12
        = 3 x 8
        = 4 x 6

        your solution should return
        { { 2, 2, 2, 3 }, { 2, 2, 6 }, { 2, 3, 4 }, { 2, 12 }, { 3, 8 }, { 4, 6 } }

        note: duplicate combination is not allowed.
*/

import java.util.ArrayList;
import java.util.List;

public class FactorCombinations {
    public List<List<Integer>> combinations(int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        List<Integer> factors = getFactors(target, new ArrayList<>());
        dfs(0, target, cur, factors, res);
        return res;
    }

    private void dfs(int idx, int target, List<Integer> cur, List<Integer> factors, List<List<Integer>> res) {
        if (idx == factors.size()) {
            if (target == 1) res.add(new ArrayList<>(cur));
            return;
        }

        dfs(idx + 1, target, cur, factors, res);
        int factor = factors.get(idx);
        int size = cur.size();
        while (target % factor == 0) {
            cur.add(factor);
            dfs(idx + 1, target /= factor, cur, factors, res);
        }
        cur.subList(size, cur.size()).clear();
    }

    public List<Integer> getFactors(int target, List<Integer> factors) {
        for (int i = 2; i <= target / 2; i++)
            if (target % i == 0) factors.add(i);

        return factors;
    }

    public static void main(String[] args) {
        FactorCombinations fc = new FactorCombinations();
        System.out.println(fc.getFactors(12, new ArrayList<>()));

        System.out.println(fc.combinations(100));
    }
}
