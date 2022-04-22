/*
        Given an array A[0]...A[n-1] of integers, find out the length of the longest ascending subsequence.

        Assumptions
        A is not null

        Examples
        Input: A = {5, 2, 6, 3, 4, 7, 5}
        Output: 4
        Because [2, 3, 4, 5] is the longest ascending subsequence.
*/

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LongestAscendingSubsequence {
    // method1 TC: O(n^2), SC: O(n)
    public int longest(int[] arr) {
        if (arr.length == 0) return 0;
        int[] M = new int[arr.length]; // longest sub-seq ending at cur idx
        Arrays.fill(M, 1);
        int res = 1;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 0; j < i; j++)
                if (arr[j] < arr[i] & M[j] + 1 > M[i])
                    M[i] = M[j] + 1;
            if (M[i] > res) res = M[i];
        }
        return res;
    }
    // method 1 ends here

    // method 2 TC: O(nlogn), SC: O(n)
    public int las2(int[] arr) {
        if (arr.length == 0) return 0;
        int[] M = new int[arr.length + 1]; // longest seb-seq
        int size = 1;

        M[1] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            int idx = largestSmaller(M, 1, size, arr[i]);
            if (idx == size) M[++size] = arr[i];
            else M[idx + 1] = arr[i];
        }

        return size;
    }

    public int largestSmaller(int[] M, int l, int r, int T) {
        while (l < r - 1) {
            int m = l + (r - l) / 2;
            if (M[m] < T) l = m;
            else r = m - 1;
        }
        return M[r] < T ? r : M[l] < T ? l : 0;
    }
    // method 2 ends here

    // return one of the seq-sub
    public int[] las(int[] a) { // TC: O(n^2), SC: O(n)
        if (a.length == 0) return new int[0];
        int[] M = new int[a.length]; // to store longest asc sub-seq ending at cur idx
        int[] pre = new int[a.length]; // to store pre idx of longest asc sub-seq ending at cur idx
        Arrays.fill(M, 1);
        Arrays.fill(pre, -1);

        int max = 1, maxIdx = 0;
        for (int i = 1; i < a.length; i++)
            for (int j = 0; j < i; j++) {
                if (a[j] < a[i])
                    if (M[j] + 1 > M[i]) {
                        pre[i] = j; // remember the pre idx of cur idx that forms the longest asc sub-seq ending at cur idx
                        M[i] = M[j] + 1;
                    }
                if (M[i] > max) {
                    maxIdx = i; // remember the ending idx of the longest asc sub-seq
                    max = M[i];
                }
            }

        int[] res = new int[max];
        for (int endIdx = maxIdx; endIdx != -1; endIdx = pre[endIdx])
            res[--max] = a[endIdx];

        return res;
    }
    // return one of the sub-seq method ends here

    // method 3, simplified binary search approach
    public int las3(int[] arr) {
        int len = 0;
        int[] tails = new int[arr.length]; // smallest val of asc sub seq at each len
        for (int x : arr) {
            int l = 0, r = len;
            while (l != r) { // binary search to narrow down where should we put x
                int m = l + (r - l) / 2;
                if (tails[m] < x) l = m + 1; // The right of the smallest element will be where cur number x should be
                else r = m;
            }
            tails[l] = x;
            if (l == len) ++len; // all values are smaller, append
        }
        return len;
    }
    // method 3 ends here

    // method 4, using arrayList
    public int las4(int[] arr) {
        int len = 0;
        List<Integer> tails = new ArrayList<>();
        for (int x : arr) {
            int l = 0, r = len;
            while (l != r) {
                int m = l + (r - l) / 2;
                if (tails.get(m) < x) l = m + 1;
                else r = m;
            }
            if (l == len) {
                tails.add(x);
                len++;
            } else tails.set(l, x);
        }
        return len;
    }

    /*
     Largest Set Of Points With Positive Slope
     Given an array of 2D coordinates of points (all the coordinates are integers),
     find the largest number of points that can form a set such that any pair of points in the set can form a line with positive slope.
     Return the size of such a maximal set.
     Assumptions
     The given array is not null
     Note: if there does not even exist 2 points can form a line with positive slope, should return 0.
     Examples
     <0, 0>, <1, 1>, <2, 3>, <3, 3>, the maximum set of points are {<0, 0>, <1, 1>, <2, 3>}, the size is 3.
    */

    public int las(Point[] points) {
//        Arrays.sort(points, new Comparator<Point>() {
//            @Override
//            public int compare(Point p1, Point p2) {
//                return p1.x != p2.x ? p1.x - p2.x : p2.y - p1.y;
//            }
//        });
        // sort by x asc and then y dsc, so all points with same x with be counted as 1
        Arrays.sort(points, (p1, p2) -> p1.x != p2.x ? p1.x - p2.x : p2.y - p1.y);

        int len = 0;
        int[] tails = new int[points.length];
        for (Point p : points) {
            int l = 0, r = len;
            while (l != r) {
                int m = l + (r - l) / 2;
                if (tails[m] < p.y) l = m + 1;
                else r = m;
            }
            tails[l] = p.y;
            if (l == len) ++len;
        }

        return len == 1 ? 0 : len;
    }

    public static void main(String[] args) {
        LongestAscendingSubsequence las = new LongestAscendingSubsequence();
        int[] a1 = new int[]{123,236,227,258,138,441,496,479,124,389,84,104,143,24,363,379,304,317,10,121,179,179,267,94,326,225,255,179,176,236,191,369,354,223,262,355,178,493,447,352,24,75,128,461,108,277,31,188,335,490,246,226,344,204,412,400,184,428,42,44,167,453,452,261,394,69,284,316,245,471,278,4,216,157,127,15,431,187,57,327,415,117,136,274,313,401,242,286,158,420,177,389,34,157,482,278,66,325,189,250,65,33,38,469,400,235,463,371,212,414,124,119,498,42,200,281,282,407,269,384,173,457,221,178,435,233,382,332,445,434,345,377,407,499,352,485,70,439,73,194,376,434,125,362,476,440,326,50,402,250,333,122,340,93,135,189,471,441,166,489,143,265,284,41,318,459,324,331,206,125,48,370,314,312,474,337,283,168,273,499,333,272,307,376,462,187,200,495,143,1,380,271,218,284,30,350,490,23,59,302,91,190,369,310,157,99,33,422,108,243,314,117,417,188,443,254,161,36,59,70,431,301,344,102,204,332,42,438,241,180,319,297,248,17,256,364,161,354,176,350,248,438,489,33,477,260,329,1,128,129,379,27,500,232,29,365,276,44,290,359,129,291,294,35,5,47,149,368,232,291,467,75,74,9,497,225,243,103,483,22,2,29,148,470,31,149,43,86,28,386,451,94,190,206,6,400,188,468,309,316,64,50,465,274,130,44,200,340,398,111,64,47,373,379,87,482,175,452,257,249,383,314,233,197,300,2,334,409,433,37,54,156,412,96,162,469,354,440,263,154,254,12,208,401,408,37,194,230,137,301,45,228,130,65,322,54,160,351,164,301,126,22,106,208,480,239,34,280,424,3,411,138,53,44,495,417,105,325,410,197,376,442,158,352,79,68,455,278,406,10,166,256,471,274,71,209,442,282,229,290,227,171,317,170,390,260,35,459,168,418,411,43,442,178,216,88,345,483,118,215,352,436,195,239,175,416,48,486,86,292,368,52,459,366,111,354,260,459,185,82,13,344,22,86,425,442,327,353,258,362,367,91,347,28,317,463,267,167,351,250,77,499,484,208,345,466,13,476,236,231,381,309,392,122,264,411,43,13,334,452,398,438,279,273,262,493,457,486,37,281,370,18,358,316,72,239,481,411,99,81,267,144,107,35,149,11,12,101,340,351,182,135,250,36,22,155,104,332,179,425,100,119,44,154,161,486,275,337,313,422,179,169,103,42,483,61,93,398,296,96,21,51,415,286,491,112,54,179,251,464,85,301,87,345,469,45,152,356,194,406,226,355,314,30,340,475,474,242,5,299,210,177,409,434,295,235,395,415,445,467,315,461,341,3,459,237,124,394,203,271,312,184,269,175,446,187,101,253,233,275,261,493,458,73,20,253,45,190,373,276,325,91,128,413,325,382,448,139,228,137,279,100,59,491,1,54,161,482,331,121,61,97,89,280,18,192,274,26,221,301,124,16,459,460,353,422,411,470,119,-452};
        int[] a2 = new int[] {5, 2, 6, 3, 4, 7, 5};

        System.out.println(las.longest(a1)); // 45
        System.out.println(las.longest(a2)); // 4
        System.out.println(las.longest(new int[]{})); // 0
        System.out.println(las.longest(new int[]{1})); // 1
        System.out.println(las.longest(new int[]{5,5,5})); // 1
        System.out.println();

        System.out.println(las.las2(a1)); // 45
        System.out.println(las.las2(a2)); // 4
        System.out.println(las.las2(new int[]{})); // 0
        System.out.println(las.las2(new int[]{1})); // 1
        System.out.println(las.las2(new int[]{5,5,5})); // 1
        System.out.println();

        System.out.println(las.las3(a1)); // 45
        System.out.println(las.las3(a2)); // 4
        System.out.println(las.las3(new int[]{})); // 0
        System.out.println(las.las3(new int[]{1})); // 1
        System.out.println(las.las3(new int[]{5,5,5})); // 1
        System.out.println();

        System.out.println(las.las4(a1)); // 45
        System.out.println(las.las4(a2)); // 4
        System.out.println(las.las4(new int[]{})); // 0
        System.out.println(las.las4(new int[]{1})); // 1
        System.out.println(las.las4(new int[]{5,5,5})); // 1
        System.out.println();

        System.out.println(Arrays.toString(las.las(new int[]{5, 5, 5}))); // [5]
        Point[] points = new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 3), new Point(3, 3), };
        System.out.println(las.las(points)); // 3
        Point[] points2 = new Point[]{new Point(1, 2), new Point(1, 3)};
        System.out.println(las.las(points2)); // 0
    }
}
