import util.ListNode;

public class InsertionSortLinkedList {
    public ListNode insertionSort(ListNode head) { // TC: O(n^2), SC: O(n)
        if (head == null) return null;
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        dummy.next = head;

        for (ListNode s1 = head; s1.next != null;){
            ListNode s2 = dummy;
            for (; s2 != s1; s2 = s2.next) {
                if (s2.next.value > s1.next.value) {
                    // index 0 1 2 3
                    // dummy 5 4 3 2
                    //      s1
                    //   s2
                    ListNode temp = s1.next; // temp = 4
                    s1.next = temp.next; // 5 --> 3
                    temp.next = s2.next; // 4 --> 5
                    s2.next = temp; // s2 --> 4
                    break;
                    // index 0 1 2 3
                    // dummy 4 5 3 2
                    //        s1
                    //   s2
                }
            }
            if (s2 == s1) s1 = s1.next;
            // index 0 1 2 3
            // dummy 2 3 4 5 6
            //               s1
            //            s2
        }

        return dummy.next;
    }

    public static void main(String[] args) {
        InsertionSortLinkedList sol = new InsertionSortLinkedList();
        System.out.println(sol.insertionSort(ListNode.fromArray(new int[]{5,4,1,2,6,3})));
    }
}
