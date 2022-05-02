/*
    Given a target integer T and an integer array A sorted in ascending order, Find the total number of occurrences of T in A.
    Examples
    A = {1, 2, 3, 4, 5}, T = 3, return 1
    A = {1, 2, 2, 2, 3}, T = 2, return 3
    A = {1, 2, 2, 2, 3}, T = 4, return 0
    Corner Cases
    What if A is null? We should return 0 in this case.
 */
public class TotalOccurrence {
    // Solution 1 starts here
    // TC: O(log(n) + count)
    // SC: O(1)
    public int totalOccurrence(int[] a, int T) {
        if (a == null || a.length == 0) return 0;
        int l = 0, r = a.length - 1, m = 0;
        while (l <= r) {
            m = l + (r - l) / 2;
            if (a[m] == T) break;
            else if (a[m] < T) l = m + 1;
            else r = m - 1;
        }

        int count = 0;
        for (l = m; l >= 0 && a[l] == T; l--) count++;
        for (r = m + 1; r < a.length && a[r] == T; r++) count++;
        return count;
    }
    // Solution 1 ends here

    // Solution 2 starts here
    // TC: O(2*log(n))
    // SC: O(1)
    public int totalOccurrence2(int[] a, int T) {
        if (a == null || a.length == 0) return 0;
        int l = firstOccur(a, T);
        if (l == -1) return 0; // T do not exist
        return lastOccur(a, T) - l + 1;
    }

    private int firstOccur(int[] a, int T) {
        int l = 0, r = a.length - 1;
        while (l < r - 1) {
            int m = l + (r - l) / 2;
            if (a[m] < T) l = m;
            else r = m;
        }
        return a[l] == T ? l : a[r] == T ? r : -1;
    }

    private int lastOccur(int[] a, int T) {
        int l = 0, r = a.length - 1;
        while (l < r - 1) {
            int m = l + (r - l) / 2;
            if (a[m] <= T) l = m;
            else r = m;
        }
        return a[r] == T ? r : a[l] == T ? l : -1;
    }
    // Solution 2 ends here
    public static void main(String[] args) {
        TotalOccurrence to = new TotalOccurrence();
        System.out.println(to.totalOccurrence(new int[]{1, 2, 2, 2, 4, 5, 8, 13, 13}, 13) == 2);
        System.out.println(to.totalOccurrence2(new int[]{1, 2, 2, 2, 4, 5, 8, 13, 13}, 13) == 2);
    }
}
