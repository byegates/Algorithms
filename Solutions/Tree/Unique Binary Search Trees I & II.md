# Unique Binary Search Trees II
[145. Find all binary search trees](https://app.laicode.io/app/problem/145)

[LeetCode 95. Unique Binary Search Trees II](https://leetcode.com/problems/unique-binary-search-trees-ii/)

## Description
Given an integer n, return all the structurally unique BST's (binary search trees),
which has exactly n nodes of unique values from 1 to n. Return the answer in any order.

### Example 1
![](https://assets.leetcode.com/uploads/2021/01/18/uniquebstn3.jpg)
Input: n = 3

Output: [[1,null,2,null,3],[1,null,3,2],[2,1,3],[3,1,null,null,2],[3,2,null,1]]

### Example 2
Input: n = 1

Output: [[1]]

### Constrains
1 <= n <= 8

#### Statistics
int is not enough for n = 20
<pre>
 n num of BSTs                      n!     n!/#BSTs
 1:          1 vs                    1 (        1x)
 2:          2 vs                    2 (        1x)
 3:          5 vs                    6 (        1x)
 4:         14 vs                   24 (        1x)
 5:         42 vs                  120 (        2x)
 6:        132 vs                  720 (        5x)
 7:        429 vs                 5040 (       11x)
 8:       1430 vs                40320 (       28x)
 9:       4862 vs               362880 (       74x)
10:      16796 vs              3628800 (      216x)
11:      58786 vs             39916800 (      679x)
12:     208012 vs            479001600 (     2302x)
13:     742900 vs           6227020800 (     8382x)
14:    2674440 vs          87178291200 (    32596x)
15:    9694845 vs        1307674368000 (   134883x)
16:   35357670 vs       20922789888000 (   591746x)
17:  129644790 vs      355687428096000 (  2743553x)
18:  477638700 vs     6402373705728000 ( 13404218x)
19: 1767263190 vs   121645100408832000 ( 68832475x)
20: 6564120420 vs  2432902008176640000 (370636407x)
</pre>

## Time and Space

### LaiCode
```java
public class Solution {
  public List<TreeNode> generateBSTs(int n) {
    return generateBSTs(1, n);
  }

  private List<TreeNode> generateBSTs(int l, int r) {
    List<TreeNode> res = new ArrayList<>();
    if (l > r) {
      res.add(null);
      return res;
    }

    if (l == r) {
      res.add(new TreeNode(l));
      return res;
    }

    for (int rootKey = l; rootKey <= r; rootKey++) {
      List<TreeNode> lefts = generateBSTs(l, rootKey - 1);
      List<TreeNode> rights = generateBSTs(rootKey + 1, r);

      for (TreeNode left : lefts)
        for (TreeNode right : rights)
          res.add(create(rootKey, left, right));
    }

    return res;
  }

  public TreeNode create(int val, TreeNode left, TreeNode right) {
      TreeNode root = new TreeNode(val);
      root.left = left;
      root.right = right;
      return root;
  }
}
```
### LeetCode
Some LeetCode solution re-uses same left trees to make the code run faster, but the reuses of references is logically and physically not appropriate.

LeetCode has 3-PARM tree constructor which is easier to use

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

/**
 * public class TreeNode {
 *   public int key;
 *   public TreeNode left;
 *   public TreeNode right;
 *   public TreeNode(int key) {
 *     this.key = key;
 *   }
 * }
 */
class Solution {
    public List<TreeNode> generateTrees(int n) {
        return generateTrees(1, n);
    }

    private List<TreeNode> generateTrees(int l, int r) {
        List<TreeNode> res = new ArrayList<>();
        if (l > r) {
            res.add(null);
            return res;
        }

        if (l == r) {
            res.add(new TreeNode(l));
            return res;
        }

        for (int rootKey = l; rootKey <= r; rootKey++) {
            List<TreeNode> lefts = generateTrees(l, rootKey - 1);
            List<TreeNode> rights = generateTrees(rootKey + 1, r);

            for (TreeNode left : lefts)
                for (TreeNode right : rights)
                    res.add(new TreeNode(rootKey, left, right));
        }

        return res;
    }

}
```
# Unique Binary Search Trees
[LaiCode 146. Find Number of BSTs Generated]()

[LeetCode 96. Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/)

## Time and Space
TC: two for loops, O(n^2)

SC:O(n), the dp array
```java
public class Solution {
  public int numOfTrees(int n) {
    int[] dp = new int[n + 1];
    dp[0] = 1;

    for (int i = 1; i <=n; i++)
      for (int root = 1; root <= i; root++)
        dp[i] += dp[root - 1] * dp[i - root];

    return dp[n];
  }
}
// TC: O(n^2) SC: O(n)
```