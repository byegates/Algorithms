/*
        Problem II:
        Given a set of characters represented by a String, return a list containing all subsets of the characters.
        Notice that each subset returned will be sorted to remove the sequence.

        Assumptions
        There could be duplicate characters in the original set.
        Examples

        Set = "abc", all the subsets are ["", "a", "ab", "abc", "ac", "b", "bc", "c"]
        Set = "abb", all the subsets are ["", "a", "ab", "abb", "b", "bb"]
        Set = "abab", all the subsets are ["", "a", "aa","aab", "aabb", "ab","abb","b", "bb"]
        Set = "", all the subsets are [""]
        Set = null, all the subsets are []
*/

/*abab:
<["", "a", "aa", "aab", "aabb", "ab", "abb", "b", "bb"]>
<["", "a", "aa", "aab", "aabb", "ab", "abb", "b", "bb"]>
        , "aab"
        , "ab"
        , "abb"
*/

import java.util.*;

public class AllSubsets {
    public List<String> subSets2(String s) { //: O(n^2), O(n)
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        char[] A = s.toCharArray();
        Arrays.sort(A);
        StringBuilder sb = new StringBuilder();
        subSets2(0, A, sb, res);
        return res;
    }

    private void subSets2(int idx, char[] A, StringBuilder sb, List<String> res) {
        if (idx == A.length) {
            res.add(sb.toString());
            return;
        }
        subSets2(idx + 1, A, sb.append(A[idx]), res);
        sb.setLength(sb.length() - 1);
        while (idx < A.length - 1 && A[idx] == A[idx + 1]) idx++;
        subSets2(idx + 1, A, sb, res);
    }

    public List<String> subSets(String s) { //: O(n^2), O(n)
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        StringBuilder sb = new StringBuilder();
        subSets(0, s.toCharArray(), sb, res);
        return res;
    }

    private void subSets(int idx, char[] A, StringBuilder sb, List<String> res) {
        if (idx == A.length) {
            res.add(sb.toString());
            return;
        }
        subSets2(idx + 1, A, sb.append(A[idx]), res);
        sb.setLength(sb.length() - 1);
        subSets2(idx + 1, A, sb, res);
    }

    //subsets 2, solution b
    public List<String> subSets2b(String s) { // TC: 2^n?, SC: n
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        StringBuilder sb = new StringBuilder();
        char[] A = s.toCharArray();
        Arrays.sort(A);

        subSets2b(0, sb, A, res);
        return res;
    }

    private void subSets2b(int idx, StringBuilder sb, char[] A, List<String> res) {
        res.add(sb.toString());
        for (int i = idx; i < A.length; i++) {
            if (i != idx && A[i] == A[i - 1]) continue; // i != idx meaning not the first of cur group
            subSets2b(i + 1, sb.append(A[i]), A, res);
            sb.setLength(sb.length() - 1);
        }
    }
    // subsets 2 solution b ends here

    // subsets 2 solution c
    public List<String> subSets2c(String s) { // TC: 2^n?, SC: n
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        StringBuilder sb = new StringBuilder();

        List<Integer> counts = new ArrayList<>();
        List<Character> L = preProcessing(s.toCharArray(), new ArrayList<>(), counts);

        subSets(0, sb, L, counts, res);
        return res;
    }

    private void subSets(int idx, StringBuilder sb, List<Character> L, List<Integer> counts, List<String> res) {
        if (idx == L.size()) {
            res.add(sb.toString());
            return;
        }

        char ch = L.get(idx);
        int count = counts.get(idx);
        for (int j = 0; j <= count; j++) {
            String s = String.valueOf(ch).repeat(j);
            subSets(idx + 1, sb.append(s), L, counts, res);
            sb.setLength(sb.length() - s.length());
        }
    }

    private List<Character> preProcessing(char[] chars, List<Character> L, List<Integer> counts) {
        Arrays.sort(chars);

        int i = 0;
        while (i < chars.length) {
            int start = i;
            while (i < chars.length && chars[i] == chars[start]) i++;
            counts.add(i - start);
            L.add(chars[start]);
        }

        return L;
    }
    //

    public static void main(String[] args) {
        AllSubsets ass = new AllSubsets();
        String tc1 = "abab";
        List<String> l1 = ass.subSets2(tc1);
        System.out.println(l1); // [, a, aa, aab, aabb, ab, abb, b, bb]
        System.out.println(l1.size()); // 9 (2+1)*(2+1)
        String tc2 = "aaabcc";
        List<String> l2 = ass.subSets2(tc2);
        System.out.println(l2); // 24 (3+1)*(1+1)*(2+1)
        System.out.println(l2.size());
        System.out.println(ass.subSets2b(tc2));
        String tc3 = "abc";
        System.out.println(ass.subSets(tc3)); // [abc, ab, ac, a, bc, b, c, ]

        System.out.println();
        System.out.println(ass.subSets2c(tc1));
        System.out.println(ass.subSets2b(tc1));

    }
}
