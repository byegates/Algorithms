# Amazon, non-NG, OA

## Saw multiple cases between Feb and Aug 2022
[LeetCode Link, If not deleted](https://leetcode.com/discuss/interview-question/2083297/amazon-sde2-oa)
<pre>
Given an array of n elements find the count of imbalances in it. Imbalance is arr[i+1] - arr[i] > 1 in sorted subarray.

Another description: Given input array size n, values of array are permutation of [1,n], find imbalance subarrays.
This is also described as array of student ranks.

Example : [4,1,3,2]
for length : 1
subarray 1: [4] => sort it => [4] imbalance : 0
subarray 2: [1] => sort it => [1] imbalance : 0
subarray 3: [3] => sort it => [3] imbalance : 0
subarray 4: [2] => sort it => [2] imbalance : 0

total count of imbalance for imbalance for length 1 is 0 + 0 + 0 + 0 = 0
for length : 2
subarray 1: [4,1] => sort it => [1,4] => imbalance : 1(because 4 - 1 > 1)
subarray 2: [1,3] => sort it => [1,3] => imbalance : 1(because 3 - 1 > 1)
subarray 3 :[3,2] => sort it => [2,3] => imbalance : 0(because 3 - 2 =1)

total count of imbalances for length 2 is 1 + 1 + 0 = 2
for length: 3
subarray 1: [4,1,3] => sort it => [1,3,4] => imbalance : 1(because 3 - 1 > 1)
subarray 2: [1,3,2] => sort it => [1,2,3] => imbalance : 0

total count of imbalances for length 3 is 1 + 0 = 1
for length: 4
subarray 1: [4,1,3,2] => sort it => [1,2,3,4] => imbalance : 0

total count of imbalances for length 4 is 0
ans = 0 + 2 + 1 + 0 = 3

Max Array Length : 3 * 10^3
</pre>
### idea
if difference between max and min of any subarray >= len of subarray, it's an imbalanced subarray
```java
public class DebugSolution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.printf("Total imbalance: %d\n\n", sol.imbalance(new int[]{4,1,3,2}));
        System.out.printf("Total imbalance: %d\n\n", sol.imbalance(new int[]{1,5,3,2,4}));
    }

}

class Solution {
    public int imbalance(int[] rank){
//        System.out.printf("Input Array:\n%s\nImbalance subArrays(s) are:\n", Arrays.toString(rank));
        int n = rank.length, res = 0, min, max;

        for (int i = 0; i < n - 1; i++) {
            max = min = rank[i];
            for (int j = i + 1; j < n; j++) {
                max = Math.max(max, rank[j]);
                min = Math.min(min, rank[j]);
                if (max - min >= j-i+1) {
                    res++;
//                    System.out.println(Arrays.toString(Arrays.copyOfRange(rank, i, j + 1)));
                }
            }
        }

        return res;
    }
}
```
### Execution result of example cases
<pre>
Input Array:
[4, 1, 3, 2]
Imbalance subArrays(s) are:
[4, 1]
[4, 1, 3]
[1, 3]
Total imbalance: 3

Input Array:
[1, 5, 3, 2, 4]
Imbalance subArrays(s) are:
[1, 5]
[1, 5, 3]
[1, 5, 3, 2]
[5, 3]
[5, 3, 2]
[2, 4]
Total imbalance: 6
</pre>
## 6/29/22
### #1
<pre>
Giving String s, contains: (,),[,],?
4 <= s.length <= 10^5

? can be replaced with any of rest 4: (,),[,]
you can switch )( to () when needed
you can switch ][ to [] when needed

How many ways can you split string s from the middle,
while ensure both the left and right part of the string have balanced numbers of brackets.
Below actions can be done as needed to make it a balanced string.

Example1:
s = "[(?][??["
answer: 2 (ways of split)
1. s1 = [(?], s2 = [??[:
    s1 replace ? to ) --> [()]
    s2 replace ?? to ]] --> []][, rearrange --> [][]
2. s1 = [(?][?, s2 = ?[

Example2:
s = "(??["
answer: 1 (ways of split)
1. s1 = (?, s2 = ?[:
    s1 replace ? to ) --> ()
    s2 replace ? to ] --> ][, rearrange --> []

Example3:
"(((?": 0
</pre>
### code, this only pass 70% test cases?
```java
class Solution {
    public int waysOfSplit(String s) {
        int n = s.length();
        if (n % 2 == 1 || n < 4) return 0;

        int[] rd = new int[s.length()];
        int[] sq = new int[s.length()];
        int[]  q = new int[s.length()];
        int cnt0 = 0, cnt1 = 0, cnt2 = 0;

        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(' -> cnt0++;
                case ')' -> cnt0--;
                case '[' -> cnt1++;
                case ']' -> cnt1--;
                case '?' -> cnt2++;
            }

            rd[i] = cnt0;
            sq[i] = cnt1;
            q[i] = cnt2;
        }

        int res = 0;

//        System.out.println();
//        System.out.println(s);
//        System.out.println(Arrays.toString(rd));
//        System.out.println(Arrays.toString(sq));
//        System.out.println(Arrays.toString(q));
//        System.out.println();
        for (int i = 1; i < rd.length - 1; i += 2) {// 1 ~ n
//            System.out.println(i);
            int left = q[i] - Math.abs(rd[i]) - Math.abs(sq[i]);
            int right = q[n - 1] - q[i] - Math.abs(rd[n - 1] - rd[i]) - Math.abs(sq[n - 1] - sq[i]);
//            if(left>=0 && left % 2 == 0 && right >= 0 && right % 2 == 0) res++;
            if(left>=0 && right >= 0) res++;
        }

        return res;
    }
}
```
### #2
<pre>
Simple BFS
min steps from <0,0> to value 9
cell with value 0 can't be crossed
value 1 is good
</pre>
```java
class Solution {
    private static final int[][] dirs = new int[][] {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int distanceTraversed(List<List<Integer>> lot) {
        Queue<int[]> q = new ArrayDeque<>();
        int m = lot.size(), n = lot.get(0).size();
        boolean[][] visited = new boolean[m][n];
        q.offer(new int[] {0, 0});
        visited[0][0] = true;

        int step = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] cur = q.poll();
                int i = cur[0], j = cur[1];
                if (lot.get(i).get(j) == 9) return step;
                for (var dir : dirs) {
                    int i2 = i + dir[0], j2 = j + dir[1];
                    if (!isValid(i2, j2, m, n) || visited[i2][j2] || lot.get(i2).get(j2) == 0) continue;
                    q.offer(new int[] {i2, j2});
                    visited[i2][j2] = true;
                }
            }
            step++;
        }

        return -1;
    }

    private boolean isValid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}
```
