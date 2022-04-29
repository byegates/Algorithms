import util.TreeNode;

public class FlattenBinaryTreeToString {
    public String flattenBinaryTree(TreeNode root) {
        return dfs(root).toString();
    }

    private StringBuilder dfs(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb;
        sb.append(root.key);
        if (root.left != null) sb.append('(').append(dfs(root.left)).append(')');
        if (root.right != null) {
            if (root.left == null) sb.append("()");
            sb.append("(").append(dfs(root.right)).append(')');
        }
        return sb;
    }

    public static void main(String[] args) {
        FlattenBinaryTreeToString fbt = new FlattenBinaryTreeToString();
        System.out.println(fbt.flattenBinaryTree(TreeNode.fromLevelOrder(new Integer[]{-6, 5, 10, 3}))); // -6(5(3))(10)
        System.out.println(fbt.flattenBinaryTree(TreeNode.fromLevelOrderSpecial(new String[]{"2","-7","#","5","10","4","3","#","-2","-1","-3"}))); // 2(-7(5(4(-1)(-3))(3))(10()(-2)))
    }
}
