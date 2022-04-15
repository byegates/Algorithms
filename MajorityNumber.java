import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MajorityNumber {
    public int majority(int[] arr) { // TC: O(n), SC: O(1)
        int cur = 0, count = 0;
        for (int v : arr) {
            if (count == 0) {
                cur = v;
                count++;
            } else if (cur == v) count++;
            else count--;
        }
        return cur;
    }

    public List<Integer> majority3(int[] arr) {
        int e1 = 0, e2 = 0, cnt1 = 0, cnt2 = 0;
        for (int e : arr)
            if (e1 == e) cnt1++;
            else if (e2 == e) cnt2++;
            else if (cnt1 == 0) {
                e1 = e;
                cnt1 = 1;
            } else if (cnt2 == 0) {
                e2 = e;
                cnt2 = 1;
            } else {
                cnt1--;
                cnt2--;
            }

        List<Integer> res = new ArrayList<>();
        cnt1 = 0;
        cnt2 = 0;
        for (int e : arr) {
            if (e == e1) cnt1++;
            if (e == e2) cnt2++;
        }
        if (cnt1 > arr.length / 3) res.add(e1);
        if (e2 != e1 && cnt2 > arr.length / 3) res.add(e2);

        return res;
    }

    public List<Integer> majorityK(int[] arr, int k) { // TC: O(n), SC: O(k)
        Map<Integer, Integer> map = new HashMap<>();
        for (int e : arr)
            if (map.containsKey(e)) map.put(e, map.get(e) + 1);
            else if (map.size() < k - 1) map.put(e, 1);
            else {
                // to avoid ConcurrentModificationException, need a new arrayList
                List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
                for (Map.Entry<Integer, Integer> set : list)
                    if (set.getValue() == 1) map.remove(set.getKey());
                    else map.put(set.getKey(), set.getValue() - 1);
            }

        List<Integer> res = new ArrayList<>();
        Map<Integer, Integer> m2 = new HashMap<>();
        for (int e : arr)
            if (map.containsKey(e))
                m2.put(e, m2.getOrDefault(e, 0) + 1);

        for (Map.Entry<Integer, Integer> set : m2.entrySet())
            if (set.getValue() > arr.length / k)
                res.add(set.getKey());

        return res;
    }

    public static void main(String[] args) {
        MajorityNumber mn = new MajorityNumber();
        System.out.println(mn.majority(new int[]{1,2,1,2,2}));
        System.out.println(mn.majority3(new int[]{2,1,1,3,1,4,5,6}));
        System.out.println(mn.majority3(new int[]{2,1,1,3,1,5,5,5}));
        System.out.println(mn.majority3(new int[]{0,0,0}));
        System.out.println(mn.majorityK(new int[]{1,4,3,5,2,2,1,6}, 4));
    }
}
