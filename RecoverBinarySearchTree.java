import util.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class RecoverBinarySearchTree {

    //use recursive inorder traversal to detect incorrect node
    TreeNode prev, node1, node2;
    public TreeNode recover(TreeNode root) {
        prev = node1 = node2 = null;
        inOrder(root);
        swap(node1, node2);
        return root;
    }

    public void inOrder(TreeNode root){
        if(root == null) return;
        //search left tree
        inOrder(root.left);

        //in inorder traversal of BST, prev should always have smaller value than current value
        if(prev != null && prev.key >= root.key){
            //incorrect smaller node is always found as prev node
            if(node1 == null) node1 = prev;
            //incorrect larger node is always found as curr(root) node
            node2 = root;
        }
        prev = root;

        inOrder(root.right);
    }

    private void swap(TreeNode n1, TreeNode n2) {
        if (n1 == null || n2 == null) return;
        int tmp = n1.key;
        n1.key = n2.key;
        n2.key = tmp;
    }

    // solution 2 inorder with a stack
    public TreeNode recover2(TreeNode root) {
        if (root == null) return null;
        TreeNode prev = null, node1 = null, node2 = null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.offerFirst(cur);
                cur = cur.left;
            } else {
                cur = stack.pollFirst();
                if (prev != null && prev.key > cur.key) {
                    if (node1 == null) node1 = prev;
                    node2 = cur;
                }
                prev = cur;
                cur = cur.right;
            }
        }
        swap(node1, node2);
        return root;
    }
    // solution 2 ends here

    static void printTests(Integer[] a, RecoverBinarySearchTree sol) {
        TreeNode root1 = TreeNode.fromLevelOrder(a);
        System.out.print(root1 + " vs ");
        System.out.println(sol.recover(root1));
        TreeNode root2 = TreeNode.fromLevelOrder(a);
        System.out.print(root2 + " vs ");
        System.out.println(sol.recover(root2));
    }

    public static void main(String[] args) {
        RecoverBinarySearchTree sol = new RecoverBinarySearchTree();
        printTests(new Integer[] {108,106,null,null,107,null,110,null,109}, sol);
        printTests(new Integer[] {3,2,null,null,1}, sol);
    }
}
