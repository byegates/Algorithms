# [LaiCode 300. Convert Binary Tree To Doubly Linked List I](https://app.laicode.io/app/problem/300)
## Space O(1) Solution
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
## Space O(height), regular Solution
### Standard Iterative
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
## Space O(height)
### Iterative InOrder
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
# [LaiCode 523. Flatten Binary Tree to Linked List](https://app.laicode.io/app/problem/523)
[114. Flatten Binary Tree to Linked List](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/)

It's actually preOrder with a bit of twist
## Solution 0: Space O(1)
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
### Solution 3 Recursion, Anti-PreOrder
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