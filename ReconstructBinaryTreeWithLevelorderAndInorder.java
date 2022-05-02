/*
        Given the levelorder and inorder traversal sequence of a binary tree, reconstruct the original tree.

        Assumptions
        The given sequences are not null, and they have the same length
        There are no duplicate keys in the binary tree
        Examples

        levelorder traversal = {5, 3, 8, 1, 4, 11}
        inorder traversal = {1, 3, 4, 5, 8, 11}

        the corresponding binary tree is

                 5
              /    \
            3        8
          /   \        \
        1      4        11
*/


import java.util.*;
import util.TreeNode;

public class ReconstructBinaryTreeWithLevelorderAndInorder {

    private static class Aggregate {
        public TreeNode node;
        public int low, high, idx;
        public Aggregate(int key, int idx, int low, int high) {
            node = new TreeNode(key);
            this.low = low;
            this.high = high;
            this.idx = idx;
        }
    }

    public TreeNode reconstruct(int[] in, int[] lvl) { // TC: O(n), SC: O(n)
        if (in.length == 0) return null;

        Queue<Aggregate> frontier = new ArrayDeque<>();
        Map<Integer, Integer> map = valToIdxMap(in, new HashMap<>());

        frontier.add(new Aggregate(lvl[0], map.get(lvl[0]), 0, in.length));
        TreeNode root = frontier.element().node;

        for (int i = 1; i < lvl.length; i++) {
            int idx = map.get(lvl[i]);
            for (Aggregate front = frontier.element(); (!(front.low <= idx && idx < front.idx) || front.node.left != null) && (!(front.idx + 1 <= idx && idx < front.high) || front.node.right != null); front = frontier.element())
                frontier.remove();

            Aggregate front = frontier.element();
            Aggregate aggregate;
            if (idx < front.idx) {
                aggregate = new Aggregate(lvl[i], idx, front.low, front.idx);
                front.node.left = aggregate.node;
            } else {
                aggregate = new Aggregate(lvl[i], idx, front.idx + 1, front.high);
                front.node.right = aggregate.node;
            }

            frontier.add(aggregate);
        }

        return root;
    }

    public Map<Integer, Integer> valToIdxMap(int[] in, Map<Integer, Integer> map) {
        for (int i = 0; i < in.length; i++)
            map.put(in[i], i);
        return map;
    }

    public TreeNode reconstruct2(int[] in, int[] lvl) { // TC: O(n^2), SC: O(n^2)
        Map<Integer, Integer> map = valToIdxMap(in, new HashMap<>());
        List<Integer> list = new ArrayList<>();
        for (int num : lvl) list.add(num);
        return helper(list, map);
    }

    private TreeNode helper(List<Integer> list, Map<Integer, Integer> map) {
        if (list.isEmpty()) return null;
        TreeNode root = new TreeNode(list.remove(0));
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();

        for (int num : list)
            if (map.get(num) < map.get(root.key))
                left.add(num);
            else right.add(num);

        root.left = helper(left, map);
        root.right = helper(right, map);

        return root;
    }

    public static void main(String[] args) {
        ReconstructBinaryTreeWithLevelorderAndInorder rli = new ReconstructBinaryTreeWithLevelorderAndInorder();
        int[] in1 = new int[]{1, 6, 5, 7, 4, 10, 9}, lvl1 = new int[]{4, 1, 10, 5, 9, 6, 7};
        System.out.println(rli.reconstruct(in1, lvl1)); // [4, 1, 10, null, 5, null, 9, 6, 7]
        System.out.println(rli.reconstruct2(in1, lvl1)); // [4, 1, 10, null, 5, null, 9, 6, 7]

        int[] in2 = new int[]{1, 3, 4, 5, 8, 11}, lvl2 = new int[]{5, 3, 8, 1, 4, 11};
        System.out.println(rli.reconstruct(in2, lvl2)); // [5, 3, 8, 1, 4, null, 11]
        System.out.println(rli.reconstruct2(in2, lvl2)); // [5, 3, 8, 1, 4, null, 11]


    }
}
