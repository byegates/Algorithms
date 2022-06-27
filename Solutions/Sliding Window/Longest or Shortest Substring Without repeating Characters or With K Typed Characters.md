# [340. Longest Substring with At Most K Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)
Exactly the same: [LaiCode 473. Longest Substring with At Most K Distinct Characters](https://app.laicode.io/app/problem/473)

Specific case where k=2: [LaiCode 490. Longest Substring with At Most Two Distinct Characters](https://app.laicode.io/app/problem/490)
### 思路
快指针(j)一次一个吃新字符, 记录一个unique字符count(第一次吃进一个新字符的时候+1).
当吃多了(count > k, 其实就是 count == k + 1), 逐一吐掉队尾(慢指针对应的)的字符, 直到unique字符数变成k。
(这个时候unique字符数一定 <= k)更新substring长度。

TC: O(n), SC: O(256) (2ms, 99.97%)
```java
class Solution {
  public int lengthOfLongestSubstringKDistinct(String s, int k) {
    int[] map = new int[256];

    int res = 0, count = 0, len;
    for (int i = 0, j = 0; j < s.length(); j++) {
      if (map[s.charAt(j)]++ == 0) count++;
      while (count > k) // reset i to the right place
          if (--map[s.charAt(i++)] == 0) count--;
      if ((len = j - i + 1) > res) res = len; // update global result
    }

    return res;
  }
}
```
## [LaiCode 285. Longest Substring With K Typed Characters](https://app.laicode.io/app/problem/285)
跟前面3题还是一样的，是指要返回substring，而不只是长度。

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

# [3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
[LaiCode 253. Longest Substring Without Repeating Characters](https://app.laicode.io/app/problem/253)
如果新字符是没见过的，吃进去，更新长度，快指针继续往前。
如果新字符是见过的，逐一吐掉慢指针对应的字符，直到新字符变成唯一的。
## Solution 1 map
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
## Solution 2, array
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
# [LaiCode 382. Shortest Substring With K Typed Characters](https://app.laicode.io/app/problem/382)

TC: O(n), SC: O(n(256))
```java
class Solution {
    public String shortest(String s, int k) {
        int[] map = new int[256];
        int res = Integer.MAX_VALUE, start = 0, count = 0, len;

        for (int i = 0, j = 0; j < s.length() && k > 0; j++) {
            if (++map[s.charAt(j)] == 1) count++;

            while (map[s.charAt(i)] > 1 || count > k)
                if (--map[s.charAt(i++)] == 0) count--;

            if (count >= k && (len = j - i + 1) < res) {
                res = len;
                start = i;
            }
        }
        return res == Integer.MAX_VALUE ? "" : s.substring(start, start + res);
    }
}
```