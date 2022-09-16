# Amazon, non-NG, OA

## 9/16/22
### #1
same as 9/5/22, #1 Similar to [973. K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)

### #2
BFS to find 9, refer to 6/29/22 #2

## 9/12/22
### #1
Same problem as [937. Reorder Data in Log Files](https://leetcode.com/problems/reorder-data-in-log-files/) with different description
### #2
[Optimal Utilization](https://leetcode.com/discuss/interview-question/373202/amazon-oa-2019-optimal-utilization)

## 9/11/22
### #1
reter to 8/22/22 #1 below
### #2
[2281. Sum of Total Strength of Wizards](https://leetcode.com/problems/sum-of-total-strength-of-wizards/) 变种
#### O(n) Solution is all over LeetCode
#### O(n^2) Solution, easy to understand or explain to others, can pass 68/82 cases on LeetCode then TLE, but fails HackerRank 3 cases
```java
class Solution1 {
    public int totalStrength(List<Integer> list) {
        int n = list.size(), M = 1_000_000_007;
        int[] psum = new int[n+1];
        int res = 0;
        for (int i = 0; i < n; i++) {
            psum[i+1] = (psum[i] + list.get(i)) % M;
        }

        for (int i = 0; i < n; i++) {
            int min = list.get(i);
            for (int j = i; j < n; j++) {
                min = Math.min(min, list.get(j));
                res = (res + (psum[j+1]-psum[i]) * min % M) % M;
            }
        }

        return res;
    }

    public int totalStrength(int[] a) {
        int n = a.length, M = 1_000_000_007;
        int[] psum = new int[n+1];
        int res = 0;
        for (int i = 0; i < n; i++) {
            psum[i+1] = (psum[i] + a[i]) % M;
        }

        for (int i = 0; i < n; i++) {
            int min = a[i];
            for (int j = i; j < n; j++) {
                min = Math.min(min, a[j]);
                res = (res + (psum[j+1]-psum[i]) * min % M) % M;
            }
        }

        return res;
    }
}
```
## 9/5/22
### #1 k closest restaurants
Similar to [973. K Closest Points to Origin](https://leetcode.com/problems/k-closest-points-to-origin/)
But not the same. When distances are the same, return smaller x
<pre>num of closest points to (0, 0), you may see same question but input and output are list, which are the same</pre>
#### quickSelect
TC: O(n), SC: O(logn)
```java
class Solution {
    record Cell(int x, int y, int d) implements Comparable<Cell> {
        @Override
        public int compareTo(Cell o) {
            return d == o.d ? x - o.x : d - o.d;
        }
    }

    public List<List<Integer>> kClosest(List<List<Integer>> list, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (list.size() == 0 || list.get(0).size() == 0) {
            res.add(new ArrayList<>());
            return res;
        }

        int[][] mx = new int[list.size()][2];
        for (int i = 0; i < mx.length; i++) mx[i] = new int[] {list.get(i).get(0), list.get(i).get(1)};
        for (var a : kClosest(mx, k)) {
            if (a.length == 0) res.add(new ArrayList<>());
            else res.add(List.of(a[0], a[1]));
        }

        return res;
    }

    public int[][] kClosest(int[][] points, int k) {
        quickSort(points, 0, points.length - 1, k - 1);  // set k-1
        return Arrays.copyOf(points, k);
    }

    private void quickSort(int[][] points, int l, int r, int k) {
        if (l > r) return;  // must check out of bound or not
        int pivot = partition(l, r, points);
        if (pivot < k) quickSort(points, pivot + 1, r, k);
        else if (pivot > k) quickSort(points, l, pivot - 1, k);
    }

    private int partition(int l, int r, int[][] points) {
        int p = getPivot(l, r);
        Cell pivot = getCell(points[p]);
        swap(p, r, points);
        int i = l, j = r-1;
        while (i <= j) {
            Cell ci = getCell(points[i]);
            Cell cj = getCell(points[j]);
            if (ci.compareTo(pivot) <= 0) i++;
            else if (cj.compareTo(pivot) > 0) j--;
            else swap(i++, j--, points);
        }
        swap(i, r, points);
        return i;
    }

    private void swap(int i, int j, int[][] points) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    private Cell getCell(int[] a) {
        return new Cell(a[0], a[1], a[0] * a[0] + a[1] * a[1]);
    }

    private int getPivot(int l, int r) {
        return l + (int) (Math.random() * (r - l));
    }
}
```
#### minHeap
```java
import java.util.ArrayList;

class Solution {
    public List<List<Integer>> kClosestRestaurant(List<List<Integer>> allLocations, int numRestaurants) {
        List<List<Integer>> res = new ArrayList<>();
        if (allLocations == null || allLocations.size() == 0 || numRestaurants == 0) {
            res.add(new ArrayList<>());
            return res;
        }
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] == b[2] ? a[1] - b[1] : a[2] < b[2] ? -1 : 1);
        for (List<Integer> loc : allLocations) {
            int x = loc.get(0), y = loc.get(1);
            pq.offer(new int[]{x, y, x * x + y * y});
        }
        for (int i = 0; i < numRestaurants; i++) {
            int[] cur = pq.poll();
            res.add(Arrays.asList(cur[0], cur[1]));
        }
        return res;
    }
}
```
#### Simply way
```java
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {
    public List<int[]> xClosest(int[][] allLocations, int num) {
        List<int[]> res = new ArrayList<>();
        // max heap of k size
        Queue<int[]> q = new PriorityQueue<>((a, b) -> {
            int d1 = a[0]*a[0] + a[1]*a[1];
            int d2 = b[0]*b[0] + b[1]*b[1];
            return d1 == d2 ? a[0] - b[0] : d1 - d2;
        });
        
        for (var a : allLocations) q.offer(a);
        
        while (num-- > 0 && !q.isEmpty()) res.add(q.poll());

        return res;
    }
}
```
#### max heap, technically more efficient
```java
import java.util.*;

class Solution {
//    record Cell(int x, int y, int d) { // you can use this for java 14 and above
//    }
    
    static class Cell {
        int x, y, d;
        public Cell (int _x, int _y, int _d) { // before java 14
            x = _x;
            y = _y;
            d = _d;
        }
    }

    public List<List<Integer>> xClosest(List<List<Integer>> list, int num) {
        if (list == null || list.size() == 0 || num == 0) return List.of(new ArrayList<>());
        int n = list.size();
        Cell[] cells = new Cell[n];
        for (int i = 0; i < n; i++) {
            var l = list.get(i);
            int x = l.get(0), y = l.get(1);
            cells[i] = new Cell(x, y, x*x + y*y);
        }

        // max heap
        Queue<Cell> q = new PriorityQueue<>((a, b) -> a.d == b.d ? b.x - a.x : b.d - a.d);

        for (Cell c : cells) {
            if (q.size() < num) q.offer(c);
            else if (c.d < q.peek().d) {
                q.poll();
                q.offer(c);
            }
        }

        List<List<Integer>> res = new LinkedList<>();
        while (!q.isEmpty()) {
            Cell c = q.poll();
            res.add(0, List.of(c.x, c.y));
        }
        return res;
    }
}

public class DebugSolution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.kClosestRestaurant(List.of(List.of(-2, -2), List.of(2, 2), List.of(3, 3)), 2)); // output: [[1, -1], [1, 2]]
        System.out.println(sol.kClosestRestaurant(List.of(List.of(1, 2), List.of(3, 4), List.of(1, -1)), 2)); // output: [[1, -1], [1, 2]]
        System.out.println(sol.kClosestRestaurant(List.of(List.of(100, 100), List.of(90, 90), List.of(80, 80), List.of(70, 70), List.of(60, 60), List.of(1, -1)), 4)); // [[1, -1], [60, 60], [70, 70], [80, 80]]
        System.out.println(sol.kClosestRestaurant(new ArrayList<>(), 0)); // [[]]
        System.out.println(sol.kClosestRestaurant(List.of(new ArrayList<>()), 0)); // [[]]
    }
}
```
### #2 same as below 6/29/22 #2

## 8/22/22
### #1 Array merge and max
<pre>
for an array, if a[i] < a[i+1], merge them and discard a[i], recursively execute this logic until no more action you can take.
return max value now
</pre>

```java
class DebugSolution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.printf("Max: %d", sol.maxAfterMerge(new int[]{20, 8, 2, 7})); // 20
        System.out.printf(", %d", sol.maxAfterMerge(new int[]{20, 8, 2, 13})); // 43
        System.out.printf(", %d", sol.maxAfterMerge(new int[]{4, 30, 15, 5, 9})); // 34
        System.out.printf(", %d\n", sol.maxAfterMerge(new int[]{4, 20, 13, 8, 9})); // 54

        System.out.printf("Max: %d", sol.maxAfterMerge(Arrays.asList(20, 8, 2, 7))); // 20
        System.out.printf(", %d", sol.maxAfterMerge(List.of(20, 8, 2, 13))); // 43
        System.out.printf(", %d", sol.maxAfterMerge(List.of(4, 30, 15, 5, 9))); // 34
        System.out.printf(", %d\n", sol.maxAfterMerge(List.of(4, 20, 13, 8, 9))); // 54
    }
}

class Solution {
    public long maxAfterMerge(List<Integer> l) { // input as list
        int n = l.size();
        long res = l.get(0), pre = l.get(n-1);
        for (int i = n - 2; i >= 0; i--) {
            pre = pre > l.get(i) ? l.get(i) + pre : l.get(i);
            if (pre > res) res = pre;
        }

        return res;
    }

    public long maxAfterMerge(int[] a) {
        long res = a[0], pre = a[a.length-1];
        for (int i = a.length - 2; i >= 0; i--) {
            pre = pre > a[i] ? a[i] + pre : a[i];
            if (pre > res) res = pre;
        }

        return res;
    }
}
```
### #2 Explained as Amazon storage or something, but effectively exact the same problem as LeetCode 2355
[2355. Maximum Number of Books You Can Take](https://leetcode.com/problems/maximum-number-of-books-you-can-take/)

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
        if (lot.size() == 0 || lot.get(0).size() == 0) return -1;
        Queue<int[]> q = new ArrayDeque<>();
        int m = lot.size(), n = lot.get(0).size();
        if (lot.get(0).get(0) == 9) return 0;
        q.offer(new int[] {0, 0});
        lot.get(0).set(0, 0);

        for (int step = 1; !q.isEmpty(); step++) {
            for (int size = q.size(); size > 0; size--) {
                int[] cur = q.poll();
                int i = cur[0], j = cur[1];
                for (var dir : dirs) {
                    int i2 = i + dir[0], j2 = j + dir[1];
                    if (!isValid(i2, j2, m, n) || lot.get(i2).get(j2) == 0) continue;
                    if (lot.get(i2).get(j2) == 9) return step;
                    lot.get(i2).set(j2, 0);
                    q.offer(new int[] {i2, j2});
                }
            }
        }

        return -1;
    }

    private boolean isValid(int i, int j, int m, int n) {
        return i >= 0 && j >= 0 && i < m && j < n;
    }
}

public class DebugSolution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.distanceTraversed(Arrays.asList(Arrays.asList(1, 0, 0), Arrays.asList(1, 0, 0), Arrays.asList(1, 9, 1)))); // 3
        System.out.println(sol.distanceTraversed(Arrays.asList(Arrays.asList(9, 0, 0), Arrays.asList(1, 0, 0), Arrays.asList(1, 0, 1)))); // 0
        System.out.println(sol.distanceTraversed(Arrays.asList(Arrays.asList(1, 9, 0), Arrays.asList(1, 0, 0), Arrays.asList(1, 1, 1)))); // 1
        System.out.println(sol.distanceTraversed(List.of(new ArrayList<>()))); // -1
        System.out.println(sol.distanceTraversed(new ArrayList<>())); // -1    }
}
```
