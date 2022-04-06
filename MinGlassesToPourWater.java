/*
n glasses with capacity of 1, 2, 3 ... to n
 to pour k liters of water, what's the min number of glasses needed?
 All glasses used must be fully filled
*/

public class MinGlassesToPourWater {
    public int min(int n, int k) {
        if (k > n * (n + 1) / 2) return -1;
        int res = 0;

        while (k > 0) {
            if (n == 0) return -1; // all glasses used and k is still > 0, no solution
            k -= n--; // just pour the largest glass and removed that glass
            res++;
            if (k <= 0) break; // if k is less than 0, it can be poured into a smaller glass(which exists for sure)
        }
        return res;
    }

    public static void main(String[] args) {
        MinGlassesToPourWater mg = new MinGlassesToPourWater();
        System.out.println(mg.min(4, 10) == 4);
        System.out.println(mg.min(5, 10) == 3);
        System.out.println(mg.min(6, 10) == 2);
        System.out.println(mg.min(7, 10) == 2);
        System.out.println(mg.min(8, 10) == 2);
        System.out.println(mg.min(9, 10) == 2);
        System.out.println(mg.min(10, 10) == 1);
        System.out.println(mg.min(5, 8) == 2);
        System.out.println(mg.min(1, 2) == -1);
        System.out.println(mg.min(10, 5) == 1);
    }
}
