/*
        Given a string with no duplicate characters, return a list with all permutations of the string and all its subsets.

        Examples
        Set = “abc”, all permutations are [“”, “a”, “ab”, “abc”, “ac”, “acb”, “b”, “ba”, “bac”, “bc”, “bca”, “c”, “cb”, “cba”, “ca”, “cab”].
        Set = “”, all permutations are [“”].
        Set = null, all permutations are [].
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllPermutationsOfSubsets {
    public List<String> allPermutationsOfSubsets(String s) { // TC: e * n! * n, SC: n
        List<String> res = new ArrayList<>();
        if (s != null) dfs(0, s.toCharArray(), res);
        return res;
    }

    private void dfs(int idx, char[] A, List<String> res) {
        res.add(new String(A, 0, idx));

        for (int i = idx; i < A.length; i++) {
            if (i != idx) swap(A, i, idx);
            dfs(idx + 1, A, res);
            if (i != idx) swap(A, i, idx);
        }
    }

    private void swap(char[] A, int l, int r) {
        char ch = A[l];
        A[l] = A[r];
        A[r] = ch;
    }

    public static void main(String[] args) {
        AllPermutationsOfSubsets aps = new AllPermutationsOfSubsets();
        List<String> l1 = aps.allPermutationsOfSubsets("slu");
        System.out.println(l1); // [, s, sl, slu, su, sul, l, ls, lsu, lu, lus, u, ul, uls, us, usl]
        System.out.println(l1.size()); // 16
        List<String> l2 = aps.allPermutationsOfSubsets("fast");
        System.out.println(l2); // [, f, fa, fas, fast, fat, fats, fs, fsa, fsat, fst, fsta, ft, fts, ftsa, fta, ftas, a, af, afs, afst, aft, afts, as, asf, asft, ast, astf, at, ats, atsf, atf, atfs, s, sa, saf, saft, sat, satf, sf, sfa, sfat, sft, sfta, st, stf, stfa, sta, staf, t, ta, tas, tasf, taf, tafs, ts, tsa, tsaf, tsf, tsfa, tf, tfs, tfsa, tfa, tfas]
        System.out.println(l2.size()); // 65
        l2 = l2.subList(0, 8);
        System.out.println(l2.size());
        System.out.println(l2);
        l2.subList(8, l2.size()).clear();
        System.out.println(l2);
        int target = 100, factor = 10;
        System.out.println(target /= factor);
    }
}

