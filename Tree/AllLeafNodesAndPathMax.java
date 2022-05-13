package Tree;

import util.TreeNode;

import java.util.ArrayList;
import java.util.List;

/*
    Given a binary tree, return all leaf nodes and path max from root to each leaf nodes
 */
public class AllLeafNodesAndPathMax {

    public static void main(String[] args) {
        AllLeafNodesAndPathMax sol = new AllLeafNodesAndPathMax();
        for (TreeNode root : TreeNode.sampleTrees())
            System.out.printf("%s\n%s\n", root, sol.search(root));
    }

    static class Result {
        TreeNode leaf;
        int pathMax;
        Result(TreeNode leaf, int pathMax) {
            this.leaf = leaf;
            this.pathMax = pathMax;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", leaf.key, pathMax);
        }
    }
    public List<Result> search(TreeNode root) {
        List<Result> res = new ArrayList<>();
        if (root != null) dfs(root, res, root.key);
        return res;
    }

    private void dfs(TreeNode root, List<Result> res, int max) {
        if (root.left == null && root.right == null) res.add(new Result(root, max));
        if (root.left  != null) dfs(root.left , res, Math.max(root.left.key , max));
        if (root.right != null) dfs(root.right, res, Math.max(root.right.key, max));
    }
}
