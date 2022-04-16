/*
        Given a dictionary containing many words, find the largest product of two words’ lengths,
        such that the two words do not share any common characters.

        Assumptions
        The words only contains characters of 'a' to 'z'
        The dictionary is not null and does not contain null string, and has at least two strings
        If there is no such pair of words, just return 0

        Examples
        dictionary = [“abcde”, “abcd”, “ade”, “xy”], the largest product is 5 * 2 = 10 (by choosing “abcde” and “xy”)
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LargestProductOfLength {
    public int largestProduct(String[] dict) {
        Map<String, Integer> map = getBitMasks(dict);

//        Arrays.sort(dict, new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                return Integer.compare(o2.length(), o1.length());
//            }
//        });
        Arrays.sort(dict, (o1, o2) -> Integer.compare(o2.length(), o1.length()));

        int res = 0;
        for (int i = 1; i < dict.length; i++)
            for (int j = 0; j < i; j++) {
                int prod = dict[i].length() * dict[j].length();
                if (prod <= res) break;

                if ((map.get(dict[i]) & map.get(dict[j])) == 0)
                    res = prod;
            }

//        below will work on LaiCode not LeetCode, see test case 2 at line 66
//        int res = 0, count = 0;
//        for (int i = 1; i < dict.length; i++)
//            for (int j = 0; j < i; j++)
//                if ((map.get(dict[i]) & map.get(dict[j])) == 0) {
//                    count++;
//                    res = Math.max(res, dict[i].length() * dict[j].length());
//                } else if (count > 1) break;

        return res;
    }

    private Map<String, Integer> getBitMasks(String[] dict) {
        Map<String, Integer> map = new HashMap<>();
        for (String str : dict) {
            int bitMask = 0;
            for (int i = 0; i < str.length(); i++)
                bitMask |= 1 << (str.charAt(i) - 'a');
            map.put(str, bitMask);
        }
        return map;
    }

    public static void main(String[] args) {
        LargestProductOfLength lpl = new LargestProductOfLength();
        System.out.println(lpl.largestProduct(new String[]{"abcdefhi","ix","hj","x"})); // 8
        System.out.println(lpl.largestProduct(new String[]{"ccd","accaceddeeeaefc","bcaffa","bbcfafbb","accacfebbabbeedfbfdb","beddecbffcdaededdaefdedfdea","cf","ddafdcbd","bbafacebacaefdaffccebddff","ebccffcddbeddccacceccaec","becfbfdccdfdeadfbfaddbcded","cbabeaaeabefedbaeaedc","dfadbbdbead","cafaefdcd","eccdbfceafeeeacfcddc","dbabbcdbb","abfbfbffcbebde","cfaadaa","fc","faebcabb","adbacebabcaaccbdeaffff","aeaefccf","dbacbeeabdbcdfccabebaecfef","ecdadeefcaddffaececffa","defcabf","abbcecbccbdaebaecaefabed","dfeeebcbaaefc","aecccbcbbdddb","dcfabacec","fccfbacbacddeaaea","dfdbfacbacbecb","cbfeebdbfecb","cffaacacbde","aafd","bdcebbbebd","afeffadcfcdacfba","dafeefbcdfaffcfacee","dcbbebfbedafedcdbab","cafaf","bcbcccfdebdd","efaaaacccff","cffbead","ebcfccfcddffdec","fffdfdcec","beeafefbdfa","cdfdbccfbaaeffcabab","ddadcbabbcb","decfaeabbecebaebeaddedae","cdcbfffbebae","aeccefcbcbbddfdc","ffefedaf","cddbabccafaffeafeedcbedbdad","eddeeccfedcefadfdfebfacb","aca","ffdcafaddcddf","ef","bbbbffe","ffccfebabaadcffacbbb","cbdeddfddffacbeeeebafebabda","ddeecb","cffdc","edcffcebadf","becbcadcafddcfbbeeddbfffcab","abcbaceeaeaddd","cfeffceebfaeefadaaccfa","eaccddb","caeafbfafecd","becaafdbaadbfecfdfde","ecabaaeafbfbcbadaac","bdcdffcfaeebeedfdfddfaf","dbbfbaeecbfcdebad","cceecddeeecdbde","beec","adbcfdbfdbccdcffffbcffbec","bbbbfe","cdaedaeaad","dadbfeafadd","fcacaaebcedfbfbcddfc","ceecfedceac","dada","ccfdaeffbcfcc","eadddbbbdfa","beb","fcaaedadabbbeacabefdabe","dfcddeeffbeec","defbdbeffebfceaedffbfee","cffadadfbaebfdbadebc","fbbadfccbeffbdeabecc","bdabbffeefeccb","bdeeddc","afcbacdeefbcecff","cfeaebbbadacbced","edfddfedbcfecfedb","faed","cbcdccfcbdebabc","efb","dbddadfcddbd","fbaefdfebeeacbdfbdcdddcbefc","cbbfaccdbffde","adbcabaffebdffad"})); // 39
    }
}
