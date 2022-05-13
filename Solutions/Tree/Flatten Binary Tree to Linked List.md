# Flatten Binary Tree to Linked List
[LeetCode 114](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)

[LaiCode 523](https://app.laicode.io/app/problem/523)

## Description
Given a binary tree, flatten it to a linked list in-place.

For example,
Given
<pre>
         1
        / \
       2   5
      / \   \
     3   4   6
</pre>
The flattened tree should look like:
<pre>
1
 \
  2
   \
    3
     \
      4
       \
        5
         \
          6</pre>
# Solution 0: Morris Traversal
## Description
![A good visualization](https://assets.leetcode.com/users/images/1c892c17-ff56-4740-8a81-47f40d38d36e_1620996109.3450835.png "A good visualization")

![A even better visualization](https://i.imgur.com/sqnrz9m.gif "Dynamic")

[JS, Python, Java, C++ | Simple O(1) Space & Recursive Solutions w/ Explanation](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/discuss/1207642/JS-Python-Java-C%2B%2B-or-Simple-O(1)-Space-and-Recursive-Solutions-w-Explanation)
## Time Complexity
O(n)
## Space Complexity
O(1)

```java
import util.TreeNode;

class Solution {
    public TreeNode flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                TreeNode runner = curr.left;
                while (runner.right != null) runner = runner.right;
                runner.right = curr.right;
                curr.right = curr.left;
                curr.left = null;
            }
            curr = curr.right;
        }
        return root;
    }
}
```
# Solution 0b
## Time Complexity
O(n)
## Space Complexity
O(1)
```java
import util.TreeNode;

class Solution {
    public TreeNode flatten(TreeNode root) {
        TreeNode head = null, curr = root;
        while (head != root) {
            if (curr.right == head) curr.right = null;
            if (curr.left == head) curr.left = null;
            if (curr.right != null) curr = curr.right;
            else if (curr.left != null) curr = curr.left;
            else {
                curr.right = head;
                head = curr;
                curr = root;
            }
        }
        return root;
    }
}
```
# Solution 1: pre Order
## Time Complexity
O(n)
## Space Complexity
O(height)
```java
class Solution {
  TreeNode prev;
  public TreeNode flatten(TreeNode root) {
    dfs(root);
    return root;
  }

  private void dfs(TreeNode root) {
    if (root == null) return;
    TreeNode left = root.left;
    TreeNode right = root.right;
    
    if (prev != null) prev.right = root;

    root.left = null;

    prev = root;
    dfs(left);
    dfs(right);
  }
}
```
# Solution 2: Mirror-postOrder
## Time Complexity
O(n)
## Space Complexity
O(height)
```java
class Solution {
  TreeNode prev;
  public TreeNode flatten(TreeNode root) {
    dfs(root);
    return root;
  }

  private void dfs(TreeNode root) {
    if (root == null) return;
    dfs(root.right);
    dfs(root.left);
    
    root.right = prev;
    root.left = null;

    prev = root;
  }
}

```