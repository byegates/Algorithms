# All Anagrams starting from the same position in two Strings

## Description
There are two strings, A and B, each of length N. A fragment of string A Corresponds with a fragment of string B if: both fragments start at the same position; letters from one fragment can be rearranged into the order of letters in the other fragment (note that the case and number of occurrences of the letter matters).
For example, given A = "dBacaAA' andB "caBdaaA",fragment "Ba" starting at position 1 of string A corresponds with fragment "aB" starting at position 1 of string B. On the other hand, fragment "ca" at position 3 in A does not correspond to "ca" at position 0 in B as they start in different positions. Fragments "aAA and "aaA starting at position 4 of both strings do not correspond as the number of occurrences of letters 'a' and 'A in the fragments differ.
Write a function: int solution(string &A, string &B)
such that, given stringsA and B, each of lengthN, returns the number of corresponding fragments of A and B.

Examples :

Given A "dBacaAA" and B = "caBdaaA", the function should return 5. The corresponding fragments are:
"dBaca" and "caBda" (starting at position 0)
"dBac" and "caBd"(starting at position 0)
."Ba" and "aB" (starting at position 1)
."a" and "a" (starting at position 4)
. "A" and "A" (at position 6).

Given A = "zzzX" and B "zzzX",the function should return 10. All fragments starting at the same positions in both strings correspond.

Given A = "abc" and B "ABC',the function should return 0. Even though the fragments consist of the same letters, in string A they are lowercase and in string B they are uppercase.

Given A = "ZZXY0z" and B = "OOYXZZ", the function should return 2.

Assume that: N is an integer within the range[1..100]. strings A and B consist only of letters (a-z and/or A-Z).
In your solution, focus on correctness. The performance of your solution will not be the focus of the assessment. They mentioned it for both the questions.

![Samples](https://github.com/byegates/Algorithms/blob/main/Solutions/Images/All%20Anagrams1.png)

![](../Images/All Anagrams1.png)

## Brute Force
There are n^2 substrings, for each, performance O(n) anagram check

TC: O(n^3)

SC: O(n), substrings

n3, for each n^2 substrings, perform a O(n) check of Anagram
```java
class Solution {
    public int numberOfFragments(String A, String B) {

        if (A == null || B == null || A.length() == 0 || B.length() == 0)
            return -1;

        System.out.printf("\n%s vs %s :\n\n", A, B);

        int res = 0, count = 0, n = A.length();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                String a = A.substring(i, j + 1);
                String b = B.substring(i, j + 1);
                if (isAnagram(a, b)) {
                    res++;
                    System.out.printf("%s vs %s \n", colorCodeStr(A, i, j), colorCodeStr(B, i, j));
                }
            }
        }
        return res;
    }

    private boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;

        int[] count = new int[58];
        for (int i = 0; i < a.length(); i++) {
            count[a.charAt(i) - 'A']++;
            count[b.charAt(i) - 'A']--;
        }
        for (int cnt : count)
            if (cnt != 0) return false;

        return true;
    }

    private String colorCodeStr(String a, int i, int j) {
        //System.out.println("a : " + " " + i + " " + j);
        return a.substring(0, i) + RED + a.substring(i, j + 1) + RESET + a.substring(j + 1);
    }
}
```

## O(n^2), use one map for everything
TC: O(n^2)

SC: O(58)? 😂

```java
class Solution {
    public int numberOfFragments(String A, String B) {

        if (A == null || B == null || A.length() == 0 || B.length() == 0)
            return -1;

        System.out.printf("\n%s vs %s :\n\n", A, B);

        int res = 0, n = A.length();
        for (int i = 0; i < n; i++) {
            int[] map = new int[58];
            int count = 0;
            for (int j = i; j < n; j++) {
                count = update(A.charAt(j) - 'A', count, +1, map);
                count = update(B.charAt(j) - 'A', count, -1, map);
                if (count == 0) {
                    res++;
                    System.out.printf("%s vs %s \n", colorCodeStr(A, i, j), colorCodeStr(B, i, j));
                }
            }
        }
        return res;
    }

    private int update(int idx, int count, int add, int[] map) {
        if (map[idx] == 0) count++;
        map[idx] += add;
        if (map[idx] == 0) count--;
        return count;
    }

    private String colorCodeStr(String a, int i, int j) {
        //System.out.println("a : " + " " + i + " " + j);
        return a.substring(0, i) + RED + a.substring(i, j + 1) + RESET + a.substring(j + 1);
    }
}
```