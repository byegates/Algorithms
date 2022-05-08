package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Node<T extends Comparable<?>> { // not used for now
    TreeNode left, right;
    T data;

    public Node(T data) {
        this.data = data;
    }
}

public class BTreePrinter {
    public static <T extends Comparable<?>> void printNode(TreeNode root) {
        int maxLevel = TreeNode.getHeight(root); // root could be null
        int maxKeyLen = TreeNode.maxDigits(root); // root could be null
        printNodeInternal(Collections.singletonList(root), 1, maxLevel, maxKeyLen);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel, int maxKeyLen) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes)) return;

        //String spaces = " ".repeat(maxKeyLen - 1);
        int spaces = Math.max(0, maxKeyLen - 1);
        int floor = maxLevel - level + maxKeyLen / 2;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0))); // 2^(depth - level - 1)
        int firstSpaces = (int) Math.pow(2, floor) - 1; // 2^(depth-lvl) - 1
        int betweenSpaces = (int) Math.pow(2, floor + 1) - 1; // 2^(depth - lvl + 1) - 1

        BTreePrinter.printWhitespaces(firstSpaces);

        // print node values
        List<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes) {
            int len = maxKeyLen - (maxKeyLen < 3 ? 0 : 1);
            if (node != null) {
                System.out.printf("%-" + len + "d", node.key);
                //System.out.print(node.key);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                //System.out.print(" ");
                System.out.print(" ".repeat(Math.max(1, len - 1)));
            }
            BTreePrinter.printWhitespaces(betweenSpaces + Math.min(0, level - maxKeyLen));
        }
        System.out.println();

        // print '/' & '\'
        for (int i = 1; i <= edgeLines; i++) {
            if (i != edgeLines && i % edgeLines != 1) continue;
            for (TreeNode node : nodes) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (node == null) {
                    BTreePrinter.printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }
                if (node.left != null)
                    System.out.print("/");
                    //System.out.print(" ".repeat(maxKeyLen - 1) + "/");
                else
                    BTreePrinter.printWhitespaces(1);
                BTreePrinter.printWhitespaces(i + i - 1 + Math.min(spaces, 2));
                if (node.right != null)
                    System.out.print("\\");
                    //System.out.print(" ".repeat(maxKeyLen - 1) + "\\");
                else
                    BTreePrinter.printWhitespaces(1);
                BTreePrinter.printWhitespaces(edgeLines + edgeLines - i);
            }
            System.out.println();
        }
        printNodeInternal(newNodes, level + 1, maxLevel, maxKeyLen);
    }

    private static void printWhitespaces(int count) {
        if (count > 0) System.out.print(" ".repeat(count));
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object obj : list) if (obj != null) return false;
        return true;
    }

    public static void main(String[] args) {
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {1, 2, 3, 4, 5, 6, 7}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {1, 1, 2, 1, 2, 3, 4, 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {1, 2, -3, 4, 5, 6, 7}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {11, 11, 12, 11, 12, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 36, 37, 38, 39, 30, 31, 32, 33, 34, 35, 36}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {11, 11, 12, 11, 12, 13, 14, 21, 22, 23, 24, 25, 26, 27, 28, 31, null, 33, 34, null, 36, null, 38, 39, 30, null, 32, 33, null, 35, 36}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {10, 12, -13, 14, 15, 16, 17}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {10, 12, 13, 14, 15, 16, 17, null, null, -108}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, 16, 17}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, -108, 17}));
        BTreePrinter.printNode(TreeNode.fromLevelOrder(new Integer[] {10, 12, 14, null, 16, 17, -18, 27, null, 36, 48, null}));
        TreeNode root = TreeNode.fromLevelOrder(new Integer[] {50, 25, 75, 15, 40, 60, 90, 10, 20, 30, 45, 55, 65, 80, 100, null, null, null, null, null, null, null, null, null, null, 62, null, 78, 85, null, null, 61, 63, 76, null, null, null, null, null, null, null, null, 77});
        BTreePrinter.printNode(root);

    }
}
