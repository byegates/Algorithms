package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static resources.ConsoleColors.RED;
import static resources.ConsoleColors.RESET;

public class Utils {
    private final static Random rand = new Random();
    public static String toString(int e, int radix) {
        StringBuilder sb = new StringBuilder();
        sb.append("0".repeat(Integer.numberOfLeadingZeros(e)));
        String s = Integer.toString(e, radix);
        for (int i = 0; i < s.length(); i++)
            if (s.charAt(i) == '1') sb.append(RED).append('1').append(RESET);
            else sb.append('0');
        return sb.toString();
    }

    public static int[] generateRandomArray(int minLen, int maxLen, int min, int max) {
        int len = rand.nextInt(minLen, maxLen);
        //int len = (int) (Math.random() * (maxLen - minLen)) + minLen;
        int[] a = new int[len];
        for (int i = 0; i < len; i++)
            a[i] = rand.nextInt(min, max);
        return a;
    }

    public static int[] generateRandomArray() {
        return generateRandomArray(3, 11, 0, 10);
    }

    public static List<Integer> generateRandomList(int minLen, int maxLen, int min, int max) {
        int len = rand.nextInt(minLen, maxLen);
        List<Integer> list = new ArrayList<>(len);
        for (int i = len; i > 0; i--) list.add(rand.nextInt(min, max));;
        return list;
    }

    public static List<Integer> generateRandomList() {
        return generateRandomList(3, 11, 0, 10);
    }
}
