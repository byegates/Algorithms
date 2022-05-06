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
            if (A[m] < A[r] || A[m] < A[l]) { // mid is in the right ascending segment
                if (target > A[m] && target <= A[r]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            } else if (A[m] > A[l] || A[m] > A[r]) { // mid is in the left ascending segment
                if (target < A[m] && target >= A[l]) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            } else { // a[m] == a[l] == a[r], we can't decide which side is mid in, we can only decrease 1 linear-ly
                // a[m] >= a[r] && a[m] >= a[l]
                // a[m] <= a[r] && a[m] <= a[l]
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
        //System.out.println(sol.search(new int[]{909, 910, 911, 914, 915, 916, 918, 919, 920, 921, 922, 923, 924, 927, 928, 929, 930, 931, 932, 933, 934, 935, 936, 939, 940, 941, 942, 943, 944, 945, 946, 947, 948, 950, 951, 952, 953, 954, 955, 956, 958, 959, 960, 961, 962, 964, 965, 966, 967, 968, 969, 970, 971, 973, 974, 976, 977, 978, 979, 980, 981, 907, 908}, 932));
        System.out.println(sol.search(new int[]{552, 553, 555, 556, 562, 563, 564, 565, 566, 567, 570, 571, 572, 573, 574, 577, 579, 580, 581, 582, 584, 585, 587, 588, 591, 592, 596, 604, 611, 612, 419, 420, 421, 423, 424, 426, 428, 430, 433, 439, 440, 441, 442, 445, 446, 447, 448, 451, 454, 461, 462, 464, 467, 470, 473, 476, 479, 482, 483, 489, 490, 491, 495, 496, 499, 501, 502, 505, 508, 510, 511, 512, 514, 515, 518, 520, 521, 532, 533, 534, 537, 538, 539, 541, 544, 545, 548, 549}, 564));
        System.out.println(sol.search(new int[]{3, 1, 1}, 1)); // 1
        System.out.println(sol.search(new int[]{1, 1, 2, 2, -1, -1, 0, 0, 1, 1}, 0));
        System.out.println(sol.search(new int[]{3, 4, 5, 1, 2}, 4));
        System.out.println(sol.search(new int[]{1, 2, 3, 4, 5}, 3));
        System.out.println(sol.search(new int[]{3, 5, 6, 1, 2}, 4));
    }
}
