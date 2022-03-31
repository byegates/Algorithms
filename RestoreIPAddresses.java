/*
        Given a string containing only digits, restore it by retiring all possible valid IP address combinations.

        Input:  ”25525511135”
        Output: [“255.255.11.135”, “255.255.111.35”]
*/

import java.util.ArrayList;
import java.util.List;

public class RestoreIPAddresses {
    public List<String> Restore(String ip) { // TC: O(3^4)?, SC: O(4)
        List<String> res = new ArrayList<>();
        char[] cur = new char[ip.length() + 4];
        dfs(0, 0, cur, ip.toCharArray(), res);
        return res;
    }

    private void dfs(int lvl, int offset, char[] cur, char[] ip, List<String> res) {
        if (lvl == 4) {
            if (lvl + offset == ip.length + 4) {
                res.add(new String(cur, 0, cur.length - 1));
            }
            return;
        }

        if (offset < ip.length) {
            cur[lvl + offset] = ip[offset];
            cur[lvl + offset + 1] = '.';
            dfs(lvl + 1, offset + 1, cur, ip, res);
        }

        if (offset + 1 < ip.length && ip[offset] != '0') {
            cur[lvl + offset] = ip[offset];
            cur[lvl + offset + 1] = ip[offset + 1];
            cur[lvl + offset + 2] = '.';
            dfs(lvl + 1, offset + 2, cur, ip, res);
        }

        if (offset + 2 < ip.length) {
            char a = ip[offset], b = ip[offset+1], c = ip[offset+2];
            if (a == '1' || a == '2' && (b < '5' || b == '5' && c < '6')) {
                cur[lvl + offset] = a;
                cur[lvl + offset + 1] = b;
                cur[lvl + offset + 2] = c;
                cur[lvl + offset + 3] = '.';
                dfs(lvl + 1, offset + 3, cur, ip, res);
            }
        }

    }

    public static void main(String[] args) {
        RestoreIPAddresses ria = new RestoreIPAddresses();
        System.out.println(ria.Restore("0000")); // [0.0.0.0]
        System.out.println(ria.Restore("25525511135")); // [255.255.11.135, 255.255.111.35]
        System.out.println(ria.Restore("012345")); // [0.1.23.45, 0.1.234.5, 0.12.3.45, 0.12.34.5, 0.123.4.5]
        System.out.println(ria.Restore("123456789")); // [123.45.67.89]
        System.out.println(ria.Restore("12345678")); // [1.234.56.78, 12.34.56.78, 123.4.56.78, 123.45.6.78, 123.45.67.8]
        System.out.println(ria.Restore("1234567")); // [1.23.45.67, 1.234.5.67, 1.234.56.7, 12.3.45.67, 12.34.5.67, 12.34.56.7, 123.4.5.67, 123.4.56.7, 123.45.6.7]
        System.out.println(ria.Restore("255255111354")); // [] Error: too long
    }
}
