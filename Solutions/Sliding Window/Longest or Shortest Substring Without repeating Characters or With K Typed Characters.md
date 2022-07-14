# Table of Contents
1. [1695. Unique subarray sum](#1695-Maximum-Erasure-Value)
   1. [亚麻OA Max Average Stock Price?](#亚麻OA-sliding-window-size-k-with-k-unique-chars)
2. [209. Minimum Size Subarray Sum](#209-Minimum-Size-Subarray-Sum)
3. [340. Longest Substring with At Most K Distinct Chars](#340-Longest-Substring-with-At-Most-K-Distinct-Characters)
   1. Same as LaiCode 473 & 490
   2. [LaiCode 285. Longest Substring With K Typed Characters](#LaiCode-285-Longest-Substring-With-K-Typed-Characters)
4. [LaiCode 382. Shortest Substring With K Typed Characters](#LaiCode-382-Shortest-Substring-With-K-Typed-Characters)
5. [3. Longest Substring Without Repeating Characters](#3-Longest-Substring-Without-Repeating-Characters)
   1. Same as LaiCode 253
6. [76. Minimum Window Substring](#76-Minimum-Window-Substring)

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
### Solution 2
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
### Solution 2, is it really correct?
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
                if (set.size() == k && cur > res) res = cur;
            }
        }
        return res;
    }
}
```
# [209-Minimum-Size-Subarray-Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)
[LaiCode 429. Minimum Size Subarray Sum](https://app.laicode.io/app/problem/429)
1. fast指针(j)先走, 直到满足条件(sum >= target)
2. slow指针(i)在满足条件的条件下, 走到最远
```java
class Solution {
    public int minSubArrayLen(int t, int[] a) { // TC: O(n), SC: O(1)
        int sum = 0, res = Integer.MAX_VALUE, len;
        for (int i = 0, j = 0; j < a.length;)
            if ((sum += a[j++]) >= t)  {
                while (sum - a[i] >= t) sum -= a[i++]; // move i as further as possible
                if ((len = j - i) < res) res = len;
            }

        return res == Integer.MAX_VALUE ? 0 : res;
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
## [LaiCode-285-Longest-Substring-With-K-Typed-Characters](https://app.laicode.io/app/problem/285)
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
## A maybe easier to understand version
when sliding window have k chars, shrink slow to make sure the last char is unique, then we update the result, remove the last, and keep going.

TC/SC: O(n), one pass
```java
class Solution {
    public String shortest(String s, int k) {
        if (k <= 0) return "";
        Map<Character, Integer> map = new HashMap<>();

        int min = Integer.MAX_VALUE, start = 0, len;
        for (int slow = 0, fast = 0; fast < s.length(); fast++) {
            char c = s.charAt(fast);
            map.put(c, map.getOrDefault(c, 0) + 1);

            if (map.size() == k) {
              int cnt = map.get(s.charAt(slow));
               // when this while ends, slow stops at a char that's unique
               // and map size is guaranteed to be still k
               // as we only reduced the count of dup chars
               // we never remove any char from count 1 to 0
              while (cnt > 1) {
                  map.put(s.charAt(slow++), --cnt);
                  cnt = map.get(s.charAt(slow));
              }

              // now we can update result as it's the first time we get to size k
              // and slow char is unique now
              if ((len = fast - slow + 1) < min) {
                  min = len;
                  start = slow;
              }

              // after result is updated, we remove the unique char
              // now the map size k - 1
              map.remove(s.charAt(slow++));
            }

        }

        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
    }
}
```
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

            for (int cnt; ( cnt = map.get(s.charAt(i)) ) > 1 || map.size() > k; i++)
                if (cnt > 1) map.put(s.charAt(i), --cnt);
                else map.remove(s.charAt(i));

            if (map.size() == k && (len = j - i + 1) < min) {
                min = len;
                start = i;
            }
        }

        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
    }
}
```
#### above code is too short, a more normal version
```java
class Solution {
    public String shortest(String s, int k) {
        if (k <= 0) return "";
        Map<Character, Integer> map = new HashMap<>();

        int min = Integer.MAX_VALUE, start = 0, len;
        for (int slow = 0, fast = 0; fast < s.length(); fast++) {
            char c = s.charAt(fast);
            map.put(c, map.getOrDefault(c, 0) + 1);

            int cnt = map.get(s.charAt(slow));
            while (cnt > 1 || map.size() > k) {
                if (cnt > 1) map.put(s.charAt(slow), --cnt);
                else map.remove(s.charAt(slow));
                cnt = map.get(s.charAt(++slow));
            }

            if (map.size() == k && (len = fast - slow + 1) < min) {
                min = len;
                start = slow;
            }
        }

        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
    }
}
```
# [3-Longest-Substring-Without-Repeating-Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
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
# [76-Minimum-Window-Substring](https://leetcode.com/problems/minimum-window-substring/)

[LaiCode 156. Minimum Window Substring](https://app.laicode.io/app/problem/156) ([LaiCode 293. Smallest Substring Containing All Characters Of Another String](https://app.laicode.io/app/problem/293)
is exact same problem as LaiCode 156/LeetCode 76, only difference is name of the method: smallest vs minWindow)

### 思路 (可变长度版的Anagram)
1. 其实是一个可变长度的Anagram Problem, match到的长度可能更长的原因(不可能短):
    1. 可以包括不相关的字符
    2. match到的字符可以包含多个
2. 建立一个target String的所有字符的count map
    1. 记录总共需要match的字符的个数(也就是map的size, 也就是target String中unique字符的个数), 可以命名为toMatch
3. one Pass 撸一遍 source String, 每一轮吃一个char
    1. 每吃一个char, 如果不在count map里面(取出来的count == null), 忽略
        1. 如果在count map里面那么count减一(物理意义是需要match的count少了一个)
            1. 每次count减少1, 都检查一下，如果字符对应的count减少到0了，总需要match的字符数(toMatch)就减1。
    2. 每次吃完一个新的字符，同时检查sliding window的尾部(此处用指针i表示)可不可以吐:
        1. 如果是不在target中的字符(map中拿出来的count为null)，随便不停的的吐
        2. 是在target中, 则要看需要match的数量是不是 < 0, 如果是，说明吃进去了more than enough的此字符，那也可以吐
            1. 每吐掉一个吃多了的字符，对应count应该增加1个，到0终止此次吐循环
    3. 总需要match字符数(toMatch)如果减少到0，说明找到一个target的完整match, 更新长度和起点到全剧结果(如果够短)
## Solution 1 using map
TC: O(n + m) where n = s.length() and m = t.length()

SC: O(unique(m)), size of map
```java
class Solution {
   public String minWindow(String s, String t) {
      if (t.equals("") || t.length() > s.length()) return "";
      Map<Character, Integer> map = new HashMap<>();
      for (char c : t.toCharArray()) map.put(c, map.getOrDefault(c, 0) + 1);

      int min = Integer.MAX_VALUE, start = 0, len;

      int toMatch = map.size();
      for (int i = 0, j = 0; j < s.length(); j++) {
         char c = s.charAt(j);
         Integer count = map.get(c);
         if (count == null) continue;
         map.put(c, --count);
         if (count == 0) toMatch--;

         for (; (count = map.get(c = s.charAt(i))) == null || count < 0; i++)
            if (count != null) map.put(c, ++count);

         if (toMatch == 0 && ((len = j - i + 1) < min)) {
            min = len;
            start = i;
         }
      }

      return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
   }
}
```

## Solution 2, array instead of map, which would run faster
as the String only have upper and lower case chars, so we can use 'z' - 'A' + 1 as its length;

Complexity is the same as previous Solution 1
```java
class Solution {
    public String minWindow(String s, String t) {
        if (t.equals("") || t.length() > s.length()) return "";
        Integer[] map = new Integer['z' - 'A' + 1]; // 'A' = 65, 'z' = 122; size of this map will be 58
        int toMatch = 0, len, idx;
        for (int i = 0; i < t.length(); i++)
            if (map[idx = t.charAt(i) - 'A'] == null) {
                map[idx] = 1;
                toMatch++;
            } else map[idx]++;

        int start = 0, min = Integer.MAX_VALUE;

        for (int i = 0, j = 0; j < s.length(); j++) { // i: slow pointer, j: fast pointer
            if (map[idx = s.charAt(j) - 'A'] == null) continue; // don't care about non-related chars

            if (--map[idx] == 0) toMatch--; // take in new chars
            for (; map[idx = s.charAt(i) - 'A'] == null || map[idx] < 0; i++) // remove old chars
                if (map[idx] != null) map[idx]++;

            if (toMatch == 0 && (len = j - i + 1) < min) {
                start = i;
                min = len;
            }
        }
        return min == Integer.MAX_VALUE ? "" : s.substring(start, start + min);
    }
}
```