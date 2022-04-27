import util.ListNode;

public class SelectionSortLinkedList {
    public static ListNode selectionSort(ListNode head) { // Lai28
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        for (ListNode slow = dummy; slow.next != null; slow = slow.next, head = slow.next) {
            ListNode minPre = slow;
            for (;head.next != null; head = head.next)
                if (head.next.value < minPre.next.value) minPre = head;

            if (minPre.next == slow.next) continue;
            ListNode min = removeNext(minPre);
            insertNodeNext(slow, min);
        }
        return dummy.next;
    }

    private static void insertNodeNext(ListNode head, ListNode node) {
        node.next = head.next;
        head.next = node;
    }

    private static ListNode removeNext(ListNode head) {
        ListNode min = head.next;
        head.next = min.next;
        min.next = null;
        return min;
    }

    public static void main(String[] args) {
        ListNode ll = ListNode.fromArray(new int[]{5, 4, 1, 2, 6, 3});
        System.out.println(selectionSort(ll));

    }
}
