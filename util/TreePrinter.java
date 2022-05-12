package util;

import java.util.*;

public class TreePrinter {
    public static String toString(TreeNode root) {
        if (root == null) return "";
        int maxKeyLen = root.maxDigits();
        int depth = root.getHeight();
        StringBuilder sb = new StringBuilder();
        List<List<Integer>> res = new ArrayList<>();
        bfs(root, depth, res);
        construct(res, depth, maxKeyLen + 2, sb);
        return sb.toString();
    }

    private static void construct(List<List<Integer>> res, int depth, int width, StringBuilder sb) {
        int lvl = 1;
        int lvlNodeWidth = (int) Math.pow(2, depth - lvl) * width;

        for (List<Integer> level : res) {
            for (int i = 0; i < level.size(); i++) {
                Integer val = level.get(i);
                if (lvl > 1) { // print '/' and '\' val == null ? " " :
                    String s = i % 2 == 0 ? "/" : "\\"; // "\n"
                    sb.append(center(s, lvlNodeWidth, i % 2 != 0));
                }
            }
            sb.append("\n");

            for (int i = 0; i < level.size(); i++) {
                Integer val = level.get(i);
                if (val == null) sb.append(center("x", lvlNodeWidth, true));
                else sb.append(center(val.toString(), lvlNodeWidth, true));
            }
            sb.append("\n");
            lvlNodeWidth /= 2;
            lvl++;
        }
    }

    private static void bfs(TreeNode root, int depth, List<List<Integer>> res) {

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        int lvl = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> tmp = new ArrayList<>();
            while (size--> 0) {
                TreeNode cur = q.poll();
                if (cur == null) {
                    tmp.add(null);
                    q.offer(null);
                    q.offer(null);
                } else {
                    tmp.add(cur.key);
                    q.offer(cur.left);
                    q.offer(cur.right);
                }
            }
            res.add(tmp);
            if(lvl++ == depth) break;
        }
     }

    private static String center(String s, int width, boolean leftAligned) { //
        if (s.length() >= width) return s;
        int diff = width - s.length();
        int mid1 = diff / 2; // 5: 2, 3, 4: 2, 2
        int mid2 = diff - mid1;
        if (leftAligned) {
            return " ".repeat(mid1) + s + " ".repeat(mid2);
        } else {
            return " ".repeat(mid2) + s + " ".repeat(mid1);
        }
    }

    public static void main(String[] args) {
        TreeNode root = TreeNode.fromLevelOrder(new Integer[]{5, null, 8, null, 4, 3, 4});
        BTreePrinter.printNode(root);
        System.out.println(TreePrinter.toString(root));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17, null, null, -108})));

        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {1, 2, 3, 4, 5, 6, 7})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {1, 1, 2, 1, 2, 3, 4, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {1, 2, -3, 4, 5, 6, 7})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {11, 11, 12, 11, 12, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 39, 30, 31, 32, 33, 34, 35, 36})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {11, 11, 12, 11, 12, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 31, null, 33, 34, null, 36, null, 38, 39, 30, null, 32, 33, null, 35, 36})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, -13, 14, 15, 16, 17})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17, null, null, -108})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, 16, 17})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, -108, 17})));
        System.out.println(TreePrinter.toString(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, 16, 17, -18, 27, null, 36, 48, null})));
        TreeNode root2 = TreeNode.fromLevelOrder(new Integer[] {111, 111, 112, 111, 112, 113, 114, 121, 122, 123, 224, 225, 226, 227, 228, 231, null, 333, 334, null, 336, null, 338, 339, 330, null, 332, 333, null, 335, 336});
        System.out.println(root2);
    }
}
