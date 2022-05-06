import util.ListNode;

public class RotateListbyKplaces {
    // sol 1
    public ListNode rotateKplace(ListNode head, int k) { // TC: O(n + n - k), SC: O(1)
        if (head == null) return null;
        ListNode cur = head;
        int len = 1;
        while (cur.next != null) {
            cur = cur.next;
            len++;
        }

        cur.next = head;
        k %= len;

        for (int i = 0; i < len - k; i++) cur = cur.next;

        head = cur.next;
        cur.next = null;
        return head;
    }
    // sol1 ends here

    // sol 2 slightly optimized
    // When k > n, TC for this solution is same as solution 1
    // when k < n,TC: k + 2 * (n - k) (cause we're moving 2 pointers) 2n - k, it's the same...
    // SC: O(1)
    public ListNode rotateKplace2(ListNode head, int k) {
        if (head == null) return null;

        ListNode cur = head;
        int len = 1;
        for (int i = 0; i < k && cur.next != null; i++) {
            cur = cur.next;
            len++;
        }

        // this means k is not longer than length of list, then we can return faster using two pointers
        if (cur.next != null) return faster(head, cur);

        cur.next = head;
        k %= len;

        for (int i = 0; i < len - k; i++) cur = cur.next;

        head = cur.next;
        cur.next = null;
        return head;
    }

    private ListNode faster(ListNode head, ListNode fast) {
        ListNode slow = head;
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        fast.next = head;
        head = slow.next;
        slow.next = null;
        return head;
    }
    // sol 2 ends here

    public static void main(String[] args) {
        ListNode root = ListNode.fromArray(new int[] {1, 2, 3, 4, 5});
        RotateListbyKplaces sol = new RotateListbyKplaces();
        System.out.println(root);
        System.out.println(root = sol.rotateKplace(root, 2));
        System.out.println(root = sol.rotateKplace(root, 3));

        System.out.println();
        System.out.println(root = sol.rotateKplace(root, 2));
        System.out.println(root = sol.rotateKplace(root, 3));
        System.out.println(root = sol.rotateKplace(root, 13));
        System.out.println(root = sol.rotateKplace(root, 12));
    }
}
