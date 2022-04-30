/*
    For all logic below, everything before "keep" pointer is what we "keep", not include "keep" pointer itself.
    And "read" is the pointer to traverse (i.e. read) the whole array
    If you prefer "slow" vs "fast" pointer, "keep" is the "slow" pointer and "read" is the "fast" pointer

    79. String DeDup 1 and,
    115. Array Deduplication I
    are the same (sorted, for all dup values, keep only 1),
    so the core logic is to keep the value when a[read] != a[keep - 1]

    80. Remove Adjacent Repeated Characters II and,
    116. Array Deduplication II are the same
    And their only differences to 79 & 115 is that keep max 2 for all dup values, so all 4 of them have very similar code.
    so the core logic is to keep the value when a[read] != a[keep - 2]

    315. Array Deduplication V is very close to above four problem too
    only variation is that array is not sorted (and the ask is to keep max 2 for all consecutive dup values)
    So we check both of the last two values before we decide to keep a value or not:
    a[read] != a[keep - 2] || a[read] != a[keep - 1]

    81. Remove Adjacent Repeated Characters III and,
    117. Array Deduplication III
    are the same problem for String and Array respectively
    the core logic I use here is to use a start pointer to record the starting index of each unique value (char/int)
    and let the read pointer move to the 1st of next different value,
    then use read - start as the count of each unique char/int, this way we know we only those with count of 1

    82. Remove Adjacent Repeated Characters IV and,
    118. Array Deduplication IV
    are the same problem for String and Array respectively
    the difference of 82 & 118 to 81 & 117 is that, not only you have to remove all consecutive values,
    if deleting something cause none-dup value to become dup, you'll need to delete that too.
    e.g.: int 81 & 117 "abbbaaccz" will be de-duped as "az"
    but 72 & 118 as us to de-dup it as "z"
    The key is to use the keep pointer (named as top in code below) as top of an "imaginary stack"
    as delete from stack is needed, we'll need to move top(keep) pointer back when dup is found, detail below

    And these two are the only code we use the keep(top) pointer to include value of the pointer index itself
    As this reconcile better with the concept of "top of stack"

    All problem have time complexity of O(n) as we traverse the array/string exactly once.
    And space complexity is O(1) if we don't consider the convert from String to char[] which is a java specific problem
    Of course, if we consider that, space will be O(n)

 */
import java.util.Arrays;

public class DeDup {
    /*
    79. String DeDup 1
    Remove adjacent, repeated characters in a given string, leaving only one character for each group of such characters.
    Assumptions
    Try to do it in place.
    Examples
    “aaaabbbc” is transferred to “abc”
    Corner Cases
    If the given string is null, returning null or an empty string are both valid.
     */
    public String s1(String s) {
        if (s == null || s.length() < 2) return s;
        return s1(s.toCharArray());
    }

    private String s1(char[] a) {
        int keep = 1;
        for (int read = 1; read < a.length; read++)
            if (a[read] != a[keep - 1])
                a[keep++] = a[read];
        return new String(a, 0, keep);
    }

    /*
    115. Array Deduplication I
    Given a sorted integer array, remove duplicate elements.
    For each group of elements with the same value keep only one of them.
    Do this in-place, using the left side of the original array and maintain the relative order of the elements of the array.
    Return the array after deduplication.
    Assumptions
    The array is not null
    Examples
    {1, 2, 2, 3, 3, 3} → {1, 2, 3}
     */
    public int[] a1(int[] a) {
        if (a == null || a.length < 2) return a;
        int keep = 1;
        for (int read = 1; read < a.length; read++)
            if (a[read] != a[keep - 1])
                a[keep++] = a[read];
        return Arrays.copyOf(a, keep);
    }

    /*
    80. Remove Adjacent Repeated Characters II
    Remove adjacent, repeated characters in a given string, leaving only two characters for each group of such characters.
    The characters in the string are sorted in ascending order.
    Assumptions
    Try to do it in place.
    Examples
    “aaaabbbc” is transferred to “aabbc”
    Corner Cases
    If the given string is null, we do not need to do anything.
     */

    public String s2(String s) {
        if (s == null || s.length() < 3) return s;
        return s2(s.toCharArray());
    }

    private String s2(char[] a) {
        int keep = 2;
        for (int read = 2; read < a.length; read++)
            if (a[read] != a[keep - 2])
                a[keep++] = a[read];
        return new String(a, 0, keep);
    }

    /*
    116. Array Deduplication II
    Given a sorted integer array, remove duplicate elements.
    For each group of elements with the same value keep at most two of them.
    Do this in-place, using the left side of the original array and maintain the relative order of the elements of the array.
    Return the array after deduplication.
    Assumptions
    The given array is not null
    Examples
    {1, 2, 2, 3, 3, 3} → {1, 2, 2, 3, 3}
     */
    public int[] a2(int[] a) {
        if (a == null || a.length < 3) return a;
        int keep = 2;
        for (int read = 2; read < a.length; read++)
            if (a[read] != a[keep - 2])
                a[keep++] = a[read];
        return Arrays.copyOf(a, keep);
    }

    /*
    81. Remove Adjacent Repeated Characters III
    Remove adjacent, repeated characters in a given string, leaving no character for each group of such characters.
    The characters in the string are sorted in ascending order.
    Assumptions
    Try to do it in place.
    Examples
    “aaaabbbc” is transferred to “c”
    Corner Cases
    If the given string is null, we do not need to do anything.
     */

    public String s3(String s) {
        if (s == null || s.length() < 2) return s;
        return s3(s.toCharArray());
    }

    private String s3(char[] a) {
        int keep = 0;
        for (int read = 0; read < a.length; ) {
            int start = read++;
            while (read < a.length && a[start] == a[read]) read++;
            if (read - start == 1) a[keep++] = a[start];
        }
        return new String(a, 0, keep);
    }

    /*
    117. Array Deduplication III
    Given a sorted integer array, remove duplicate elements.
    For each group of elements with the same value do not keep any of them.
    Do this in-place, using the left side of the original array and maintain the relative order of the elements of the array.
    Return the array after deduplication.
    Assumptions
    The given array is not null
    Examples
    {1, 2, 2, 3, 3, 3} → {1}
     */
    public int[] a3(int[] a) {
        if (a == null || a.length < 2) return a;
        int keep = 0;
        for (int read = 0; read < a.length; ) {
            int start = read++; // start is the first of every new char
            while (read < a.length && a[read] == a[start]) read++;
            if (read - start == 1) a[keep++] = a[start]; // use read - start as count for each char, so we only keep unique char
        }
        return Arrays.copyOf(a, keep);
    }

    /*
    82. Remove Adjacent Repeated Characters IV
    Repeatedly remove all adjacent, repeated characters in a given string from left to right.
    No adjacent characters should be identified in the final string.
    Examples
    "abbbaaccz" → "aaaccz" → "ccz" → "z"
    "aabccdc" → "bccdc" → "bdc"
     */

    public String s4(String s) {
        if (s == null || s.length() < 2) return s;
        return s4(s.toCharArray());
    }

    private String s4(char[] a) {
        int top = -1;
        for (int read = 0; read < a.length;)
            if (top == -1 || a[top] != a[read]) a[++top] = a[read++];
            else {
                while (read < a.length && a[read] == a[top]) read++;
                top--;
            }
        return new String(a, 0, top + 1);
    }

    /*
    118. Array Deduplication IV
    Given an unsorted integer array, remove adjacent duplicate elements repeatedly, from left to right.
    For each group of elements with the same value do not keep any of them.
    Do this in-place, using the left side of the original array. Return the array after deduplication.
    Assumptions
    The given array is not null
    Examples
    {1, 2, 3, 3, 3, 2, 2} → {1, 2, 2, 2} → {1}, return {1}
     */
    public int[] a4(int[] a) {
        if (a == null || a.length < 2) return a;
        int top = -1; // we use the first part of array as an imaginary stack as well what to keep, top is the index of the top value in stack, inclusive.
        for (int read = 0; read < a.length;) // read is the "fast" pointer, if you prefer to use slow and fast
            if (top == -1 || a[top] != a[read]) a[++top] = a[read++]; // top of stack is not dup, or it's the 1st char, in these cases, we always write
            else {
                while (read < a.length && a[read] == a[top]) read++; // when dup found, we move read to the first of next char
                top--; // delete from "stack", or the result range to keep
            }
        return Arrays.copyOf(a, top + 1);
    }
    /*
    315. Array Deduplication V
    Given an integer array(not guaranteed to be sorted),
    remove adjacent repeated elements. For each group of elements with the same value keep at most two of them.
    Do this in-place, using the left side of the original array and maintain the relative order of the elements of the array.
    Return the final array.
    Assumptions
    The given array is not null
    Examples
    {1, 2, 2, 3, 3, 3} --> {1, 2, 2, 3, 3}
    {2, 1, 2, 2, 2, 3} --> {2, 1, 2, 2, 3}
     */
    public int[] a5(int[] a) {
        if (a == null || a.length < 3) return a;
        int keep = 2;
        for (int read = 2; read < a.length; read++)
            if (a[read] != a[keep - 2] || a[read] != a[keep - 1])
                a[keep++] = a[read];
        return Arrays.copyOf(a, keep);
    }

    public static void main(String[] args) {
        DeDup dedup = new DeDup();
        System.out.println(dedup.s1("aaaabbbc").equals("abc"));
        System.out.println(dedup.s1("aa").equals("a"));
        System.out.println(dedup.s1("a").equals("a"));
        System.out.println(dedup.s1("").equals(""));
        System.out.println(dedup.s1((String) null) == null);

        System.out.println();
        System.out.println(dedup.s2("aaaabbbc").equals("aabbc"));
        System.out.println(dedup.s2("aaa").equals("aa"));
        System.out.println(dedup.s2("aa").equals("aa"));
        System.out.println(dedup.s2("a").equals("a"));
        System.out.println();

        System.out.println(dedup.s3("abbccde").equals("ade"));
        System.out.println(dedup.s3("a").equals("a"));
        System.out.println(dedup.s3("abbbaaccz").equals("az"));
        System.out.println(dedup.s4("abbbaaccz").equals("z"));
        System.out.println(dedup.s4("aabccdc").equals("bdc"));
        System.out.println();

        System.out.println(Arrays.equals(dedup.a1(new int[]{1, 2, 2, 3, 3, 3}), new int[]{1, 2, 3}));
        System.out.println(Arrays.equals(dedup.a1(new int[]{1, 2, 3, 4, 4, 5}), new int[]{1, 2, 3, 4, 5}));
        System.out.println(Arrays.equals(dedup.a2(new int[]{1, 1, 2, 3, 3, 3, 4, 5, 5, 5}), new int[]{1, 1, 2, 3, 3, 4, 5, 5}));
        System.out.println(Arrays.equals(dedup.a3(new int[]{1, 2, 2, 3, 3, 3}), new int[]{1}));
        System.out.println(Arrays.equals(dedup.a3(new int[]{1, 1, 2, 3, 3, 3, 4, 5, 5, 5}), new int[]{2, 4}));

        System.out.println(Arrays.toString(dedup.a5(new int[]{5, 1, 1, 2, 3, 1, 1, 2, 2, 2, 5, 5, 5, 6})));
        System.out.println(Arrays.equals(dedup.a4(new int[]{1, 2, 3, 3, 3, 2, 2}), new int[]{1}));
        System.out.println(Arrays.equals(dedup.a4(new int[]{1, 2, 3, 3, 3, 2, 2}), new int[]{1}));
        System.out.println(Arrays.equals(dedup.a4(new int[]{1, 1, 2, 3, 3, 3, 2, 1, 6}), new int[]{1, 6}));
        System.out.println(Arrays.equals(dedup.a5(new int[]{5, 1, 1, 2, 3, 1, 1, 2, 2, 2, 5, 5, 5, 6}), new int[]{5, 1, 1, 2, 3, 1, 1, 2, 2, 5, 5, 6}));
    }

}
