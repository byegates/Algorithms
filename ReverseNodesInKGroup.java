import util.ListNode;

public class ReverseNodesInKGroup {
    // solution 1, count first
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head == null || k == 1) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode curr, prev = dummy , next;
        int count = 0;
        for (curr = dummy; curr.next != null; curr = curr.next)
            count++;

        for (; count >= k; count -= k){
            curr = prev.next;
            next = curr.next;
            for(int i = 1; i < k; i++){
                curr.next = next.next;
                next.next = prev.next;
                prev.next = next;
                next = curr.next;
            }
            prev = curr;
        }
        return dummy.next;
    }
    // solution 1 ends here
}
