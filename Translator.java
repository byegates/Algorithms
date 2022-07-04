import java.util.HashMap;
import java.util.Map;

public class Translator {
    private final Map<String, String> roots = new HashMap<>();
    private final Map<String, Map<String, String>> dict = new HashMap<>();
    public void add(String lang1, String word1, String lang2, String word2) { // 1 is input, 2 is output
        init(lang1, word1); init(lang2, word2); // initialize each word for union find
        String root1 = find(word1), root2 = find(word2);
        if (!root1.equals(root2)) {
            roots.put(root2, root1);
            dict.get(root1).putAll(dict.get(root2));
            dict.remove(root2);
        }
    }

    private void init(String lang, String word) {
        String s = roots.get(word);
        if (s == null) {
            roots.put(word, word);
            Map<String, String> entries = new HashMap<>();
            entries.put(lang, word);
            dict.put(word, entries);
        }
    }

    private String find(String word) {
        String root = roots.get(word);
        if (!root.equals(word)) roots.put(word, find(root));
        return roots.get(word);
    }

    public String get(String lang, String word, String lang2) {
        String root = find(word);
        if (root == null) return "";
        return dict.get(root).get(lang2);
    }

    public Map<String, String> getRoots() {
        return roots;
    }

    public Map<String, Map<String, String>> getDict() {
        return dict;
    }

    public static void main(String[] args) {
        Translator tl = new Translator();
        tl.add("EN", "Hello", "ES", "Hola");
        tl.add("FR", "Bonjour", "ES", "Hola");
        tl.add("CN", "你好", "KR", "안녕하십니까");
        tl.add("KR", "안녕하십니까", "JP", "こんにちは");
        tl.add("GR", "Hallo", "JP", "こんにちは");
        tl.add("GR", "Hallo", "EN", "Bonjour");

        String[] langs = new String[] {"EN", "FR", "ES", "CN", "KR", "JP", "GR"};
        String[] words = new String[] {"Hello", "Bonjour", "Hola", "你好", "안녕하십니까", "こんにちは", "Hallo"};

        System.out.println("Translate Hola into Different Languages:");
        for (String q : langs) System.out.printf("%s: %s\n", q, tl.get("ES", "Hola", q));
        System.out.println();
        System.out.printf("dict  after Round1 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round1 queries: %s\n", tl.getRoots());
        System.out.println();
        System.out.println("Translate different Language words into French:");
        for (String s : words) System.out.printf("%s(FR) <-- %s\n", tl.get("ES", s, "FR"), s);
        System.out.println();
        System.out.printf("dict  after Round2 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round2 queries: %s\n", tl.getRoots());
    }
}


/*
All prints:
Translate Hola into Different Languages:
EN: Hello
FR: Bonjour
ES: Hola
CN: 你好
KR: 안녕하십니까
JP: こんにちは
GR: Hallo

dict  after Round1 queries: {Hallo={JP=こんにちは, KR=안녕하십니까, EN=Hello, GR=Hallo, CN=你好, FR=Bonjour, ES=Hola}}
roots after Round1 queries: {Hallo=Hallo, こんにちは=你好, Hello=Hallo, 你好=Hallo, Bonjour=Hallo, 안녕하십니까=你好, Hola=Hallo}

Translate different Language words into French:
Bonjour(FR) <-- Hello
Bonjour(FR) <-- Bonjour
Bonjour(FR) <-- Hola
Bonjour(FR) <-- 你好
Bonjour(FR) <-- 안녕하십니까
Bonjour(FR) <-- こんにちは
Bonjour(FR) <-- Hallo

dict  after Round2 queries: {Hallo={JP=こんにちは, KR=안녕하십니까, EN=Hello, GR=Hallo, CN=你好, FR=Bonjour, ES=Hola}}
roots after Round2 queries: {Hallo=Hallo, こんにちは=Hallo, Hello=Hallo, 你好=Hallo, Bonjour=Hallo, 안녕하십니까=Hallo, Hola=Hallo}

 */