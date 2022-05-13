package Tree;
/*
        Determine the length of the longest ascending path in the binary tree.
        A valid path is a part of the path from root to any of the leaf nodes.

        Examples:
                 5
              /    \
            3        2
          /   \        \
        1      0        11

        the longest ascending path is 2 -> 11, length is 2.
*/


import util.TreeNode;

public class LongestAscendingPathBinaryTree {
    static class Helper {
        int max = 0;
    }
    public int longest(TreeNode root) {
        Helper H = new Helper();
        helper(root, null, 0, H);
        return H.max;
    }

    private void helper (TreeNode root, TreeNode pre, int cur, Helper H) {
        if (root == null) return;

        cur = pre != null && pre.key < root.key ? cur + 1 : 1;
        if (cur > H.max) H.max = cur;

        helper(root.left, root, cur, H);
        helper(root.right, root, cur, H);
    }

    public static void main(String[] args) {
        LongestAscendingPathBinaryTree lap = new LongestAscendingPathBinaryTree();
        Integer[] arr = new Integer[]{487,132,214,344,185,null,404,428,246,403,146,223,47,250,132,337,166,152,429,5,280,425,148,283,388,61,205,401,null,111,414,163,478,483,null,358,420,165,130,391,null,263,null,324,445,460,66,323,450,214,158,253,255,270,403,17,323,437,248,417,274,134,41,496,226,406};
        TreeNode root = TreeNode.fromLevelOrder(arr);
        // BTreePrinter.printNode(root);
        System.out.println(lap.longest(root)); // 4
        Integer[] a2 = new Integer[]{5, 3, 2, 1, 0, null, 11};
        TreeNode r2 = TreeNode.fromLevelOrder(a2);
        // BTreePrinter.printNode(r2);
        System.out.println(lap.longest(r2)); // 2
        System.out.println(lap.longest(TreeNode.fromLevelOrder(new Integer[]{1}))); // 1
        System.out.println(lap.longest(TreeNode.fromLevelOrder(new Integer[]{1, 2}))); // 2
        System.out.println(lap.longest(TreeNode.fromLevelOrder(new Integer[]{1, 2, 3}))); // 2
        System.out.println(lap.longest(TreeNode.fromLevelOrder(new Integer[]{1, 2, 3, 4}))); // 3
        System.out.println(lap.longest(TreeNode.fromLevelOrder(new Integer[]{2, 1, 3}))); // 2
    }
}
