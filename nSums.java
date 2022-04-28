import java.util.*;

public class nSums {
    /*
    2 Sum Closest
    Find the pair of elements in a given array that sum to a value that is closest to the given target number.
    Return the values of the two numbers.
    Assumptions
    The given array is not null and has length of at least 2
    Examples
    A = {1, 4, 7, 13}, target = 7, closest pair is 1 + 7 = 8, return [1, 7].

    Analysis
    TC: O(nlogn) dominated by sort
    SC: O(log(n)) by primitive sort(something like quick sort)
    Without considering sort:
    TC: O(n)
    SC: O(1)
     */
    public List<Integer> closest(int[] a, int T) {
        List<Integer> res = new ArrayList<>();
        Arrays.sort(a);
        int diff = Integer.MAX_VALUE;
        for (int i = 0, j = a.length - 1; i < j;) {
            int sum = a[i] + a[j];
            int curDiff = Math.abs(T - sum);
            if (curDiff < diff) {
                res = Arrays.asList(a[i], a[j]);
                diff = curDiff;
            }
            if (sum == T) return res;
            else if (sum < T) i++;
            else j--;
        }

        return res;
    }

    /*
    2 Sum Smaller
    Determine the number of pairs of elements in a given array that sum to a value smaller than the given target number.
    Assumptions
    The given array is not null and has length of at least 2
    Examples
    A = {1, 2, 2, 4, 7}, target = 7, number of pairs is 6({1,2}, {1, 2}, {1, 4}, {2, 2}, {2, 4}, {2, 4})

    Analysis:
    Sort will dominate the TC and SC:
    TC: O(nlogn)
    SC: O(log(n))
    Without considering sort:
    TC: O(n)
    SCL O(1)
     */
    public int smallerPairs(int[] a, int T) {
        Arrays.sort(a);
        int res = 0;

        for (int i = 0, j = a.length - 1; i < j;) {
            int sum = a[i] + a[j];
            if (sum < T) {
                res += j - i;
                i++;
            } else j--;
        }

        return res;
    }


    /*
    Given two arrays A and B, determine whether there exists a pair of elements, one drawn from each array,
    that sums to the given target number.
    Assumptions
    The two given arrays are not null and have length of at least 1
    Examples
    A = {3, 1, 5}, B = {2, 8}, target = 7, return true(pick 5 from A and pick 2 from B)
    A = {1, 3, 5}, B = {2, 8}, target = 6, return false

    Analysis:
    assume m is the smaller length of a and b, and n is the larger
    TC: O(m + n)
    SC: O(m)
     */
    public boolean existSum(int[] a, int[] b, int T) {
        swapArray(a, b);
        Set<Integer> set = new HashSet<>();
        for (int x : a) set.add(x);
        for (int x : b)
            if (set.contains( T - x)) return true;
        return false;
    }

    private void swapArray(int[] a, int[] b) {
        if (a.length > b.length) { // swap and make sure 'a' is always the smaller size array
            int[] tmp = a;
            a = b;
            b = tmp;
        }
    }

    /*
    3 Sum 3 Arrays
    Given three arrays, determine if a set can be made by picking one element from each array that sums to the given target number.
    Assumptions
    The three given arrays are not null and have length of at least 1
    Examples
    A = {1, 3, 5}, B = {8, 2}, C = {3}, target = 14, return true(pick 3 from A, pick 8 from B and pick 3 from C)

    Analysis:
    let l, m, n be the length of a, b, c in ascending order
    TC: O(n + m * l)
    SC: O(m + n)
     */
    public boolean exist(int[] a, int[] b, int[] c, int t) {
        swapArray(a, b); // 'a' is shorter
        swapArray(a, c); // 'a' is shortest of all 3
        swapArray(b, c); // 'b' is shorter vs 'c'

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int x : c) set1.add(x); // add the longest array to set to save time
        for (int x2 : b)
            if (set2.add(x2)) // de-dup elements in b
                for (int x3 : a)
                    if (set1.contains(t - x2 - x3))
                        return true;

        return false;
    }

    public static void main(String[] args) {
        nSums ns = new nSums();
        System.out.println(ns.closest(new int[]{1, 4, 7, 13}, 7));
    }

}
