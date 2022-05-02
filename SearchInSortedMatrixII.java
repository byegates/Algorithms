import java.util.Arrays;

/*
    Given a 2D matrix that contains integers only,
    which each row is sorted in ascending order and each column is also sorted in ascending order.
    Given a target number, returning the position that the target locates within the matrix.
    If the target number does not exist in the matrix, return {-1, -1}.
    Assumptions:
    The given matrix is not null.
    Examples:
    matrix = { {1, 2, 3}, {2, 4, 5}, {6, 8, 10} }
    target = 5, return {1, 2}
    target = 7, return {-1, -1}
 */
public class SearchInSortedMatrixII {
    /*
    Divide the matrix into max 4 smaller matrices (3 is enough actually), we can get rid of at least 1/4 each time.
    Use recursion to facilitate with handing corner cases.
    On average, we get rid of 1/4 each time, so the search space become 3/4 each time.
    Let total number of searches be x and total elements in matrix be n
    (1 - 1/4) ^ x * n = 1 ==> (3/4)^x = 1/n ==> (4/3)^x = n ==> x = log(4/3)_n;
    TC: log(4/3)_n (round of searches)
    SC: log(4/3)_n (height of recursion tree)
     */
    public int[] search(int[][] mx, int t) {
        if (mx.length == 0 || mx[0].length == 0) return new int[] {-1, -1};
        int rows = mx.length, cols = mx[0].length;
        int l = 0, r = rows * cols - 1;
        return search(mx, t, 0, 0, rows - 1, cols - 1);
    }

    private int[] search(int[][] mx, int t, int x1, int y1, int x2, int y2) {
        if (x1 > x2 || y1 > y2) return new int[] {-1, -1};
        if (x1 == x2 && y1 == y2) return mx[x1][y1] == t ? new int[]{x1, y1} : new int[] {-1, -1};
        if (t < mx[x1][y1] || t > mx[x2][y2]) return new int[] {-1, -1};

        int x0 = x1 + (x2 - x1) / 2;
        int y0 = y1 + (y2 - y1) / 2;

        int[] res = search(mx, t, x1, y1, x0, y0);
        if (res[0] != -1)return res;
        res = search(mx, t, x1, y0 + 1, x2, y2);
        if (res[0] != -1)return res;
        res = search(mx, t, x0 + 1, y1, x2, y0);
        return res[0] != -1 ? res : new int[] {-1, -1};
    }

    public static void main(String[] args) {
        SearchInSortedMatrixII sol = new SearchInSortedMatrixII();
        int[] a = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println(Arrays.toString(sol.search(new int[][]{{1}, {2}, {3}, {4}}, 2))); // [1, 0]
        System.out.println(Arrays.toString(sol.search(new int[][]{{1, 2, 3, 4}, {2, 5, 8, 9}, {3, 7, 11, 11}}, 7))); // [2, 1]

    }
}
