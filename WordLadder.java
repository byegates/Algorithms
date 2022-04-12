import java.util.*;

public class WordLadder {
    public int ladderLength(String begin, String end, List<String> words) {
        // word length: L, words dictionary size: N
        // TC: 25 * L * N O(L*N), SC: O(n)
        if (words == null || words.size() == 0) return 0;

        Set<String> dict = new HashSet<>(words);
        if (!dict.contains(begin) || !dict.contains(end)) return 0;

        int len = 1;
        Queue<String> q = new ArrayDeque<>();
        q.offer(begin);
        dict.remove(begin);

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0)
                if (bfs(q.poll().toCharArray(), end, dict, q)) return ++len;
            len++;
        }

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
    }
}
