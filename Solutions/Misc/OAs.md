# OAs

## 2 sigma
<pre>
5.. 下水道出水, Tree， 分成两个tree， 让出水量差最小
inputs:
parent: List(int): parent = j => j 是 i 的parent
inputs: List(int): 每个node的额外出水量
Example 1: 
Parent ={-1,0,0,0}  input ={10,11,10,10}
Return 19 , cut 1 for one substree, {0,2,3} for another, so best is 30 -11 = 19
Example2:
Parent ={-1, 0,0,1,1,2}  input= {1,2,2,1,1,1}
(0,2,5} and {1,3,4}   so return 0;
</pre>

### TC/SC: O(n)
```java
public class DebugSolution {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println("Case 1:");
        System.out.printf("Result : %d\n\n", sol.minSplit(new int[]{-1, 0, 0, 1, 1, 2}, new int[]{1, 2, 2, 1, 1, 1}));
        System.out.println("Case 2:");
        System.out.printf("Result : %d\n\n", sol.minSplit(new int[]{-1, 0, 1, 2}, new int[]{1, 4, 3, 4}));
        System.out.println("Case 3:");
        System.out.printf("Result : %d\n\n", sol.minSplit(new int[]{-1, 0, 0, 0}, new int[]{10, 11, 10, 10}));
    }
}

class Solution {
    public int minSplit(int[] p, int[] a) {
        System.out.printf("Parent : %s\n", Arrays.toString(p));
        System.out.printf("Input  : %s\n", Arrays.toString(a));
        int n = a.length;
        // get sum for each subtree
        for (int i = n-1; i >= 1; i--) a[p[i]] += a[i];

        System.out.printf("Sum'd  : %s\n", Arrays.toString(a));

        // find the min diff between subtree sum and rest of the tree;
        int res = Integer.MAX_VALUE;
        for (int i = 1; i < n; i++)
            res = Math.min(res, Math.abs(a[0] - 2 * a[i])); // a[0] is the sum of whole tree

        return res;
    }
}
```
### sample result prints
<pre>
Case 1:
Parent : [-1, 0, 0, 1, 1, 2]
Input  : [1, 2, 2, 1, 1, 1]
Sum'd  : [8, 4, 3, 1, 1, 1]
Result : 0

Case 2:
Parent : [-1, 0, 1, 2]
Input  : [1, 4, 3, 4]
Sum'd  : [12, 11, 7, 4]
Result : 2

Case 3:
Parent : [-1, 0, 0, 0]
Input  : [10, 11, 10, 10]
Sum'd  : [41, 11, 10, 10]
Result : 19
</pre>