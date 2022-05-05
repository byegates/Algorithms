import java.util.Arrays;

public class InsertionSort {
    // Insertion sort with binary search to decide where to insert
    public int[] sort(int[] a) {
        if (a == null) return null;
        for (int j = 1; j < a.length; j++)
            for (int i = 0; i < j; i++)
                insertAtFirstLarger(a, firstLarger(a, j - 1, a[j]), j);

        return a;
    }

    private void insertAtFirstLarger(int[] a, int i, int j) { // insert j to i;
        int val = a[j];
        for (int k = j; k > i; k--) a[k] = a[k - 1];
        a[i] = val;
    }

    private int firstLarger(int[] a, int r, int t) {
        int l = 0;
        while (l < r) {
            int m = l + (r - l) / 2;
            if (a[m] > t) r = m;
            else l = m + 1;
        }
        return a[l] > t ? l : l + 1;
    }

    public static void main(String[] args) {
        InsertionSort sol = new InsertionSort();
        System.out.println(Arrays.toString(sol.sort(new int[]{528, 494, 585, 535, 300, 467, 76, 361, 264, 675, 434, 608, 588, 726, 48, 832, 288, 731, 405, 125, 629, 806, 680, 196, 103, 773, 939, 876, 52, 360, 551, 546, 571, 893, 256, 324, 326, 89, 709, 303, 455})));
    }

}
// =if(ISNUMBER(index(split(C18,"."),0,1)), HYPERLINK(CONCAT("https://app.laicode.io/app/problem/",index(split(C18,"."),0,1)),index(split(C18,"."),0,1)),"格式不对")
