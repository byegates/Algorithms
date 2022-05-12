/*
  If your tree is big, by nature it will take up lots of spaces at deeper depth.
  if you have a tree with height of 8, then the number of nodes at the last level is 2^7 = 128
  say the value of each node is something like -106, 108, meaning, it takes up to 4 spaces (perfectly reasonable)
  And to make it pretty (or not to ugly), you add 2 spaces between each node value
  then you need 128 * (4+2) = 768. depend on the monitor you use, but not a lot of terminal can display this width.
  So, you shouldn't reasonably use this to print too big of a tree, and expect to see everything.

  On complexity,
  we traverse the tree multiple times to get different information (height, max key length, all values) before print the tree
  Of course this can be optimized (probably will be soon), but as it's not expected you'll use this to print a very big tree,
  and this is just the first version, so we are aiming for simplicity and quick completion rather than performance.
 */
package util;

import java.util.*;

public class TreePrinter {
    public static String toString(TreeNode root) {
        if (root == null) return "";
        int maxKeyLen = root.maxDigits();
        int height = root.getHeight();
        StringBuilder sb = new StringBuilder();
        List<List<Integer>> allKeys = new ArrayList<>();
        bfs(root, height, allKeys);
        construct(allKeys, height, maxKeyLen + 2, sb);
        return sb.toString();
    }

    private static void construct(List<List<Integer>> allKeys, int height, int width, StringBuilder sb) {
        int lvl = 1;
        int lvlNodeWidth = (int) Math.pow(2, height - lvl) * width; // the width of each node at each level, it's going to decrease by half per level

        for (List<Integer> curLevelKeys : allKeys) {
            for (int i = 0; i < curLevelKeys.size(); i++) {
                if (lvl > 1) { // we print '/' and '\' first and starting from 2nd level
                    String s = i % 2 == 0 ? "/" : "\\";
                    sb.append(center(s, lvlNodeWidth, i % 2 != 0)); // always rightAlign '\' and leftAlign '/'
                }
            }
            sb.append("\n"); // done printing all the back and forward slashes

            for (Integer key : curLevelKeys) {
                if (key == null) sb.append(center("x", lvlNodeWidth, true));
                else sb.append(center(key.toString(), lvlNodeWidth, true));
            }
            sb.append("\n"); // done print one level of keys
            lvlNodeWidth /= 2; // reduce width per node by half per level
            lvl++;
        }
    }

    private static void bfs(TreeNode root, int height, List<List<Integer>> res) {

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        for (int lvl = 1; lvl <= height; lvl++) { // because we offer null to q, so q is never going to be empty, we stop based on lvl/depth
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
        }
     }

    private static String center(String s, int width, boolean leftAligned) {
        // center align string within certain width, when can't align exactly at center, leftAligned determines we align s to the left or right
        if (s.length() >= width) return s;
        int diff = width - s.length();
        int mid1 = diff / 2;
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
