/*
Rotate an N * N matrix clockwise 90 degrees.
        Assumptions
        The matrix is not null and N >= 0
        Examples
        { {1,  2,  3}
          {8,  9,  4},
          {7,  6,  5} }

        after rotation is
        { {7,  8,  1}
          {6,  9,  2},
          {5,  4,  3} }
*/

import java.util.Arrays;

public class RotateMatrix {
    public void rotate(int[][] mx) { // TC:O(n^2), SC: O(1)
        for (int start = 0, end = mx.length - 1; start < end; start++, end--)
            for (int j = 0; j < end - start; j++) { // 0 ~ n - 2 on level 1
                int tmp = mx[start][start + j];
                mx[start][start + j] = mx[end - j][start];
                mx[end - j][start] = mx[end][end - j];
                mx[end][end - j] = mx[start + j][end];
                mx[start + j][end] = tmp;
            }
    }

    public static boolean mxEqual(int[][] A, int [][] B) {
        if (A.length != B.length) return false;
        if (A[0].length != B[0].length) return false;
        for (int i = 0; i < A.length; i++) {
            if (!Arrays.equals(A[i], B[i])) return false;
        }
        return true;
    }

    public static void printMX(int[][] mx) {
        System.out.println();
        for (int[] row : mx)
            System.out.println(Arrays.toString(row));
    }

    public static void main(String[] args) {
        int[][] mx = new int[][]{{67,16,45,46,68,5,41,28},{29,68,56,45,87,98,84,72},{57,98,18,55,10,56,37,46},{96,4,51,39,55,32,44,73},{73,42,71,22,90,98,7,66},{51,85,79,91,3,40,33,61},{37,6,20,74,77,25,83,8},{75,28,37,63,54,53,24,67}};
        int[][] expected = new int[][]{{75, 37, 51, 73, 96, 57, 29, 67}, {28, 6, 85, 42, 4, 98, 68, 16}, {37, 20, 79, 71, 51, 18, 56, 45}, {63, 74, 91, 22, 39, 55, 45, 46}, {54, 77, 3, 90, 55, 10, 87, 68}, {53, 25, 40, 98, 32, 56, 98, 5}, {24, 83, 33, 7, 44, 37, 84, 41}, {67, 8, 61, 66, 73, 46, 72, 28}};

        printMX(mx);
        printMX(expected);
        RotateMatrix rm = new RotateMatrix();
        rm.rotate(mx);
        printMX(mx);
        System.out.println(mxEqual(expected, mx));

        // simpler demo
        int[][] mx2 = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        printMX(mx2);
        rm.rotate(mx2);
        printMX(mx2);
    }
}
