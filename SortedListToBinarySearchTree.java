import util.BTreePrinter;
import util.ListNode;
import util.TreeNode;

import java.util.List;

import static util.Utils.listEqualsArray;

/*
    Given a singly linked list where elements are sorted in ascending order, convert it to a height balanced BST.
    For testing purpose, please make sure for any node in the result, its left subtree should have equal or only one more node than its right subtree.
    Example:
        Given ascending order list: 1→3→4→5→8→11
        return Binary Search Tree is
                  5
              /        \
            3          11
        /      \      /
      1        4    8
 */
public class SortedListToBinarySearchTree {
    private int getSize(ListNode head) {
        int size = 0;
        for (; head != null; head = head.next) size++;
        return size;
    }

    private TreeNode BST(ListNode[] a, int l, int r) {
        if (l > r) return null;
        int mid = r - (r - l) / 2;
        TreeNode root = new TreeNode(a[mid].value);
        root.left  = BST(a, l, mid - 1);
        root.right = BST(a, mid + 1, r);
        return root;
    }

    public TreeNode sortedListToBST(ListNode head) {
        int size = getSize(head);
        if (size == 0) return null;
        ListNode[] a = new ListNode[size];
        for (int i = 0; i < size; i++) {
            a[i] = head;
            head = head.next;
        }
        return BST(a, 0, size - 1);
    }

    public static void main(String[] args) {
        SortedListToBinarySearchTree sol = new SortedListToBinarySearchTree();
        int[] a = new int[]{16, 36, 39, 42, 76, 82, 86, 107, 117, 149, 153, 186, 188, 193, 204, 217, 237, 246, 248, 250, 255, 256, 257, 258, 259, 280, 300, 320, 321, 341, 350, 354, 364, 367, 371, 376, 385, 410, 413, 465, 474, 506, 511, 536, 549, 564, 568, 590, 605, 609, 620, 630, 639, 658, 668, 678, 683, 685, 700, 702, 714, 733, 752, 762, 779, 786, 810, 811, 812, 822, 825, 847, 854, 869, 874, 886, 896, 928, 930, 943, 945, 958, 963, 990, 992, 993};
        ListNode head = ListNode.fromArray(a);
        TreeNode root = sol.sortedListToBST(head);
        List<Integer> res = root.inOrder();
        System.out.println(listEqualsArray(res, a));
    }

}
