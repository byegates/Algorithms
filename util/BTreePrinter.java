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

class BTreePrinter {
    public static <T extends Comparable<?>> void printNode(TreeNode root) {
        int maxLevel = TreeNode.getHeight(root);
        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }
    private static <T extends Comparable<?>> void printNodeInternal(List<TreeNode> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes)) return;

        int floor = maxLevel - level;
        int edgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<TreeNode> newNodes = new ArrayList<>();
        for (TreeNode node : nodes) {
            if (node != null) {
                System.out.print(node.key);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }
            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println();

        for (int i = 1; i <= edgeLines; i++) {
            for (TreeNode node : nodes) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (node == null) {
                    BTreePrinter.printWhitespaces(edgeLines + edgeLines + i + 1);
                    continue;
                }
                if (node.left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);
                BTreePrinter.printWhitespaces(i + i - 1);
                if (node.right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);
                BTreePrinter.printWhitespaces(edgeLines + edgeLines - i);
            }
            System.out.println();
        }
        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {while (count-- > 0) System.out.print(" ");}

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object obj : list) if (obj != null) return false;
        return true;
    }
}
