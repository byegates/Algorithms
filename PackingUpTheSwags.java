/*
    Our company is going to distribute swags at the recruiting event. We will put the swags into square-shaped boxes.
    Each box has to be completely filled so that the swags wouldn't break during transportation.
    For example, a box can contain 1 swag, 4 swags, 9 swags, etc. (The boxes can be sufficiently large.)
    Given the number of swags, what is the minimum number of boxes to pack them up?

    Example #1
    Input: 4
    Output: 1 (just one 2x2 box)

    Example #2
    Input: 10
    Output: 2 (one 3x3 box and one 1x1 box)

    Analysis:
    1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20
    1 2 3 1 2 3 4 2 1  2  3  3  2  3  4  1  2  2  3  2
 */

import java.util.Arrays;

public class PackingUpTheSwags { // TC: O(n^1.5), SC: O(n)
    public int minBoxes(int n) {
        int[] M = new int[n + 1];
        Arrays.fill(M, n);
        M[0] = 0;
        M[1] = 1;

        for (int i = 2; i <= n; i++)
            for (int j = 1; j * j <= i; j++)
                M[i] = Math.min(M[i], 1 + M[i - j * j]);

        return M[n];
    }

    public static void main(String[] args) {
        PackingUpTheSwags pts = new PackingUpTheSwags();
        for (int i = 1; i <= 20; i++)
            System.out.printf("%2d : %2d\n", i, pts.minBoxes(i));
    }
}
