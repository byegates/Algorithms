### 一万米高空总结
这两题看清楚本质的话，做的事情很简单:
InOrder(300) or PreOrder(523) Traverse a Tree.
然后按照Traverse的顺序把pre和cur之间单链或者双链起来。

先不考虑有点跳跃的SpaceO(1)的解法。常规做法的话，就是要先把InOrder/PreOrder的traversal(不管是iterative还是recursion)给写出来，

之后呢，因为要前后连起来，再定义一个prev，按照Order的顺序把prev在合适的地方设置好，设置完了之后，就会发现，前后连起来很容易：

1. 双链: prev.right = curr; curr.left = prev;
2. 单链: prev.right = curr; curr.left = null;

然后注意一下小细节，prev最开始是空，不能直接.right, 会NPE, 所以单独判断一次。（这个时候也是InOrder记录你要返回的node的好机会，因为prev == null的时候肯定是第一个node）

按照这个思路的话，iterative写法比recursion更容易(不用传prev), 更好写。

还有一个要注意的是，preOrder recursion写法，树的左右结构会被破坏掉，所以就先用两个变量在左右被断开前，把左右保存一下。

# [LaiCode 300. Convert Binary Tree To Doubly Linked List I](https://app.laicode.io/app/problem/300)
## Space O(height), regular Solution
### Standard Iterative
比标准的InOrder Traversal多4行代码，注意哪4行，以及每一行在干嘛
```java
class Solution { // TC: O(n)
  public TreeNode toDoubleLinkedList(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode cur = root, prev = null;
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.offerFirst(cur);
        cur = cur.left;
      } else {
        cur = stack.pollFirst();
        if (prev == null) root = cur;
        else prev.right = cur;
        cur.left = prev;
        prev = cur;
        cur = cur.right;
      }
    }

    return root;
  }
}
```
### Recursion inOrder
也是比标准的InOrder多四行，什么是标准的InOrder:
1. 先去左边
2. 对自己做一些事情
3. 再去右边
```java
class Solution { // TC: O(n), SC: O(height)
  TreeNode prev, head;
  public TreeNode toDoubleLinkedList(TreeNode root) {
    prev = head = null;
    dfs(root);
    return head;
  }

  private void dfs(TreeNode root) {
    if (root == null) return;
    dfs(root.left);
    if (prev == null) head = root;
    else prev.right = root;
    root.left = prev;
    prev = root;
    dfs(root.right);
  }
}
```
## Space O(1) Solution
只看代码不一定好理解，有机会搞个图
```java
class Solution { // TC: O(n), SC: O(1)
  public TreeNode toDoubleLinkedList(TreeNode root) {
    TreeNode curr = root;
    TreeNode prev = null;
    while (curr != null) {
      if (curr.left != null) { // process left subtree until null found
        TreeNode left = curr.left;
        TreeNode runner = left;
        while (runner.right != null) runner = runner.right;
        runner.right = curr;
        if (prev != null) prev.right = curr.left;
        curr.left = null; // must cut here, otherwise left tree process will loop indefinitely
        curr = left;
      } else {
        curr.left = prev;
        if (prev == null) root = curr;
        prev = curr;
        curr = curr.right;
      }
    }

    return root;
  }
}
```
## [43. In-order Traversal Of Binary Tree (iterative)](https://app.laicode.io/app/problem/43)
We could use space O(1) solution from above to do this too. (Just as a fun demonstration, this way the structure of the tree is changed, be aware)
```java
class Solution {
  public List<Integer> inOrder(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    TreeNode curr = root;
    TreeNode prev = null;
    while (curr != null) {
      if (curr.left != null) { // process left subtree until null found
        TreeNode left = curr.left;
        TreeNode runner = left;
        while (runner.right != null) runner = runner.right;
        runner.right = curr;
        if (prev != null) prev.right = curr.left;
        curr.left = null; // must cut here, otherwise left tree process will loop indefinitely
        curr = left;
      } else {
        res.add(curr.key);
        prev = curr;
        curr = curr.right;
      }
    }

    return res;
  }
}
```
# [LeetCode 426. Convert Binary Search Tree to Sorted Doubly Linked List](https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/)
## Space O(height)
### Iterative InOrder
跟LaiCode题的唯一区别是要把最后一个node和第一个node连成环。也是一个标准的InOrder Traversal
```java
class Solution {
    public Node treeToDoublyList(Node root) {

    Deque<Node> stack = new ArrayDeque<>();
    Node cur = root, prev = null;
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.offerFirst(cur);
        cur = cur.left;
      } else {
        cur = stack.pollFirst();
        if (prev == null) root = cur;
        else prev.right = cur;
        cur.left = prev;
        prev = cur;
        cur = cur.right;
      }
    }
    
    if (prev != null) {
        prev.right = root;
        root.left = prev;
    }

    return root;
    
    }
}
```
### Recursion InOrder
跟LaiCode题的唯一区别是要把最后一个node和第一个node连成环。也是一个标准的InOrder Traversal
```java
class Solution {
  Node prev, head;
  public Node treeToDoublyList(Node root) {
    prev = head = null;
    dfs(root);
      
  if (prev != null) {
        prev.right = head;
        head.left = prev;
    }

    return head;
  }

  private void dfs(Node root) {
    if (root == null) return;
    dfs(root.left);
    if (prev == null) head = root;
    else prev.right = root;
    root.left = prev;
    prev = root;
    dfs(root.right);
  }
}
```
## Space O(1)
```java
class Solution { // TC: O(n), SC: O(1)
    public Node treeToDoublyList(Node root) {

    Node curr = root;
    Node prev = null;
    while (curr != null) {
      if (curr.left != null) { // process left subtree until null found
        Node left = curr.left;
        Node runner = left;
        while (runner.right != null) runner = runner.right;
        runner.right = curr;
        if (prev != null) prev.right = curr.left;
        curr.left = null; // must cut here, otherwise left tree process will loop indefinitely
        curr = left;
      } else {
        curr.left = prev;
        if (prev == null) root = curr;
        prev = curr;
        curr = curr.right;
      }
    }
        
    if (root != null) {
        prev.right = root;
        root.left = prev;
    }
    
    return root;
    }
}
```
# [LaiCode 523. Flatten Binary Tree to Linked List](https://app.laicode.io/app/problem/523)
[LeetCode 114. Flatten Binary Tree to Linked List](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)

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

## Space O(height) regular Solution
### Solution 1: standard Iterative preOrder Traversal
Looks quite simple? (Simpler than recursion, you could say)
```java
class Solution { // TC: O(n)
  public TreeNode flatten(TreeNode root) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    if (root != null) stack.offerFirst(root);
    TreeNode prev = null;
    while (!stack.isEmpty()) {
      TreeNode cur = stack.pollFirst();
      if (cur.right != null) stack.offerFirst(cur.right);
      if (cur.left  != null) stack.offerFirst(cur.left );
      if (prev != null) prev.right = cur;
      prev = cur;
      cur.left = null;
    }

    return root;
  }
}
```
### Solution 2 Recursion, PreOrder
标准的PreOrder Recursion
1. 对自己先做一些事情
2. 去自己左边
3. 去自己右边

当然，对自己做事情之前要把左右先保存一下，那是因为如果不保存的话，就去不了自己的左边和右边了
```java
class Solution { // TC : O(n), SC: O(height)
  TreeNode prev;
  public TreeNode flatten(TreeNode root) {
    prev = null;
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
### Solution 3 Recursion, Anti-PostOrder
PreOrder是 中左右

PostOrder是 左右中

这一题用的是 右左中（类似于反着postOrder），因为右左中traverse的结果，直接反序就是：中左右，也就是我们想要的PreOrder

所以这个右左中的Recursion也很标准:
1. 先去右边
2. 再去左边
3. 对自己做些事情
```java
class Solution { // TC: O(n), SC: O(height)
  TreeNode prev;
  public TreeNode flatten(TreeNode root) {
    prev = null;
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
## Solution 0: Space O(1) (简装版Morris Traversal?)
### 图示一下 Space O(1) 的过程
![A good visualization](https://assets.leetcode.com/users/images/1c892c17-ff56-4740-8a81-47f40d38d36e_1620996109.3450835.png "A good visualization")

![A even better visualization](https://i.imgur.com/sqnrz9m.gif "Dynamic")

[JS, Python, Java, C++ | Simple O(1) Space & Recursive Solutions w/ Explanation](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/discuss/1207642/JS-Python-Java-C%2B%2B-or-Simple-O(1)-Space-and-Recursive-Solutions-w-Explanation)

```java
class Solution { // TC: O(n), SC: O(1)
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
