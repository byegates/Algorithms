package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static resources.ConsoleColors.*;
import static resources.ConsoleColors.CYAN;

public class Utils {
    private final static Random rand = new Random();

    public static void main(String[] args) {
        for (int i = 1; i < Integer.MAX_VALUE/2; i *= 10) {
            int num = i + i / 10 + i / 100;
            System.out.printf("%10d: %d\n", num, numOfDigits(num));
        }

        int[][] mx = new int[][]{{1, 2,}, {3, 4}};
        System.out.println(Utils.toString(mx));
        System.out.println(Utils.toString(mx, 3));
        int[] a = new int[]{1, 2,};
        System.out.println(Utils.toString(a));
        System.out.println(Utils.toString(a, 3));

        int[] a2 = new int[] {1, 2, -1, 3};
        System.out.println(toString(a2, 2));
        System.out.println(toString(toIntegerArray(a2), 2));
    }

    public static String toString(int e, int radix) {
        if (e == 0) return "0".repeat(32);
        StringBuilder sb = new StringBuilder();
        if ( e < 0) {
            while (e != 0) {
                sb.append(e & 1);
                e >>>= 1;
            }
            sb.reverse();
            StringBuilder sb2 = new StringBuilder();
            for (int i = 0; i < sb.length(); i++)
                if (sb.charAt(i) == '1') sb2.append(RED).append('1').append(RESET);
                else sb2.append('0');
            return sb2.toString();
        }
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

    public static Integer[] toIntegerArray(int[] a) {
        Integer[] res = new Integer[a.length];
        for (int i = 0; i < a.length; i++) res[i] = a[i];
        return res;
    }

    public static int[] toIntArray(Integer[] a) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) res[i] = a[i];
        return res;
    }

    public static List<Integer> toList(int[] a) {
        List<Integer> list = new ArrayList<>();
        for (int val : a) list.add(val);
        return list;
    }

    public static String toString(int[] a, int width) {
        return toString(new int[][]{a}, width);
    }

    public static String toString(int[] a) {
        return toString(new int[][]{a}, 1);
    }

    public static String toString(int[][] mx) {
        return toString(mx, 1);
    }

    public static String toString(int[][] mx, int width) {
        int m = mx.length, n = mx[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%" + width + "s", row[j]));
                if (j != n - 1) sb.append(", ");
            }
            sb.append(i == m - 1 ? "]" : "]\n");
        }
        return sb.append("\n").toString();
    }

    public static String toString(long[] mx, int width) {
        return toString(new long[][]{mx}, width);
    }

    public static String toString(long[][] mx, int width) {
        int m = mx.length, n = mx[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%" + width + "s", row[j]));
                if (j != n - 1) sb.append(", ");
            }
            sb.append(i == m - 1 ? "]" : "]\n");
        }
        return sb.append("\n").toString();
    }

    public static String toString(boolean[] mx, int width) {
        return toString(new boolean[][]{mx}, width);
    }

    public static String toString(boolean[][] mx, int width) {
        int m = mx.length, n = mx[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%" + width + "s", row[j]));
                if (j != n - 1) sb.append(", ");
            }
            sb.append(i == m - 1 ? "]" : "]\n");
        }
        return sb.append("\n").toString();
    }

    public static String toString(char[] mx, int width) {
        return toString(new char[][]{mx}, width);
    }

    public static String toString(char[][] mx, int width) {
        int m = mx.length, n = mx[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%" + width + "s", row[j]));
                if (j != n - 1) sb.append(", ");
            }
            sb.append(i == m - 1 ? "]" : "]\n");
        }
        return sb.append("\n").toString();
    }

    public static <T> String toString(T[] mx, int width) {
        T[][] _mx = (T[][]) Array.newInstance(mx.getClass().getComponentType(), 1, mx.length);
        _mx[0] = mx;
        return toString(_mx, width);
    }

    public static <T> String toString(T[][] mx, int width) {
        int m = mx.length, n = mx[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append(String.format("%" + width + "s", row[j]));
                if (j != n - 1) sb.append(", ");
            }
            sb.append(i == m - 1 ? "]" : "]\n");
        }
        return sb.append("\n").toString();
    }

    public static String toStringMD(int[][] mx, int width) {
        Integer[][] mx0 = new Integer[mx.length][mx[0].length];
        for (int i = 0; i < mx.length; i++) for (int j = 0; j < mx[0].length; j++)
            mx0[i][j] = mx[i][j];
        return toStringMD(mx0, width);
    }

    public static String toStringMD(Integer[][] mx, int width) {
        int m = mx.length, n = mx[0].length;
        StringBuilder sb = new StringBuilder();
        // add header
        sb.append("| ").append(" ".repeat(width));
        for (int j = 0; j < n; j++) sb.append(String.format("| %-" + width + "s", j));
        sb.append("|\n");

        // add ----- line
        for (int j = 0; j <= n; j++) sb.append("|-").append("-".repeat(width));
        sb.append("|\n");

        // add context
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append(String.format("| %-" + width + "s|", i));
            for (int j = 0; j < n; j++) {
                sb.append(String.format(" %-" + width + "s", row[j])).append("|");
            }
            sb.append("\n");
        }
        return sb.append("\n").toString();
    }

    public static int[][] mxDeepCopy(int[][] mx) {
        int[][] mx2 = new int[mx.length][mx[0].length];
        for (int i = 0; i < mx.length; i++) mx2[i] = mx[i].clone();
        return mx2;
    }

    public static char[][] mxDeepCopy(char[][] mx) {
        char[][] mx2 = new char[mx.length][mx[0].length];
        for (int i = 0; i < mx.length; i++) mx2[i] = mx[i].clone();
        return mx2;
    }

    public static void printMXCompare(int[][] board, int[][] b2) {

        System.out.print("\n");
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] != b2[r][c]) System.out.printf(RED_BOLD_BRIGHT + "%3s  " + RESET, board[r][c]);
                else if (board[r][c] == 1) System.out.printf(CYAN + "%3s  " + RESET, board[r][c]);
                else System.out.printf("%3s  ", board[r][c]);

//                if ((c + 1) % 3 == 0 && c != 8) System.out.print(CYAN + "|  " + RESET);
            }
            System.out.println();

//            if ((r + 1) % 3 == 0 && r != 8) System.out.print(CYAN + "-------------------------------\n" + RESET);
        }
        System.out.println();
    }

    public static String SudokuBoard2Str(char[][] mx) {
        int m = mx.length, n = mx[0].length, width = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < m; i++) {
            var row = mx[i];
            sb.append('[');
            for (int j = 0; j < n; j++) {
                sb.append('"');
                sb.append(String.format("%" + width + "s", row[j]));
                sb.append('"');
                if (j != mx[0].length - 1) sb.append(", ");
            }
            sb.append(i == m - 1 ? "]" : "],");
        }
        sb.append("]");
        return sb.toString();
    }

    public static String toString(char[][] mx) {
        return toString(mx, 1);
    }

    public static String toString(boolean[][] mx) {
        return toString(mx, 5);
    }

    public static <T> String toString(T[][] mx) {
        int width = 1;
        for (var a : mx) for (var v : a) if (v instanceof Boolean) {width = 5;break;}
        return toString(mx, width);
    }

    public static int sum(int[] a) {
        return sum(new int[][]{a});
    }

    public static int sum(int[][] mx) {
        int sum = 0;
        for (int[] row : mx) for (int x : row)
            sum += x;
        return sum;
    }

    public static String toArray(String s) {
        return s.replaceAll("\"", "'").replaceAll("\\[", "{").replaceAll("]", "}");
    }
}
