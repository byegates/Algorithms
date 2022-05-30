/*
    After the event, our company will take the students out for dinner.
    The restaurant has a large round table that can fit the whole party.
    We want to know if we can arrange the students so that the names of all students around the table form an “infinite loop.”
    For each pair of neighboring students s1 and s2, the last letter of s1’s name must be identical to the first letter of s2’s name.

    For example, “ALICE” and “ERIC” can sit together, but “ALICE” and “BOB” cannot.
    Given an array of names, determine if it is possible to arrange the students at the round table in this way.
    Input: an array of names. Each name contains capital letters only.
    Output: true or false.

    Example
    Input: String[] = {“ALICE”, “CHARLES”, “ERIC”, “SOPHIA”}
    Output: true

    Analysis:
 */

import java.util.*;

public class InfiniteLoopAroundTheDinnerTable {
    // Solution 1, dfs swap-swap, preferred, TC: O(n!) max, can be real small, SC: O(n)
    public boolean isInfinite(String[] names) {
        System.out.printf("%s : ", Arrays.toString(names));
        if (names == null || names.length == 0) return false;
        return dfs(1, names);
    }

    private boolean dfs(int idx, String[] names) {
        if (idx == names.length)
            return valid(names, idx - 1, 0);

        for (int i = idx; i < names.length; i++) {
            if (!valid(names, idx - 1, i)) continue;
            if (i != idx) swap(names, i, idx);
            if (dfs(idx + 1, names)) return true;
            if (i != idx) swap(names, i, idx);
        }

        return false;
    }

    private void swap(String[] names, int i, int j) {
        String tmp = names[i];
        names[i] = names[j];
        names[j] = tmp;
    }

    public boolean valid(String[] names, int i, int j) {
        if (i < 0) return true;
        return names[i].charAt(names[i].length() - 1) == names[j].charAt(0);
    }

    // Solution 2, dfs with set de-dup
    public boolean isInfinite2(String[] names) {
        System.out.printf("%s : ", Arrays.toString(names));
        if (names == null || names.length == 0) return false;
        Set<String> set = new HashSet<>();
        String[] cur = new String[names.length];
        return dfs2(0, cur, set, names);
    }

    private boolean dfs2(int idx, String[] cur, Set<String> set, String[] names) {
        if (idx == cur.length)
            return valid(cur, idx - 1, 0);

        for (String s : names) {
            if (!set.add(s)) continue;
            cur[idx] = s;
            if (!valid(cur, idx - 1, idx)) continue;
            if (dfs2(idx + 1, cur, set, names)) return true;
            set.remove(s);
        }

        return false;
    }
    // Solution 2 ends here

    public void verifySolution2(String[] names) {
        System.out.println(isInfinite(names) == isInfinite2(names));
    }

    public static void main(String[] args) {
        InfiniteLoopAroundTheDinnerTable il = new InfiniteLoopAroundTheDinnerTable();
        System.out.println(il.isInfinite(new String[]{"a", "bc", "c", "ddb", "cd", "da", "aad"}));
//        System.out.println(il.isInfinite(l1));
//        System.out.println(il.isInfinite(l2));
        System.out.println(il.isInfinite(new String[]{"A"})); // true
        System.out.println(il.isInfinite(new String[0])); // false
        System.out.println(il.isInfinite(null)); // false
        System.out.println(il.isInfinite(new String[]{"A", "B"})); // false

        List<String> l1 = new ArrayList<>(Arrays.asList("ALICE", "CHARLES", "ERIC", "SOPHIA"));
        List<String> l2 = new ArrayList<>(Arrays.asList("ALICE", "XRIC", "CHARLES", "SOPHIA"));
        List<String> l3 = new ArrayList<>(Arrays.asList("AxB", "BxC", "CxD", "DxE", "ExF", "FxG", "GxA"));
        System.out.println();
        il.verifySolution2(new String[]{"A"}); // true
        il.verifySolution2(new String[0]); // true
        il.verifySolution2(null); // true
        il.verifySolution2(new String[]{"A", "B"}); // true
        il.verifySolution2(l1.toArray(new String[0]));
        il.verifySolution2(l2.toArray(new String[0]));
        System.out.println();
        System.out.println(il.isInfinite(l1.toArray(new String[0]))); // true
        System.out.println(il.isInfinite(l2.toArray(new String[0]))); // false
        Collections.shuffle(l1);
        System.out.println(il.isInfinite(l1.toArray(new String[0]))); // true
        System.out.println();
        il.verifySolution2(l1.toArray(new String[0]));
        l1.set(0, "XXX");
        System.out.println(il.isInfinite(l1.toArray(new String[0]))); // false
        il.verifySolution2(l1.toArray(new String[0]));
        il.verifySolution2(l3.toArray(new String[0]));
        System.out.println();

        System.out.println(il.isInfinite(l3.toArray(new String[0]))); // true
        Collections.shuffle(l3);
        il.verifySolution2(l3.toArray(new String[0]));
        System.out.println(il.isInfinite(l3.toArray(new String[0]))); // true
        Collections.shuffle(l3);
        il.verifySolution2(l3.toArray(new String[0]));
        System.out.println(il.isInfinite(l3.toArray(new String[0]))); // true

        List<String> l5 = new ArrayList<>(Arrays.asList("AB", "BA", "A", "AC", "CA", "A", "A"));
        il.verifySolution2(l5.toArray(new String[0]));
        System.out.println(il.isInfinite(l5.toArray(new String[0])));
        Collections.shuffle(l5);
        il.verifySolution2(l5.toArray(new String[0]));
        System.out.println(il.isInfinite(l5.toArray(new String[0])));

        List<String> l4 = new ArrayList<>(Arrays.asList("AxB", "BxC", "CxD", "DxE", "ExF", "FxG", "GxH", "HxI", "IxJ", "JxK", "KxL", "LxM", "MxN", "NxO", "OxP", "PxQ", "QxR", "RxS", "SxT", "TxU", "UxV", "VxW", "WxX", "XxY", "YxZ", "ZxA"));
        il.verifySolution2(l4.toArray(new String[0]));
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        il.verifySolution2(l4.toArray(new String[0]));
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        il.verifySolution2(l4.toArray(new String[0]));
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        il.verifySolution2(l4.toArray(new String[0]));
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        il.verifySolution2(l4.toArray(new String[0]));
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        l4.set(0, "XXX");
        l4.set(5, "YYY");
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // false
        il.verifySolution2(l4.toArray(new String[0]));

        List<String> l6 = new ArrayList<>(Arrays.asList("AB", "BC", "CD", "DA"));
        System.out.println(il.isInfinite(l6.toArray(new String[0])));
        il.verifySolution2(l6.toArray(new String[0]));
        Collections.shuffle(l6);
        System.out.println(il.isInfinite(l6.toArray(new String[0])));
        il.verifySolution2(l6.toArray(new String[0]));

    }
}

/*
        [ALICE, CHARLES, ERIC, SOPHIA] : true
        [SOPHIA, CHARLES, ERIC, ALICE] : true
        [XXX, ALICE, ERIC, CHARLES] : false
        [AxB, BxC, CxD, DxE, ExF, FxG, GxA] : true
        [DxE, FxG, GxA, AxB, BxC, CxD, ExF] : true
        [BxC, DxE, GxA, ExF, CxD, FxG, AxB] : true
        [AB, BA, A, AC, CA, A, A] : true
        [BA, CA, A, A, A, AC, AB] : true
        [AxB, BxC, CxD, DxE, ExF, FxG, GxH, HxI, IxJ, JxK, KxL, LxM, MxN, NxO, OxP, PxQ, QxR, RxS, SxT, TxU, UxV, VxW, WxX, XxY, YxZ, ZxA] : true
*/
