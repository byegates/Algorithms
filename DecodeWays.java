/*
        A message containing letters from A-Z is being encoded to numbers using the following ways:

        ‘A’ = 1
        ‘B’ = 2
        …
        ‘Z’ = 26

        Given an encoded message containing digits, determine the total number of ways to decode it.
        Input:    “212”
        It can be either decoded as 2,1,2("BAB") or 2,12("BL") or 21,2("UB"), return 3.
*/

import java.util.ArrayList;
import java.util.List;

public class DecodeWays {
    // calc num of ways to decode
    public int numDecodeWay(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = s.length();
        int[] M = new int[n + 1];
        M[0] = 1;
        M[1] = s.charAt(0) == '0' ? 0 : 1;

        for (int i = 2; i <= n; i++) {
            char c = s.charAt(i-1);
            if (c > '0' && c <= '9') M[i] += M[i-1];
            int num2 = Integer.valueOf(s.substring(i-2, i));
            if (num2 > 9 && num2 < 27) M[i] += M[i-2];
        }

        return M[n];
    }
    // calc num of ways to decode ends here

    // record all ways to decode
    public List<String> Decode(String s) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int toCapitalLetter = 64;
        dfs(0, s.toCharArray(), sb, res, toCapitalLetter);
        return res;
    }

    private void dfs(int idx, char[] a, StringBuilder sb, List<String> res, int toCapitalLetter) {
        if (idx == a.length) {
            res.add(sb.toString());
            return;
        }

        if (a[idx] > '0' && a[idx] <= '9') {
            sb.append((char) (a[idx] - '0' + toCapitalLetter));
            dfs(idx + 1, a, sb, res, toCapitalLetter);
            sb.setLength(sb.length() - 1);
        }

        if (idx < a.length - 1) {
            int num = Integer.parseInt(String.format("%s%s", a[idx], a[idx + 1]));
            if (num > 9 && num < 27) {
                sb.append((char) (num + toCapitalLetter));
                dfs(idx + 2, a, sb, res, toCapitalLetter);
                sb.setLength(sb.length() - 1);
            }
        }
    }
    // record all ways to decode ends here

    public static void main(String[] args) {
        DecodeWays dw = new DecodeWays();
        String s0 = "0";
        String s1 = "624212611";
        String s2 = "1398152164";
        String s3 = "968822189183411";
        String s4 = "2776717328126106";
        String s9 = "624212641113981521649688221891834112776717328126106";

        System.out.println(dw.numDecodeWay(s9)); // 54000
        System.out.println(dw.numDecodeWay(s0)); // 0

        System.out.println(dw.Decode(s1)); // [FBDBABFAA, FBDBABFK, FBDBAZAA, FBDBAZK, FBDBLFAA, FBDBLFK, FBDUBFAA, FBDUBFK, FBDUZAA, FBDUZK, FXBABFAA, FXBABFK, FXBAZAA, FXBAZK, FXBLFAA, FXBLFK, FXUBFAA, FXUBFK, FXUZAA, FXUZK]
        System.out.println(dw.Decode(s0)); // []
        System.out.println(dw.Decode("5")); // [E]
        System.out.println(dw.Decode("226")); // [BBF, BZ, VF]
        System.out.println(dw.Decode("236")); // [BCF, WF]

        // simple tests
        System.out.println(dw.Decode(s0).size() == dw.numDecodeWay(s0));
        System.out.println(dw.Decode(s1).size() == dw.numDecodeWay(s1));
        System.out.println(dw.Decode(s2).size() == dw.numDecodeWay(s2));
        System.out.println(dw.Decode(s3).size() == dw.numDecodeWay(s3));
        System.out.println(dw.Decode(s4).size() == dw.numDecodeWay(s4));
        System.out.println(dw.Decode(s9).size() == dw.numDecodeWay(s9));
    }
}
