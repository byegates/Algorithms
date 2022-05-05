import java.util.Arrays;

public class RainbowSortIII {
    public int[] rainbowSortIII(int[] a, int k) {
        int l = 0, r = a.length - 1;
        for (int min = 1; min < k; min++, k--)
            for (int i = l; i <= r;)
                if (a[i] == min) swap(a, i++, l++);
                else if (a[r] == k) r--;
                else if (a[i] == k) swap(a, i, r--);
                else i++;

        return a;
    }

    private void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void main(String[] args) {
        RainbowSortIII sol = new RainbowSortIII();
        System.out.println(Arrays.toString(sol.rainbowSortIII(new int[]{1, 3, 4, 2, 5, 2, 1}, 5)));
    }
}
