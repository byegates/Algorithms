# Amazon, non-NG, OA

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
