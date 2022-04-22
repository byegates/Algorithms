import util.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class ReconstructBinarySearchTreeWithPreorderTraversal {
    public TreeNode reconstruct(int[] pre) {
        List<Integer> list = new ArrayList<>();
        for (int x : pre) list.add(x);
        return helper(list);
    }

    private TreeNode helper(List<Integer> list) {
        if (list.size() == 0) return null;
        TreeNode root = new TreeNode(list.remove(0));
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int x : list)
            if (x < root.key) left.add(x);
            else right.add(x);

        root.left = helper(left);
        root.right = helper(right);

        return root;
    }

    public static void main(String[] args) {
        ReconstructBinarySearchTreeWithPreorderTraversal bstPre = new ReconstructBinarySearchTreeWithPreorderTraversal();
        int[] arr = new int[]{3, 2, 1, 8, 5, 4, 7, 12, 10, 13};
        System.out.println(bstPre.reconstruct(arr));
    }
}
