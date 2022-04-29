package util;

public class Matrix {
    public static String toString(int[][] mx, int width) {
        int rows = mx.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            int[] row = mx[i];
            sb.append('[');
            for (int j = 0; j < mx[0].length; j++) {
                sb.append(String.format("%" + width + "s", row[j]));
                if (j != mx[0].length - 1) sb.append(", ");
            }
            sb.append(i == rows - 1 ? "]" : "]\n");
        }
        return sb.toString();
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

    public static int sum(int[] a) {
        return sum(new int[][]{a});
    }

    public static int sum(int[][] mx) {
        int sum = 0;
        for (int[] row : mx)
            for (int x : row)
                sum += x;
        return sum;

    }

    public static void main(String[] args) {
        int[][] mx = new int[][]{{1, 2,}, {3, 4}};
        System.out.println(toString(mx));
        System.out.println(toString(mx, 3));
        int[] a = new int[]{1, 2,};
        System.out.println(toString(a));
        System.out.println(toString(a, 3));
    }
}
