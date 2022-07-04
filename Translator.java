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
        if (word == null || word.equals("")) return null;
        String root = roots.get(word);
        if (root == null) return null;
        if (!word.equals(root)) roots.put(word, find(root));
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
        tl.add("CN", "你好", "KR", "안녕하세요");
        tl.add("KR", "안녕하세요", "JP", "こんにちは");
        tl.add("GR", "Hallo", "JP", "こんにちは");
        tl.add("GR", "Hallo", "EN", "Bonjour");

        String[] langs = new String[] {"EN", "FR", "ES", "CN", "KR", "JP", "GR"};
        String[] words = new String[] {"Hello", "Bonjour", "Hola", "你好", "안녕하세요", "こんにちは", "Hallo"};

        System.out.println("Translate Hola into Different Languages:");
        String wordToTranslate = "Hola";
        for (String q : langs) System.out.printf("%s -> %s: %s\n", wordToTranslate, q, tl.get("ES", wordToTranslate, q));
        System.out.println();
        System.out.printf("dict  after Round1 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round1 queries: %s\n", tl.getRoots());
        System.out.println();
        System.out.println("Translate different Language words into French:");
        String toLang = "FR";
        for (String s : words) System.out.printf("%s: %s <- %s\n", toLang, tl.get("ES", s, toLang), s);
        System.out.println();
        System.out.printf("dict  after Round2 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round2 queries: %s\n", tl.getRoots());
    }
}


/*
All prints:
Translate Hola into Different Languages:
Hola -> EN: Hello
Hola -> FR: Bonjour
Hola -> ES: Hola
Hola -> CN: 你好
Hola -> KR: 안녕하세요
Hola -> JP: こんにちは
Hola -> GR: Hallo

dict  after Round1 queries: {Hallo={JP=こんにちは, KR=안녕하세요, EN=Hello, GR=Hallo, CN=你好, FR=Bonjour, ES=Hola}}
roots after Round1 queries: {Hallo=Hallo, こんにちは=你好, Hello=Hallo, 你好=Hallo, 안녕하세요=你好, Bonjour=Hallo, Hola=Hallo}

Translate different Language words into French:
FR: Bonjour <- Hello
FR: Bonjour <- Bonjour
FR: Bonjour <- Hola
FR: Bonjour <- 你好
FR: Bonjour <- 안녕하세요
FR: Bonjour <- こんにちは
FR: Bonjour <- Hallo

dict  after Round2 queries: {Hallo={JP=こんにちは, KR=안녕하세요, EN=Hello, GR=Hallo, CN=你好, FR=Bonjour, ES=Hola}}
roots after Round2 queries: {Hallo=Hallo, こんにちは=Hallo, Hello=Hallo, 你好=Hallo, 안녕하세요=Hallo, Bonjour=Hallo, Hola=Hallo}

 */