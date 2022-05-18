# Table of Contents
1. [Binary-Search-Tree-Pre-and-Post-Order-Reconstruction-and-Validation](#Binary-Search-Tree-Pre-and-Post-Order-Reconstruction-and-Validation)
   1. [Reconstruct-Binary-Search-Tree-With-Postorder-Traversal](#Reconstruct-Binary-Search-Tree-With-Postorder-Traversal)
   2. [Reconstruct-Binary-Search-Tree-With-Preorder-Traversal](#Reconstruct-Binary-Search-Tree-With-Preorder-Traversal)
   3. [Valid-Post-order-Traversal-Of-Binary-Search-Tree](#Valid-Post-order-Traversal-Of-Binary-Search-Tree)
   4. [Verify-Preorder-Sequence-in-Binary-Search-Tree](#Verify-Preorder-Sequence-in-Binary-Search-Tree)
2. [Reconstruct Binary Tree With Preorder And Inorder](#Reconstruct-Binary-Tree-With-Preorder-And-Inorder)
3. [Reconstruct Binary Search Tree With Level Order Traversal](#Reconstruct-Binary-Search-Tree-With-Level-Order-Traversal)
# Binary-Search-Tree-Pre-and-Post-Order-Reconstruction-and-Validation
## Reconstruct-Binary-Search-Tree-With-Postorder-Traversal
[LaiCode 210 Reconstruct Binary Search Tree With Postorder Traversal](https://app.laicode.io/app/problem/210)

TC: O(n)

SC: O(height)
```java
class Solution {
    int cur;
    public TreeNode reconstruct(int[] post) {
        cur = post.length - 1;
        return reconstruct(post, Integer.MIN_VALUE);
    }

    private TreeNode reconstruct(int[] post, int min) {
        if (cur < 0 || post[cur] <= min) return null;
        TreeNode root = new TreeNode(post[cur--]);
        root.right = reconstruct(post, root.key);
        root.left = reconstruct(post, min);
        return root;
    }
}
```

## Reconstruct-Binary-Search-Tree-With-Preorder-Traversal
[LaiCode 211 Reconstruct Binary Search Tree With Postorder Traversal](https://app.laicode.io/app/problem/211)

TC: O(n)

SC: O(height)
```java
class Solution {
  int cur = 0;
  public TreeNode reconstruct(int[] pre) {
    return dfs(pre, Integer.MAX_VALUE);
  }

  private TreeNode dfs(int[] pre, int max) {
    if (cur == pre.length || pre[cur] > max)
      return null;

    TreeNode root = new TreeNode(pre[cur++]);
    root.left = dfs(pre, root.key);
    root.right = dfs(pre, max);
    return root;
  }
}
```
[LeetCode 1008. Construct Binary Search Tree from Preorder Traversal](https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/)

TC: O(n)

SC: O(height)
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

class Solution {
    int cur;
    public TreeNode bstFromPreorder(int[] pre) {
        return dfs(pre, Integer.MAX_VALUE);
    }

    private TreeNode dfs(int[] pre, int max) {
        if (cur == pre.length || pre[cur] > max) return null;
        return new TreeNode(pre[cur], dfs(pre, pre[cur++]), dfs(pre, max));
    }
}
```

## Valid-Post-order-Traversal-Of-Binary-Search-Tree
[LaiCode 304 Valid Post-order Traversal Of Binary Search Tree](https://app.laicode.io/app/problem/304)

TC: O(n)

SC: O(height)
```java
class Solution {
  int cur;
  public boolean validPostOrder(int[] post) {
    cur = post.length - 1;
    dfs(post, Integer.MIN_VALUE, Integer.MAX_VALUE);
    return cur == -1;
  }

  private void dfs(int[] post, int min, int max) {
    if (cur < 0 || post[cur] <= min || post[cur] >= max) return;

    int rootKey = post[cur--];
    dfs(post, rootKey, max);
    dfs(post, min, rootKey);
  }
}
```
## Verify-Preorder-Sequence-in-Binary-Search-Tree
[LeetCode 255. Verify Preorder Sequence in Binary Search Tree](https://leetcode.com/problems/verify-preorder-sequence-in-binary-search-tree/)

TC: O(n)

SC: O(height)
```java
class Solution {
    int idx = 0;
    public boolean verifyPreorder(int[] pre) {
        dfs(pre, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return idx == pre.length;
    }
    
    private void dfs(int[] pre, int min, int max) {
        if (idx == pre.length || pre[idx] <= min || pre[idx] >= max) return;
        
        int rootKey = pre[idx++]; // must increase the index here
        dfs(pre, min, rootKey); // pretend to create left subtree
        dfs(pre, rootKey, max); // pretend to create right subtree 
    }
}
```
# Reconstruct-Binary-Tree-With-Preorder-And-Inorder
[LaiCode 213. Reconstruct Binary Tree With Preorder And Inorder](https://app.laicode.io/app/problem/213)

TC: O(n)

SC: O(height)
```java
class Solution {
    int preIdx = 0, inIdx = 0;
    public TreeNode reconstruct(int[] in, int[] pre) {
        return reconstruct(in, pre, Integer.MAX_VALUE);
    }

    private TreeNode reconstruct(int[] in, int[] pre, int parentKey) {
        if (preIdx >= in.length || in[inIdx] == parentKey) return null;

        TreeNode root = new TreeNode(pre[preIdx++]);
        root.left  = reconstruct(in, pre, root.key);
        inIdx++;
        root.right = reconstruct(in, pre,  parentKey);
        return root;
    }
}
```
# Reconstruct-Binary-Search-Tree-With-Level-Order-Traversal
[LaiCode 212. Reconstruct Binary Search Tree With Level Order Traversal](https://app.laicode.io/app/problem/212)

## Description
Given the levelorder traversal sequence of a binary search tree, reconstruct the original tree.

Assumptions

The given sequence is not null
There are no duplicate keys in the binary search tree
Examples

levelorder traversal = {5, 3, 8, 1, 4, 11}

the corresponding binary search tree is
<pre>
        5
      /    \
    3        8
  /   \        \
1      4        11
</pre>

# Solution 0: Linear Traversal with validation
## Time and Space
TC: O(n), we traverse the array once

SC: O(n), the queue will have the last level (n/2) at max for a complete tree, will have only 1 node in it at any time for a linked list tree

```java
class Solution {
    static class Node {
        TreeNode node;
        int min, max;
        Node(TreeNode node, int min, int max) {
            this.node = node;
            this.min = min;
            this.max = max;
        }
    }

    public TreeNode reconstruct(int[] lvl) {
        if (lvl == null || lvl.length == 0) return null;
        Queue<Node> q = new ArrayDeque<>();
        TreeNode root = new TreeNode(lvl[0]);
        q.offer(new Node(root, Integer.MIN_VALUE, Integer.MAX_VALUE));

        for (int i = 1; i < lvl.length;) {
            Node cur = q.poll();
            if (lvl[i] < cur.node.key && lvl[i] > cur.min)
                q.offer(new Node(cur.node.left = new TreeNode(lvl[i++]), cur.min, cur.node.key));

            if (i < lvl.length && lvl[i] > cur.node.key && lvl[i] < cur.max)
                q.offer(new Node(cur.node.right = new TreeNode(lvl[i++]), cur.node.key, cur.max));
        }


        return root;
    }
}
```
# Solution 1: Naive Insert
## Time and Space
### TC: O(n * height): O(n*log(n)) ~ O(n^2)
For every insert, the code traverses the current height of the tree, so,

#### for a complete tree
| lvl | # of <br/>nodes | traveled<br/>height | Total     |
|-----|-----------------|---------------------|-----------|
| 1   | 1               | 1                   | 1 * 1     |
| 2   | 2               | 2                   | 2 * 2     |
| 3   | 4 (2^3-1)       | 3                   | 4 * 3     |
| ... | ...             | ...                 | ...       |
| h   | 2^(h-1) (n/2)   | h (log(n))          | 2^(-=1)*h |

Total is roughly n(log(n))

#### for a linked list tree
1 + 2 + 3 + ... + n = n^2
### SC: O(height): (log(n) ~ n)
### Code
```java
class Solution {
  public TreeNode reconstruct(int[] lvl) {
    if (lvl == null || lvl.length == 0) return null;
    TreeNode root = new TreeNode(lvl[0]);
    for (int i = 1; i < lvl.length; i++)
      insert(root, lvl[i]);
    return root;
  }

  public TreeNode insert(TreeNode root, int val) {
    if (root == null) return new TreeNode(val);
    if (root.key < val) root.right = insert(root.right, val);
    if (root.key > val) root.left = insert(root.left, val);
    return root;
  }
}
```
# Solution 2 Using Integer List
## Time and Space
Time: O(n*height)

Space:
real time GC: O(n)

with real time GC: O(n*height)
```java
class Solution {
    public TreeNode reconstruct(int[] pre) {
        List<Integer> list = new ArrayList<>();
        for (int x : pre) list.add(x);
        return helper(list);
    }

    private TreeNode helper(List<Integer> list) {
        if (list.size() == 0) return null;
        
        // Do not remove, extra overhead
        TreeNode root = new TreeNode(list.get(0));
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int i = 1; i < list.size(); i++)
            if (list.get(i) < root.key) left.add(list.get(i));
            else right.add(list.get(i));

        root.left = helper(left);
        root.right = helper(right);

        return root;
    }
}
```
