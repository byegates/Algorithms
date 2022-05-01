public class ArrayHopper {
    /*
    88. Array Hopper I
    Given an array A of non-negative integers, you are initially positioned at index 0 of the array.
    A[i] means the maximum jump distance from that position (you can only jump towards the end of the array).
    Determine if you are able to reach the last index.

    Assumptions
    The given array is not null and has length of at least 1.
    Examples
    {1, 3, 2, 0, 3}, we are able to reach the end of array(jump to index 1 then reach the end of the array)
    {2, 1, 1, 0, 2}, we are not able to reach the end of array
     */
    public boolean canJumpFromStart(int[] a) {
        int len = a.length;
        boolean[] dp = new boolean[len]; // can you jump to this index from the beginning step
        dp[0] = true;

        for (int i = 0; i < len; i++)
            for (int j = Math.min(len - 1, a[i] + i); dp[i] && j > i; j--) {
                dp[j] = true;
                if (j == len - 1) return true; // early return for better performance, not mandatory
            }

        return dp[len - 1];
    }
    /*
     89. Array Hopper II
     Given an array A of non-negative integers, you are initially positioned at index 0 of the array.
     A[i] means the maximum jump distance from index i (you can only jump towards the end of the array).
     Determine the minimum number of jumps you need to reach the end of array.
     If you can not reach the end of the array, return -1.

    Assumptions
    The given array is not null and has length of at least 1.
    Examples
    {3, 3, 1, 0, 4}, the minimum jumps needed is 2 (jump to index 1 then to the end of array)
    {2, 1, 1, 0, 2}, you are not able to reach the end of array, return -1 in this case.
    */
    public int minJump(int[] A) { // TC: O(n^2), SC: O(n), better
        int len = A.length;
        int[] dp = new int[len];

        for (int i = len - 2; i >= 0; i--) {
            dp[i] = -1;
            for (int j = Math.min(len - 1, i + A[i]); j >= i; j--)
                if (dp[j] != -1 && (dp[i] == -1 || dp[j] + 1 < dp[i]))
                    dp[i] = dp[j] + 1;
        }

        return dp[0];
    }

    public int minJumpFromStart(int[] a) { // TC: O(n^2), SC: O(n), not better as first one
        int len = a.length;
        int[] dp = new int[len];

        for (int i = 1; i < len; i++) {
            dp[i] = -1;
            for (int j = i - 1; j >= 0; j--)
                if (dp[j] != -1 && a[j] + j >= i && (dp[i] == -1 || dp[j] + 1 < dp[i]))
                    dp[i] = dp[j] + 1;
        }

        return dp[len - 1];
    }

    public int greedy(int[] A) { // TC: O(n), SC: O(1)
        if (A.length <= 1) return 0;

        int jump = 0; // # of jumps performed
        int cur = 0; // furthest point reachable after current jump
        int nxt = 0; // furthest after next jump

        for (int i = 0; i < A.length; i++) {
            if (i > cur) {
                jump++; // if current index(i) is not reached yet, one more jump
                if (cur == nxt) return -1; // no progress after 1 jump, no more jumps
                cur = nxt;
            }
            nxt = Math.max(nxt, A[i] + i);
        }

        return jump;
    }

    public static void main(String[] args) {
        ArrayHopper ah = new ArrayHopper();
        System.out.println(ah.greedy(new int[]{5,6,0,0,0,10,0,0,0})); // 2
        System.out.println(ah.greedy(new int[]{2, 1, 1, 0, 2})); // -1
    }
}