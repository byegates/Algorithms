import java.util.*;

public class WordLadder {
    // Solution 1
    // word length: L, words dictionary size: N
    // TC: 25 * L^2 * N ==> O(L*N^2), 25*L transformations to check edge,
    // and each need at least O(L) to convert back to String, at least another O(L) for hashing
    // SC: O(n), queue and dict size
    public int ladderLength(String begin, String end, List<String> words) {
        if (words == null || words.size() == 0) return 0;

        Set<String> dict = new HashSet<>(words);
        if (!dict.contains(begin) || !dict.contains(end)) return 0;

        Queue<String> q = new ArrayDeque<>();
        q.offer(begin);
        dict.remove(begin); // for de-dup

        for (int len = 1; !q.isEmpty(); len++) // use for loop as while loop too...
            for (int size = q.size(); size > 0; size--)
                if (bfs(q.poll().toCharArray(), end, dict, q)) return ++len;

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
    // Solution 1 ends here

    // Solution 2 starting from both end to save time
    // q1 and q2 defined using two Set<String> serves as two queue starting with begin and end word
    // During the processing
    public int ladderLength2(String begin, String end, List<String> words) {
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
    // Solution 2 ends here

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
