package util;

import java.util.*;

public class Debugger {
    public static void main(String[] args) {
        //Creating a tree from Integer Array
        Integer[] A = new Integer[]{7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        TreeNode root = TreeNode.fromLevelOrder(A);
//        System.out.println(root);
        // delete a tree node
        root = root.deleteNode(7);
        //System.out.println(root.levelOrder());
//        System.out.println(root);

        // Creating Binary Tree from String Array
        String[] strArr = new String[]{"17", "13", "21", "9", "14", "18", "46", "5", "#", "#", "#", "#", "#", "32", "47", "#", "#", "22", "42", "#", "49", "#", "29"};
        TreeNode root2 = TreeNode.fromLevelOrderSpecial(strArr);
//        System.out.println(root2);

        // Creating graph and some prints
        int[][] matrix = new int[][]{{1, 2}, {1, 3}, {2, 4}, {3, 4},};
        List<GraphNode> graph = GraphNode.fromMatrixToList(matrix);
        GraphNode n5 = new GraphNode(5);
        graph.add(n5);
        System.out.printf("The given graph is Bipartite : %s\n", GraphNode.isBipartite(graph));
        GraphNode.printListGraph(graph); // Print all Graph Nodes in List and their neighbors
        System.out.println();

        // Heap Sort, need maxHeap
        Integer[] A2 = new Integer[]{7, 3, 11, 1, 5, 9, 13, 0, 2, 4, 6, 8, 10, 12, 14};
        Heap heap = new Heap(A2, Comparator.reverseOrder()); // or (i1, i2) -> i2.compareTo(i1)
        System.out.println("Heap before self heapsort  : " + heap);
        heap.heapsort();
        System.out.println("Heap after  self heapsort  : " + heap);

        // Pass an array to heapsort method directly, this will get rid of the original array... not good?
        Integer[] A3 = new Integer[]{4, 8, 2, 5, 3, 0, 6, 7, 1};
        heap.heapsort(A3);
        System.out.println("Outside arr after heapsort : " + Arrays.toString(A3));
        System.out.println("Original Heap still intact : " + heap);
        System.out.println();

        // play with heap, offer and print out tree structure
        Integer[] hpA = new Integer[]{7, 3, 1, 5, 0, 2, 4, 6};
        Heap hp = new Heap(4);
        System.out.println("Poll from a new heap        : " + hp.poll() + " (cur capacity: " + hp.cap() + ")");
        System.out.println();
        for (int i : hpA) {
            hp.offer(i);
            System.out.println(TreeNode.fromLevelOrder(hp.array()));
            System.out.printf("Current size : %d (cur capacity: %d)\n", hp.size(), hp.cap());
            System.out.println("Heap after offer : " + hp);
            System.out.println();
        }

        while (!hp.isEmpty()) {
            System.out.println("Poll : " + hp.poll());
            System.out.println(TreeNode.fromLevelOrder(hp.array()));
            System.out.printf("Current size : %d (cur capacity: %d)\n", hp.size(), hp.cap());
            System.out.println(hp);
            System.out.println();
        }

        //root.postOrderIterative();
        //TreeNode sameRoot = TreeNode.fromLevelOrder(root.levelOrder().toArray(Integer[]::new));

        // test iterator with for each loop
        TreeNode rootItr = TreeNode.fromLevelOrderSpecial(new String[]{"5", "2", "12", "1", "3", "#", "14"});
        List<Integer> res = new ArrayList<>();

        // TreeNode can now be printed directly (in level order)
        System.out.println("\nPrint a tree directly: ");
        System.out.println(rootItr);

        // create an iterator and iterate it manually
        for (Iterator<TreeNode> it = rootItr.iterator(); it.hasNext(); )
            res.add(it.next().key);
        System.out.printf("Iterate an iterator manually: %s\n", res);

        // use for each loop directly on an iterable
        res = new ArrayList<>();
        for (TreeNode treeNode : rootItr)
            res.add(treeNode.key);
        System.out.printf("For each loop on an iterable: %s\n", res);
    }
}
