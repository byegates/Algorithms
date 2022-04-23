package util;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isWord;
    int count;
}

public class Trie {
    private final TrieNode root;
    public Trie () {
        root = new TrieNode();
    }

    public boolean insert (String s) {
        if (search(s)) return false;

        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            TrieNode next = cur.children.get(c);
            if (next == null) {
                next = new TrieNode();
                cur.children.put(c, next);
            }
            next.count++;
            cur = next;
        }
        return cur.isWord = true;
    }

    public boolean delete (String s) {
        if (!search(s)) return false;
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            TrieNode next = cur.children.get(c);
            next.count--;
            if (next.count == 0) {
                cur.children.remove(c);
                return true;
            }
            cur = next;
        }
        cur.isWord = false;
        return true;
    }

    public boolean search (String s) {
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            TrieNode next = cur.children.get(s.charAt(i));
            if (next == null) return false;
            cur = next;
        }
        return cur.isWord;
    }
}

class DebugTrie {
    public static void main(String[] args) {
        Trie trie = new Trie();
        for (String s : new String[]{"cat", "app", "apple", "apples", "dog", "doggy", "tesla"})
            trie.insert(s);

        System.out.println(trie.search("app"));
        System.out.println(trie.search("apple"));

        trie.delete("apple");
        System.out.println(trie.search("apple"));
        trie.insert("apple");
        System.out.println(trie.search("apple"));
        trie.delete("app");
        System.out.println(trie.search("apple"));
        System.out.println(trie.search("app"));
    }
}