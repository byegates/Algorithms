/*
        Given N pairs of parentheses “()”, return a list with all the valid permutations.

        Assumptions

        N > 0
        Examples

        N = 1, all valid permutations are ["()"]
        N = 3, all valid permutations are ["((()))", "(()())", "(())()", "()(())", "()()()"]
*/

import java.util.ArrayList;
import java.util.List;

public class AllValidPermutationsOfParenthesesI {
    // Solution using StringBuilder starts here
    public List<String> validParentheses(int n) {
        StringBuilder sb = new StringBuilder();
        List<String> res = new ArrayList<>();
        dfs(n, 0, 0, sb, res);
        return res;
    }

    private void dfs(int n, int l, int r, StringBuilder sb, List<String> res) {
        if (l == n && r == n) {
            res.add(sb.toString());
        }

        if (l < n) {
            dfs(n, l + 1, r, sb.append('('), res);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (r < l) {
            dfs(n, l, r + 1, sb.append(')'), res);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    // Solution using StringBuilder ends here

    // for print out if blocks
    public void printBlocks(List<String> res) {
        for (String s : res) {
            printBlock(s);
            System.out.println();
        }
    }
    public void printBlock(String s) {
        int spaces = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                printSpaces(spaces);
                System.out.print("if {\n");
                spaces += 2;
            } else {
                printSpaces(spaces -= 2);
                System.out.print("}\n");
            }
        }
    }

    public void printSpaces(int e) {
        System.out.print(" ".repeat(e));
    }
    // print if blocks logic ends here

    // Solution using char array starts here
    public List<String> usingCharArray(int n) { // TC: O(2^2n), SC: O(2n)
        List<String> res = new ArrayList<>();
        char[] cur = new char[2 * n];
        usingCharArray(0, 0, cur, res);
        return res;
    }

    public void usingCharArray(int l, int r, char[] cur, List<String> res) {
        if (l + r == cur.length) {
            res.add(new String(cur));
            return;
        }

        if (l < cur.length / 2) {
            cur[l + r] = '(';
            usingCharArray(l + 1, r, cur, res);
        }

        if (r < l) {
            cur[l + r] = ')';
            usingCharArray(l, r + 1, cur, res);
        }
    }
    // Solution use char array ends here

    public static void main(String[] args) {
        AllValidPermutationsOfParenthesesI avpop = new AllValidPermutationsOfParenthesesI();
        avpop.printBlocks(avpop.validParentheses(3));
    }
}
