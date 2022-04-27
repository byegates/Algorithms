package util;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    static class Node<K, V> {
        Node<K, V> next;
        Node<K, V> prev;
        K key;
        V value;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        Node<K, V> update(K key, V value) {
            this.key = key;
            this.value = value;
            return this;
        }
    }

    private final int limit;
    private Node<K, V> head;
    private Node<K, V> tail;
    private final Map<K, Node<K, V>> map;

    // limit is the max capacity of the cache
    public LRUCache(int limit) {
        this.limit = limit;
        this.map = new HashMap<>();
    }

    public void set(K key, V value) {
        Node<K, V> node = map.get(key);
        if (node != null) {
            node.value = value;
            remove(node);
        } else if (map.size() < limit) node = new Node<>(key, value);
        else node = remove(tail).update(key, value);
        append(node);
    }

    public V get(K key) {
        Node<K, V> node = map.get(key);
        if (node == null) return null;
        return append(remove(node)).value;
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
            System.out.printf("%s: %s: %s\n", lru.toString().equals(results[i]), c, lru);
        }
    }
}
