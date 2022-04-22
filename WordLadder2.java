// Solution using string directly without index, easier to understand
// for more efficient solution, refer to another file named WordLadderII

import java.util.*;

public class WordLadder2 {
    public List<List<String>> findLadders(String begin, String end, List<String> words) {
        List<List<String>> res = new ArrayList<>();
        if (!words.contains(end)) return res;

        Map<String, List<String>> paths = new HashMap<>(); // to store node to node paths
        bfs(begin, end, words, paths); // bfs to traversal the whole graph and store paths
        dfs(new ArrayList<>(), end, begin, paths, res); // dfs to get all path

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

        if (!paths.containsKey(cur)) return; // if there's no valid paths, we'll just return;

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
                if (step + 1 == map.get(next)) // any expanded/generated nodes has min steps are valid path
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
