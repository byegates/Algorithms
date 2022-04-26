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
}
