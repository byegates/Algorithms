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
    public void sort(LinkedList<Integer> s1) {
        LinkedList<Integer> s2 = new LinkedList<>();
        LinkedList<Integer> s3 = new LinkedList<>();
        sort(s1, s2, s3, s1.size());
    }

    private void sort(LinkedList<Integer> s1, LinkedList<Integer> s2, LinkedList<Integer> s3, int len) {
        if (len <= 1) return;
        int mid1 = len / 2, mid2 = len - mid1;

        for (int i = 0; i < mid1; i++)
            s2.offerFirst(s1.pollFirst());

        sort(s2, s3, s1, mid1);
        sort(s1, s3, s2, mid2);

        int i = 0, j = 0;

        while (i < mid1 && j < mid2 && !s1.isEmpty() && !s2.isEmpty()) // s1 and s2 will not be empty, added only to avoid IDE warning
            if (s2.peek() < s1.peek()) {
                s3.offerFirst(s2.pollFirst());
                i++;
            } else {
                s3.offerFirst(s1.pollFirst());
                j++;
            }

        while (i++ < mid1) s3.offerFirst(s2.pollFirst());
        while (j++ < mid2) s3.offerFirst(s1.pollFirst());

        for (int k = 0; k < len; k++) s1.offerFirst(s3.pollFirst());
    }

    public static void main(String[] args) {
        SortNumbersInThreeStacks sn3 = new SortNumbersInThreeStacks();
        int[] res = new int[]{3, 7, 5, 1, 9, 8, 6, 2, 4};
        LinkedList<Integer> s1 = new LinkedList<>();

        for (int v : res) s1.offerFirst(v);

        sn3.sort(s1);

        while (!s1.isEmpty()) System.out.println(s1.pollFirst());
    }
}
