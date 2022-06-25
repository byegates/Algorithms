package util;

import java.util.*;

import static util.Utils.numLength;

public class TreeNode implements Iterable<TreeNode> {
    public int key;
    public TreeNode left;
    public TreeNode right;

    TreeNode(int key, TreeNode left, TreeNode right) {
        this.key = key;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int key) {
        this(key, null, null);
    }

    public static TreeNode fromLevelOrder(Integer[] A) {
        if (A == null || A.length==0) return null;
        Queue<TreeNode> q = new LinkedList<>();
        TreeNode root = new TreeNode(A[0]);
        q.offer(root);
        int idx = 1;
        while (idx < A.length){
            Integer leftKey = A[idx++];
            Integer rightKey = idx >= A.length ? null : A[idx++];
            TreeNode cur = q.poll();
            if (leftKey != null) {
                cur.left = new TreeNode(leftKey);
                q.offer(cur.left);
            }
            if (rightKey != null){
                cur.right = new TreeNode(rightKey);
                q.offer(cur.right);
            }
        }
        return root;
    }

    public static TreeNode fromLevelOrderSpecial(String[] arr) {
        if (arr == null || arr.length == 0) return null;
        return fromLevelOrder(stringToIntArr(arr));
    }

    static Integer[] stringToIntArr(String[] A) {
        Integer[] res = new Integer[A.length];
        for (int i = 0; i < A.length; i++)
            res[i] = A[i].equals("#") ? null : Integer.parseInt(A[i]);
        return res;
    }

    public List<Integer> inOrder() {
        return inOrder(this);
    }

    public List<Integer> preOrder() {
        return preOrder(this);
    }

    public List<Integer> postOrder() {
        return postOrder(this);
    }

    public List<Integer> postOrderIterative() {
        return postOrderIterative(this);
    }

    public List<Integer> levelOrder() {
        return levelOrder(this);
    }

    public TreeNode insert(int key) {
        return insert(this, key);
    }

    public int getHeight() {
        return getHeight(this);
    }

    public boolean isCompleted() {
        return isCompleted(this);
    }

    public List<List<Integer>> layerByLayer() {
        return layerByLayer(this);
    }

    public TreeNode deleteNode(int key) {
        return deleteNode(this, key);
    }

    public boolean pathSumToTarget(int target) {
        return pathSumToTarget(this, target);
    }

//    public String toString() {
//        return TreePrinter.toString(this);
//    }

    public String toString() {
        return String.format("%s", this.key);
    }

    public static String toString(TreeNode root) {
        return levelOrder(root).toString();
    }

    public int maxDigits() {
        return maxDigits(this);
    }

    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.key == key) {
            if (root.left == null) return root.right;
            else if (root.right == null) return root.left;
            // left & right are both not null
            if (root.right.left == null) { // right tree and no left sub
                root.right.left = root.left;
                return root.right;
            }
            // now root.right has left sub, search for left most(smallest) as candidate
            TreeNode smallest = smallest(root.right);
            smallest.left = root.left;
            smallest.right = root.right;
            return smallest;
        }
        if (key < root.key) root.left = deleteNode(root.left, key);
        else root.right = deleteNode(root.right, key); // key < root.key
        return root;
    }
    private static TreeNode smallest(TreeNode root) {
        while (root.left.left != null) root = root.left;
        TreeNode smallest = root.left;
        root.left = root.left.right;
        return smallest;
    }

    public static boolean isCompleted(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new ArrayDeque<>();
        q.offer(root);
        boolean sawBubble = false;
        while (!q.isEmpty()) {
            TreeNode cur = q.poll();
            if (cur.left == null) sawBubble = true;
            else if (sawBubble) return false;
            else q.offer(cur.left);
            if (cur.right == null) sawBubble = true;
            else if (sawBubble) return false;
            else q.offer(cur.right);
        }
        return true;
    }

    public static List<List<Integer>> layerByLayer(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> res0 = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode cur = q.poll();
                res0.add(cur.key);
                if (cur.left != null) q.offer(cur.left);
                if (cur.right != null) q.offer(cur.right);
            }
            res.add(res0);
        }
        return res;
    } //TC: O(n), SC: O(n)

    static int getHeight(TreeNode root) {
        return root == null ? 0 : 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
    static TreeNode insert(TreeNode root, int key) {//insert into Binary tree, interactive I
        if (root == null) return new TreeNode(key);
        TreeNode cur = root, pre = root;
        while (cur != null && pre.key != key) {
            pre = cur;
            cur = key < cur.key ? cur.left : cur.right;
        }
        if (key < pre.key) pre.left = new TreeNode(key);
        else if (key > pre.key) pre.right = new TreeNode(key);
        return root;
    }

    static public List<Integer> levelOrder(TreeNode root){
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> q = new ArrayDeque<>();
        res.add(root.key);
        q.offerLast(root);
        while(!q.isEmpty()){
            TreeNode cur = q.pollFirst();
            res.add(cur.left == null ? null : cur.left.key);
            res.add(cur.right == null ? null : cur.right.key);
            if (cur.left != null) q.offerLast(cur.left);
            if (cur.right != null) q.offerLast(cur.right);
        }
        return trimTrailingNull(res);
    }

    public List<Integer> levelOrderWithoutNull(TreeNode root){
        List<Integer> A = new ArrayList<>();
        if(root == null){return A;}
        Deque<TreeNode> Q = new ArrayDeque<>();
        Q.offerLast(root);
        while (!Q.isEmpty()) {
            TreeNode cur = Q.pollFirst();
            A.add(cur.key);
            if (cur.left != null) Q.offerLast(cur.left);
            if (cur.right != null) Q.offerLast(cur.right);
        }
        return A;
    }

    public static List<Integer> trimTrailingNull (List<Integer> A) {
        int size = A.size();
        for (int i = size - 1; i >= 0; i--)
            if (A.get(i) == null) A.remove(i);
            else break;
        return A;
    }

    static List<Integer> inOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inOrder(root, res);
        return res;
    }
    static List<Integer> preOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        preOrder(root, res);
        return res;
    }
    public static List<Integer> postOrder(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        postOrder(root, res);
        return res;
    }
    private static void inOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;
        inOrder(root.left, res);
        res.add(root.key);
        inOrder(root.right, res);
    }
    private static void preOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;
        res.add(root.key);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }
    private static void postOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;
        postOrder(root.left, res);
        postOrder(root.right, res);
        res.add(root.key);
    }
    private static List<Integer> postOrderIterative(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) return res;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.offerFirst(root);
        TreeNode pre = null;
        while (!stack.isEmpty()) {
            TreeNode cur = stack.peekFirst();
            if (pre == null || cur == pre.right || cur == pre.left) {//coming down for the first time, keep going down
                if (cur.left != null) stack.offerFirst(cur.left); // keep going left until no more left child
                else if (cur.right != null) stack.offerFirst(cur.right); //Will not execute until first left null met
                else { // Leaf nodes
                    stack.pollFirst();//value was peeked and saved to cur already
                    res.add(cur.key);
                }
            } else if (pre == cur.right || pre == cur.left && cur.right == null) {//coming back after traversal both sides
                stack.pollFirst();
                res.add(cur.key);
            } else stack.offerFirst(cur.right); // pre == cur.left && cur.right != null, back from left or no right
            pre = cur;
        }
        return res;
    }

    public int maxPathSum2PlusNodes() {return maxPathSum2PlusNodes(this);}
    public static int maxPathSum2PlusNodes(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE};
        maxPathSum2PlusNodes(root, max);
        return max[0];
    }

    private static int maxPathSum2PlusNodes(TreeNode root, int[] max) {
        if (root == null) return Integer.MIN_VALUE;
        if (root.left == null && root.right == null) return root.key;
        int left = maxPathSum2PlusNodes(root.left, max);
        int right = maxPathSum2PlusNodes(root.right, max);
        int fromChildren = left >= 0 && right >= 0 ? left + right : Math.max(left, right);
        max[0] = Math.max(root.key + fromChildren, max[0]);
        return root.key + (left >= 0 || right >= 0 ? Math.max(left, right) : 0);
    }

    public static boolean pathSumToTarget(TreeNode root, int target) { // TC: O(n), SC:O(h)
        if (root == null) return false;
        Set<Integer> set = new HashSet<>();
        set.add(0);
        return exist(root, target, 0, set);
    }

    private static boolean exist(TreeNode root, int target, int preSum, Set<Integer> set) {
        preSum += root.key;
        if (set.contains(preSum - target)) return true;

        boolean addedThisLevel = set.add(preSum);
        if (root.left != null && exist(root.left, target, preSum, set)) return true;
        if (root.right != null && exist(root.right, target, preSum, set)) return true;
        if (addedThisLevel) set.remove(preSum);
        return false;
    }

    public TreeNode fromInPre(int[] in, int[] pre) { // TC: O(3n) → O(n), SC: O(height)
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < in.length; i++) map.put(in[i], i);

        return reconstruct(in, 0, in.length - 1, pre, 0, pre.length - 1, map);
    }

    private TreeNode reconstruct(int[] in, int inL, int inR, int[] pre, int preL, int preR, Map<Integer, Integer> map) {
        if (inR < inL) return null;

        TreeNode root = new TreeNode(pre[preL]);

        int rootIdx = map.get(pre[preL]);
        int leftSize = rootIdx - inL;

        root.left  = reconstruct(in, inL, rootIdx - 1, pre, preL + 1, preL + leftSize, map);
        root.right = reconstruct(in, rootIdx + 1, inR, pre, preL + leftSize + 1, preR, map);

        return root;
    }

    public static int maxDigits(TreeNode root) {
        int[] maxDigits = new int[1];
        maxDigits(root, maxDigits);
        return maxDigits[0];
    }

    private static void maxDigits(TreeNode root, int[] maxDigits) {
        if (root == null) return;
        maxDigits[0] = Math.max(maxDigits[0], numLength(root.key));
        maxDigits(root.left, maxDigits);
        maxDigits(root.right, maxDigits);
    }

    private static TreeNode BST(int[] a, int l, int r) {
        if (l > r) return null;
        int mid = r - (r - l) / 2;
        TreeNode root = new TreeNode(a[mid]);
        root.left  = BST(a, l, mid - 1);
        root.right = BST(a, mid + 1, r);
        return root;
    }

    public static TreeNode inOrderToBST(int[] a) {
        return BST(a, 0, a.length - 1);
    }
    static class Node {
        TreeNode node;
        int min, max;
        Node(TreeNode node, int min, int max) {
            this.node = node;
            this.min = min;
            this.max = max;
        }
    }

    public static List<TreeNode> sampleTrees() {
        List<TreeNode> res = new ArrayList<>();
        res.add(TreeNode.fromLevelOrder(new Integer[]{5, null, 8, null, 4, 3, 4}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17, null, null, -108}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {1, 2, 3, 4, 5, 6, 7}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {1, 1, 2, 1, 2, 3, 4, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {1, 2, -3, 4, 5, 6, 7}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {11, 11, 12, 11, 12, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 39, 30, 31, 32, 33, 34, 35, 36}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {11, 11, 12, 11, 12, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 31, null, 33, 34, null, 36, null, 38, 39, 30, null, 32, 33, null, 35, 36}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, -13, 14, 15, 16, 17}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17, null, null, -108}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, 16, 17}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, -108, 17}));
        res.add(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, 16, 17, -18, 27, null, 36, 48, null}));
        Integer[] arr2 = new Integer[] {111, 111, 112, 111, 112, 113, 114, 121, 122, 123, 224, 225, 226, 227, 228, 231, null, 333, 334, null, 336, null, 338, 339, 330, null, 332, 333, null, 335, 336};
        // Add some negative numbers to arr2
        Integer[] arr2b = new Integer[arr2.length];
        for (int i = 0; i < arr2.length; i++) arr2b[i] = arr2[i] != null ? i % 2 == 0 ? arr2[i] : -arr2[i] : null;
        res.add(TreeNode.fromLevelOrder(arr2));
        res.add(TreeNode.fromLevelOrder(arr2b));
        res.add(TreeNode.fromLevelOrderSpecial(new String[]{"460","59","35","#","287","272","61","292","148","354","140","277","442","130","453","#","96","46","#","119","90","304","#","202","360","300","472","299","110","406","365","142","#","288","276","#","332","87","#","29"}));
        res.add(TreeNode.fromLevelOrder(new Integer[]{1, 3, 2, 4, null, null, 5}));
        res.add(TreeNode.fromLevelOrder(new Integer[]{31, 11, null, 7, 26, 2, 8, 16, 30, null, 6, null, 10, 13, 22, 27}));
        return res;
    }

    @Override
    public Iterator<TreeNode> iterator() {
        return new LevelOrderIterator(this);
        //return new InOrderIterator(this);
        //return new PreOrderIterator(this);
        //return new PostOrderIterator(this);
    }
}
