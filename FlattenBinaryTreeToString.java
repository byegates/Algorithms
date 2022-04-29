import util.TreeNode;

public class FlattenBinaryTreeToString {
    public String flattenBinaryTree(TreeNode root) { // TC: O(n), SC: O(height)
        StringBuilder sb = new StringBuilder();
        dfs(root, sb);
        return sb.toString();
    }

    private void dfs(TreeNode root, StringBuilder sb) { // preOrder/Depth First Search to traversal the whole tree
        if (root == null) return;
        sb.append(root.key);
        if (root.left != null) {
            sb.append('(');
            dfs(root.left, sb);
            sb.append(')');
        }
        if (root.right != null) {
            if (root.left == null) sb.append("()");
            sb.append("(");
            dfs(root.right, sb);
            sb.append(')');
        }
    }

    public static void main(String[] args) {
        FlattenBinaryTreeToString fbt = new FlattenBinaryTreeToString();
        System.out.println(fbt.flattenBinaryTree(TreeNode.fromLevelOrder(new Integer[]{-6, 5, 10, 3}))); // -6(5(3))(10)
        System.out.println(fbt.flattenBinaryTree(TreeNode.fromLevelOrderSpecial(new String[]{"2","-7","#","5","10","4","3","#","-2","-1","-3"}))); // 2(-7(5(4(-1)(-3))(3))(10()(-2)))
    }
}
