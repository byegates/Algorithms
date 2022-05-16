# Reconstruct Binary Search Tree With Level Order Traversal
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