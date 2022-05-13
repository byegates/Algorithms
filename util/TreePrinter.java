/*
  If your tree is big, by nature it will take up lots of spaces at deeper depth.
  So, you shouldn't reasonably use this to print too big of a tree, and expect to see everything.

  Example:
  if you have a tree with height of 8, then the number of nodes at the last level is 2^7 = 128
  say the value of each node is something like -106, 108, meaning, each node key takes up to 4 spaces when you print them out (perfectly reasonable)
  And to make it pretty (or not too ugly), you add 2 spaces for each node value
  then you need 128 * (4+2) = 768 spaces as the width of your printed tree.
  Depend on the monitor you use, but most if not none can display this width.

  NOTE,
  we traverse the tree multiple times to get different information (height, max key length, all values) for simplicity and quick completion rather than performance.
 */
package util;

import java.util.*;

public class TreePrinter {
    public static String toString(TreeNode root) {
        if (root == null) return "";
        int maxKeyLen = root.maxDigits(); // traverse the whole tree to get the max length from key value, will print all key nodes based on this max length to make sure all nodes takes up the same space
        int height = root.getHeight();
        List<List<Integer>> allKeys = new ArrayList<>();
        bfs(root, height, allKeys);
        StringBuilder sb = new StringBuilder();
        construct(allKeys, height, maxKeyLen + (maxKeyLen == 1 ? 2 : 1), sb);
        return sb.toString();
    }

    private static void construct(List<List<Integer>> allKeys, int height, int width, StringBuilder sb) {
        int depth = 1;
        int lvlNodeWidth = (int) Math.pow(2, height - depth) * width; // the width of each node at each level, it's going to decrease by half per level

        for (List<Integer> curLevelKeys : allKeys) {
            for (int i = 0; i < curLevelKeys.size(); i++)
                if (depth > 1) // we print '/' and '\' first and starting from 2nd level
                    sb.append(center(i % 2 == 0 ? "/" : "\\", lvlNodeWidth, i % 2 != 0)); // always rightAlign '/' and leftAlign '\'

            sb.append("\n"); // done printing all the back and forward slashes

            for (Integer key : curLevelKeys)
                sb.append(center(key == null ? "x" : key.toString(), lvlNodeWidth, true));

            sb.append("\n"); // done print one level of keys

            lvlNodeWidth /= 2; // reduce width per node by half per level
            depth++;
        }
    }

    private static void bfs(TreeNode root, int height, List<List<Integer>> res) {

        Queue<TreeNode> q = new LinkedList<>(); // LinkedList allow null to be offered, while ArrayDeque does not
        q.offer(root);

        for (int depth = 1; depth <= height; depth++) { // because we offer null to q, so q is never going to be empty, we stop based on depth and height
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
        if (s.length() >= width) return s; // this is not supposed to happen, but put it here as base case
        int diff = width - s.length();
        int mid1 = diff / 2;
        int mid2 = diff - mid1;
        if (leftAligned) {
            return " ".repeat(mid1) + s + " ".repeat(mid2);
        } else {
            return " ".repeat(mid2) + s + " ".repeat(mid1);
        }
    }

    public static void print(TreeNode root) {
        System.out.println(toString(root));
    }

    public static void main(String[] args) {
        for (TreeNode root : TreeNode.sampleTrees()) System.out.println(root);
    }
}
