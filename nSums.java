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
     */
    public List<Integer> closest(int[] a, int T) {
        List<Integer> res = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
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

    public static void main(String[] args) {
        nSums ns = new nSums();
        System.out.println(ns.closest(new int[]{1, 4, 7, 13}, 7));
    }

}
