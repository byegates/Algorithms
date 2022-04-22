/*
    Find distance between two given keys of a Binary Tree, no parent pointers are given.
    Distance between two nodes is the minimum number of edges to be traversed to reach one node from others.

    Assumptions:
    There are no duplicate keys in the binary tree.
    The given two keys are guaranteed to be in the binary tree.
    The given two keys are different.

    Examples:
         1
       /  \
      2    3
     / \  / \
    4   5 6  7
           \
            8
    distance(4, 5) = 2
    distance(4, 6) = 4
 */

import util.BTreePrinter;
import util.TreeNode;

public class DistanceOfTwoNodesInBinaryTree {
    public int distance(TreeNode root, int k1, int k2) {
        TreeNode lca = lca(root, k1, k2);
        return depth(lca, k1, 0) + depth(lca, k2, 0);
    }

    public TreeNode lca(TreeNode root, int k1, int k2) {
        if (root == null || root.key == k1 || root.key == k2) return root;
        TreeNode left = lca(root.left, k1, k2);
        TreeNode right = lca(root.right, k1, k2);
        if (left != null && right != null) return root;
        return left != null ? left : right;
    }

    public int depth(TreeNode root, int k, int depth) {
        if (root == null) return -1;
        if (root.key == k) return depth;
        int left = depth(root.left, k, depth + 1);
        int right = depth(root.right, k, depth + 1);
        return left != -1 ? left : right;
    }

    public static void main(String[] args) {
        DistanceOfTwoNodesInBinaryTree dtb = new DistanceOfTwoNodesInBinaryTree();
        TreeNode root = TreeNode.fromLevelOrder(new Integer[]{7, 13, -9, 101, 5, 8, 10, null, 2, 4, 6});
        TreeNode root2 = TreeNode.fromLevelOrder(new Integer[]{1, 2, 3, 4, 5, 6, 7, null, null, null, 8});
        BTreePrinter.printNode(root2);
        System.out.println(dtb.distance(root, 10, 4)); // 5
        System.out.println(dtb.distance(root2, 5, 4)); // 2
        System.out.println(dtb.distance(root2, 6, 4)); // 4
    }
}
