import util.ListNode;

public class SelectionSortLinkedList {
    // Solution 1
    public static ListNode selectionSort(ListNode head) { // Lai28, TCO(n^2), SC: O(1)
        if (head == null || head.next == null) return head;
        // ListNode dummy = new ListNode(0, head);
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode sorted = new ListNode(0);
        ListNode cur = sorted; // cur is the iteration node of result LinkedList
        while (dummy.next != null) { // every iteration we delete one node from dummy, insert one to sorted
            ListNode minPre = dummy; // default first Node as min / minPre
            for (head = minPre.next; head.next != null; head = head.next) // find pre Node for min in current LinkedList
                if (head.next.value < minPre.next.value) minPre = head;
            cur.next = minPre.next; // insert min to new LinkedList
            cur = cur.next; // maintain cur as last node of sorted to take in new value
            minPre.next = minPre.next.next; // delete min from original LinkedList
        }

        return sorted.next;
    }
    // Solution 1 ends here

    // Solution 2 "in-place"? TC: O(n^2), SC: O(1)
    public static ListNode selectionSort2(ListNode head) { // Lai28
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
    // Solution 2 ends here

    public static void main(String[] args) {
        ListNode ll = ListNode.fromArray(new int[]{5, 4, 1, 2, 6, 3});
        ll = selectionSort2(ll);System.out.println(ll);
        ll = selectionSort(ll);System.out.println(ll);
    }
}
