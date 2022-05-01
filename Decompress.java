/*
        175. Decompress String II
        run decompress(String s, true)
        Given a string in compressed form, decompress it to the original string.
        The adjacent repeated characters in the original string are compressed to have the character followed by the number of repeated occurrences.

        Assumptions
        The string is not null
        The characters used in the original string are guaranteed to be ‘a’ - ‘z’
        There are no adjacently repeated characters with length > 9
        Examples
        “a1c0b2c4” → “abbcccc”

        174. Decompress String I
        “acb2c4” → “acbbcccc”
        "ap2lec3n" → "applecccn"
        run decompress(String s, false)
*/

public class Decompress {
    public static String decompress(String s) {
        return decompress(s.toCharArray());
    }

    public static String decompress(char[] A) { // // TC: O(n), SC: O(1)
        int newLen = 0, write = 0;
        for (int curCharIdx = 0, read = 0;read < A.length;) {
            if (!Character.isDigit(A[read])) {
                curCharIdx = read++;
                if (read == A.length || !Character.isDigit(A[read])) {
                    A[write++] = A[curCharIdx];
                    newLen++;
                }
            } else {
                int curCharCount = 0;
                while (read < A.length && Character.isDigit(A[read]))
                    curCharCount = curCharCount * 10 + (A[read++] - '0');
                newLen += curCharCount;
                if (curCharCount == 0) continue;

                if (read - write >= curCharCount)
                    for (; curCharCount > 0; curCharCount--)
                        A[write++] = A[curCharIdx];
                else
                    while (curCharIdx < read)
                        A[write++] = A[curCharIdx++];
            }
        }

        if (newLen <= write) return new String(A, 0, write);
        return round2(A, write - 1, newLen, newLen > A.length ? new char[newLen] : A);
    }

    private static String round2(char[] src, int read, int newLen, char[] dest) {
        int write = newLen - 1;
        while (read >= 0) {
            if (!Character.isDigit(src[read])) dest[write--] = src[read--];
            else { // reverse read all digits (count)
                int curCharCount = 0, power = 0;
                while (read >= 0 && Character.isDigit(src[read])) // a123: 3 * 10^0 + 2 * 10^1 + 1 * 10^2
                    curCharCount += (src[read--] - '0') * (int) (Math.pow(10, power++)); // read is 1 byte before digits on the right char
                for (;curCharCount > 0; curCharCount--) dest[write--] = src[read];
                read--; // after written cur char, go read the next
            }
        }
        return new String(dest, 0, newLen);
    }

    public static void main(String[] args) {
        System.out.println(decompress("a1c0b2c4"));
        System.out.println(decompress("a13b21c0d2e11f13"));
        System.out.println(decompress("e4d3c2b21a0"));
        System.out.println(decompress("ap2lec3n"));
    }
}