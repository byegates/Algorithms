/*
        Given one stack with integers, sort it with two additional stacks (total 3 stacks).

        After sorting the original stack should contain the sorted integers and
        from top to bottom the integers are sorted in ascending order.

        Assumptions:
        The given stack is not null.
        The time complexity should be O(n log n).
*/


import java.util.LinkedList;

public class SortNumbersInThreeStacks {
    public void sort(LinkedList<Integer> s1) { // 2.5*nlogn ==> O(nlogn)
        LinkedList<Integer> s2 = new LinkedList<>();
        LinkedList<Integer> s3 = new LinkedList<>();
        sort(s1, s2, s3, s1.size()); // sort top s1.size() values in s1 in ascending order from top to bottom
    }

    private void sort(LinkedList<Integer> s1, LinkedList<Integer> s2, LinkedList<Integer> s3, int len) {
        if (len <= 1) return;
        int mid1 = len / 2, mid2 = len - mid1;

        for (int i = 0; i < mid1; i++)
            s2.offerFirst(s1.pollFirst());

        // the key is to sort s2 and s1, the sequence of 2nd and 3rd stack doesn't matter
        // sort(s2, s3, s1, mid1); will also work
        // sort(s1, s3, s2, mid2); will also work
        sort(s2, s1, s3, mid1); // sort top mid1 values in s2 in ascending order from top to bottom
        sort(s1, s2, s3, mid2);// sort top mid2 values in s1 in ascending order from top to bottom

        int i = 0, j = 0;

        while (i < mid1 && j < mid2)
            if (s2.peekFirst() < s1.peekFirst()) {
                s3.offerFirst(s2.pollFirst());
                i++;
            } else {
                s3.offerFirst(s1.pollFirst());
                j++;
            }

        for (; i < mid1; i++) s3.offerFirst(s2.pollFirst()); // special use of for loop in order to code this in one line
        for (; j < mid2; j++) s3.offerFirst(s1.pollFirst());

        for (int k = 0; k < len; k++) s1.offerFirst(s3.pollFirst());
    }

    public static void main(String[] args) {
        SortNumbersInThreeStacks sn3 = new SortNumbersInThreeStacks();

        int[] res = new int[]{3, 8, 4, 5, 1, 7, 2, 6};
        LinkedList<Integer> s1 = new LinkedList<>();

        for (int v : res)
            s1.offerFirst(v); // Add test data from int[] res to stack

        sn3.sort(s1);

        while (!s1.isEmpty())
            System.out.println(s1.pollFirst()); // print out sorted data from stack
    }
}
