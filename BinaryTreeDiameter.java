import util.BTreePrinter;
import util.TreeNode;

/*
    Given a binary tree in which each node contains an integer number.
    The diameter is defined as the longest distance from one leaf node to another leaf node.
    The distance is the number of nodes on the path.
    If there does not exist any such paths, return 0.
    Examples
         5
      /    \
    2      11
         /    \
        6     14
    The diameter of this tree is 4 (2 → 5 → 11 → 14)
 */
public class BinaryTreeDiameter {
    int max = 0;
    public int diameter(TreeNode root) {
        dfs(root);
        return max;
    }

    private int dfs(TreeNode root) { // TC: O(n), SC: O(height)
        if (root == null) return 0;
        int left = dfs(root.left) + 1;
        int right = dfs(root.right) + 1;
        if (root.left != null && root.right != null)
            max = Math.max(max, left + right - 1);
        return Math.max(left, right);
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.fromLevelOrder(new Integer[]{1, 2, 6, 3, 4, null, null, 7, 8, 5, null, null, null, 9, null, 10});
        BinaryTreeDiameter btd = new BinaryTreeDiameter();
        System.out.println(btd.diameter(root));
    }
}
