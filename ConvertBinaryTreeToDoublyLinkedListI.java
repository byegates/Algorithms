/*
        Given a binary tree, convert it to a doubly linked list in place (use the left pointer as previous,
        use the right pointer as next).

        The values in the nodes of the doubly linked list should follow the in-order traversal sequence of the binary tree.
        Examples:
          10
         /  \
        5    15
       /
      2
        Output:  2 <-> 5 <-> 10 <-> 15
*/

public class ConvertBinaryTreeToDoublyLinkedListI {

    //method 1 starts here
    public TreeNode toDoubleLinkedList(TreeNode root) { // TC: O(n), SC: O(height)
        TreeNode dummy = new TreeNode(0);
        TreeNode[] prev = new TreeNode[] {dummy};
        helper(root, prev);
        if (dummy.right != null) dummy.right.left = null;
        return dummy.right;
    }

    private void helper(TreeNode root, TreeNode[] prev) {
        if (root == null) return;
        helper(root.left, prev);
        prev[0].right = root;
        root.left = prev[0];
        prev[0] = root;
        helper(root.right, prev);
    }
    // method 1 ends here

    // method 2 starts here
    public TreeNode toDoubleLinkedList2(TreeNode root) { // TC: O(n), SC: O(height)
        if (root == null) return null;
        return helper(root)[0];
    }

    private TreeNode[] helper(TreeNode root) {
        if (root == null) return null; // base case 1
        if (root.left == null && root.right == null) return new TreeNode[] {root, root}; // base case 2

        TreeNode[] left = helper(root.left);
        TreeNode[] right = helper(root.right);

        if (left != null) link(left[1], root); // connect when not null
        if (right != null) link(root, right[0]);

        return new TreeNode[] {left == null ? root : left[0], right == null ? root : right[1]};
    }

    private void link(TreeNode a, TreeNode b) {
        a.right = b;
        b.left = a;
    }
    //method 2 ends here

    public static void main(String[] args) {
        System.out.println("TBD");
    }
}
