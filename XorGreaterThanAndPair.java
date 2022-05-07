import java.util.Arrays;
import java.util.List;

import static util.Matrix.sum;

public class XorGreaterThanAndPair {
    public long bigXorSum(List<Integer> list) {
        int[] count = new int[31]; // for any positive integer, the msb index can be 0 to 30 in total 31 possibilities, (max int is 2^31 - 1)
        long res = 0;
        for (Integer num : list) {
            int msb = 31 - Integer.numberOfLeadingZeros(num); // msb stands for most significant digit, meaning the left most 1, and here we need its index
            res += sum(count) - count[msb]; // every round we make a pair with all previous numbers, but exclude numbers with same msb index
            count[msb]++;
        }
        return res;
    }

    public static void main(String[] args) {
        XorGreaterThanAndPair sol = new XorGreaterThanAndPair();
        System.out.println(sol.bigXorSum(Arrays.asList(4, 3, 5, 2))); // 4
        System.out.println(sol.bigXorSum(Arrays.asList(4, 3, 5, 2, 1))); // 8
    }
}
