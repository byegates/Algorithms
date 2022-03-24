/*      Given a set of characters represented by a String,
        return a list containing all subsets of the characters whose size is K.
        Notice that each subset returned will be sorted for deduplication.

        Assumptions
        There could be duplicate characters in the original set.

        Examples
        Set = "abc", K = 2, all the subsets are [“ab”, “ac”, “bc”].
        Set = "abb", K = 2, all the subsets are [“ab”, “bb”].
        Set = "abab", K = 2, all the subsets are [“aa”, “ab”, “bb”].
        Set = "", K = 0, all the subsets are [""].
        Set = "", K = 1, all the subsets are [].
        Set = null, K = 0, all the subsets are [].
*/


import java.util.*;

public class AllSubsetsIIofSizeK {
    public List<String> subSetsIIOfSizeK(String s, int k) { // TC: 2^n?, SC: n
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        StringBuilder sb = new StringBuilder();

        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

        List<Character> L = new ArrayList<>(map.keySet());
        Collections.sort(L);

        subSets(0, sb, k, map, res, L);
        return res;
    }

    private void subSets(int idx, StringBuilder sb, int k, Map<Character, Integer> map, List<String> res, List<Character> L) {
        if (sb.length() == k) res.add(sb.toString());
        if (sb.length() == k || idx == L.size()) return;

        char ch = L.get(idx);
        int count = map.get(ch);
        for (int j = 0; j < count; j++)
            subSets(idx + 1, sb.append(ch), k, map, res, L);

        sb.setLength(sb.length() - count);
        subSets(idx + 1, sb, k, map, res, L);
    }

    public List<String> subSetsIIOfSizeK2(String s, int k) { // TC: 2^n?, SC: n
        List<String> res = new ArrayList<>();
        if (s == null) return res;
        StringBuilder sb = new StringBuilder();

        Map<Character, Integer> map = new HashMap<>();
        for (char c : s.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

        List<Character> L = new ArrayList<>(map.keySet());
        Collections.sort(L);

        subSets2(0, sb, k, map, res, L);
        return res;
    }

    private void subSets2(int idx, StringBuilder sb, int k, Map<Character, Integer> map, List<String> res, List<Character> L) {
        if (sb.length() == k) res.add(sb.toString());
        if (sb.length() == k || idx == L.size()) return;

        char ch = L.get(idx);
        int count = map.get(ch);
        StringBuilder sb0 = new StringBuilder();
        for (int j = 0; j <= count; j++) {
            subSets2(idx + 1, sb.append(sb0), k, map, res, L);
            sb.setLength(sb.length() - sb0.length());
            sb0.append(ch);
        }
    }

    public static void main(String[] args) {
        AllSubsetsIIofSizeK as2sk = new AllSubsetsIIofSizeK();
        System.out.println(as2sk.subSetsIIOfSizeK("ofozjot", 6)); // [fjootz, fjooot, fjoooz, foootz, joootz]
        System.out.println(as2sk.subSetsIIOfSizeK2("ofozjot", 6)); // [joootz, foootz, fjootz, fjoooz, fjooot]
    }
}
