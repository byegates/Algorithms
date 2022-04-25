import java.util.*;

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
