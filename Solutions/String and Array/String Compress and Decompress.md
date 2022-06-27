# [394. Decode String](https://leetcode.com/problems/decode-string/)
[LaiCode 621. Decompress String III](https://app.laicode.io/app/problem/621)
TC: O(n), SC: O(# of '[')
```java
class Solution {
    int idx;
    public String decodeString(String s) {
        idx = 0;
        return onePass(s).toString();
    }

    public StringBuilder onePass(String s) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        
        while (idx < s.length()) {
            char c = s.charAt(idx++);
            if (c >= '0' && c <= '9') num = c - '0' + num * 10;
            else if (c == '[') {
                StringBuilder sb0 = onePass(s);
                for ( ; num > 0; num--) sb.append(sb0);
            } else if (c == ']') break;
            else sb.append(c);
        }
        
        return sb;
    }
}
```