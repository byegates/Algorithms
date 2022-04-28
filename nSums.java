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


    public static void main(String[] args) {
        nSums ns = new nSums();
        System.out.println(ns.closest(new int[]{1, 4, 7, 13}, 7));
    }

}
