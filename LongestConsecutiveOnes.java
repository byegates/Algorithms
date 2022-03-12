//Given an array of integers that contains only 0s and 1s and a positive integer k,
// you can flip at most k 0s to 1s, return the longest subarray that contains only integer 1 after flipping.
//
//        Assumptions:
//        1. Length of given array is between [1, 20000].
//        2. The given array only contains 1s and 0s.
//        3. 0 <= k <= length of given array.
//
//        Example 1:
//        Input: array = [1,1,0,0,1,1,1,0,0,0], k = 2
//        Output: 7
//
//        Explanation: flip 0s at index 2 and 3, then the array becomes [1,1,1,1,1,1,1,0,0,0],
//        so that the length of the longest subarray that contains only integer 1 is 7.
//
//        Example 2:
//        Input: array = [1,1,0,0,1,1,1,0,0,0], k = 0
//        Output: 3
//
//        Explanation: k is 0, so you can not flip any 0 to 1,
//        then the length of the longest subarray that contains only integer 1 is 3.

public class LongestConsecutiveOnes {
    public static int longestConsecutiveOnes(int[] A, int k) {
        int longest = 0;
        int end = 0;
        for (int start = 0; start < A.length; start++) {
            if (A[start] == 1 || k-- > 0) longest = Math.max(longest, start - end + 1);
            while (k == -1) if (A[end++] == 0) k++;
        }
        return longest;
    } // TC: O(n) n is length of A, SC: O(1)

    public static void main(String[] args) {
        System.out.println(longestConsecutiveOnes(new int[]{1,1,0,0,1,1,1,0,0,0}, 2));
        System.out.println(longestConsecutiveOnes(new int[]{1,1,0,0,1,1,1,0,0,0}, 0));
        System.out.println(longestConsecutiveOnes(new int[]{1,0,0,1,1,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,1,0,0,0,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,0,1,0,0,0,1,1,0,1,0,1,0,0,1,1,0,0,1,0,1,0,0,0,1,1,0,0,1,1,1,1,1,1,1,0,1,1,1,0,1,0,0,0,1,0,1,1,1,1,0,0,1,0,1,0,1,1,0,1,0,1,1,0,0,1,1,0,1,1,0,1,1,0,0,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1,1,0,1,0,1,0,1,1,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,1,1,0,0,0,1,1,1,0,0,1,1,1,0,1,1,1,0,0,0,0,0,0,1,0,1,0,1,0,1,1,0,1,0,0,0,1,0,1,1,1,0,1,1,1,0,1,0,1,1,0,1,1,1,1,0,0,0,1,1,1,1,0,1,1,1,0,1,0,0,0,0,0,1,1,1,0,1,0,0,0,1,0,0,1,1,0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,0,0,0,0}, 76));
    }
}
