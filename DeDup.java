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
    public String s1(String s) { // TC: O(n), SC: O(1) excluding String to Array switch, O(n) including that
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

    public String s2(String s) { // TC: O(n), SC: O(1) excluding String to Array switch, O(n) including that
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

    public String s3(String s) { // TC: O(n), SC: O(1) excluding String to Array switch, O(n) including that
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
    82. Remove Adjacent Repeated Characters IV
    Repeatedly remove all adjacent, repeated characters in a given string from left to right.
    No adjacent characters should be identified in the final string.
    Examples
    "abbbaaccz" → "aaaccz" → "ccz" → "z"
    "aabccdc" → "bccdc" → "bdc"
     */

    public String s4(String s) { // TC: O(n), SC: O(1) excluding String to Array switch, O(n) including that
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
    public int[] a1(int[] a) { // TC: O(n), SC: O(1)
        if (a == null || a.length < 2) return a;
        int keep = 1;
        for (int read = 1; read < a.length; read++)
            if (a[read] != a[keep - 1])
                a[keep++] = a[read];
        return Arrays.copyOf(a, keep);
    }

    /*
    116. Array Deduplication II
    Given a sorted integer array, remove duplicate elements. For each group of elements with the same value keep at most two of them. Do this in-place, using the left side of the original array and maintain the relative order of the elements of the array. Return the array after deduplication.
    Assumptions
    The given array is not null
    Examples
    {1, 2, 2, 3, 3, 3} → {1, 2, 2, 3, 3}
     */
    public int[] a2(int[] a) { // TC: O(n), SC: O(1)
        if (a == null || a.length < 3) return a;
        int keep = 2;
        for (int read = 2; read < a.length; read++)
            if (a[read] != a[keep - 2])
                a[keep++] = a[read];
        return Arrays.copyOf(a, keep);
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
            int start = read++;
            while (read < a.length && a[read] == a[start]) read++;
            if (read - start == 1) a[keep++] = a[start];
        }
        return Arrays.copyOf(a, keep);
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
        int top = -1;
        for (int read = 0; read < a.length;)
            if (top == -1 || a[top] != a[read]) a[++top] = a[read++];
            else {
                while (read < a.length && a[read] == a[top]) read++;
                top--;
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
    public int[] a5(int[] a) { // TC: O(n), SC: O(1)
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
