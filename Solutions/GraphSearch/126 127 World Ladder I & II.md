# [127. Word Ladder](https://leetcode.com/problems/word-ladder/)
[661. Word Ladder](https://app.laicode.io/app/problem/661)

## Solution 1
<pre>
word length: L, words dictionary size: N
TC: 25 * L^2 * N ==> O(L*N^2), 25*L transformations to check edge,
and each need at least O(L) to convert back to String, at least another O(L) for hashing
SC: O(n), queue and dict size
</pre>
```java
public class WordLadder {
    public int ladderLength(String begin, String end, List<String> words) {
        if (words == null || words.size() == 0) return 0;

        Set<String> dict = new HashSet<>(words);
        if (!dict.contains(begin)) dict.add(begin);
        if (!dict.contains(end)) return 0;

        Queue<String> q = new ArrayDeque<>();
        q.offer(begin);
        dict.remove(begin); // for de-dup

        for (int len = 1; !q.isEmpty(); len++)
            for (int size = q.size(); size > 0; size--)
                if (bfs(q.poll().toCharArray(), end, dict, q))
                    return ++len;

        return 0;
    }

    private boolean bfs(char[] cur, String end, Set<String> dict, Queue<String> q) {
        for (int i = 0; i < cur.length; i++) {
            char ch = cur[i];
            for (char j = 'a'; j <= 'z'; j++) {
                if (j == ch) continue;
                cur[i] = j;
                String changed = new String(cur);
                if (changed.equals(end)) return true;
                if (!dict.contains(changed)) continue;
                q.offer(changed);
                dict.remove(changed);
            }
            cur[i] = ch;
        }
        return false;
    }

    public static void main(String[] args) {
        WordLadder wl = new WordLadder();
        System.out.println(wl.ladderLength("cdb", "bab", Arrays.asList("bac","adb","abb","bdb","bba","cdd","bab","aaa","bcd","acd","cdb"))); // 3
        System.out.println(wl.ladderLength("git", "hot", Arrays.asList("git","hit","hog","hot"))); // 3
        System.out.println(wl.ladderLength("hot", "dog", Arrays.asList("hot","dog"))); // 0

        System.out.println(wl.ladderLength2("cdb", "bab", Arrays.asList("bac","adb","abb","bdb","bba","cdd","bab","aaa","bcd","acd","cdb"))); // 3
        System.out.println(wl.ladderLength2("git", "hot", Arrays.asList("git","hit","hog","hot"))); // 3
        System.out.println(wl.ladderLength2("hot", "dog", Arrays.asList("hot","dog"))); // 0
    }
}

```

## Solution 2
Start from both end to save time.
```java
class Solution {
    // q1 and q2 defined using two Set<String> serves as two queue starting with begin and end word
    // During the processing
    public int ladderLength(String begin, String end, List<String> words) {
        Set<String> set = new HashSet<>(words);
        if (!set.contains(end)) return 0;

        set.remove(begin);set.remove(end); // set is used for de-dup

        Set<String> q1 = new HashSet<>(), q2 = new HashSet<>();

        q1.add(begin);
        q2.add(end);
        int step = 1;

        while (!q1.isEmpty() && !q2.isEmpty()) {
            swapSmallerAsQ1(q1, q2);
            step++;
            Set<String> q = new HashSet<>();
            for (String s : q1) // all nodes in q1 are visited and q1 can be cleared out
                if (bfs(s.toCharArray(), set, q, q2))
                    return step;
            q1 = q; // q has all the new node generated, use q to replace q1 will ensure all old nodes are gone and new generated nodes are staged for processing
        }
        return 0;
    }

    private boolean bfs(char[] a, Set<String> set, Set<String> q, Set<String> q2){
        for (int i = 0; i < a.length; i++) {
            char orig = a[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == orig) continue;
                a[i] = c;
                String neighbor = new String(a);
                if (q2.contains(neighbor)) return true; // if two sides meet, we found the path
                if (set.remove(neighbor)) q.add(neighbor); // generate neighbor if it's not visited
            }
            a[i] = orig;
        }
        return false;
    }

    public void swapSmallerAsQ1(Set<String> q1, Set<String> q2) {
        if (q1.size() > q2.size()) { // starting from the side with fewer nodes
            Set<String> q = q1;
            q1 = q2;
            q2 = q;
        }
    }
}
```

# [126. Word Ladder II](https://leetcode.com/problems/word-ladder-ii/)
[662. Word Ladder II](https://app.laicode.io/app/problem/662)
```java
public class WordLadder2 {
    public List<List<String>> findLadders(String begin, String end, List<String> words) {
        List<List<String>> res = new ArrayList<>();
        if (!words.contains(end)) return res;

        Map<String, List<String>> paths = new HashMap<>(); // to store node to node paths
        bfs(begin, end, words, paths); // bfs to traversal the whole graph and store paths
        if (paths.containsKey(end)) dfs(new ArrayList<>(), end, begin, paths, res); // dfs to get all path

        return res;
    }

    private void dfs(List<String> sol, String cur, String begin, Map<String, List<String>> paths, List<List<String>> res) {
        if (cur.equals(begin)) { // we hit a valid path if we get from end to begin
            List<String> tmp = new ArrayList<>(sol);
            tmp.add(begin); // begin word is not in map as key
            Collections.reverse(tmp); // path is backwards: from end to begin, so we reverse it, we had to either reverse it here, or add path the normal way then we'll add redundant paths
            res.add(tmp);
            return;
        }

        sol.add(cur);
        for (String next : paths.get(cur))
            dfs(sol, next, begin, paths, res);
        sol.remove(sol.size() - 1);

    }

    private void bfs(String begin, String end, List<String> words, Map<String, List<String>> paths) {
        Map<String, Integer> map = new HashMap<>(); // for de-dup as well as counting steps for bfs
        for (String word : words) map.put(word, -1); // -1 means not visited yet

        Queue<String> q = new ArrayDeque<>();

        q.offer(begin);
        map.put(begin, 0);

        while (!q.isEmpty()) {
            String cur = q.poll();
            int step = map.get(cur);
            if (cur.equals(end)) break;
            for (String next : allValidNeighbors(cur.toCharArray(), map)) {
                if (map.get(next) == -1) {
                    q.offer(next);
                    map.put(next, step + 1); // first time expanding from cur to next, record the steps, it's the min steps we can get
                }
                if (map.get(next) == step + 1) // any expanded/generated nodes has min steps are valid path
                    addPath(cur, next, paths);
            }
        }
    }

    private void addPath(String cur, String next, Map<String, List<String>> paths) {
        if (!paths.containsKey(next))
            paths.put(next, new ArrayList<>());
        paths.get(next).add(cur); // We store the path backwards
    }

    public List<String> allValidNeighbors(char[] cur, Map<String, Integer> map) {
        List<String> neighbors = new ArrayList<>();
        for (int i = 0; i < cur.length; i++) {
            char orig = cur[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == orig) continue;
                cur[i] = c;
                String neighbor = new String(cur);
                if (map.containsKey(neighbor)) neighbors.add(neighbor);
            }
            cur[i] = orig;
        }
        return neighbors;
    }

    public static void main(String[] args) {
        WordLadder2 wl2 = new WordLadder2();
        System.out.println(wl2.findLadders("git", "hot", Arrays.asList("hit","hog","hot","got"))); // [[git, got, hot], [git, hit, hot]]
        System.out.println(wl2.findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog"))); // [[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]
        System.out.println(wl2.findLadders("hot", "dog", Arrays.asList("hot","dog"))); // []
    }
}
```

## Solution 2
```java
public class WordLadderII {

    private Map<String, Integer> listToIdxMap(List<String> list) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list.size(); i++) map.put(list.get(i), i);
        return map;
    }

    private List<String> addBeginWord(String beginWord, List<String> words, Map<String, Integer> map) {
        words = new ArrayList<>(words);
        words.add(beginWord);
        map.put(beginWord, words.size() - 1);
        return words;
    }

    private List<Integer> neighbors(int curIdx, List<String> words, Map<String, Integer> map) {
        List<Integer> nodes = new ArrayList<>();
        char[] cur = words.get(curIdx).toCharArray();
        for (int i = 0; i < cur.length; i++) {
            char orig = cur[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == orig) continue;
                cur[i] = c;
                String changed = new String(cur);
                int idx = map.getOrDefault(changed, -1);
                if (idx != -1) nodes.add(idx);
            }
            cur[i] = orig;
        }
        return nodes;
    }

    private List<List<Integer>> createPathList(int size) {
        List<List<Integer>> path = new ArrayList<>(size);
        for (int i = 0; i < size; i++) path.add(new ArrayList<>());
        return path;
    }

    private void dfs(int cur, int end, List<String> sol, List<String> words, List<List<Integer>> paths, List<List<String>> res) {
        if (cur == end) {
            res.add(new ArrayList<>(sol));
            return;
        }

        for (int next : paths.get(cur)) {
            sol.add(words.get(next));
            dfs(next, end, sol, words, paths, res);
            sol.remove(sol.size() - 1);
        }
    }

    private void bfs(int begin, int end, List<String> words, Map<String, Integer> map, List<List<Integer>> paths) {
        // Using an int array as map for de-dup and keep track of bfs levels, as each word has a unique index
        int[] steps = new int[words.size()];
        Arrays.fill(steps, -1); // 1 means not visited
        Queue<Integer> q = new ArrayDeque<>(); // q for bfs

        q.offer(begin); // Search backwards, so that it's straight forward to get the path
        steps[begin] = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == end)
                break;
            for (int next : neighbors(cur, words, map)) {
                if (steps[next] == -1) {
                    q.offer(next);
                    steps[next] = steps[cur] + 1; // first time expanding from cur to next, record the steps, it's the min steps we can get
                }
                if (steps[cur] + 1 == steps[next])
                    paths.get(cur).add(next); // any expanded/generated nodes has min steps are valid path
            }
        }
    }

    // time and space complexity:
    // Assume length of words list is n, that means there are n nodes in the graph
    // Double direction edges (or you could say one no direction edge) exits between any two nodes when changing
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> words) {
        List<List<String>> res = new ArrayList<>();
        Map<String, Integer> map = listToIdxMap(words); // map String to index, throughout the program we'll use index instead of string for intermediate logic
        if (!map.containsKey(endWord)) return res;

        // get begin and end index, will use idx of words to form path and de-dup to save space
        if (!map.containsKey(beginWord))
            words = addBeginWord(beginWord, words, map);
        int end = map.get(endWord), begin = map.get(beginWord);

        // Create collections for solution
        List<List<Integer>> paths = createPathList(words.size());
        List<String> solution = new ArrayList<>();
        solution.add(words.get(begin));

        bfs(begin, end, words, map, paths); // bfs to fill all steps into paths list
        dfs(begin, end, solution, words, paths, res); // dfs to search all steps and return all complete path

        return res;
    }

    public static void main(String[] args) {
        WordLadderII wl2 = new WordLadderII();
        System.out.println(wl2.findLadders("git", "hot", Arrays.asList("hit","hog","hot","got"))); // [[git, got, hot], [git, hit, hot]]
        System.out.println(wl2.findLadders("hit", "cog", Arrays.asList("hot","dot","dog","lot","log","cog"))); // [[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]
        System.out.println(wl2.findLadders("hot", "dog", Arrays.asList("hot","dog"))); // []
    }
}
```

## Solution 3 Using a graphNode
```java
public class WordLadderIIc {

    static class GraphNode {
        private static List<String> words; // copy of all words
        private static Map<String, Integer> map; // word to index map to map all word to integer, shouldn't initialize here as this is static field of static class
        private static int beginIdx;
        private static int endIdx;
        private static GraphNode beginNode;
        private static GraphNode endNode;
        int idx; // integer encoding of a string using its index
        int step = -1; // step -1 means this node has not been visited, begin node will have step 0, rest of the nodes reachable from begin will have a positive step indicates min number of steps from begin
        List<Integer> neighbors = new ArrayList<>();
        List<Integer> prev = new ArrayList<>();

        GraphNode(String s) {
            idx = toIdx(s);
            createNeighbors();
        }

        public static List<GraphNode> createGraph(List<String> list, String beginWord, String endWord) {
            words = list;
            map = new HashMap<>(); // must initialize here
            for (int i = 0; i < words.size(); i++) map.put(words.get(i), i);
            beginIdx = map.get(beginWord);
            endIdx = map.get(endWord);

            List<GraphNode> graph = new ArrayList<>();
            for (String word : words) graph.add(new GraphNode(word));

            beginNode = graph.get(beginIdx);
            endNode = graph.get(endIdx);

            return graph;
        }

        public void createNeighbors() {
            char[] a = word().toCharArray();
            for (int i = 0; i < a.length; i++) {
                char orig = a[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == orig) continue;
                    a[i] = c;
                    int neiIdx = toIdx(new String(a));
                    if (neiIdx != -1) neighbors.add(neiIdx);
                }
                a[i] = orig;
            }
        }

        public int toIdx(String s) {
            return map.getOrDefault(s, -1);
        }

        public String word() {
            return words.get(idx);
        }
    }

    private void bfs(List<GraphNode> graph) {
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(GraphNode.beginIdx);
        GraphNode.beginNode.step = 0;

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == GraphNode.endIdx) break;

            for (int nei : graph.get(cur).neighbors) {
                if (graph.get(nei).step == -1) {
                    q.offer(nei);
                    graph.get(nei).step = graph.get(cur).step + 1;
                }
                if (graph.get(nei).step == graph.get(cur).step + 1)
                    graph.get(nei).prev.add(cur);
            }
        }
    }

    private void dfs(List<GraphNode> graph, int cur, List<String> sol, List<List<String>> res) {
        if (graph.get(cur).prev.size() == 0) {
            List<String> tmp = new ArrayList<>(sol);
            Collections.reverse(tmp);
            res.add(tmp);
            return;
        }

        for (int pre : graph.get(cur).prev) {
            sol.add(graph.get(pre).word());
            dfs(graph, pre, sol, res);
            sol.remove(sol.size() - 1);
        }

    }

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> words) {
        List<List<String>> res = new ArrayList<>();
        if (!words.contains(endWord)) return res;
        if (!words.contains(beginWord)) words.add(beginWord);

        List<GraphNode> graph = GraphNode.createGraph(words, beginWord, endWord);

        bfs(graph);
        List<String> sol = new ArrayList<>();
        sol.add(endWord);
        if (GraphNode.endNode.prev.size() > 0) // if this size == 0, that means no valid path to end
            dfs(graph, GraphNode.endIdx, sol, res);

        return res;
    }

    public static void main(String[] args) {
        WordLadderIIc wl2 = new WordLadderIIc();
        System.out.println(wl2.findLadders("git", "hot", Arrays.asList("git", "hit", "hog", "hot", "got"))); // [[git, got, hot], [git, hit, hot]]
        System.out.println(wl2.findLadders("hit", "cog", Arrays.asList("hit", "hot", "dot", "dog", "lot", "log", "cog"))); // [[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]
        System.out.println(wl2.findLadders("hot", "dog", Arrays.asList("hot", "dog"))); // []
    }
}
```
## Solution 4?
```java
public class Wordladder2b {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> words) {

        //make sure both begin and end words in the dictionary
        if (!words.contains(endWord)) return new ArrayList<>();
        if (!words.contains(beginWord)) words.add(beginWord);
        Set<String> set = new HashSet<>(words);

        Map<String, List<String>> pathPool = getPathCandidates(beginWord, endWord, set);

        return getFinalPath(endWord, pathPool);
    }

    private Map<String, List<String>> getPathCandidates(String beginWord, String endWord, Set<String> set) {

        Map<String, List<String>> map = new HashMap<>();
        Queue<String> q = new ArrayDeque<>();

        //initial state
        q.offer(beginWord);
        map.put(beginWord, null);

        //expand and generate process
        while (!q.isEmpty()) {
            int curSize = q.size();
            Map<String, List<String>> curMap = new HashMap<>();
            for (int j = 0; j < curSize; j++) {
                String cur = q.poll();
                StringBuilder sb = new StringBuilder(cur);
                for (int i = 0; i < cur.length(); i++) {
                    char orig = cur.charAt(i);
                    for (char c = 'a'; c <= 'z'; c++) {
                        if (c == orig) continue;
                        sb.setCharAt(i, c);
                        String nei = sb.toString();
                        if (!set.contains(nei)) continue; //1st, check whether it is in the dictionary
                        if (map.containsKey(nei)) continue; //2nd, make sure the neighbor was not generated in the previous level
                        if (!curMap.containsKey(nei)) { //3.1, if the neighbor is first time generated, push it into the queue
                            q.offer(nei);
                            curMap.put(nei, new ArrayList<>());
                        }
                        //3.2, No matter the neighbor is generated by a sibling or the firs time, always add path
                        curMap.get(nei).add(cur);
                    }
                    sb.setCharAt(i, orig);
                }
            }

            //put valid neighbors into the global map
            for (Map.Entry<String, List<String>> entry : curMap.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
                if (entry.getKey().equals(endWord))
                    return map;
            }
        }
        return map;
    }

    private List<List<String>> getFinalPath(String endWord, Map<String, List<String>> pathPool) {
        if (!pathPool.containsKey(endWord)) return new ArrayList<>();

        List<List<String>> result = new ArrayList<>();
        dfsHelper(endWord, pathPool, new ArrayList<>(), result);

        return result;
    }

    private void dfsHelper(String curWord, Map<String, List<String>> pathPool, List<String> cur, List<List<String>> result) {
        //base case
        if (pathPool.get(curWord) == null) {
            List<String> tmp = new ArrayList<>(cur);
            tmp.add(curWord);
            Collections.reverse(tmp);
            result.add(tmp);
            return;
        }

        //recursion
        cur.add(curWord);
        for (String pre : pathPool.get(curWord))
            dfsHelper(pre, pathPool, cur, result);
        cur.remove(cur.size() - 1);
    }

    public static void main(String[] args) {
        Wordladder2b wl2 = new Wordladder2b();
        System.out.println(wl2.findLadders("git", "hot", Arrays.asList("git", "hit","hog","hot","got"))); // [[git, got, hot], [git, hit, hot]]
        System.out.println(wl2.findLadders("hit", "cog", Arrays.asList("hit", "hot","dot","dog","lot","log","cog"))); // [[hit, hot, dot, dog, cog], [hit, hot, lot, log, cog]]
        System.out.println(wl2.findLadders("hot", "dog", Arrays.asList("hot","dog"))); // []
        int[] a = new int[]{1, 2, 3};
        List<Integer> l = new ArrayList<>(Arrays.stream(a).boxed().toList());
        l.sort(Collections.reverseOrder());
    }
}
```