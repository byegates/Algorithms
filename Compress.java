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

    public static String compress(char[] A, boolean writeOne) {
        int write = 0, singleCharCount = 0;
        for (int read = 0; read < A.length;) {
            int start = read;
            while (read < A.length && A[start] == A[read]) read++;
            A[write++] = A[start];
            if (read - start == 1) singleCharCount++;
            else write = writeCount(A, write, read - start);
        }

        if (!writeOne) singleCharCount = 0;
        if (singleCharCount == 0) return new String(A, 0, write);
        int newLen = write + singleCharCount;
        return round2(A, write - 1, newLen, newLen > A.length ? new char[newLen] : A);
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

    private static int writeCount(char[] A, int write, int count) {
        int len = 0;
        for (int i = count; i > 0; i /= 10) len++; // count the len for us to write count
        write += len;
        for (int i = count; i > 0; i /= 10) A[--write] = (char) (i % 10 + '0');
        return write + len;
    }

    public static void main(String[] args) {
        System.out.println(compress("abbcccdeee", true)); // a1b2c3d1e3
        System.out.println(compress("hhhhhhhhhhhhhhhhhhhhhxxxxxxxxxxxxxxaaaaaaaaaddddffffooooooooooooll", true)); // h21x14a9d4f4o12l2
        System.out.println(compress("abbcccdeee", false)); // ab2c3de3
        System.out.println(compress("aaaaaaaaaaaanneeeeeeefffffffwwwwwwwwwwwwwwfffhhhhhhhhhhhhhhhheeeeeeeeeeeeeeedddddddddddddddddddddddddddddgggggggggggggggggggggllllllllllllllllllvvvvvvvvvvvjggggggggggggggggggggccccccccccccccccjjjjttttttttttttttttttttttttttttttmdddkkkkkkkkkkkkkkkkkooooooooooooooooooooaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhhhhhyyyyyyyyyyyyyyyyyyyyyyyyyyyyoooooooooooooohhhhhelnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjuuuuuuuuuuuuffffffffffffffffffffffffaaaaaaaaaaaaaaaaaaaappppppppppppppppppppppppppppppfffffffffffffffffffffffffffffggeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeevvvvvvvvvvvvvvveeeeeeeeeeeeeeeeeeeeeeellllllllllllllllllllaaaaaaiiiiiiiiiiiiillllgggggggggggggggggggggggggggg", false)); // a12n2e7f7w14f3h16e15d29g21l18v11jg20c16j4t30md3k17o20a42h20y28o14h5eln40b30j30u12f24a20p30f29g2e34v15e23l20a6i13l4g28
        System.out.println(compress("aaaaaaaaaaaanneeeeeeefffffffwwwwwwwwwwwwwwfffhhhhhhhhhhhhhhhheeeeeeeeeeeeeeedddddddddddddddddddddddddddddgggggggggggggggggggggllllllllllllllllllvvvvvvvvvvvjggggggggggggggggggggccccccccccccccccjjjjttttttttttttttttttttttttttttttmdddkkkkkkkkkkkkkkkkkooooooooooooooooooooaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaahhhhhhhhhhhhhhhhhhhhyyyyyyyyyyyyyyyyyyyyyyyyyyyyoooooooooooooohhhhhelnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjuuuuuuuuuuuuffffffffffffffffffffffffaaaaaaaaaaaaaaaaaaaappppppppppppppppppppppppppppppfffffffffffffffffffffffffffffggeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeevvvvvvvvvvvvvvveeeeeeeeeeeeeeeeeeeeeeellllllllllllllllllllaaaaaaiiiiiiiiiiiiillllgggggggggggggggggggggggggggg", false).equals("a12n2e7f7w14f3h16e15d29g21l18v11jg20c16j4t30md3k17o20a42h20y28o14h5eln40b30j30u12f24a20p30f29g2e34v15e23l20a6i13l4g28"));
    }

} // TC: O(n), SC: O(1) unless new array