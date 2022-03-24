/*
        Get all valid permutations of l pairs of (), m pairs of <> and n pairs of {}.

        Assumptions
        l, m, n >= 0
        l + m + n > 0

        Examples
        l = 1, m = 1, n = 0, all the valid permutations are ["()<>", "(<>)", "<()>", "<>()"]
*/

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class AllValidPermutationsOfParenthesesII { // TC: O(2^2*(l+m+n)), SC: O(2*(l+m+n))
    char[] p = new char[] {'(', ')', '<', '>', '{', '}'}; // pick array
    int[] c; // count for our picks

    public List<String> validParentheses(int l, int m, int n) {
        List<String> res = new ArrayList<>();
        int len = 2 * (l + m + n);
        c = new int[]{l, l, m, m, n, n};
        char[] cur = new char[len];
        Deque<Character> stack = new ArrayDeque<>();
        dfs(0, stack, cur, res);
        return res;
    }

    private void dfs(int idx, Deque<Character> stack, char[] cur, List<String> res) {
        if (idx == cur.length) {
            res.add(new String(cur));
            return;
        }

        for (int i = 0; i < c.length; i++)
            if (i % 2 == 0) {
                if (c[i] > 0) {
                    stack.offerFirst(p[i]);
                    add(idx, i, cur);
                    dfs(idx + 1, stack, cur, res);
                    stack.pollFirst();
                    c[i]++;
                }
            } else {
                if (!stack.isEmpty() && stack.peekFirst() == p[i - 1]) {
                    stack.pollFirst();
                    add(idx, i, cur);
                    dfs(idx + 1, stack, cur, res);
                    stack.offerFirst(p[i-1]);
                    c[i]++;
                }
            }
    }

    private void add(int idx, int i, char[] cur) {
        c[i]--;
        cur[idx] = p[i];
    }

    public static void main(String[] args) {
        AllValidPermutationsOfParenthesesII avpop2 = new AllValidPermutationsOfParenthesesII();
        System.out.println(avpop2.validParentheses(2, 1, 1));
        System.out.println(avpop2.validParentheses(3, 0, 0));
    }
}
