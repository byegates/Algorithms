/*
        175. Decompress String II
        run decompress(String s, true)
        Given a string in compressed form, decompress it to the original string.
        The adjacent repeated characters in the original string are compressed to have the character followed by the number of repeated occurrences.

        Assumptions
        The string is not null
        The characters used in the original string are guaranteed to be ‘a’ - ‘z’
        Adjacently repeated characters' length can go over 9 in below solution
        Examples
        “a1c0b2c4” → “abbcccc”

        174. Decompress String I
        “acb2c4” → “acbbcccc”
        "ap2lec3n" → "applecccn"
        run decompress(String s, false)
*/

public class Decompress {
    public static String decompress(String s) {
        return decompress(s.toCharArray(), true);
    }

    public static String decompress(String s, boolean fixLen) {
        return decompress(s.toCharArray(), fixLen);
    }

    public static String decompress(char[] a, boolean fixLen) {
        int write = 0, newLen = 0;
        for (int read = 0; read < a.length; ) {
            int curCharIdx = read++, count = 0; // curCharIdx to remember the first char of each group
            for (; read < a.length && a[read] >= '0' && a[read] <= '9'; read++) // get count of each char (if exist)
                count = a[read] - '0' + count * 10;
            if (!fixLen && count == 0) count = 1; // to accommodate two different type of encoding: ab2 vs a1b2
            newLen += count; // calculate overall len of new String
            if (read - write >= count) // enough space to decompress in-place, so we do it
                for (; count > 0; count--) a[write++] = a[curCharIdx];
            else // Not enough space to decompress in-place right away, just copy everything for now
                while (curCharIdx < read) a[write++] = a[curCharIdx++];
        }
        if (newLen == write) return new String(a, 0, write); // we've done everything in-place, return
        return round2(a, write - 1, newLen, newLen > a.length ? new char[newLen] : a); // need round2 to write chars we couldn't finish earlier
    }

    private static String round2(char[] src, int read, int len, char[] dst) {
        for (int write = len; read >= 0; ) {
            char c = src[read--];
            int count = 0;
            for (int power = 0 ; c >= '0' && c <= '9'; c = src[read--]) // get count, formula as below
                count += (c - '0') * Math.pow(10, power++); // 123: 3 * 10^0 + 2 * 10^1 + 1 * 10^2
            dst[--write] = c; // write one char no matter what
            while (count-- > 1) dst[--write] = c; // write additional chars if count is greater than 1
        }
        return new String(dst, 0, len);
    }
    public static void main(String[] args) {
        System.out.println(decompress("a1c0b2c4"));
        System.out.println(decompress("a13b21c0d2e11f13"));
        System.out.println(decompress("e4d3c2b21a0"));
        System.out.println(decompress("ap2lec3n", false));
    }
}