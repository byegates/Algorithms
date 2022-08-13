# Amazon, New Grad, OA

## 6/29/22
### #1
<pre>
Min start health of game</pre>

```java
import java.util.List;

class Solution {
    public long findMinHealth(List<Integer> power, int armor) {
        int max = 0, sum = 0;
        for (int x : power) {
            if (x > max) max = x;
            sum += x;
        }
        return sum - Math.min(armor, max) + 1;
    }
}
```
### #2
<pre>
Divide list of integers into groups, range with array can't exceed k</pre>
TC: O(nlogn), SC: O(n)
```java
class Solution {
    public int minGroup(List<Integer> list, int k) {
        Collections.sort(list);
        int start = list.get(0), res = 1;
        for (int x : list) {
            if (x - start > k) {
                start = x;
                res++;
            }
        }
        return res;
    }
}
```

## 8/12/22
### #1
原题: [2221. Find Triangular Sum of an Array](https://leetcode.com/problems/find-triangular-sum-of-an-array/)

暴力解应该就可以

### #2
<pre>
一个string中间任意位置分成两半的话，要求两遍的common unique char count > k,
assume k > 1?
</pre>

#### Solution 1
TC: 2 * 26 * n, SC: 26 * n
```java
class Solution {
    public static int findNumWaystoSplit(String s, int k) {
        if (s.length() <= 3) return 0;
        char[] a = s.toCharArray();
        int n = a.length;
        int[][] map = new int[26][n];

        // Count how many of each 26 lower english char appears in [0,j] 
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < 26; i++) {
                if (j > 0) map[i][j] += map[i][j-1];
            }
            map[a[j]-'a'][j]++;
        }
        
        int res = 0;
        // left: [0,j-1], right part: [j,n-1]
        // if k's min value is 1, we need at least 2 chars each side, so scan bound: [2,n-1)
        for (int j = 2; j < n-1; j++) {
            int count = 0;
            for (int i = 0; i < 26; i++) {
                if (map[i][j-1] > 0 && map[i][n-1] - map[i][j-1] > 0) {
                    if (++count > k) {
                        res++;
                        break; // j is a good breaking point, move on to next
                    }
                }
            }
        }
        return res;
    }
}
```
#### Solution 2a, use an array map for left side
TC: O(n^2), SC: O(26)
```java
class Solution {
    public static int findNumWaystoSplit(String s, int k) {
        if (s.length() <= 3) return 0;
        char[] a = s.toCharArray();
        int n = a.length;
        int[] map = new int[26];
        map[a[0] - 'a']++;

        int res = 0;
        for (int i = 1; i < n-2; i++) {
            map[a[i] - 'a']++;
            int count = 0;
            for (int j = i+1; j < n; j++) {
                if (map[a[j] - 'a'] > 0) {
                    if (++count > k) {
                        res++;
                        break;
                    }
                }
            }
        }

        return res;
    }
}
```

#### Solution 2b
TC: O(n^2), SC: O(26)
```java
class Solution {
    public static int findNumWaystoSplit(String s, int k) {
        if (s.length() <= 3) return 0;
        char[] a = s.toCharArray();
        int n = a.length;
        Set<Character> set = new HashSet<>();
        set.add(a[0]);

        int res = 0;
        for (int i = 1; i < n-2; i++) {
            set.add(a[i]);
            int count = 0;
            for (int j = i+1; j < n; j++) {
                if (set.contains(a[j])) {
                    if (++count > k) {
                        res++;
                        break;
                    }
                }
            }
        }

        return res;
    }
}
```