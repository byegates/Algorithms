# [LaiCode 300. Convert Binary Tree To Doubly Linked List I](https://app.laicode.io/app/problem/300)
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