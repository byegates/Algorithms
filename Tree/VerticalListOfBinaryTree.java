package Tree;

import util.TreeNode;

import java.util.*;

public class VerticalListOfBinaryTree {
    public static void main(String[] args) {
        VerticalListOfBinaryTree sol = new VerticalListOfBinaryTree();
        System.out.println(sol.verticalPrint(TreeNode.fromLevelOrder(new Integer[]{4, 2, null, 1, 3, null, null, 7, 9})));
    }

    static class Pair {
        TreeNode node;
        int idx;
        Pair(TreeNode node, int idx) {
            this.node = node;
            this.idx = idx;
        }
    }
    public List<List<Integer>> verticalPrint(TreeNode root) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] range = bfs(root, map);

        List<List<Integer>> res = new ArrayList<>(range[1] - range[0] + 1);

        for (int i = range[0]; i <= range[1]; i++) res.add(map.get(i));

        return res;
    }

    private int[] bfs(TreeNode root, Map<Integer, List<Integer>> map) {
        Queue<Pair> q = new ArrayDeque<>();
        if (root != null) q.offer(new Pair(root, 0));

        int min = 0, max = -1; // so that size (max - min + 1) is 0 when root is null

        while (!q.isEmpty()) {
            Pair cur = q.poll();
            min = Math.min(min, cur.idx);
            max = Math.max(max, cur.idx);

            var list = map.getOrDefault(cur.idx, new ArrayList<>());
            list.add(cur.node.key);
            map.put(cur.idx, list);

            if (cur.node.left  != null) q.offer(new Pair(cur.node.left , cur.idx - 1));
            if (cur.node.right != null) q.offer(new Pair(cur.node.right, cur.idx + 1));
        }

        return new int[] {min, max};
    }
}
