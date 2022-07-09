# [300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)
[LaiCode 1. Longest Ascending Subsequence](https://app.laicode.io/app/problem/1)
## TC O(n^2), SC: O(n)
```java
class Solution {
  public int lengthOfLIS(int[] a) {
    int res = 1, n = a.length;
    if (n == 0) return 0;
    int[] dp = new int[a.length];
    Arrays.fill(dp, 1); // must do, everybody is a length 1 sub seq by themselves
    for (int i = 0; i < n; i++) for (int j = 0; j < i; j++)
      if (a[j] < a[i] && dp[j] + 1 > dp[i]) {
        dp[i] = dp[j] + 1;
        if (dp[i] > res) res = dp[i];
      }

    return res;
  }
}
```

## TC: O(nlogn), SC: O(n)
<pre>
tails list index vs value 物理意义:
index + 1 代表subsequence的长度, 其value代表对应长度的sub sequence的最小ending value
更新逻辑:
在tail里面找smallest larger(>)所对应的index, replace that value (append if not exist)
</pre>
#### List
```java
class Solution {
  public int lengthOfLIS(int[] a) {
    List<Integer> tails = new ArrayList<>();
    for (int x : a) {
      int l = 0, r = tails.size();
      while (l < r) { // find smallest larger
        int m = l + (r - l) / 2;
        if (tails.get(m) < x) l = m + 1;
        else r = m;
      }

      if (l < tails.size()) tails.set(l, x);
      else tails.add(x);
    }
    return tails.size();
  }
}
```
#### Array
```java
class Solution {
    public int lengthOfLIS(int[] a) {
        int[] tails = new int[a.length];
        int size = 0;
        for (int x : a) {
            int l = 0, r = size;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (tails[m] < x) l = m + 1;
                else r = m;
            }
            if (l < size) tails[l] = x;
            else tails[size++] = x;
        }
        return size;
    }
}
```

# [LaiCode 682. Longest Ascending Subsequence II](https://app.laicode.io/app/problem/682)
## TC: O(n^2), SC: O(n)
Record pre index for every element whenever any element is updated.
```java
class Solution {
  public int[] longest(int[] a) {
    if (a.length == 0) return new int[0];
    int[] dp = new int[a.length], pre = new int[a.length];

    Arrays.fill(dp, 1);
    Arrays.fill(pre, -1);

    int end = 0, max = 1;
    for (int i = 0; i < a.length; i++) for (int j = 0; j < i; j++)
        if (a[j] < a[i]) {
            if (dp[j] + 1 > dp[i]) {
                dp[i] = dp[j] + 1;
                pre[i] = j;
            }
            if (dp[i] > max) {
                end = i;
                max = dp[i];
            }
        }

    int[] res = new int[max];
    for (int i = end; i != -1; i = pre[i]) res[--max] = a[i];

    return res;
  }
}
```
## TC: O(nlogn), SC: O(n)
```java
class Solution {
  public int[] longest(int[] a) {
    int size = 0, n = a.length;
    if (n == 0) return new int[0];
    int[] tails = new int[n], pre = new int[n], indices = new int[n];

    for (int i = 0; i < n; i++) {
        int l = 0, r = size;
        while (l < r) { // find the smallest larger position (in l)
            int m = l + (r - l) / 2;
            if (tails[m] < a[i]) l = m + 1;
            else r = m;
        }
        if (l < size) { // replace an existing val, set indices of cur end value
            pre[i] = l > 0 ? indices[l - 1] : -1;
            tails[l] = a[i];
            indices[l] = i;
        } else { // add to the end, increasing LIS(The Longest Increasing Subsequence) by 1
            pre[i] = size > 0 ? indices[size - 1] : -1;
            tails[size] = a[i];
            indices[size++] = i;
        } // we update pre index for each element in both above case, -1 means this guy has no pre
    }

    int[] res = new int[size];
    for (int i = indices[size - 1]; i != -1; i = pre[i]) res[--size] = a[i];
    return res;
  }
}
```

# [673. Number of Longest Increasing Subsequence](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)

# [LaiCode 683. Count Ascending Subsequence](https://app.laicode.io/app/problem/683)

# [577. Longest Tremble Subsequence](https://app.laicode.io/app/problem/577)

