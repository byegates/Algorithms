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
    public boolean isInfinite(String[] names) {
        System.out.printf("%s : ", Arrays.toString(names));
        if (names == null || names.length == 0) return false;
        return dfs(0, names);
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

    public static void main(String[] args) {
        InfiniteLoopAroundTheDinnerTable il = new InfiniteLoopAroundTheDinnerTable();
        List<String> l1 = new ArrayList<>(Arrays.asList("ALICE", "CHARLES", "ERIC", "SOPHIA"));
        List<String> l2 = new ArrayList<>(Arrays.asList("ALICE", "XRIC", "CHARLES", "SOPHIA"));
//        System.out.println(il.isInfinite(l1));
//        System.out.println(il.isInfinite(l2));
        System.out.println(il.isInfinite(new String[]{"A"})); // true
        System.out.println(il.isInfinite(new String[0])); // false
        System.out.println(il.isInfinite(null)); // false
        System.out.println(il.isInfinite(new String[]{"A", "B"})); // false
        System.out.println(il.isInfinite(l1.toArray(new String[0]))); // true
        System.out.println(il.isInfinite(l2.toArray(new String[0]))); // false
        Collections.shuffle(l1);
        System.out.println(il.isInfinite(l1.toArray(new String[0]))); // true
        l1.set(0, "XXX");
        System.out.println(il.isInfinite(l1.toArray(new String[0]))); // false

        List<String> l3 = new ArrayList<>(Arrays.asList("AxB", "BxC", "CxD", "DxE", "ExF", "FxG", "GxA"));
        System.out.println(il.isInfinite(l3.toArray(new String[0]))); // true
        Collections.shuffle(l3);
        System.out.println(il.isInfinite(l3.toArray(new String[0]))); // true
        Collections.shuffle(l3);
        System.out.println(il.isInfinite(l3.toArray(new String[0]))); // true

        List<String> l5 = new ArrayList<>(Arrays.asList("AB", "BA", "A", "AC", "CA", "A", "A"));
        System.out.println(il.isInfinite(l5.toArray(new String[0])));
        Collections.shuffle(l5);
        System.out.println(il.isInfinite(l5.toArray(new String[0])));

        List<String> l4 = new ArrayList<>(Arrays.asList("AxB", "BxC", "CxD", "DxE", "ExF", "FxG", "GxH", "HxI", "IxJ", "JxK", "KxL", "LxM", "MxN", "NxO", "OxP", "PxQ", "QxR", "RxS", "SxT", "TxU", "UxV", "VxW", "WxX", "XxY", "YxZ", "ZxA"));
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        Collections.shuffle(l4);
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // true
        l4.set(0, "XXX");
        l4.set(5, "YYY");
        System.out.println(il.isInfinite(l4.toArray(new String[0]))); // false
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
