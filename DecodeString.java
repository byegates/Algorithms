/*
        Given an encoded string, return it's decoded string.
        The encoding rule is: k[encoded_string],
        where the encoded_string inside the square brackets is being repeated exactly k times.
        Note that k is guaranteed to be a positive integer.

        You may assume that the input string is always valid;
        No extra white spaces, square brackets are well-formed, etc.
        Furthermore,
        you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k.
        For example, there won't be input like 3a or 2[4].

        Examples:
        s = "3[a]2[bc]", return "aaabcbc".
        s = "3[a2[c]]", return "accaccacc".
        s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
        https://leetcode.com/problems/decode-string/
        https://app.laicode.io/app/problem/616
*/

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class DecodeString {
    // method 1 starts here, recursion with queue
    public String decodeString(String s) {
        Queue<Character> q = new ArrayDeque<>(s.length());
        for (int i = 0; i < s.length(); i++) q.offer(s.charAt(i));
        return helper(q);
    }

    public String helper(Queue<Character> q) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!q.isEmpty()) {
            char c = q.poll();
            if (c >= '0' && c <= '9')
                num = c - '0' + num * 10;
            else if (c == '[') {
                String s = helper(q);
                for (;num > 0;num--) sb.append(s);
            } else if (c == ']') break;
            else sb.append(c);
        }
        return sb.toString();
    }
    // method 1 ends here

    // method 2, two stacks
    public String decodeString2(String s) {
        Deque<Integer> intStack = new ArrayDeque<>(); // stack to store int
        Deque<StringBuilder> strStack = new ArrayDeque<>(); // stack to store chars
        int num = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') num = num * 10 + c - '0';
            else if (c == '[') {
                intStack.offerFirst(num);
                strStack.offerFirst(sb);
                sb = new StringBuilder();
                num = 0;
            }
            else if (c == ']') {
                StringBuilder tmp = sb;
                sb = strStack.pollFirst();
                for (num = intStack.pollFirst(); num > 0; num--) sb.append(tmp);
            }
            else sb.append(c);
        }
        return sb.toString();
    }
    // method 2 ends here

    public static void main(String[] args) {
        DecodeString ds = new DecodeString();
        System.out.println(ds.decodeString("3[a]2[bc]")); // aaabcbc
        System.out.println(ds.decodeString2("3[a]2[bc]")); // aaabcbc

        System.out.println(ds.decodeString("la6[i7[o8[f]f9[e]]10[r]]")); // laiofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrr
        System.out.println(ds.decodeString2("la6[i7[o8[f]f9[e]]10[r]]")); // laiofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrriofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeeofffffffffeeeeeeeeerrrrrrrrrr
    }
}
