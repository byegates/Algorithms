# Table of Contents
1. [BST Pre-Order and Post-Order Reconstruction and Validation](#Binary-Search-Tree-Pre-and-Post-Order-Reconstruction-and-Validation)
   1. [210. Reconstruct BST With Preorder Traversal](#Reconstruct-Binary-Search-Tree-With-Preorder-Traversal)
   2. [211. Reconstruct BST With Postorder Traversal](#Reconstruct-Binary-Search-Tree-With-Postorder-Traversal)
   3. [LeetCode 255. Verify Preorder Sequence in BST](#Verify-Preorder-Sequence-in-Binary-Search-Tree)
   4. [304. Valid Postorder Traversal Of BST](#Valid-Post-order-Traversal-Of-Binary-Search-Tree)
2. [213. Reconstruct Binary Tree With Preorder And Inorder](#Reconstruct-Binary-Tree-With-Preorder-And-Inorder)
   1. [301. Get Post-order Sequence By Pre-order and In-order](#Get-Post-order-Sequence-By-Pre-order-and-In-order)
3. [212. Reconstruct BST With Level Order Traversal](#Reconstruct-BST-With-Level-Order-Traversal)
4. [215. Reconstruct Binary Tree With Levelorder And Inorder](#Reconstruct-Binary-Tree-With-Levelorder-And-Inorder)

# Binary-Search-Tree-Pre-and-Post-Order-Reconstruction-and-Validation

## Reconstruct-Binary-Search-Tree-With-Preorder-Traversal
[LaiCode 210 Reconstruct BST With Preorder Traversal](https://app.laicode.io/app/problem/210)

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
## Reconstruct-Binary-Search-Tree-With-Postorder-Traversal
[LaiCode 211 Reconstruct BST With Postorder Traversal](https://app.laicode.io/app/problem/211)

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
        if (cur < 0 || post[cur] < min) return null;
        TreeNode root = new TreeNode(post[cur--]);
        root.right = reconstruct(post, root.key);
        root.left = reconstruct(post, min);
        return root;
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
# Reconstruct-Binary-Tree-With-Preorder-And-Inorder
[LaiCode 213. Reconstruct Binary Tree With Preorder And Inorder](https://app.laicode.io/app/problem/213)

[LeetCode 105. Construct Binary Tree from Preorder and Inorder Traversal](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/)
## Solution 1
```java
class Solution { // TC: O(n), SC: O(height)
   static class Idx {
      int i = 0, p = 0; // i as index for inorder, p as index for preorder
   }
   public TreeNode buildTree(int[] pre, int[] in) {
      return dfs(in, pre, new Idx(), Integer.MAX_VALUE);
   }

   private TreeNode dfs(int[] in, int[] pre, Idx idx, int parentKey) {
      if (idx.p == pre.length || in[idx.i] == parentKey) return null;

      TreeNode root = new TreeNode(pre[idx.p++]);
      root.left  = dfs(in, pre, idx, root.val);
      idx.i++;
      root.right = dfs(in, pre, idx,  parentKey);
      return root;
   }
}
```
## Solution 2, using value to index map of inOrder and index boundary
```java
class Solution {
  public TreeNode reconstruct(int[] in, int[] pre) { // TC: O(3n) → O(n), SC: O(height)
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) map.put(in[i], i);

    return reconstruct(in, 0, in.length - 1, pre, 0, pre.length - 1, map);
  }

  private TreeNode reconstruct(int[] in, int inL, int inR, int[] pre, int preL, int preR, Map<Integer, Integer> map) {
    if (inR < inL) return null;

    TreeNode root = new TreeNode(pre[preL]);

    int rootIdx = map.get(pre[preL]);
    int leftLen = rootIdx - inL;

    root.left  = reconstruct(in, inL, rootIdx - 1, pre, preL + 1, preL + leftLen, map);
    root.right = reconstruct(in, rootIdx + 1, inR, pre, preL + leftLen + 1, preR, map);

    return root;
  }
}
```
# Get-Post-order-Sequence-By-Pre-order-and-In-order
[LaiCode 301. Get Post-order Sequence By Pre-order and In-order](https://app.laicode.io/app/problem/301)
## Description
Given Inorder and Preorder traversals of a binary tree, get the Postorder traversal without reconstructing a binary tree.
<pre>
Assumptions:
The given Inorder and Preorder traversals are guaranteed to be valid.

Examples:
Input:
Inorder traversal in[] = {4, 2, 5, 1, 3, 6}
Preorder traversal pre[] = {1, 2, 4, 5, 3, 6}

Output:
Postorder traversal is {4, 5, 2, 6, 3, 1}
</pre>

## Solution 1: TC: O(n), SC: O(height)
```java
class Solution { // p is index for preOrder, po is index for post order(output)
  static class Idx {
    int i = 0, p = 0, po = 0;
  }

  public int[] postOrder(int[] pre, int[] in) {
    int[] po = new int[in.length];
    reconstruct(in, pre, po, new Idx(), Integer.MAX_VALUE);
    return po;
  }

  private void reconstruct(int[] in, int[] pre, int[] po, Idx idx, int parentKey) {
    if (idx.p == pre.length || in[idx.i] == parentKey) return;

    int rootKey = pre[idx.p++]; // preOrder
    reconstruct(in, pre, po, idx, rootKey);
    idx.i++; // inOrder
    reconstruct(in, pre, po, idx, parentKey);
    po[idx.po++] = rootKey; // postOrder
  }
}
```
## Solution 2, postOrder
```java
class Solution {
  int idx = 0; // index for post order, backwards
  public int[] postOrder(int[] pre, int[] in) { // TC: O(3n) → O(n), SC: O(height)
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < in.length; i++) map.put(in[i], i);

    int[] po = new int[in.length];
    dfs(po, in, 0, in.length - 1, pre, 0, pre.length - 1, map);
    return po;
  }

  private void dfs(int[] po, int[] in, int inL, int inR, int[] pre, int preL, int preR, Map<Integer, Integer> map) {
    if (inR < inL) return;

    int rootIdx = map.get(pre[preL]);
    int leftLen = rootIdx - inL;

    dfs(po, in, inL, rootIdx - 1, pre, preL + 1, preL + leftLen, map); // pretend to create left  subtree
    dfs(po, in, rootIdx + 1, inR, pre, preL + leftLen + 1, preR, map); // pretend to create right subtree

    po[idx++] = pre[preL]; // post order
  }
}
```
## Solution 2b, anti-pre order, backwards
```java
class Solution {
  int idx; // index for post order, backwards
  public int[] postOrder(int[] pre, int[] in) { // TC: O(3n) → O(n), SC: O(height)
    Map<Integer, Integer> map = new HashMap<>();
    idx = in.length - 1;
    for (int i = 0; i < in.length; i++) map.put(in[i], i);

    int[] po = new int[in.length];
    dfs(po, in, 0, in.length - 1, pre, 0, pre.length - 1, map);
    return po;
  }

  private void dfs(int[] po, int[] in, int inL, int inR, int[] pre, int preL, int preR, Map<Integer, Integer> map) {
    if (inR < inL) return;

    po[idx--] = pre[preL]; // anti-preOrder to create postOrder array

    int rootIdx = map.get(pre[preL]);
    int leftLen = rootIdx - inL;

    dfs(po, in, rootIdx + 1, inR, pre, preL + leftLen + 1, preR, map); // pretend to create right subtree
    dfs(po, in, inL, rootIdx - 1, pre, preL + 1, preL + leftLen, map); // pretend to create left  subtree
  }
}
```
# Reconstruct-BST-With-Level-Order-Traversal
[LaiCode 212. Reconstruct BST With Level Order Traversal](https://app.laicode.io/app/problem/212)

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
# Reconstruct-Binary-Tree-With-Levelorder-And-Inorder
[LaiCode 215. Reconstruct Binary Tree With Levelorder And Inorder](https://app.laicode.io/app/problem/215)

## Description
Given the levelorder and inorder traversal sequence of a binary tree, reconstruct the original tree.

<pre>
Assumptions
The given sequences are not null, and they have the same length
There are no duplicate keys in the binary tree

Examples
levelorder traversal = {5, 3, 8, 1, 4, 11}
inorder traversal = {1, 3, 4, 5, 8, 11}

the corresponding binary tree is
      5
    /    \
   3      8
 /   \     \
1     4     11
</pre>

## Solution 0 Linear scan with queue and map based on index
If you find this solution hard to understand, go to solution 1 which is decent and good for interviews generally.
```java
class Solution { //TC: O(n), SC: O(n)
   private static class Aggregate {
      public TreeNode node;
      public int low, high, idx;
      public Aggregate(int key, int idx, int low, int high) {
         node = new TreeNode(key);
         this.low = low;
         this.high = high;
         this.idx = idx;
      }
   }

   public TreeNode reconstruct(int[] in, int[] lvl) {
      if (in.length == 0) return null;

      Queue<Aggregate> frontier = new ArrayDeque<>();
      Map<Integer, Integer> map = valToIdxMap(in, new HashMap<>());

      frontier.add(new Aggregate(lvl[0], map.get(lvl[0]), 0, in.length));
      TreeNode root = frontier.element().node;

      for (int i = 1; i < lvl.length; i++) {
         int idx = map.get(lvl[i]);
         for (Aggregate front = frontier.element(); (!(front.low <= idx && idx < front.idx) || front.node.left != null) && (!(front.idx + 1 <= idx && idx < front.high) || front.node.right != null); front = frontier.element())
            frontier.remove();

         Aggregate front = frontier.element();
         Aggregate aggregate;
         if (idx < front.idx) {
            aggregate = new Aggregate(lvl[i], idx, front.low, front.idx);
            front.node.left = aggregate.node;
         } else {
            aggregate = new Aggregate(lvl[i], idx, front.idx + 1, front.high);
            front.node.right = aggregate.node;
         }

         frontier.add(aggregate);
      }

      return root;
   }

   private Map<Integer, Integer> valToIdxMap(int[] in, Map<Integer, Integer> map) {
      for (int i = 0; i < in.length; i++) map.put(in[i], i);
      return map;
   }
}
```

## Solution 1 level order value to index map
```java
class Solution { // TC: O(n*height): O(nlog(n)) ~ O(n^2), SC: O(n)
  public TreeNode reconstruct(int[] in, int[] lvl) {
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < lvl.length; i++) map.put(lvl[i], i);
    return reconstruct(in, map, 0, in.length - 1);
  }

  private TreeNode reconstruct(int[] in, Map<Integer, Integer> map, int inL, int inR){
    if (inL > inR) return null;

    int rootIdx = inL;
    for (int i = rootIdx + 1; i <= inR; i++) // find the root(min) steps of inorder numbers in lvl order
      if (map.get(in[i]) < map.get(in[rootIdx]))
        rootIdx = i;

    TreeNode root = new TreeNode(in[rootIdx]);
    root.left = reconstruct(in, map, inL, rootIdx - 1);
    root.right = reconstruct(in, map, rootIdx + 1, inR);

    return root;
  }
}
```
