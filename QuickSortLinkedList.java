import util.ListNode;

public class QuickSortLinkedList {
    private ListNode tail;
    public ListNode quickSort(ListNode head) { // TC: average O(nlogn), worst O(n^2), SC: O(log(n))
        if (head == null) return null;
        if (head.next == null) return tail = head;

        ListNode small = new ListNode(0);
        ListNode large = new ListNode(0);
        partition(head, small, large); // use head as pivot
        head.next = null; // must cut it

        if (small.next != null) {
            small.next = quickSort(small.next);
            tail.next = head;
        } else small.next = head;
        tail = head;
        head.next = quickSort(large.next);
        return small.next;
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
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{9, 7, 0, 5, 4, 1, 8, 2, 6, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{5, 4, 1, 2, 6, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{4, 2, 6, 3, 5})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 5, 3, 1})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{1, -1, 0})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 5, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{2, 8})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 2})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{0})));
        System.out.println(qs.quickSort(ListNode.fromArray(null)));
    }
}
