package util;

public class Matrix {
    public static String toString(int[][] mx) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : mx) {
            sb.append('[');
            for (int j = 0; j < mx[0].length; j++) {
                sb.append(String.format("%3s", row[j]));
                if (j != mx[0].length - 1) sb.append(", ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}
