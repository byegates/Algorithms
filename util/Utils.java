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

    public static int numLength(int num) {
        if (num < 0) return numOfDigits(-num) + 1;
        else return numOfDigits(num);
    }

    public static int numOfDigits(int num) {
        if (num < 100000) {
            if (num < 100) {
                if (num < 10) {
                    return 1;
                } else {
                    return 2;
                }
            } else {
                if (num < 1000) {
                    return 3;
                } else {
                    if (num < 10000) {
                        return 4;
                    } else {
                        return 5;
                    }
                }
            }
        } else {
            if (num < 10000000) {
                if (num < 1000000) {
                    return 6;
                } else {
                    return 7;
                }
            } else {
                if (num < 100000000) {
                    return 8;
                } else {
                    if (num < 1000000000) {
                        return 9;
                    } else {
                        return 10;
                    }
                }
            }
        }
    }

    public static boolean listEqualsArray(List<Integer> l, int[] a) {
        if (l.size() != a.length) return false;
        for (int i = 0; i < l.size(); i++)
            if (l.get(i) != a[i]) return false;
        return true;
    }

    public static <T> boolean listEqualsArray(List<T> l, T[] a) {
        if (l.size() != a.length) return false;
        for (int i = 0; i < l.size(); i++)
            if (l.get(i).equals(a[i])) return false;
        return true;
    }

    public static void main(String[] args) {
        for (int i = 1; i < Integer.MAX_VALUE/2; i *= 10) {
            int num = i + i / 10 + i / 100;
            System.out.printf("%10d: %d\n", num, numOfDigits(num));
        }
    }
}
