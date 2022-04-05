/*
        Given a non-negative integer array representing the heights of a list of adjacent bars.
        Suppose each bar has a width of 1. Find the largest rectangular area that can be formed in the histogram.

        Assumptions
        The given array is not null or empty

        Examples
        { 2, 1, 3, 3, 4 }, the largest rectangle area is 3 * 3 = 9(starting from index 2 and ending at index 4)
*/


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class LargestRectangleInHistogram {
    public int largest(int[] h) { // TC: O(n), SC: O(n)
        int max = 0;
        int[] stack = new int[h.length + 1];
        int top = -1;

        for (int i = 0; i <= h.length; i++) {
            int cur = i == h.length ? 0 : h[i];
            while (top > -1 && cur < h[stack[top]]) {
                int height = h[stack[top--]];
                int left = top == -1 ? 0 : stack[top] + 1;
                max = Math.max(max, height * (i - left));
            }
            stack[++top] = i;
        }
        return max;
    }

    public int largest3(int[] A) { // TC: O(n), SC: O(n)
        int res = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i <= A.length; i++) {
            int cur = i == A.length ? 0 : A[i];
            while (!stack.isEmpty() && A[stack.peekFirst()] >= cur) {
                int height = A[stack.pollFirst()];
                int left = stack.isEmpty() ? 0 : stack.peekFirst() + 1;
                res = Math.max(res, height * (i - left));
            }
            stack.offerFirst(i);
        }
        return res;
    }

    public int largest2(int[] A) { // TC: O(n), SC: O(n)
        int max = 0;
        int[] lessFromLeft = new int[A.length]; // idx of the first bar the left that is lower than current
        int[] lessFromRight = new int[A.length]; // idx of the first bar the right that is lower than current
        lessFromRight[A.length - 1] = A.length;
        lessFromLeft[0] = -1;

        for (int i = 1; i < A.length; i++) {
            int p = i - 1;
            while (p >= 0 && A[p] >= A[i]) p = lessFromLeft[p];
            lessFromLeft[i] = p;
        }

        for (int i = A.length - 2; i >= 0; i--) {
            int p = i + 1;
            while (p < A.length && A[p] >= A[i]) p = lessFromRight[p];
            lessFromRight[i] = p;
        }

        for (int i = 0; i < A.length; i++) {
            max = Math.max(max, A[i] * (lessFromRight[i] - lessFromLeft[i] - 1));
        }
        return max;
        }


    public static void main(String[] args) {
        LargestRectangleInHistogram lrh = new LargestRectangleInHistogram();
        System.out.println(lrh.largest(new int[]{2,1,5,6,2,3})); // 10
        System.out.println(lrh.largest(new int[]{2,1,3,3,4})); // 9
        System.out.println(lrh.largest(new int[]{0,1,0,1})); // 1
        System.out.println(lrh.largest2(new int[]{0,1,0,1})); // 3
    }

}
