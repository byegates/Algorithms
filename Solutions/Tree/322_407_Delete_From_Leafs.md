# Delete Zero Nodes From Leaf
[322. Delete Zero Nodes From Leaf](https://app.laicode.io/app/problem/322)

[LeetCode 1325. Delete Leaves With a Given Value](https://leetcode.com/problems/delete-leaves-with-a-given-value/)
### TC & SC
TC: O(n)

SC: O(height)

### LaiCode
```java
class Solution {
  public TreeNode deleteZero(TreeNode root) {
    if (root == null) return null;
    root.left  = deleteZero(root.left );
    root.right = deleteZero(root.right);
    if (root.key == 0 && root.left == null && root.right == null) return null;
    return root;
  }
}
```
### LeetCode
```java
class Solution {
  public TreeNode removeLeafNodes(TreeNode root, int target) {
    if (root == null) return null;
    root.left  = removeLeafNodes(root.left , target);
    root.right = removeLeafNodes(root.right, target);
    if (root.val == target && root.left == null && root.right == null) return null;
    return root;
  }
}
```
# 407. Trim Binary Tree by Path Cost
[LaiCode 407. Trim Binary Tree by Path Cost](https://app.laicode.io/app/problem/407)

### TC & SC
as we only go down depth of k:

TC: Omin(n, (2^k))

SC: O(min(height, k))

```java
class Solution {
  public TreeNode trimTree(TreeNode root, int k) {
    return dfs(root, k - 1, 0); // if at level k - 1 or smaller, a node is leaf node, then it should be deleted
  }

  private TreeNode dfs(TreeNode root, int k, int depth) {
    if (root == null || depth >= k) return root; // if k == 0 we dont delete anything, as we only delete level < k
    root.left = dfs(root.left, k, depth + 1);
    root.right = dfs(root.right, k, depth + 1);
    // once we get here, depth is < k for sure, as depth >= k has been returned by corner case
    if (root.left == null && root.right == null) return null;
    return root;
  }
}
```