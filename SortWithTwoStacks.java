import java.util.LinkedList;

interface test {
    int val = 0;
    void print();
    void print2();
}

public class SortWithTwoStacks {
    public void sort(LinkedList<Integer> s1) {
        if (s1 == null || s1.size() <= 1) return;
        LinkedList<Integer> s2 = new LinkedList<>();
        //sort1(s1, s2);
        sort2(s1, s2);
    }
    // Solution 2
    // sort the smallest value into s2, at the end swap back to s1
    private void sort2 (LinkedList<Integer> s1, LinkedList<Integer> s2) {// TC: O(n^2), SC: O(n)? O(1)?
        int preMin = Integer.MIN_VALUE, cur;
        while (!s1.isEmpty() && s1.peekFirst() > preMin) {
            int count = 0, curMin = Integer.MAX_VALUE;
            while (!s1.isEmpty()) {
                s2.offerFirst(cur = s1.pollFirst());
                if (cur < curMin) {
                    curMin = cur;
                    count = 1;
                } else if (cur == curMin) count++;
            }
            while (!s2.isEmpty() && s2.peekFirst() > preMin)
                if ((cur = s2.pollFirst()) != curMin) s1.offerFirst(cur);
            for (; count > 0; count--) s2.offerFirst(curMin);
            preMin = curMin;
        }
        while (!s2.isEmpty()) s1.offerFirst(s2.pollFirst());
    }
    // Solution 2 ends here

    // Solution 1
    // remember prev max value, sort max values one by one into s1
    private void sort1(LinkedList<Integer> s1, LinkedList<Integer> s2) { // TC: O(n^2), SC: O(n)? O(1)?
        int preMax = Integer.MAX_VALUE, cur;
        while (s1.peekFirst() < preMax) {
            int count = 0, curMax = Integer.MIN_VALUE;
            while (!s1.isEmpty() && s1.peekFirst() < preMax) {
                s2.offerFirst(cur = s1.pollFirst());
                if (cur > curMax) {
                    curMax = cur;
                    count = 1;
                } else if (cur == curMax) count++;
            }

            for (; count > 0; count--) s1.offerFirst(curMax);
            while (!s2.isEmpty())
                if ((cur = s2.pollFirst()) != curMax)
                    s1.offerFirst(cur);

            preMax = curMax;
        }
    }
    // solution 1 ends here

    public static void main(String[] args) {
        SortWithTwoStacks sol = new SortWithTwoStacks();
        LinkedList<Integer> s1 = new LinkedList<>();
        sol.sort(s1);
        for (int i = 0; i < 10; i++) s1.offerFirst((int) (Math.random() * 10));
        System.out.println(s1);
        sol.sort(s1);
        System.out.println(s1);
        TestClass tst = new TestClass();
        tst.print();

        test t2 = new test() {
            @Override
            public void print() {
                System.out.println(test.val);
            }
            @Override
            public void print2() {
                System.out.println("print2");
            }
        };
        t2.print();
        t2.print2();
    }
}

class TestClass implements test{

    @Override
    public void print() {
        System.out.println(val);
    }

    @Override
    public void print2() {
        System.out.println("print2");
    }
}