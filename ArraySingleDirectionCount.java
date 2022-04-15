/*
        Given an array A of length N containing all positive integers from [1...N].
        Get an array B such that B[i] represents how many elements A[j] (j > i) in array A that are smaller than A[i].

        Assumptions:
        The given array A is not null.

        Examples:
        A = { 4, 1, 3, 2 }, we should get B = { 3, 0, 1, 0 }.
        Requirement:
        time complexity = O(nlogn).
*/

import java.util.Arrays;

public class ArraySingleDirectionCount {
    //method 1 starts here
    public int[] countLeftSmall(int[] arr) {
        int[] indices = new int[arr.length];
        for (int i = 0; i < indices.length; i++) indices[i] = i;
        int[] counts = new int[arr.length];
        int[] idxHelper = new int[arr.length];
        mergeSort(arr, indices, counts, idxHelper, 0, arr.length - 1);
        return counts;
    }

    private void mergeSort(int[] arr, int[] indices, int[] counts, int[] idxHelper, int l, int r) {
        if (l >= r) return;
        int m = l + (r - l) / 2;
        mergeSort(arr, indices, counts, idxHelper, l, m);
        mergeSort(arr, indices, counts, idxHelper, m + 1, r);
        merge(arr, indices, counts, idxHelper, l, m, r);
    }

    private void merge(int[] arr, int[] indices, int[] counts, int[] idxHelper, int l, int m, int r) {
        for (int i = l; i <= r; i++) idxHelper[i] = indices[i];
        int i = l, j = m + 1, write = l;
        while (i <= m) {
            if (j > r || arr[idxHelper[i]] <= arr[idxHelper[j]]) {
                counts[idxHelper[i]] += (j - m - 1);
                indices[write++] = idxHelper[i++];
            } else {
                indices[write++] = idxHelper[j++];
            }
        }
    }
    // method 1 ends here

    // method 2 starts here, method 2 can count both right an left, depend on the boolean value passed in
    static class Cell {
        int val, idx, count;
        Cell (int val, int idx) {
            this.val = val;
            this.idx = idx;
        }
    }
    public int[] count(int[] arr, boolean countLeft) { // TC: O(nlogn), SC: O(n)
        if (arr.length == 0) return new int[0];
        Cell[] cells = new Cell[arr.length];
        for (int i = 0; i < arr.length; i++) {
            cells[i] = new Cell(arr[i], i);
        }

        Cell[] helper = new Cell[arr.length];

        mergeSort(cells, helper, 0, arr.length - 1, countLeft);

        int[] res = new int[arr.length];

        for (Cell cell : cells)
            res[cell.idx] = cell.count;

        return res;
    }

    private void mergeSort(Cell[] cells, Cell[] helper, int left, int right, boolean countLeft) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(cells, helper, left, mid, countLeft);
        mergeSort(cells, helper, mid + 1, right, countLeft);
        merge(cells, helper, left, mid, right, countLeft);
    }

    private void merge(Cell[] cells, Cell[] cellsHelper, int left, int mid, int right, boolean countLeft) {
        for (int i = left; i <= right; i++)
            cellsHelper[i] = cells[i];

        int i = left, j = mid + 1, write = left;

        if (countLeft) {
            while (i <= mid || j <= right) { // we need to make sure we move both to the end to count everything
                if (j > right || i <= mid && cellsHelper[i].val < cellsHelper[j].val) {
                    cells[write++] = cellsHelper[i++];
                } else {
                    cellsHelper[j].count += i - left;
                    cells[write++] = cellsHelper[j++];
                }
            }
        } else {
            while (i <= mid) {
                if (j > right || cellsHelper[i].val <= cellsHelper[j].val) {
                    cellsHelper[i].count += j - mid - 1;
                    cells[write++] = cellsHelper[i++];
                } else cells[write++] = cellsHelper[j++];
            }
        }
    }
    // method 2 ends here

    public int[] countArray(int[] arr) {
        return count(arr, false);
    }

    public static void main(String[] args) {
    ArraySingleDirectionCount lsc = new ArraySingleDirectionCount();
    System.out.println(Arrays.toString(lsc.countLeftSmall(new int[]{4, 1, 3, 2}))); // [3, 0, 1, 0]
    System.out.println(Arrays.toString(lsc.count(new int[]{4, 1, 3, 2}, false))); // [3, 0, 1, 0]
    System.out.println(Arrays.toString(lsc.countArray(new int[]{4, 1, 3, 2}))); // [3, 0, 1, 0]
    System.out.println(Arrays.toString(lsc.count(new int[]{4, 1, 3, 2, 9, 5, 7, 5}, false))); // [3, 0, 1, 0, 3, 0, 1, 0]
    System.out.println(Arrays.toString(lsc.count(new int[]{4, 1, 3, 2, 9, 5, 7, 5}, true))); // [0, 0, 1, 1, 4, 4, 5, 4]
    }
}
