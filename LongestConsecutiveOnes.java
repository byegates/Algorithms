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
        int slow = 0;
        for (int fast = 0; fast < A.length; fast++) {
            if (A[fast] == 1 || k-- > 0) // keep moving while its 1 or k is greater than 0
                longest = Math.max(longest, fast - slow + 1);
            while (k == -1) if (A[slow++] == 0) k++; // k == -1 so we move slow to after one 0(throw one 0 out)
        }
        return longest;
    } // TC: O(n) n is length of A, SC: O(1)

    public static int solution2(int[] A, int k) {
        int longest = 0;
        int slow = 0;
        for (int fast = 0; fast < A.length; fast++) {
            if (A[fast] == 0) // When the Snake eats a 0, reduce k first, when k is done, move slow until after one 0
                if (k > 0) k--;
                else while (A[slow++] != 0) ; // move slow to after 0, when never fast eats one 0, slow throw one 0 out
            longest = Math.max(longest, fast - slow + 1); // slow is always at the right position, so we always update
        }
        return longest;
    }

    public static void main(String[] args) {
        int[] A1 = new int[]{1,1,0,0,1,1,1,0,0,0};
        int[] A2 = new int[]{1,1,0,0,1,1,1,0,0,0};
        int[] A3 = new int[]{1,0,0,1,1,1,0,1,0,1,1,0,1,0,1,1,1,0,1,0,1,1,1,0,0,0,1,1,1,1,1,1,0,0,1,1,1,0,0,1,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,1,0,1,0,0,0,1,1,0,1,0,1,0,0,1,1,0,0,1,0,1,0,0,0,1,1,0,0,1,1,1,1,1,1,1,0,1,1,1,0,1,0,0,0,1,0,1,1,1,1,0,0,1,0,1,0,1,1,0,1,0,1,1,0,0,1,1,0,1,1,0,1,1,0,0,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1,1,0,1,0,1,0,1,1,0,0,1,0,0,0,0,0,0,1,0,0,0,1,0,0,1,0,0,0,0,0,1,1,0,0,0,0,0,1,0,1,1,0,0,0,1,1,1,0,0,1,1,1,0,1,1,1,0,0,0,0,0,0,1,0,1,0,1,0,1,1,0,1,0,0,0,1,0,1,1,1,0,1,1,1,0,1,0,1,1,0,1,1,1,1,0,0,0,1,1,1,1,0,1,1,1,0,1,0,0,0,0,0,1,1,1,0,1,0,0,0,1,0,0,1,1,0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,0,0,0,0};
        System.out.println(longestConsecutiveOnes(A1, 2));
        System.out.println(longestConsecutiveOnes(A2, 0));
        System.out.println(longestConsecutiveOnes(A3, 76));
        System.out.println(solution2(A1, 2));
        System.out.println(solution2(A2, 0));
        System.out.println(solution2(A3, 76));
    }
}
