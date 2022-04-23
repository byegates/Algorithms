package Trie;

import java.util.List;

public class Trie {
    private final TrieNode root = new TrieNode();

    public Trie () {}

    public Trie(List<String> list) {
        for (String s : list)
            this.insert(s);
    }

    public Trie(String[] list) {
        for (String s : list)
            this.insert(s);
    }

    public TrieNode root() {
        return root;
    }

    public boolean delete(String s) {
        if (!search(s)) return false;
        s = s.toLowerCase();
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            TrieNode next = cur.children[idx];
            if (--next.count == 0) {
                cur.children[idx] = null;
                return true;
            }
            cur = next;
        }
        cur.isWord = false;
        return true;
    }

    public boolean insert(String s) {
        if (search(s)) return false;
        s = s.toLowerCase();

        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (cur.children[idx] == null)
                cur.children[idx] = new TrieNode();

            TrieNode next = cur.children[idx];
            next.count++;
            cur = next;
        }
        return cur.isWord = true;
    }

    public boolean search(String s) {
        s = s.toLowerCase();
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            TrieNode next = cur.children[idx];
            if (next == null) return false;
            cur = next;
        }
        return cur.isWord;
    }
}

class DebugTrie {
    public static void main(String[] args) {
        Trie trie = new Trie(new String[]{"cat", "app", "Apple", "apples", "dog", "doggy", "tesla"});

        System.out.println(trie.search("app")); // true
        System.out.println(trie.search("apple")); // true
        System.out.println(trie.delete("Apple")); // true
        System.out.println(trie.search("apple")); // false
        trie.delete("apples");
        System.out.println();
        System.out.println(trie.search("apple")); // false
        System.out.println(trie.insert("apple")); // true
        System.out.println(trie.insert("apple")); // false
        System.out.println(trie.search("apple")); // true
        trie.delete("app");
        System.out.println();
        System.out.println(trie.search("apple")); // true
        System.out.println(trie.search("app")); // false
        System.out.println(trie.search("apples")); // false
        trie.insert("apples");System.out.println(trie.search("apples")); // true
    }
}