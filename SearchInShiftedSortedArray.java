public class SearchInShiftedSortedArray {
    /*
    22. Search In Shifted Sorted Array II
    Given a target integer T and an integer array A, A is sorted in ascending order first,
    then shifted by an arbitrary number of positions.
    For Example, A = {3, 4, 5, 1, 2} (shifted left by 2 positions).
    Find the index i such that A[i] == T or return -1 if there is no such index.
    Assumptions
    There could be duplicate elements in the array.
    Return the smallest index if target has multiple occurrence.
    Examples
    A = {3, 4, 5, 1, 2}, T = 4, return 1
    A = {3, 3, 3, 1, 3}, T = 1, return 3
    A = {3, 1, 3, 3, 3}, T = 1, return 1
    Corner Cases
    What if A is null or A is of zero length? We should return -1 in this case.
     */
    public int search(int[] A, int target) {
        if (A == null || A.length == 0) return -1;
        if (A[0] == target) return 0; // this is quite important, ruling this out will help with binary search later
        int l = 0, r = A.length - 1;
        while(l <= r) {
            int m = (l + r) / 2;
            if (A[m] == target) { // with a[0] == target returned earlier, we can do binary search for the 1st occurrence now
                return firstOccur(A, target, 1, m);
            }
            if (A[m] < A[r] || A[m] < A[l]) { // mid is in the left ascending segment
                if (target > A[m] && target <= A[r]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            } else if (A[m] > A[l] || A[m] > A[r]) { // mid is in the right ascending segment
                if (target < A[m] && target >= A[l]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            } else { // mid-value is equal to either left or right boundary, we can't decide which side is mid in, we can only decrease 1 linear-ly
                r--;
            }
        }

        return -1;
    }

// target can be 0, -1, 2 in below case, either way, there's no problem;
// 11112222222|-1-1 0000000]1111
// l                  m
// 1[1112222222|-1-1 0 0 0]
//                       m
// |-1-1-1-1-1 0 0 0
//                 m
    private int firstOccur(int[] a, int t, int l, int r) {
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] == t) r = m;
            else l = m + 1;
        }
        return l;
    }

    /*
    23. Shift Position (find the index of the smallest element's index)
    Given an integer array A, A is sorted in ascending order first then shifted by an arbitrary number of positions,
    For Example, A = {3, 4, 5, 1, 2} (shifted left by 2 positions). Find the index of the smallest number.
    Assumptions
    There are no duplicate elements in the array
    Examples
    A = {3, 4, 5, 1, 2}, return 3
    A = {1, 2, 3, 4, 5}, return 0
    Corner Cases
    What if A is null or A is of zero length? We should return -1 in this case.
     */
    public int smallest(int[] a) {
        if (a == null || a.length == 0) return -1;
        int l = 0, r = a.length - 1;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] > a[r]) l = m + 1;
            else r = m;
        }
        return l;
    }
    /*
    21. Search In Shifted Sorted Array I
    Given a target integer T and an integer array A, A is sorted in ascending order first,
    then shifted by an arbitrary number of positions.
    For Example, A = {3, 4, 5, 1, 2} (shifted left by 2 positions).
    Find the index i such that A[i] == T or return -1 if there is no such index.
    Assumptions
    There are no duplicate elements in the array.
    Examples
    A = {3, 4, 5, 1, 2}, T = 4, return 1
    A = {1, 2, 3, 4, 5}, T = 4, return 3
    A = {3, 5, 6, 1, 2}, T = 4, return -1
    Corner Cases
    What if A is null or A is of zero length? We should return -1 in this case.
     */
    public int search1(int[] a, int t) {
        if (a == null || a.length == 0) return -1;
        int l = 0, r = a.length - 1;
        while (l <= r) {
            int m = l + (r - l) / 2;
            if (a[m] == t) return m;
            if (a[m] > a[r])
                if (a[m] > t && t >= a[l]) r = m - 1;
                else l = m + 1;
            else
            if (a[m] < t && t <= a[r]) l = m + 1;
            else r = m - 1;

        }
        return -1;
    }

    public static void main(String[] args) {
        SearchInShiftedSortedArray sol = new SearchInShiftedSortedArray();
        System.out.println(sol.search(new int[]{3, 1, 1}, 1));
        System.out.println(sol.search(new int[]{1, 1, 2, 2, -1, -1, 0, 0, 1, 1}, 0));
        System.out.println(sol.search(new int[]{3, 4, 5, 1, 2}, 4));
        System.out.println(sol.search(new int[]{1, 2, 3, 4, 5}, 3));
        System.out.println(sol.search(new int[]{3, 5, 6, 1, 2}, 4));
    }
}
