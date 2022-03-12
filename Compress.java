//Given a string, replace adjacent, repeated characters with the character followed by the number of repeated occurrences.
//
//        Assumptions
//
//        The string is not null
//
//        The characters used in the original string are guaranteed to be ‘a’ - ‘z’
//
//        Examples
//
//        “abbcccdeee” → “a1b2c3d1e3”


public class Compress {
    public static String compress(String s) {
        return compress(s.toCharArray());
    }

    public static String compress(char[] A) {
        int singleCharCount = 0;
        int read = 0, write = 0;
        while (read < A.length) {
            int start = read;
            while (read < A.length && A[start] == A[read]) read++;
            A[write++] = A[start];
            if (read - start == 1) singleCharCount++;
            else write = copyDigits(A, write, read - start);
        }

        if (singleCharCount == 0) return new String(A, 0, write);
        int newLen = write + singleCharCount;
        return round2(A, write - 1, newLen, newLen > A.length ? new char[newLen] : A);
    }

    private static String round2(char[] src, int read, int newLen, char[] dest) {
        int write = newLen - 1;
        while (read >= 0) {
            if (!Character.isDigit(src[read])) dest[write--] = '1'; // either write '1' or write all digits
            else while (Character.isDigit(src[read])) dest[write--] = src[read--];
            dest[write--] = src[read--];
        }
        return new String(dest, 0, newLen);
    }

    private static int copyDigits(char[] A, int write, int count) {
        int digitsLength = 0;
        for (int i = count; i > 0; i /= 10) digitsLength++;
        write += digitsLength;
        for (int i = count; i > 0; i /= 10) A[--write] = (char) (i % 10 + '0');
        return write + digitsLength;
    }

    public static void main(String[] args) {
        System.out.println(compress("abbcccdeee"));
        System.out.println(compress("hhhhhhhhhhhhhhhhhhhhhxxxxxxxxxxxxxxaaaaaaaaaddddffffooooooooooooll"));
    }

} // TC: O(n), SC: O(1) unless new array