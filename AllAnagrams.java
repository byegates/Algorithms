//Find all occurrence of anagrams of a given string s in a given string l. Return the list of starting indices.
//
//        Assumptions
//
//        s is not null or empty.
//        s is not null.
//        Examples
//
//        l = "abcbac", s = "ab", return [0, 3] since the substring with length 2 starting from index 0/3 are all anagrams of "ab" ("ab", "ba").

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAnagrams {
    public static List<Integer> allAnagrams(String s, String l) {
        List<Integer> res = new ArrayList<>();

        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++)
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);

        int charsToMatch = map.size();
        for (int j = 0; j < l.length(); j++) {
            if (updateCount(l.charAt(j), -1, map) == 0) charsToMatch--;
            if (j - s.length() >= 0 && updateCount(l.charAt(j - s.length()), 1, map) == 1) charsToMatch++;
            if (charsToMatch == 0) res.add(j - s.length() + 1);
        }

        return res;
    }

    private static int updateCount(char ch, int val, Map<Character, Integer> map) {
        int count = map.getOrDefault(ch, Integer.MIN_VALUE);
        if (count != Integer.MIN_VALUE) map.put(ch, count += val);
        return count;
    } // TC: O(m + n), SC: O(m), m is the length of s and n is the length of l

    public static void main(String[] args) {
        System.out.println(allAnagrams("ab", "abcbac"));
        System.out.println(allAnagrams("aab", "ababacbbaac"));
    }
}

