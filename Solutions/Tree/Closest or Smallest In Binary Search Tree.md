# Table of Content
1. [135. Closest Number In Binary Search Tree](#Closest-Number-In-Binary-Search-Tree)
2. [504. Closest Number In Binary Search Tree II](#504-Closest-Number-In-Binary-Search-Tree-II)
   1. [667. K Closest Elements in BST](#667-K-Closest-Elements-in-BST)
3. [452. Kth Smallest Element in a BST](#452-Kth-Smallest-Element-in-a-BST)

# 135. Closest-Number-In-Binary-Search-Tree
[LaiCode 135. Closest Number In Binary Search Tree](https://app.laicode.io/app/problem/135)

## Description
In a binary search tree, find the node containing the closest number to the given target number.
<pre>
Assumptions:

The given root is not null.
There are no duplicate keys in the binary search tree.

Examples:
     5
  /    \
 2     11
     /    \
    6     14
closest number to 4 is 5
closest number to 10 is 11
closest number to 6 is 6
</pre>

```java
class Solution { // TC: O(h), SC: O(1)
  public int closest(TreeNode root, int target) {
    int res = root.key;

    while (root != null) {
      if (root.key == target) return target;
      if (Math.abs(root.key - target) < Math.abs(res - target)) res = root.key;
      if (root.key < target) root = root.right;
      else root = root.left;
    }

    return res;
  }
}
```

# 504-Closest-Number-In-Binary-Search-Tree-II
[504. Closest Number In Binary Search Tree II](https://app.laicode.io/app/problem/504)

## Description
In a binary search tree, find k nodes containing the closest numbers to the given target number. return them in sorted array

Assumptions:

The given root is not null.
There are no duplicate keys in the binary search tree.
<pre>
Examples:
    5
  /    \
 2     11
     /    \
    6     14
closest number to 4 is 5
closest number to 10 is 11
closest number to 6 is 6
</pre>

```java
class Solution {
  // TC: O(n+k), SC: O(max(height, k)) (stack will have O(height), q will have O(k))
  public int[] closestKValues(TreeNode root, double target, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>(); // for inOrder Traversal
    Queue<Integer> q = new ArrayDeque<>(); // for holding the sliding window results

    TreeNode cur = root;

    while (cur != null || !stack.isEmpty()) { // standard inOrder Traversal
      if (cur != null) {
        stack.offerFirst(cur);
        cur = cur.left;
      } else {
        cur = stack.pollFirst();
        if (gotAllNodes(cur, target, k, q)) break;
        cur = cur.right;
      }
    }

    int[] res = new int[q.size()];
    for (int i = 0; !q.isEmpty();) res[i++] = q.poll();
    return res;
  }

  private boolean gotAllNodes(TreeNode cur, double target, int k, Queue<Integer> q) {
    if (q.size() < k) q.offer(cur.key);
    else if (Math.abs(q.peek() - target) > Math.abs(cur.key - target)) {
      q.poll();
      q.offer(cur.key);
    } else return true;

    return false;
  }
}
```
## 667-K-Closest-Elements-in-BST
[667. K-Closest Elements in BST](https://app.laicode.io/app/problem/667)

Same as 504, only difference is the return type is List<Integer> instead of int[]
Only difference:
```java
List<Integer> res = new ArrayList<>(q.size());
while (!q.isEmpty()) res.add(q.poll());
```

# 452-Kth-Smallest-Element-in-a-BST
[LaiCode 452. Kth-Smallest Element in a BST](https://app.laicode.io/app/problem/452)
Given a binary search tree, write a function kthSmallest to find the kth smallest element in it.

Note:
You may assume k is always valid, 1 <=k <= BST's total elements.

```java
class Solution {
  // TC: O(k), SC: O(height)
  public int kthSmallest(TreeNode root, int k) {
    Deque<TreeNode> stack = new ArrayDeque<>();
    TreeNode cur = root;
    int i = 0;
    while (cur != null || !stack.isEmpty()) {
      if (cur != null) {
        stack.offerFirst(cur);
        cur = cur.left;
      } else {
        cur = stack.pollFirst();
        if (++i == k) return cur.key; // input guarantees return here 
        cur = cur.right;
      }
    }

    return 0; // doesn't really matter
  }
}
```

## Follow up:
What if the BST is modified (insert/delete operations) often, and you need to find the kth smallest frequently? How would you optimize the kthSmallest routine?

### Thoughts
maintain a sorted array(or list) of top k-Smallest elements?

#### Insertion
Everything time there's an insertion, if the new element is >= the kth element, do nothing.

If new element is smaller, insert it into the sorted array to the right position.

to maintain above feature: O(log(k))

#### Deletion
If an element >= largest element in array, do nothing.

If <, remove that element from array, inOrder traverse the tree again to find the kth-element.

TC: O(k)

#### Any better idea?