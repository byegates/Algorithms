# Cisco nonNG OA

## 8/12/22
### #1
<pre>
get sum and min of float array, round to 2 decimal place
</pre>
```java
class Solution {
    public static void main(String[] args) {
        BigDecimal bd = new BigDecimal("1.445");
        System.out.println(bd.floatValue());
        System.out.println(bd.setScale(2, RoundingMode.HALF_UP));
    }
}
```
### #2
<pre>
Decode String
(ab(d){3}){2} --> abdddabddd
(ab(c){3}d){2} --> abcccdabcccd
</pre>
#### recursion
```java
class Solution {
    static int idx;
    public static String expandedString(String s) {
        idx = 0;
        return dfs(s).toString();
    }

    public static StringBuilder dfs(String s) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (idx < s.length()) {
            char c = s.charAt(idx++);
            if (c == '(') {
                sb.append(dfs(s)); // append next level chars to current level
            } else if (c == '}') { // repeat
                StringBuilder tmp = new StringBuilder();
                for (; num > 0; num--) tmp.append(sb);
                return tmp;
            } else if (Character.isDigit(c)) {
                num = num * 10 + c - '0'; // count numbers
            } else if(c != ')' && c != '{') {
                sb.append(c); // accumulate chars
            }
        }
        return sb;
    }
}
```
#### use stack
```java
class Solution {
    public static String decodeString(String s) {
        Deque<String> str = new ArrayDeque<>();
        Deque<Integer> counter = new ArrayDeque<>();
        StringBuilder frag = new StringBuilder();
        int count = 0;
        for(int i = 0; i < s.length(); i++){
            char cur = s.charAt(i);

            if(Character.isDigit(cur)){
                count = count*10 + cur - '0';
            }else if(Character.isLetter(cur)){
                frag.append(cur);
            }else if(cur == '('){
                if(count > 0) counter.offerFirst(count);
                count = 0;
                str.offerFirst(frag.toString());
                frag.setLength(0);
            }else if(cur == '}'){
                StringBuilder prev = new StringBuilder(str.pollFirst());
                for(int j = 0; j < count; j++){
                    prev.append(frag);
                }
                frag = prev;
                count = 0;
            }
        }
        return frag.toString();
    }
}
```
### #3
<pre>
for each number <= x:
 if sum of every bit of number == target y
 count it
return total count
</pre>
```java
class Solution {
    public static int sumDigits(int x, int y) {
        int res = 0;
        for (int i = 1; i <= x; i++) {
            int sum = 0;
            for (int z = i; z > 0; z /= 10) {
                sum += z%10;
            }
            if (sum == y) res++;
        }
        return res;
    }
}
```