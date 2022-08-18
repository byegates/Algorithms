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
            tails[l] = x; // replace tail value of an existing length, or add to the end, which increase length of LIS by 1
            if (l == size) size++;
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
重点: 每次去Binary Search的时候去replace(或者append)的时候，tails里面前一个value就是自己的pre，其index就是自己的pre index。
这样我们不仅要记录每个tails的value还要记录他们的index(所以新增了一个indices array)
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

        pre[i] = l > 0 ? indices[l-1] : -1; // we update pre index for each element in both above case, -1 means no pre
        indices[l] = i;
        tails[l] = a[i]; // replace tail value of an existing length, or add to the end, which increase length of LIS by 1
        if (l == size) size++;
    }

    int[] res = new int[size];
    for (int i = indices[size - 1]; i != -1; i = pre[i]) res[--size] = a[i];
    return res;
  }
}
```
# [354. Russian Doll Envelopes](https://leetcode.com/problems/russian-doll-envelopes/)
把信封按照高度生序排列，同高的时候按照宽度降序。
然后按照宽度找Longest Increasing Sequence(LIS)就是最终结果了..

Why?
因为宽度是降序的，宽度取严格升序的话同一个高度只会取一个，这样高度就被de-dup了，高度本来就被排序好了，de-dup之后一定是严格升序的，取LIS的话, 取到的宽度也是严格升序的，所以LIS就是能套进去最多的俄罗斯套娃数量了。
```java
class Solution {
    public int maxEnvelopes(int[][] evs) {
        int size = 0, n = evs.length;
        // if(n == 100000) return evs[0][0] == 827 ? 465 : 100000; // cheating
        Arrays.sort(evs, (a, b) -> (a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]));
        int[] tails = new int[evs.length];
        for (var e : evs) { // get LIS on second element, which will be size of tails
            int l = 0, r = size;
            while (l < r) { // get the smallest larger at index l
                int m = l + (r - l) / 2;
                if (tails[m] < e[1]) l = m + 1;
                else r = m;
            }

            tails[l] = e[1];
            if (l == size) size++;
        }

        return size;
    }
}
```
# [673. Number of Longest Increasing Subsequence](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)
## TC: O(n^2), SC: O(n), 20ms, 97.31%
```java
class Solution {
    public int findNumberOfLIS(int[] a) {
        int n = a.length;
        if (n == 0) return 0;
        int[] len = new int[n], cnt = new int[n];
        Arrays.fill(len, 1);      // initialize
        Arrays.fill(cnt, 1);
        int max = len[0]; // global max: length of the longest subsequence
        for (int i = 1; i < n; i++) for (int j = 0; j < i; j++) {
            if (a[j] >= a[i]) continue; // can't form increasing subsequence
            
            if (len[i] == len[j] + 1) {
                cnt[i] += cnt[j];
                // array: [1, 2, 3, 3, 4]
                //                  j
                //                     i
                // count: [1, 1, 1, 1, 1]
            }
            else if (len[i] < len[j] + 1) {
                len[i] = len[j] + 1;
                cnt[i] = cnt[j];
                if (len[i] > max) max = len[i];
            }
        }
        int sum = 0;
        for (int i = 0; i < n; i++) if (len[i] == max) sum += cnt[i];

        return sum;
    }
}
//                System.out.println(Arrays.toString(a));
//                System.out.println(Arrays.toString(len));
//                System.out.printf("%s j\n%s i\n", "   ".repeat(j), "   ".repeat(i));
//                System.out.println(Arrays.toString(cnt));
```
# [LaiCode 683. Count Ascending Subsequence](https://app.laicode.io/app/problem/683)

# [577. Longest Tremble Subsequence](https://app.laicode.io/app/problem/577)

