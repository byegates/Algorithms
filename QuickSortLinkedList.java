import util.ListNode;
public class QuickSortLinkedList {
    static class Pivot { // helper class to pass two ListNode between methods
        ListNode prev, newEnd;
        Pivot(ListNode prev, ListNode newEnd) {
            this.prev = prev;
            this.newEnd = newEnd;
        }
    }
    public ListNode quickSort(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        quickSort(dummy, getTail(head)); // Begin node (prev of begin node, we always use prev for begin node for the possibility of reconnecting) and end node to sort
        return dummy.next;
    }

    private ListNode getTail(ListNode head) {
        while (head.next != null) head = head.next;
        return head;
    }

    private void quickSort(ListNode start, ListNode end) {
        if (start == end || start.next == end) return;
        Pivot pivot = partition(start, end);
        quickSort(start, pivot.prev);
        quickSort(pivot.prev.next, pivot.newEnd); // pivot.prev.next is pivot itself, and it's the prev of next partition; and after partition we have a new end node
    }

    private Pivot partition(ListNode prev, ListNode end) {
        ListNode cur = prev.next;
        prev.next = null; // cut connection now, will reconnect at the end, may not be mandatory, just to be safe
        ListNode next = end.next; // save for reconnect later
        end.next = null; // cut connection now
        ListNode large = new ListNode(0);
        ListNode small = new ListNode(0);
        ListNode endOfLarge = large, endOfSmall = small;

        while (cur != end) { // we use right as pivot
            if (cur.value < end.value) {
                endOfSmall.next = cur;
                cur = cur.next;
                endOfSmall = endOfSmall.next;
            } else {
                endOfLarge.next = cur;
                cur = cur.next;
                endOfLarge = endOfLarge.next;
            }
        }

        return connect(prev, end, next, small, large, endOfSmall, endOfLarge);
    }

    private Pivot connect(ListNode prev, ListNode pivot, ListNode next, ListNode small, ListNode large, ListNode endOfSmall, ListNode endOfLarge) {

        ListNode list = new ListNode(0); // list for whole partitioned segment
        ListNode lastNode = list;
        ListNode prePivot = prev;

        if (small.next != null) { // connect small to sorted
            endOfSmall.next = null;
            lastNode.next = small.next;
            lastNode = endOfSmall;
            prePivot = endOfSmall;
        }
        lastNode.next = pivot; // connect pivot to sorted
        lastNode = pivot;

        if (large.next != null) { // connect large to sorted
            endOfLarge.next = null;
            lastNode.next = large.next;
            lastNode = endOfLarge;
        }

        prev.next = list.next;
        lastNode.next = next;

        return new Pivot(prePivot, lastNode);
    }

    public static void main(String[] args) {
        QuickSortLinkedList qs = new QuickSortLinkedList();
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{5, 4, 1, 2, 6, 3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{4, 2, 6, 3, 5})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{8, 2})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{3})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{0})));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{1, -1, 0})));
        System.out.println(qs.quickSort(ListNode.fromArray(null)));
        System.out.println(qs.quickSort(ListNode.fromArray(new int[]{9, 7, 0, 5, 4, 1, 8, 2, 6, 3})));
    }
}
