# [224. Basic Calculator](https://leetcode.com/problems/basic-calculator/)
[448. Basic Calculator](https://app.laicode.io/app/problem/448)

## Solution 1, one signs stack
TC/SC: O(n)
```java
class Solution {
    public int calculate(String s) {
        int curNum = 0, prevSign = 1, curSign = 1, res = 0;
        int[] signs = new int[s.length() / 2];
        int top = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') curNum = curNum * 10 + (c - '0');
            if (c == '(') {
                signs[++top] = prevSign;
                curSign *= prevSign;
                prevSign = 1;
            } else if (c == '+' || c == '-' || c == ')' || i == s.length() - 1) {
                res += curNum * prevSign * curSign;
                if (c == ')') curSign *= signs[top--];
                else if (c == '+') prevSign =  1;
                else if (c == '-') prevSign = -1;
                curNum = 0;
            }
        }

        return res;
    }
}
```
## Solution 1b, two stacks
TC/SC: O(n)
```java
class Solution {
    public int calculate(String s) {
        int curNum = 0, prevSign = 1, curSum = 0;
        int[]  nums = new int[s.length() / 2];
        int[] signs = new int[s.length() / 2];
        int t1 = -1, t2 = -1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') curNum = curNum * 10 + (c - '0');
            if (c == '(') {
                nums[++t1] = curSum;
                signs[++t2] = prevSign;
                prevSign = 1;
                curSum = 0;
            } else if (c == '+' || c == '-' || c == ')' || i == s.length() - 1) {
                curSum += curNum * prevSign;
                curNum = 0;
                if (c == ')') {
                    curSum *= signs[t2--];
                    curSum +=  nums[t1--];
                } else if (c == '+') prevSign =  1;
                else if (c == '-') prevSign = -1;
            }
        }

        return curSum;
    }
}
```
# [227. Basic Calculator II](https://leetcode.com/problems/basic-calculator-ii/)
[450. Basic Calculator II](https://app.laicode.io/app/problem/450)

TC: O(n)

SC: O(1)
```java
class Solution {
    public int calculate(String s) {
        int curNum = 0, preNum = 0, result = 0;
        char prevSign = '+';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') curNum = curNum * 10 + c - '0'; // handle numbers with more than one digit
            if (c == '+' || c == '-' || c == '*' || c == '/' || i == s.length() - 1) {
                switch (prevSign) {
                    case '+' : result += preNum; preNum =  curNum; break;
                    case '-' : result += preNum; preNum = -curNum; break;
                    case '*' : preNum *= curNum; break;
                    case '/' : preNum /= curNum; break;
                }
                curNum = 0;
                prevSign = c;
            }
        }
        return result + preNum;
    }
}
```

# [772. Basic Calculator III](https://leetcode.com/problems/basic-calculator-iii/)
TC: O(n), exact one round of scan, no Queue or stack.

SC: O(number of brackets)

```java
class Solution {
    int idx; // for linear scan the input string between recursion calls
    public int calculate(String s) {
        idx = 0; // everytime the Solution object is called, we should to initialize the index
        return calc2(s);
    }
    
    public int calc2(String s) { // exact calc2 code, except two lines of logic added to handle '(' and ')'
        int curNum = 0, preNum = 0, result = 0;
        char prevSign = '+';
        while (idx < s.length()) { // 1
            char c = s.charAt(idx++);
            if (c == '(') curNum = calc2(s); // recursively calculate everything between '(' and ')'
            else if (c >= '0' && c <= '9') curNum = curNum * 10 + c - '0'; // handle numbers with more than one digit
            if (c == '+' || c == '-' || c == '*' || c == '/' || c == ')' || idx == s.length()) {
                switch (prevSign) {
                    case '+' : result += preNum; preNum =  curNum; break;
                    case '-' : result += preNum; preNum = -curNum; break;
                    case '*' : preNum *= curNum; break;
                    case '/' : preNum /= curNum; break;
                }
                if (c == ')') break; // finish current level processing, go 1 level back
                curNum = 0;
                prevSign = c;
            }
        }
        return result + preNum;
    }
}
```
[0ms submission](https://leetcode.com/submissions/detail/724750952/)
