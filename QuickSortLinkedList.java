import util.ListNode;

public class QuickSortLinkedList {
    // with tail
    public ListNode quickSort2(ListNode head) {
        ListNode[] tail = new ListNode[1];
        return quickSort(head, tail);
    }

    private ListNode quickSort(ListNode head, ListNode[] tail) {
        if (head == null) return null;
        if (head.next == null) return (tail[0] = head);

        ListNode small = new ListNode(0);
        ListNode large = new ListNode(0);
        ListNode curS = small;
        ListNode curL = large;
        ListNode pivot = head;
        head = head.next;
        pivot.next = null;

        while (head != null) {
            if (head.value < pivot.value) {
                curS.next = head;
                curS = curS.next;
            } else {
                curL.next = head;
                curL = curL.next;
            }
            head = head.next;
        }

        curS.next = curL.next = null;

        if (small.next != null) {
            small.next = quickSort(small.next, tail);
            tail[0].next = pivot;
        } else small.next = pivot;

        tail[0] = pivot;
        pivot.next = quickSort(large.next, tail);

        return small.next;
    }
    // with tail solution ends here

    public ListNode quickSort(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode small = new ListNode(0);
        ListNode large = new ListNode(0);
        partition(head, small, large); // use head as pivot
        small.next = quickSort(small.next);
        large.next = quickSort(large.next);
        ListNode cur = small;
        while (cur.next != null) {
            cur = cur.next;
        }
        cur.next = head;
        head.next = large.next;
        return small.next;
        // Write your solution here
    }

    private void partition(ListNode head, ListNode small, ListNode large) { // Notice small & large, java pass by value
        ListNode cur = head;
        while (cur.next != null) {
            if (cur.next.value < head.value) {
                small.next = cur.next;
                small = small.next;
            } else {
                large.next = cur.next;
                large = large.next;
            }
            cur = cur.next;
        }

        large.next = small.next = null;
    }

    public static void main(String[] args) {
        QuickSortLinkedList qs = new QuickSortLinkedList();
        System.out.println(qs.quickSort(ListNode.fromArray(null)));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{9, 7, 0, 5, 4, 1, 8, 2, 6, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{9, 7, 0, 5, 4, 1, 8, 2, 6, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{0})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{3})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{2, 8})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 2})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{2, 8})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{8, 2})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{8, 5, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 5, 3})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{8, 5, 3, 1})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 5, 3, 1})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{5, 4, 1, 2, 6, 3})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{5, 4, 1, 2, 6, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{4, 2, 6, 3, 5})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{4, 2, 6, 3, 5})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{1, -1, 0})));
        System.out.println(qs.quickSort2(ListNode.fromArray(new int[]{1, -1, 0})));
    }
}
