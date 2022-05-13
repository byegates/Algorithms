package Tree;

import util.TreeNode;

public class DistanceOfTwoNodesInBST {
    public int distanceBST(TreeNode root, int k1, int k2) { // TC: O(height) log(n)~n, SC: O(1)
        TreeNode lca = lca(root, Math.min(k1, k2), Math.max(k1, k2));
        return distance(lca, k1) + distance(lca, k2);
    }

    private TreeNode lca(TreeNode root, int min, int max) {
        while (true) {
            if (root.key < min) root = root.right;
            else if (root.key > max) root = root.left;
            else return root;
        }
    }

    private int distance(TreeNode root, int k) {
        for (int depth = 0; ; depth++) {
            if (root.key == k) return depth;
            else if (root.key < k) root = root.right;
            else root = root.left;
        }
    }

    public static void main(String[] args) {
        DistanceOfTwoNodesInBST db = new DistanceOfTwoNodesInBST();
        TreeNode root = TreeNode.fromLevelOrder(new Integer[]{4, 2, 6, 1, 3, 5, 7});
        System.out.println(root);
        System.out.println(db.distanceBST(root, 1, 2)); // 1
        System.out.println(db.distanceBST(root, 1, 3)); // 2
        System.out.println(db.distanceBST(root, 1, 4)); // 2
        System.out.println(db.distanceBST(root, 1, 5)); // 4
        System.out.println(db.distanceBST(root, 1, 6)); // 3
        System.out.println(db.distanceBST(root, 1, 7)); // 4
    }
}
