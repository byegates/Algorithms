import util.ListNode;

import java.util.List;

public class InsertionSortLinkedList {
    public ListNode insertionSort(ListNode head) { // TC: O(n^2), SC: O(n)
        if (head == null) return null;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;

        for (ListNode s1 = head; s1.next != null;){
            ListNode s2 = dummy;
            for (; s2 != s1; s2 = s2.next) {
                if (s2.next.value > s1.next.value) {
                    ListNode s3 = s1.next;
                    s1.next = s3.next;
                    s3.next = s2.next;
                    s2.next = s3;
                    break;
                }
            }
            if (s2 == s1) s1 = s1.next;
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        InsertionSortLinkedList sol = new InsertionSortLinkedList();
        System.out.println(sol.insertionSort(ListNode.fromArray(new int[]{5,4,1,2,6,3})));
    }
}
