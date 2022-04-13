/*
        Given a beginning word, an end word and a dictionary,
        find the least number transformations from begin word to end word, and return the transformation sequences.
        Return empty list if there is no such transformations.

        In each transformation, you can only change one letter,
        and the word should still in the dictionary after each transformation.

        Assumptions
        1. All words have the same length.
        2. All words contain only lowercase alphabetic characters.
        3. There is no duplicates in the word list.
        4.The beginWord and endWord are non-empty and are not the same.

        Example: start = "git", end = "hot", dictionary = {"git","hit","hog","hot","got"}
        Output: [["git","hit","hot"], ["git","got","hot"]]
        LeetCode: https://leetcode.com/problems/word-ladder-ii/submissions/
*/


import java.util.*;

public class WordLadderII {

    private Map<String, Integer> listToIdxMap(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) map.put(list.get(i), i);
        return map;
    }

    private List<String> addBeginWord(String beginWord, List<String> words, Map<String, Integer> indices) {
        if (!indices.containsKey(beginWord)) {
            words = new ArrayList<>(words);
            words.add(beginWord);
            indices.put(beginWord, words.size() - 1);
        }
        return words;
    }

    private List<Integer> validNeighbors(int idx, List<String> words, Map<String, Integer> indices) {
        List<Integer> neighbors = new ArrayList<>();
        char[] cur = words.get(idx).toCharArray();
        for (int i = 0; i < cur.length; i++) {
            char orig = cur[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == orig) continue;
                cur[i] = c;
                String changed = new String(cur);
                int neighborIdx = indices.getOrDefault(changed, -1);
                if (neighborIdx != -1) neighbors.add(neighborIdx);
            }
            cur[i] = orig;
        }
        return neighbors;
    }

    private List<List<Integer>> createPathList(int size) {
        List<List<Integer>> path = new ArrayList<>(size);
        for (int i = 0; i < size; i++) path.add(new ArrayList<>());
        return path;
    }

    public List<List<String>> allPaths(int begin, int end, List<String> words, List<List<Integer>> paths) {
        List<List<String>> res = new ArrayList<>();
        List<String> cur = new ArrayList<>();
        cur.add(words.get(begin));
        dfs(begin, end, cur, words, paths, res);
        return res;
    }

    private void dfs(int begin, int end, List<String> cur, List<String> words, List<List<Integer>> paths, List<List<String>> res) {
        if (begin == end) {
            res.add(new ArrayList<>(cur));
            return;
        }

        for (int next : paths.get(begin)) {
            cur.add(words.get(next));
            dfs(next, end, cur, words, paths, res);
            cur.remove(cur.size() - 1);
        }
    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> words) {
        Map<String, Integer> indices = listToIdxMap(words);
        if (!indices.containsKey(endWord)) return new ArrayList<>();

        // get begin and end index, will use idx of words to form path and de-dup to save space
        words = addBeginWord(beginWord, words, indices);
        int end = indices.get(endWord), begin = indices.get(beginWord);

        // Using an int array as map for de-dup and keep track of bfs levels, as each word has a unique index
        int[] steps = new int[words.size()];
        Arrays.fill(steps, -1); // -1 means not visited
        Queue<Integer> q = new ArrayDeque<>(); // q for bfs
        List<List<Integer>> paths = createPathList(words.size());

        q.offer(end); // Search backwards, so that it's straight forward to get the path
        steps[end] = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == begin) return allPaths(begin, end, words, paths);

            for (int prev : validNeighbors(cur, words, indices)) {
                if (steps[prev] == -1) {
                    q.offer(prev);
                    steps[prev] = steps[cur] + 1;
                }
                if (steps[cur] + 1 == steps[prev]) paths.get(prev).add(cur);
            }
        }

        return new ArrayList<>();
    }

    public static void main(String[] args) {
        WordLadderII wl2 = new WordLadderII();
        System.out.println(wl2.findLadders("git", "hot", Arrays.asList("hit","hog","hot","got")));
        System.out.println(wl2.findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog")));
    }
}
