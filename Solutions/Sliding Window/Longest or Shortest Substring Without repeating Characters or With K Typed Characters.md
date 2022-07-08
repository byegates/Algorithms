# Table of Contents
1. [1695. Unique subarray sum](#[1695-Maximum-Erasure-Value](https://leetcode.com/problems/maximum-erasure-value/))
   1. [亚麻OA Max Average Stock Price?](#sliding-window-size-k-with-k-unique-chars)
2. [340. Longest Substring with At Most K Distinct Chars](#[340-Longest-Substring-with-At-Most-K-Distinct-Characters])
   1. LaiCode 473 & 490
   2. [LaiCode 382. Shortest Substring With K Typed Characters](#[LaiCode-382-Shortest-Substring-With-K-Typed-Characters])
3. 

# [1695-Maximum-Erasure-Value](https://leetcode.com/problems/maximum-erasure-value/)
TC: O(n), SC:O(n)
<pre>
4,2,4,5,6
  j
i

4,2,4,5,6
    j
  i

4,2,4,5,6
        j
  i

res: 17
</pre>
```java
class Solution {
    public int maximumUniqueSubarray(int[] a) {
        int cur = 0, res = -1, slow = 0;
        Set<Integer> set = new HashSet<>();
        for (int x : a) {
            for (; !set.add(x); set.remove(a[slow++]))
                cur -= a[slow];
            cur += x;
            if (cur > res) res = cur;
        }
        return res;
    }
}
```
## Solution 2
```java
class Solution {
    public int maximumUniqueSubarray(int[] a) {
        int res = -1, cur = 0;
        Set<Integer> set = new HashSet<>(); //记录Selected range里包含的所有数字，查重
        for (int slow = 0, fast = 0; slow < a.length && fast < a.length; )
            if (!set.add(a[fast])) {
                set.remove(a[slow]);
                cur -= a[slow++];
            } else {
                cur += a[fast++];
                if (cur > res) res = cur;
            }

        return res;
    }
}
```

# 亚麻OA-sliding-window-size-k-with-k-unique-chars
TC: O(n), SC: O(k)
```java
class Solution {
    public int max(int[] a, int k){
        int res = -1, cur = 0, slow = 0; //res: max sum, cur: cur sum, slow: start of sliding window (inclusive)
        Set<Integer> set = new HashSet<>(); //记录Selected range里包含的所有数字，查重
        //x is the value of right bound of sliding window
        for (int x : a) {
            for (; !set.add(x); set.remove(a[slow++]))
                cur -= a[slow];
            cur += x;
            if (set.size() > k) {
                set.remove(a[slow]);
                cur -= a[slow++];
            }
            //前移快指针操作
            if (set.size() == k && cur > res) res = cur;
        }
        return res;
    }
}
```
## Solution 2, is it really correct?
```java
class Solution {
    public int max(int[] a, int k){
        int res = -1, cur = 0; //res: max sum, cur: cur sum
        Set<Integer> set = new HashSet<>(); //记录Selected range里包含的所有数字，查重
        for (int slow = 0, fast = 0; slow < a.length && fast < a.length; ) {
            if (set.size() >= k || !set.add(a[fast])) {
                set.remove(a[slow]);
                cur -= a[slow++];
            } else {
                cur += a[fast++];
                if (cur > res) res = cur;
            }
        }
        return res;
    }
}
```
# [340-Longest-Substring-with-At-Most-K-Distinct-Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)
Exactly the same: [LaiCode 473. Longest Substring with At Most K Distinct Characters](https://app.laicode.io/app/problem/473)

Special case where k=2: [LaiCode 490. Longest Substring with At Most Two Distinct Characters](https://app.laicode.io/app/problem/490)
### 思路
1. 快指针(j)一次一个吃新字符, 记录一个unique字符count(第一次吃进一个新字符的时候+1). 
2. 当吃多了(count > k, 其实就是 count == k + 1), 逐一吐掉队尾(慢指针对应的)的字符, 直到unique字符数变成k。
3. (这个时候unique字符数一定 <= k)更新substring长度。

TC: O(n), SC: O(256) (2ms, 99.97%)
```java
class Solution {
  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] map = new int[256];

    int res = 0, count = 0;
    for (int start = 0, end = 0; end < s.length(); end++) {
      if (map[s.charAt(end)]++ == 0) count++;
      while (count > k) // reset i to the right place
          if (--map[s.charAt(start++)] == 0) count--;
        res = Math.max(res, end - start + 1);
    }

    return res;
  }
}
```
#### map Solution (7ms, 91.11%)
```java
class Solution {
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();
        
        int res = 0;
        for (int start = 0, end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            while (map.size() > k) {
                int cnt = map.get(c = s.charAt(start++));
                if (cnt > 1) map.put(c, --cnt);
                else map.remove(c);
            }
            res = Math.max(res, end - start + 1);
        }
        
        return res;
    }
}
```
## [LaiCode 285. Longest Substring With K Typed Characters](https://app.laicode.io/app/problem/285)
跟前面一样的，只是要返回substring，而不只是长度。

TC: O(n), SC: O(256)
```java
class Solution {
  public String longest(String s, int k) {
    int[] map = new int[256];

    int res = 0, start = 0, count = 0, len;
    for (int i = 0, j = 0; j < s.length(); j++) {
      if (map[s.charAt(j)]++ == 0) count++;
      while (count > k)
          if (--map[s.charAt(i++)] == 0) count--;
      if ((len = j - i + 1) > res) {
        res = len;
        start = i;
      }
    }

    return s.substring(start, start + res);
  }
}
```

# [LaiCode-382-Shortest-Substring-With-K-Typed-Characters](https://app.laicode.io/app/problem/382)
还是一个sliding window 问题, 吃的时候记住每个char的count(如果用array还要记一下一共吃了多少个char)要注意的是:
1. 吐的时候不仅char超过k了要吐，最后一个char的count只要超过一个也要吐，因为我们最短substring:
   1. k = 4: cdddddefb
             i      j
TC: O(n), SC: O(n(256))
```java
class Solution {
    public String shortest(String s, int k) {
        int[] map = new int[26];
        int res = Integer.MAX_VALUE, start = 0, count = 0, len;

        for (int i = 0, j = 0; j < s.length() && k > 0; j++) {
            if (++map[s.charAt(j) - 'a'] == 1) count++;

            while (map[s.charAt(i) - 'a'] > 1 || count > k)
                if (--map[s.charAt(i++) - 'a'] == 0) count--;

            if (count >= k && (len = j - i + 1) < res) {
                res = len;
                start = i;
            }
        }
        return res == Integer.MAX_VALUE ? "" : s.substring(start, start + res);
    }
}
```
### Map Solution
```java
class Solution {
    public String shortest(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();

        int min = Integer.MAX_VALUE, start = 0, len;
        for (int i = 0, j = 0; j < s.length() && k > 0; j++) {
            char c = s.charAt(j);
            map.put(c, map.getOrDefault(c, 0) + 1);

            for (int cnt; ( cnt = map.get(s.charAt(i)) ) > 1 || map.size() > k; )
                if (cnt > 1) map.put(s.charAt(i++), --cnt);
                else map.remove(s.charAt(i++));

            if (map.size() == k && (len = j - i + 1) < min) {
                min = len;
                start = i;
            }
        }

        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
    }
}
```

# [3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
[LaiCode 253. Longest Substring Without Repeating Characters](https://app.laicode.io/app/problem/253)
## Solution 1: use an index map(array)
### 1a array (4 ms, 96.18%, almost everytime)
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int[] map = new int[256];
        Arrays.fill(map, -1);

        int res = 0, start = 0;
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (map[c] >= 0)
                start = Math.max(start, map[c] + 1);
            map[c] = end;
            res = Math.max(res, end - start + 1);
        }

        return res;
    }
}
```
### 1b map (5 ms, 92.02%, occasionally)
```java
class Solution {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();

        int res = 0, start = 0;
        for (int end = 0; end < s.length(); end++) {
            char c = s.charAt(end);
            if (map.containsKey(c))
                start = Math.max(start, map.get(c) + 1);
            map.put(c, end);
            res = Math.max(res, end - start + 1);
        }

        return res;
    }
}
```
## Solution 2 use a count set/array, count everything
1. 如果新字符是没见过的，吃进去，更新长度，快指针继续往前。
2. 如果新字符是见过的，逐一吐掉慢指针对应的字符，直到新字符变成唯一的。
### Solution 2a set (11ms)
TC: O(n), SC: O(n)
```java
class Solution {
    public int longest(String s) {
        Set<Character> set = new HashSet<>();

        int res = 0;
        for (int i = 0, j = 0; j < s.length(); )
            if (set.add(s.charAt(j)))
                res = Math.max(res, ++j - i); // j - i + 1: len
            else set.remove(s.charAt(i++));

        return res;
    }
}
```
### Solution 2b, array
```java
class Solution {
  public int lengthOfLongestSubstring(String s) {
    int[] map = new int[256];
    
    int res = 0;
    for (int i = 0, j = 0; j < s.length(); )
      if (map[s.charAt(j)] == 0) {
        res = Math.max(res, j - i + 1);
        map[s.charAt(j++)] = 1;
      } else map[s.charAt(i++)]--;

    return res;
  }
}
```
