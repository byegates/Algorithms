# Cisco NG OA

## 8/11/22, 3题/70min
### #1
a打印国际象棋棋盘
```java
class Solution {
    private char[][] chessBoard(int n) {
        char[][] res = new char[n][n];

        for (int i = 0; i < n; i++) for (int j = 0; j < n; j++) {
            if (i % 2 == 0) {
                res[i][j] = j % 2 == 0 ? 'W' : 'B';
            } else {
                res[i][j] = j % 2 == 0 ? 'B' : 'W';
            }
        }
        return res;
    }
}
```
#### result
<pre>
n = 7
[W, B, W, B, W, B, W]
[B, W, B, W, B, W, B]
[W, B, W, B, W, B, W]
[B, W, B, W, B, W, B]
[W, B, W, B, W, B, W]
[B, W, B, W, B, W, B]
[W, B, W, B, W, B, W]

n = 8
[W, B, W, B, W, B, W, B]
[B, W, B, W, B, W, B, W]
[W, B, W, B, W, B, W, B]
[B, W, B, W, B, W, B, W]
[W, B, W, B, W, B, W, B]
[B, W, B, W, B, W, B, W]
[W, B, W, B, W, B, W, B]
[B, W, B, W, B, W, B, W]
</pre>

### #2, bit count
[191. Number of 1 Bits](https://leetcode.com/problems/number-of-1-bits/)
### Solution 0a
```java
class Solution {
    public int hammingWeight(int n) {
        int cnt = 0;
        while (n != 0) {
            n &= n - 1; // set last bit to 0, why? try
            cnt++;
        }
        return cnt;
    }
}
```
### Solution 0b
```java
class Solution {
    public int hammingWeight(int n) {
        int cnt = 0;
        for (; n != 0;n &= n-1, cnt++) ;

        return cnt;
    }
}
```
### Solution 1
```java
class Solution {
    private int bitCount(int x) {
        int count = 0;
        for (int i = 0; i < 32; i++) {
            if (((x >> i) & 1) == 1) {
                count++;
            }
        }
        return count;
    }
}
```
#### test all integers against Java official API
```java
class Solution {
    public static void main(String[] args) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (Integer.bitCount(i) != bitCount(i))
                System.out.printf("i: %d\n", i);
        }
    }
}
```
#### Java Integer.bitCount official code
```java
class Solution {
    public static int bitCount(int i) {
        // HD, Figure 5-2
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }
}
```
### #3，validate IPv4 address
Similar to but simpler than [468. Validate IP Address](https://leetcode.com/problems/validate-ip-address/)
```java
class Solution {
    private boolean isIPv4(String s) {
        int n = s.length();
        String[] a = s.split("\\.");
        // System.out.println(Arrays.toString(a));

        if (s.charAt(s.length() - 1) == '.') return false;
        if (a.length != 4) return false;

        for (String num : a) {
            try {
                if (num.length() > 1 && num.charAt(0) == '0')
                    return false;

                int parsedNum = Integer.parseInt(num);
                if (parsedNum < 0 || parsedNum > 255)
                    return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }
}
```
#### another way:
```java
class Solution {
    private static String validatev4YH(String s) {
        String T = "VALID";
        String F = "INVALID";

        int curNum = 0, curDigit = 0, count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != '.') {
                if (curDigit == 0 && s.charAt(i) == '0' && (i != s.length() - 1 && s.charAt(i+1) != '.')) {
                    return F;
                }
                curDigit++;
                curNum = curNum * 10 + s.charAt(i) - '0';
                if (curNum > 255) return F;
            } else {
                curDigit = curNum = 0;
                if (++count > 3) return F;
            }
        }

        if (count < 3) return F;
        return T;
    }
}
```
