import java.util.*;

public class WordBreakCount {
    Map<String,List<String>> map = new HashMap<>();

    public List<String> cut(String s, List<String> dict) {
        Set<String> set = new HashSet<>(dict);
        return cut(s, set);
    }

    public int count(String s, List<String> dict) {
        if (s == null || s.length() == 0) return 0;
        Set<String> set = new HashSet<>(dict);
        int[] M = new int[s.length() + 1];
        M[0] = 1;
        for (int i = 1; i < M.length; i++) {
            for (int j = 0; j < i; j++) {
                if (M[j] > 0 && set.contains(s.substring(j, i))) {
                    M[i] += M[j];
                }
            }
        }
        return M[s.length()];
    }

    public int count2(String s, List<String> dict) {
        Set<String> set = new HashSet<>(dict);
        return cut(s, set).size();
    }

    public List<String> cut(String s, Set<String> set) {
        List<String> res = new ArrayList<>();
        if(s == null || s.length() == 0)
            return res;
        if(map.containsKey(s))
            return map.get(s);
        if(set.contains(s))
            res.add(s);

        for(int i = 1 ; i < s.length() ; i++) {
            String t = s.substring(i);
            if(set.contains(t)) {
                List<String> temp = cut(s.substring(0 , i) , set);
                if(temp.size() != 0)
                    for (String value : temp)
                        res.add(value + ", " + t);
            }
        }
        map.put(s , res);
        return res;
    }

    public static void main(String[] args) {
        WordBreakCount wbc = new WordBreakCount();
        System.out.println(wbc.count("catsanddog", Arrays.asList("cat","cats","and","sand","dog")));
        System.out.println(wbc.count("catsanddogcatsanddog", Arrays.asList("cat","cats","and","sand","dog")));
        String s3 = "catsanddogcatsanddogcatsanddogcatsanddog"; // catsanddog * 4
        List<String> dict = Arrays.asList("cat","cats","and","sand","dog");
        int count3 = wbc.count(s3, dict);
        System.out.println(count3);
        List<String> res3 = wbc.cut(s3, dict);
        System.out.println(res3.size() == count3);
        for (String s : res3)
            System.out.println(s);
        String s4 = "robsandrobsand";
        List<String> d2 = Arrays.asList("rob", "sand", "robs", "and", "robsand");
        List<String> res4 = wbc.cut(s4, d2);
        System.out.println(wbc.count(s4, d2));
        for (String s : res4)
            System.out.println(s);
        System.out.println();
        System.out.println(wbc.count("", d2));

    }
}
