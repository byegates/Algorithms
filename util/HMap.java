package util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class HMap <K, V> implements Map<K, V> {
    /*
    Version 1.00
    First implementation of Map Interface, as a HashMap
    tested:
    LeetCode 706. Design HashMap : https://leetcode.com/submissions/detail/737324896/
    438. Find All Anagrams in a String: https://leetcode.com/submissions/detail/737325550/
    126. Word Ladder II: https://leetcode.com/submissions/detail/737327314/
     */
    private int size;
    private Entry<K, V>[] arr;

    // Defaults
    private final float loadFactor;
    private static final float DEFAULT_LOAD_FACTOR = 0.7f;
    private static final int RESIZE = 2, DEFAULT_CAP = 16;

    public HMap() {
        this(DEFAULT_CAP, DEFAULT_LOAD_FACTOR);
    }
    public HMap(int cap, float loadFactor) {
        if (cap < 1) throw new IllegalArgumentException("cap can't be < 1!");
        if (loadFactor <= .0 || loadFactor > 1) throw new IllegalArgumentException("loadFactor must be > 0 and <= 1!");
        size = 0;
        arr = new Entry[cap];
        this.loadFactor = loadFactor;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object k) {
        K key = (K) k;
        Entry<K, V> cur = arr[getIdx(key)];
        while (cur != null) {
            if (equalsKey(cur.key, key)) return true;
            cur = cur.next;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object v) {
        V val = (V) v;
        for (Entry<K, V> cur : arr) {
            while (cur != null) {
                if (equalsVal(cur.val, val)) return true;
                cur = cur.next;
            }
        }
        return false;
    }

    @Override
    public V get(Object k) {
        K key = (K) k;
        Entry<K, V> cur = arr[getIdx(key)];
        while (cur != null) {
            if (equalsKey(cur.key, key))
                return cur.val;
            cur = cur.next;
        }
        return null;
    }

    @Override
    public V put(Object k, Object v) {
        K key = (K) k;V val = (V) v;
        int idx = getIdx(key);
        Entry<K, V> cur = arr[idx];
        while (cur != null) {
            if (equalsKey(cur.key, key)) { // update when key exists
                V oldVal = cur.val;
                cur.val = val;
                return oldVal;
            }
            cur = cur.next;
        }

        // insert as head when key do not exist, attach original head as next
        arr[idx] = new Entry<>(key, val, arr[idx]);

        // increase size and check for rehash
        if ((++size + .0f) / arr.length >= loadFactor) rehash();
        return null;
    }

    @Override
    public V remove(Object k) {
        K key = (K) k;
        int idx = getIdx(key);
        Entry<K, V> cur = arr[idx];
        Entry<K, V> pre = null;
        while (cur != null) {
            if (equalsKey(cur.key, key)) { // remove when key match found
                if (pre == null) arr[idx] = cur.next;
                else pre.next = cur.next;
                size--;
                return cur.val;
            }
            pre = cur;
            cur = cur.next;
        }
        return null;
    }

    @Override
    public void putAll( Map m) {

    }

    @Override
    public void clear() {
        Arrays.fill(arr, null);
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return null;
    }

    private int getIdx(K key) {
        if (key == null) return 0;
        return (key.hashCode() & 0x7FFFFFFF) % arr.length;
    }

    private void rehash() {
        Entry<K, V>[] old = arr;
        arr = new Entry[arr.length * RESIZE];
        for (Entry<K, V> cur : old)
            while (cur != null) {
                Entry<K, V> next = cur.next;
                int idx = getIdx(cur.key);
                cur.next = arr[idx];
                arr[idx] = cur;
                cur = next;
            }
    }

    private boolean equalsKey(K k1, K k2) {
        if (k1 == null || k2 == null) return k1 == k2;
        return k1.equals(k2);
    }

    private boolean equalsVal(V v1, V v2) {
        if (v1 == null || v2 == null) return v1 == v2;
        return v1.equals(v2);
    }

    static class Entry<K, V> {
        final K key;
        V val;
        Entry<K, V> next;

        Entry(K key, V val, Entry<K, V> next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }
}
