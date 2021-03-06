import util.Utils;

import java.util.Arrays;

public class Compress {
    /*
    611. Compress String II
    Given a string, replace adjacent, repeated characters with the character followed by the number of repeated occurrences.
    Assumptions
    The string is not null
    The characters used in the original string are guaranteed to be ‘a’ - ‘z’
    Examples
    “abbcccdeee” → “a1b2c3d1e3”

    For 173. Compress String, logic will be same as below but without singleCharCount logic(consider it always 0)
    */
    public static String compress(String s, boolean writeOne) {
        return compress(s.toCharArray(), writeOne);
    }

    public static String compress(char[] a, boolean writeOne) {
        int write = 0, singleCharCount = 0;
        for (int read = 0; read < a.length;) {
            int start = read;
            while (read < a.length && a[start] == a[read]) read++;
            a[write++] = a[start];
            if (read - start == 1) singleCharCount++;
            else write = writeCount(a, write, read - start);
        }

        if (!writeOne) singleCharCount = 0;
        if (singleCharCount == 0) return new String(a, 0, write);
        int newLen = write + singleCharCount;
        return round2(a, write - 1, newLen, newLen > a.length ? new char[newLen] : a);
    }

    private static String round2(char[] src, int read, int newLen, char[] dest) {
        int write = newLen - 1;
        while (read >= 0) {
            if (!Character.isDigit(src[read])) dest[write--] = '1'; // write '1' first for single char
            else
                while (Character.isDigit(src[read])) dest[write--] = src[read--]; // just copy all digits for the count
            dest[write--] = src[read--]; // copy the char itself
        }
        return new String(dest, 0, newLen);
    }

    // used by below solution for LeetCode too
    private static int writeCount(char[] a, int write, int count) {
        int len = 0;
        for (int i = count; i > 0; i /= 10) len++; // calculate the len for us to write count
        write += len;
        for (int i = count; i > 0; i /= 10) a[--write] = (char) (i % 10 + '0');
        return write + len;
    }

    // LeetCode 443. String Compression
    public static int compress(char[] a) {
        int write = 0;
        for (int read = 0; read < a.length; ) {
            int start = read;
            while (read < a.length && a[read] == a[start]) read++;
            a[write++] = a[start];
            if (read - start > 1)
                write = writeCount(a, write, read - start);
        }
        return write;
    }

    // Solution 2 using StringBuilder
    public String compressSB(String s) {
        StringBuilder sb = new StringBuilder();

        for (int read = 0; read < s.length(); ) {
            int start = read;
            while (read < s.length() && s.charAt(read) == s.charAt(start)) read++;

            sb.append(s.charAt(start)).append(read - start);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(compress("abbcccdeee", true).equals("a1b2c3d1e3"));
        System.out.println(compress("hhhhhhhhhhhhhhhhhhhhhxxxxxxxxxxxxxxaaaaaaaaaddddffffooooooooooooll", true).equals("h21x14a9d4f4o12l2"));
        System.out.println(compress("abbcccdeee", false).equals("ab2c3de3"));
        System.out.println(compress("aaaaaaaaaaaanneeeeeeefffffffwwwwwwwwwwwwwwfffhhhhhhhhhhhhhhhheeeeeeeeeeeeeeedddddddddddddddddddddddddddddgggggggggggggggggggggllllllllllllllllllvvvvvvvvvvvjggggggggggggggggggggccccccccccccccccjjjjttttttttttttttttttttttttttttttmdddkkkkkkkkkkkkkkkkkooooooooooooooooooooaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhhhhhyyyyyyyyyyyyyyyyyyyyyyyyyyyyoooooooooooooohhhhhelnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjuuuuuuuuuuuuffffffffffffffffffffffffaaaaaaaaaaaaaaaaaaaappppppppppppppppppppppppppppppfffffffffffffffffffffffffffffggeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeevvvvvvvvvvvvvvveeeeeeeeeeeeeeeeeeeeeeellllllllllllllllllllaaaaaaiiiiiiiiiiiiillllgggggggggggggggggggggggggggg", false).equals("a12n2e7f7w14f3h16e15d29g21l18v11jg20c16j4t30md3k17o20a42h20y28o14h5eln40b30j30u12f24a20p30f29g2e34v15e23l20a6i13l4g28"));
        String[] sa = new String[] {"a","a","b","b","c","c","c"};
        char[] a = new char[sa.length];
        for (int i = 0; i < sa.length; i++) a[i] = sa[i].charAt(0);
        System.out.println(Arrays.toString(Arrays.copyOf(a, compress(a))));
    }

} // TC: O(n), SC: O(1) unless new array