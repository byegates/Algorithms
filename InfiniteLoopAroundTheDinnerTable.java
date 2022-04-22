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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InfiniteLoopAroundTheDinnerTable {
    public boolean isInfiniteLoop(List<String> list) {
        System.out.printf("%s : ", list);
        return dfs(0, list);
    }

    private boolean dfs(int idx, List<String> list) {
        if (idx == list.size() - 1)
            return false;

        if (isInfinite(list)) return true;

        for (int i = idx; i < list.size(); i++) {
            if (i != idx) swap(list, i, idx);
            if (dfs(idx + 1, list)) return true;
            if (i != idx) swap(list, i, idx);
        }

        return false;
    }

    private void swap(List<String> list, int i, int j) {
        String tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    public boolean isInfinite(List<String> list) {
        int n = list.size();
        String cur = list.get(n - 1);
        for (int i = 0; i < n; i++) {
            String next = list.get(i);
            if (cur.charAt(cur.length() - 1) != next.charAt(0))
                return false;
            cur = next;
        }
        return true;
    }

    public static void main(String[] args) {
        InfiniteLoopAroundTheDinnerTable il = new InfiniteLoopAroundTheDinnerTable();
        List<String> l1 = new ArrayList<>(Arrays.asList("ALICE", "CHARLES", "ERIC", "SOPHIA"));
        List<String> l2 = new ArrayList<>(Arrays.asList("ALICE", "ERIC", "CHARLES", "SOPHIA"));
//        System.out.println(il.isInfinite(l1));
//        System.out.println(il.isInfinite(l2));
        System.out.println(il.isInfiniteLoop(l1)); // true
        Collections.shuffle(l1);
        System.out.println(il.isInfiniteLoop(l1)); // true
        l1.set(0, "XXX");
        System.out.println(il.isInfiniteLoop(l1)); // false

        List<String> l3 = new ArrayList<>(Arrays.asList("AxB", "BxC", "CxD", "DxE", "ExF", "FxG", "GxA"));
        System.out.println(il.isInfiniteLoop(l3)); // true
        Collections.shuffle(l3);
        System.out.println(il.isInfiniteLoop(l3)); // true
        Collections.shuffle(l3);
        System.out.println(il.isInfiniteLoop(l3)); // true

        List<String> l5 = new ArrayList<>(Arrays.asList("AB", "BA", "A", "AC", "CA", "A", "A"));
        System.out.println(il.isInfiniteLoop(l5));
        Collections.shuffle(l5);
        System.out.println(il.isInfiniteLoop(l5));

        List<String> l4 = new ArrayList<>(Arrays.asList("AxB", "BxC", "CxD", "DxE", "ExF", "FxG", "GxH", "HxI", "IxJ", "JxK", "KxL", "LxM", "MxN", "NxO", "OxP", "PxQ", "QxR", "RxS", "SxT", "TxU", "UxV", "VxW", "WxX", "XxY", "YxZ", "ZxA"));
        System.out.println(il.isInfiniteLoop(l4)); // true
        Collections.shuffle(l4);
        System.out.println(il.isInfiniteLoop(l4)); // too slow, can't solve

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
