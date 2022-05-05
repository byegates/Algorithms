package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListNode {
    public int value;
    public ListNode next;
    public ListNode(int value) {
        this(value, null);
    }
    public ListNode(int value, ListNode next) {
        this.value = value;
        this.next = next;
    }


    public int size() {return size(this);}
    public  String toString() {return toString(this);}
    public List<Integer> toList() {return toList(this);}
    public ListNode get(int idx) {return get(this, idx);}
    public ListNode appendHead(int value) {return appendHead(this, value);}
    public ListNode appendTail(int value) {return appendTail(this, value);}
    public ListNode reverseInPairs() {return reverseInPairs(this);}
    public ListNode reorder() {return reorder(this);}
    public ListNode reverse() {return reverse(this);}
    public ListNode reverse2() {return reverse2(this);}
    public boolean hasCycle() {return hasCycle(this);}
    public ListNode cycleNode() {return cycleNode(this);}

    public static String toString(ListNode head) {
        return head.toList().toString();
    }

    public static ListNode reverse(ListNode head) {// Lai34
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    public static ListNode reverse2(ListNode head) {// Lai653
        if (head == null || head.next == null) return head;
        ListNode newHead = reverse2(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static ListNode insert(ListNode head, int value) {// Lai39
        ListNode dummy = new ListNode(0);
        ListNode node = new ListNode(value);
        ListNode prev = dummy;
        while (prev.next != null && prev.next.value < value) prev = prev.next;
        node.next = prev.next;
        prev.next = node;
        return dummy.next;
    }

    public static ListNode fromArray(int[] array) {
        if (array == null || array.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        for (int i : array) {
            cur.next = new ListNode(i);
            cur = cur.next;
        }
        return dummy.next;
    }

    public static List<Integer> toList(ListNode head) {
        ArrayList<Integer> res = new ArrayList<>();
        ListNode cycleNode = cycleNode(head); // will be null if no cycle node
        while (head != cycleNode) {
            res.add(head.value);
            head = head.next;
        }
        if (cycleNode != null) {
            do {
                res.add(head.value);
                head = head.next;
            } while (head != cycleNode);
            res.add(null);
            res.add(cycleNode.value);
        }
        return res;
    }

    public static int size(ListNode head) {
        int i = 0;
        while (head != null) {
            head = head.next;
            i++;
        }
        return i;
    }
    public static ListNode get(ListNode head, int idx) {
        while (idx > 0 && head != null) {
            head = head.next;
            idx--;
        }
        return head;
    }

    public static ListNode appendHead(ListNode head, int value) {
        ListNode newHead = new ListNode(value);
        newHead.next = head;
        return newHead;
    }

    public static ListNode appendTail(ListNode head, int value) {
        ListNode node = new ListNode(value);
        if (head == null) return node;
        ListNode cur = head;
        while (cur.next != null) cur = cur.next;
        cur.next = node;
        return head;
    }

    public static ListNode middleNode(ListNode head) {
        if (head == null) return null;
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    public static ListNode mergeSorted(ListNode h1, ListNode h2) {// Lai39
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (h1 != null && h2 != null) {
            if (h1.value <= h2.value) {
                cur.next = h1;
                h1 = h1.next;
            } else {
                cur.next = h2;
                h2 = h2.next;
            }
            cur = cur.next;
        }
        if (h1 != null) cur.next = h1;
        if (h2 != null) cur.next = h2;
        return dummy.next;
    }

    public static ListNode merge(ListNode h1, ListNode h2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        while (h2 != null) {
            cur.next = h1;
            h1 = h1.next;
            cur.next.next = h2;
            h2 = h2.next;
            cur = cur.next.next;
        }
        if (h1 != null) cur.next = h1;
        return dummy.next;
    }

    public static ListNode reorder(ListNode head) {// Lai41
        if (head == null || head.next == null) return head;
        ListNode mid = middleNode(head);
        ListNode second = reverse(mid.next);
        mid.next = null;
        return merge(head, second);
    }

    public static ListNode addTwoNumbers(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int val = 0;
        while (a != null || b != null || val != 0) {
            if (a != null) {
                val += a.value;
                a = a.next;
            }
            if (b != null) {
                val += b.value;
                b = b.next;
            }
            cur.next = new ListNode(val % 10);
            val /= 10;
            cur = cur.next;
        }
        return dummy.next;
    }

    public static ListNode reverseInPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;
        ListNode newHead = head.next;
        while (head != null && head.next != null) {
            ListNode next = head.next.next;
            head.next.next = head;
            prev.next = head.next;
            head.next = next;
            prev = head;
            head = next;
        }
        return dummy.next;
    }

    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    public static ListNode cycleNode(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (fast == null || fast.next == null) return null;
        while (head != slow) {
            head = head.next;
            slow = slow.next;
        }
        return slow;
    }

    public static void main(String[] args) {
        int[] lai36 = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ListNode root36 = ListNode.fromArray(lai36);
        System.out.println("HasCycle: " + root36.hasCycle());
        root36.next.next.next.next.next.next.next.next.next = root36.next.next.next;
        System.out.println("HasCycle: " + root36.hasCycle());
        System.out.printf("Cycle Node: %s\n", root36.cycleNode());
        System.out.println(root36.toList());
        System.out.println("ReverseInPairs :");
        int[] Lai35a = new int[]{1,2,3,4,5,6,7,8,9};
        System.out.println(Arrays.toString(Lai35a));
        ListNode Lai35h = ListNode.fromArray(Lai35a);
        System.out.println(Lai35h.reverseInPairs());

        System.out.println("AppendHead and AppendTail to null :");
        System.out.printf("%s, %s\n", ListNode.appendHead(null, 0), ListNode.appendHead(null, 1));

        ListNode head = ListNode.fromArray(new int[]{0, 1});
        System.out.printf("AppendTail & AppendHead to %s:\n", head);
        System.out.printf("%s, %s\n", head.appendTail(9), head.appendHead(2));

        System.out.println("Recursion reverse and middle node :");
        ListNode h2 = ListNode.fromArray(Lai35a);
        System.out.println(h2 = h2.reverse2());
        System.out.println(ListNode.middleNode(h2));

        System.out.println("Reorder Test :");
        List<Integer> ans = Arrays.asList(1, 9, 2, 8, 3, 7, 4, 6, 5);
        System.out.println(Arrays.toString(Lai35a));
        ListNode Lai41 = ListNode.fromArray(Lai35a);
        System.out.println(Lai41.reorder());
        System.out.println(Lai41.toList().equals(ans));
    }

}
