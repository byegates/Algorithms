//Find all occurrence of anagrams of a given string s in a given string l. Return the list of starting indices.
//
//        Assumptions
//
//        s is not null or empty.
//        s is not null.
//        Examples
//
//        l = "abcbac", s = "ab", return [0, 3] since the substring with length 2 starting from index 0/3 are all anagrams of "ab" ("ab", "ba").

import java.util.*;

public class AllAnagrams {
    public static List<Integer> allAnagrams(String s, String l) {
        List<Integer> res = new ArrayList<>();

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);

        int toMatch = map.size();
        for (int j = 0; j < l.length(); j++) {
            toMatch = updateMap(-1, l.charAt(j), toMatch, map);
            if (j >= s.length()) toMatch = updateMap(1, l.charAt(j - s.length()), toMatch, map);
            if (toMatch == 0) res.add(j - s.length() + 1); // j - s.length() is what we just got rid of, so next index is the start of the keeper substring
        }

        return res;
    }

    private static int updateMap(int val, char ch, int toMatch, Map<Character, Integer> map) {
        Integer count = map.get(ch);
        if (count == null) return toMatch;
        if (count == 0) toMatch++; // count: 0 -> none 0(1/-1), toMatch must increase
        map.put(ch, count += val);
        if (count == 0) toMatch--; // count: none 0(1/-1) -> 0, toMatch must decrease
        return toMatch;
    } // TC: O(m + n), SC: O(m), m is the length of s and n is the length of l

    private static List<Integer> toList(int[] A) {
        List<Integer> res = new ArrayList<>(A.length);
        for (int i : A) res.add(i);
        return res;
    }

    public static void main(String[] args) {
        List<Integer> res1 = allAnagrams("ab", "abcbac");
        System.out.println(res1);
        System.out.println("Is above result correct ? " + res1.equals(toList(new int[]{0, 3})));
        List<Integer> res2 = allAnagrams("aab", "ababacbbaac");
        System.out.println(res2);
        System.out.println("Is above result correct ? " + res2.equals(toList(new int[]{0, 2, 7})));
    }
}

