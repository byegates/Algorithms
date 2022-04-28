/*
    Given two arrays of integers, find the median value.
    Assumptions
    The two given array are not null and at least one of them is not empty
    The two given array are not guaranteed to be sorted
    Examples
    A = {4, 1, 6}, B = {2, 3}, median is 3
    A = {1, 4}, B = {3, 2}, median is 2.5
 */
public class MedianOfTwoArrays {
    public double median(int[] a, int[] b) { // TC: O(n + m) (worst: O((n + m)^2)), SC: O(log(n + m))
        int len1 = a.length, len2 = b.length, len = len1 + len2;
        int medianLen = len / 2;
        quickSelect(a, b, 0, len - 1, medianLen); // if len = 7, medianLen will be 3 which is the idx of the 4th value
        double m1 = getVal(a, b, medianLen);
        if (len % 2 == 1) return m1; // for odd number, medianLen is the idx for the middle value which is what we need
        quickSelect(a, b, 0, len - 1, medianLen - 1);
        double m2 = getVal(a, b, medianLen - 1); // if len = 8, m1 is 5th(idx = 4) value, now we get 4th (idx = 3)
        return (m1 + m2) / 2;
    }

    private void quickSelect(int[] a, int[] b, int l, int r, int k) {
        int p = partition(a, b, l, r);
        if (p < k) quickSelect(a, b, p + 1, r, k);
        else if (p > k) quickSelect(a, b, l, p - 1, k);
    }

    private int partition(int[] a, int[] b, int l, int r) {
        // for simplicity, we'll just use right as pivot, for best performance, we could use a random or more advanced pivot
        int pivotVal = getVal(a, b, r);
        int i = l, j = r - 1;
        while (i <= j) {
            if (getVal(a, b, i) < pivotVal) i++;
            else if (getVal(a, b, j) >= pivotVal) j--;
            else swap(a, b, i++, j--);
        }
        swap(a, b, r, i);
        return i;
    }

    private void swap(int[] a, int[] b, int i, int j) {
        int tmp = getVal(a, b, i);
        setVal(a, b, i, getVal(a, b, j));
        setVal(a, b, j, tmp);
    }

    private void setVal(int[] a, int[] b, int k, int val) {
        if (k < a.length) a[k] = val;
        else b[k - a.length] = val;
    }

    private int getVal(int[] a, int[] b, int k) {
        return k < a.length ? a[k] : b[k - a.length];
    }

    public static void main(String[] args) {
        double epsilon = .000001;
        int[] a = new int[]{9, 11, 5, 10, 8};
        int[] b = new int[]{3, 4, 2};
        MedianOfTwoArrays m2 = new MedianOfTwoArrays();
        System.out.println(Math.abs(m2.median(a, b) - 6.5) <= epsilon);
        System.out.println(Math.abs(m2.median(new int[]{1, 4}, new int[]{3, 2}) - 2.5) <= epsilon);
        System.out.println(Math.abs(m2.median(new int[]{4, 1, 6}, new int[]{2, 3}) - 3) <= epsilon);
    }
}
