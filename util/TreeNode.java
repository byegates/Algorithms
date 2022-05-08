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
    public TreeNode(int key) {this(key, null, null);}

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

    public List<Integer> inOrder() {return inOrder(this);}
    public List<Integer> preOrder() {return preOrder(this);}
    public List<Integer> postOrder() {return postOrder(this);}
    public List<Integer> postOrderIterative() {return postOrderIterative(this);}
    public List<Integer> levelOrder() {return levelOrder(this);}
    public TreeNode insert(int key) {return insert(this, key);}
    public int getHeight() {return getHeight(this);}
    public boolean isCompleted() {return isCompleted(this);}
    public List<List<Integer>> layerByLayer() {return layerByLayer(this);}
    public TreeNode deleteNode(int key) {return deleteNode(this, key);}
    public boolean pathSumToTarget(int target) {return pathSumToTarget(this, target);}
    public String toString() {return levelOrder(this).toString();}
    public int maxDigits() {return maxDigits(this);}

    public static String toString(TreeNode root) {return levelOrder(root).toString();}

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
        List<Integer> A = new ArrayList<>();
        if(root == null){return A;}
        Deque<TreeNode> Q = new ArrayDeque<>();
        A.add(root.key);
        Q.offerLast(root);
        while(!Q.isEmpty()){
            TreeNode cur = Q.pollFirst();
            A.add(cur.left == null ? null : cur.left.key);
            A.add(cur.right == null ? null : cur.right.key);
            if (cur.left != null) Q.offerLast(cur.left);
            if (cur.right != null) Q.offerLast(cur.right);
        }
        return trimTrailingNull(A);
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

    @Override
    public Iterator<TreeNode> iterator() {
        return new LevelOrderIterator(this);
        //return new InOrderIterator(this);
        //return new PreOrderIterator(this);
        //return new PostOrderIterator(this);
    }
}
