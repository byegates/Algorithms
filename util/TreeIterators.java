package util;

import java.util.*;

public class TreeIterators {

    public static void main(String[] args) {
        // create tree from arr and take a quick peek
//        TreeNode root = TreeNode.fromLevelOrder(new Integer[] {50, 25, 75, 15, 40, 60, 90, 10, 20, 30, 45, 55, 65, 80, 100, null, null, null, null, null, null, null, null, null, null, 62, null, 78, 85, null, null, 61, 63, 76, null, null, null, null, null, null, null, null, 77});
        Integer[] arr = new Integer[] {50, 25, 75, 15, 40, 60, 90, 10, null, 30};
        TreeNode root = TreeNode.fromLevelOrder(arr);
        System.out.println(root);


        // Iterate an iterator manually
        System.out.printf("Iterate an iterator manually: %s\n", iterate(new PreOrderIterator(root)));
        System.out.printf("Iterate an iterator manually: %s\n", iterate(new InOrderIterator(root)));
        System.out.printf("Iterate an iterator manually: %s\n", iterate(new PostOrderIterator(root)));
        System.out.printf("Iterate an iterator manually: %s\n", iterate(new LevelOrderIterator(root)));

        // use for each loop directly on an iterable
        List<Integer> res = new ArrayList<>();
        for (TreeNode treeNode : root)
            res.add(treeNode.key);
        System.out.printf("For each loop on an iterable: %s\n", res);

    }

    public static List<Integer> iterate(PostOrderIterator it) {
        List<Integer> res = new ArrayList<>();
        while (it.hasNext()) res.add(it.next().key);
        return res;
    }

    public static List<Integer> iterate(InOrderIterator it) {
        List<Integer> res = new ArrayList<>();
        while (it.hasNext()) res.add(it.next().key);
        return res;
    }

    public static List<Integer> iterate(PreOrderIterator it) {
        List<Integer> res = new ArrayList<>();
        while (it.hasNext()) res.add(it.next().key);
        return res;
    }

    public static List<Integer> iterate(LevelOrderIterator it) {
        List<Integer> res = new ArrayList<>();
        while (it.hasNext()) res.add(it.next().key);
        return res;
    }

}

class LevelOrderIterator implements Iterator<TreeNode> {
    Queue<TreeNode> q = new ArrayDeque<>();

    LevelOrderIterator(TreeNode root) {
        if (root !=  null) q.offer(root);
    }
    @Override
    public boolean hasNext() {
        return !q.isEmpty();
    }

    @Override
    public TreeNode next() {
        if (hasNext()) {
            TreeNode cur = q.poll();
            if (cur.left != null) q.offer(cur.left);
            if (cur.right != null) q.offer(cur.right);
            return cur;
        }
        return null;
    }
}

class PostOrderIterator implements Iterator<TreeNode> {
    Deque<TreeNode> stack;
    TreeNode pre, cur;
    PostOrderIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        if (root != null) stack.offerFirst(root);
    }
    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TreeNode next() {
        while (hasNext()) {
            cur = stack.peekFirst();
            if (pre == null || pre.left == cur || pre.right == cur) { // going down, 1st round
                if (cur.left != null) {
                    stack.offerFirst(cur.left);
                } else if (cur.right != null) {
                    stack.offerFirst(cur.right);
                }
                else {
                    pre = cur;
                    return stack.pollFirst();
                }
            } else if (cur.left == pre && cur.right != null) { // coming back from left, need to go to right
                stack.offerFirst(cur.right);
            } else if (cur.right == null && cur.left == pre || cur.right == pre) { // print cur
                pre = cur;
                return stack.pollFirst();
            }
            pre = cur;
        }
        return null;
    }
}

class InOrderIterator implements Iterator<TreeNode> {
    Deque<TreeNode> stack;
    TreeNode cur;
    InOrderIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        cur = root;
    }
    @Override
    public boolean hasNext() {
        return cur != null || !stack.isEmpty();
    }

    @Override
    public TreeNode next() {
        while (hasNext()) {
            if (cur != null) {
                stack.offerFirst(cur);
                cur = cur.left;
            } else {
                cur = stack.pollFirst();
                TreeNode res = cur;
                cur = cur.right;
                return res;
            }
        }
        return null;
    }
}

class PreOrderIterator implements Iterator<TreeNode> {
    private final Deque<TreeNode> stack;
    PreOrderIterator(TreeNode root) {
        stack = new ArrayDeque<>();
        if (root != null) stack.offerFirst(root);
    }
    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public TreeNode next() {
        if (hasNext()) {
            TreeNode cur = stack.pollFirst();
            if (cur.right != null) stack.offerFirst(cur.right);
            if (cur.left != null) stack.offerFirst(cur.left);
            return cur;
        }
        return null;
    }
}
