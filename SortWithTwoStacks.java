import java.util.LinkedList;

public class SortWithTwoStacks {

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
