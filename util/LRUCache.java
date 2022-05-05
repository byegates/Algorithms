package util;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    static class Node<K, V> {
        Node<K, V> next, prev;
        K key;
        V value;

        Node(K key, V value) {
            update(key, value);
        }

        Node<K, V> update(K key, V value) {
            this.key = key;
            this.value = value;
            return this;
        }
    }

    private final int limit;
    private Node<K, V> head, tail;
    private final Map<K, Node<K, V>> map;

    // limit is the max capacity of the cache
    public LRUCache(int limit) {
        this.limit = limit;
        map = new HashMap<>();
    }

    public boolean isFull() {
        return map.size() >= limit;
    }

    public void set(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node == null && !isFull()) append(new Node<>(key, value));
        else {
            if (node == null) node = tail;
            append(remove(node).update(key, value));
        }
    }

    public V get(K key) {
        Node<K, V> node = map.get(key);
        return node == null ? null : append(remove(node)).value;
    }

    private Node<K, V> remove(Node<K, V> node) {
        map.remove(node.key);
        if (node.prev != null) node.prev.next = node.next;
        if (node.next != null) node.next.prev = node.prev;
        if (node == head) head = head.next;
        if (node == tail) tail = tail.prev;
        node.next = node.prev = null;
        return node;
    }

    private Node<K, V> append(Node<K, V> node) {
        map.put(node.key, node);
        if (head == null) head = tail = node;
        else {
            node.next = head;
            head.prev = node;
            head = node;
        }
        return node;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<K, V> cur = tail;
        while (cur != null) {
            sb.append(cur.key);
            if (cur.prev != null) sb.append(", ");
            cur = cur.prev;
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        LRUCache<Character, Character> lru = new LRUCache<>(3);
        char[] arr = new char[]{'a', 'b', 'c', 'b', 'a', 'd', 'c', 'd', 'a', 'e'};
        String[] results = new String[] {"[a]", "[a, b]", "[a, b, c]", "[a, c, b]", "[c, b, a]", "[b, a, d]", "[a, d, c]", "[a, c, d]", "[c, d, a]", "[d, a, e]"};
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            lru.set(c, c);
            System.out.printf("%5s: %s: %s\n", lru.toString().equals(results[i]), c, lru);
        }
        String[] commands = new String[]{"LRUCache","put","put","put","put","put","get","put","get","get","put","get","put","put","put","get","put","get","get","get","get","put","put","get","get","get","put","put","get","put","get","put","get","get","get","put","put","put","get","put","get","get","put","put","get","put","put","put","put","get","put","put","get","put","put","get","put","put","put","put","put","get","put","put","get","put","get","get","get","put","get","get","put","put","put","put","get","put","put","put","put","get","get","get","put","put","put","get","put","put","put","get","put","put","put","get","get","get","put","put","put","put","get","put","put","put","put","put","put","put"};
        int[][] args2 = new int[][]{{10},{10,13},{3,17},{6,11},{10,5},{9,10},{13},{2,19},{2},{3},{5,25},{8},{9,22},{5,5},{1,30},{11},{9,12},{7},{5},{8},{9},{4,30},{9,3},{9},{10},{10},{6,14},{3,1},{3},{10,11},{8},{2,14},{1},{5},{4},{11,4},{12,24},{5,18},{13},{7,23},{8},{12},{3,27},{2,12},{5},{2,9},{13,4},{8,18},{1,7},{6},{9,29},{8,21},{5},{6,30},{1,12},{10},{4,15},{7,22},{11,26},{8,17},{9,29},{5},{3,4},{11,30},{12},{4,29},{3},{9},{6},{3,4},{1},{10},{3,29},{10,28},{1,20},{11,13},{3},{3,12},{3,8},{10,9},{3,26},{8},{7},{5},{13,17},{2,27},{11,15},{12},{9,19},{2,15},{3,16},{1},{12,17},{9,1},{6,19},{4},{5},{5},{8,1},{11,7},{5,2},{9,28},{1},{2,2},{7,4},{4,22},{7,24},{9,26},{13,28},{11,26}};
        LRUCache<Integer, Integer> lru2 = new LRUCache<>(args2[0][0]);
        for (int i = 1; i < commands.length; i++) {
            String command = commands[i];
            int[] arg = args2[i];
            if (command.equals("put")) lru2.set(arg[0], arg[1]);
            else if (command.equals("get")) lru2.get(arg[0]);
            else System.out.println("Wong command: " + command);
            System.out.println(lru2);
        }
    }
}
