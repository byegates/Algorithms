import java.util.LinkedList;

public class SortWithTwoStacks {
    // using inserting sort (插入排序)
    // s2 will always have values sorted descending from top (ascending from bottom)
    // first step s2 is empty, and we move 1 element to s2, which is for sure sorted
    // starting second step, we poll an int from s1 first (poll value from s1 is always the first step of each iteration)
    // then we poll all values in s2 that's larger than the value we just polled, and push them into s1
    // then we offer(insert) the cur value polled at the start of s1 into s2, now this value is at the right position (exactly like insertion sort)
    //
    // Now we resume polling element from s1 to s2, all elements we just polled from s2 to s1 will be larger than current top of s2 for sure
    // so these will be put back to s2 in the right sequence, everything in s2 is sorted at all time
    //
    // After s1 is done, s2 will have all elements sorted ascending from bottom, then we move everything from s2 to s1,
    // s1 will have everything ascending from top
    public void sort(LinkedList<Integer> s1) {
        if (s1 == null || s1.size() <= 1) return;
        LinkedList<Integer> s2 = new LinkedList<>();

        while (!s1.isEmpty()) {
            int cur = s1.pollFirst();

            while (!s2.isEmpty() && s2.peekFirst() > cur)
                s1.offerFirst(s2.pollFirst());

            s2.offerFirst(cur);
        }

        while (!s2.isEmpty()) s1.offerFirst(s2.pollFirst());

    }

    public static void main(String[] args) {
        SortWithTwoStacks sol = new SortWithTwoStacks();
        LinkedList<Integer> s1 = new LinkedList<>();
        sol.sort(s1);
        for (int i = 0; i < 10; i++) s1.offerFirst((int) (Math.random() * 10));
        System.out.println(s1);
        sol.sort(s1);
        System.out.println(s1);
    }
}
