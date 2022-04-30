import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/*
    Given two integer arrays A1 and A2, sort A1 in such a way that the relative order among the elements will be same as those are in A2.
    For the elements that are not in A2, append them in the right end of the A1 in ascending order.
    Assumptions:
    A1 and A2 are both not null.
    There are no duplicate elements in A2.
    Examples:
    A1 = {2, 1, 2, 5, 7, 1, 9, 3}, A2 = {2, 1, 3}, A1 is sorted to {2, 2, 1, 1, 3, 5, 7, 9}
 */
public class SortInSpecifiedOrder {
    static class Comparator261 implements Comparator<Integer> {
        private Map<Integer, Integer> map;
        public Comparator261(int[] a) {
            map = new HashMap<>();
            for (int i = 0; i < a.length; i++) map.put(a[i], i);
        }
        @Override
        public int compare(Integer i1, Integer i2) {
            Integer idx1 = map.get(i1);
            Integer idx2 = map.get(i2);
            if (idx1 != null && idx2 != null) return idx1.compareTo(idx2);
            if (idx1 == null && idx2 == null) return i1.compareTo(i2);
            return idx1 != null ? -1 : 1;
        }
    }

    public Integer[] toInteger(int[] a) {
        Integer[] res = new Integer[a.length];
        for (int i = 0; i < a.length; i++) res[i] = a[i];
        return res;
    }

    public int[] toInt(Integer[] a) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) res[i] = a[i];
        return res;
    }

    public int[] sortSpecial(int[] a1, int[] a2) { // O(m + n)
        Integer[] a1i = toInteger(a1);
        Arrays.sort(a1i, new Comparator261(a2));
        return toInt(a1i);
    }

    public static void main(String[] args) {
        int[] a1 = new int[]{2, 1, 2, 5, 7, 1, 9, 3};
        int[] a2 = new int[]{2, 1, 3};
        SortInSpecifiedOrder sso = new SortInSpecifiedOrder();
        System.out.println(Arrays.equals(sso.sortSpecial(a1, a2), new int[]{2, 2, 1, 1, 3, 5, 7, 9}));
    }

}
