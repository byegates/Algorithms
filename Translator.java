import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Translator {
    record Word (String l, String w) {
        @Override
        public String toString() {
            return String.format("%s-%s", w, l);
        }
    } // l for language, w for word
    private final Map<Word, Word> roots = new HashMap<>();
    private final Map<Word, Map<String, String>> dict = new HashMap<>();
    public void add(String l1, String w1, String l2, String w2) { // 1 is input, 2 is output, l for language, w for word
        if (l1 == null || l1.equals("") || w1 == null || w1.equals("") || l2 == null || l2.equals("") || w2 == null || w2.equals("")) return;
        add(new Word(l1, w1), new Word(l2, w2));
    }

    public void add(Word w1, Word w2) { // Use Word (record class) as input for easier testing
        if (w1 == null || w1.w.equals("") || w2 == null || w2.w.equals("") || w1.l.equals("") || w2.l.equals("")) return;
        init(w1); init(w2); // initialize each word for union find
        Word root1 = find(w1), root2 = find(w2);
        if (!root1.equals(root2)) {
            roots.put(root2, root1); // union two words, use root1 as new root, this could be optimized
            dict.get(root1).putAll(dict.get(root2)); // merge invert mapping dictionary
            dict.remove(root2); // root2 dictionary is no longer needed, as it's been merged into root1
        }
    }

    private void init(Word word) { // first time adding any word, root will be itself, add a single entry in dictionary
        Word s = roots.get(word);
        if (s == null) {
            roots.put(word, word);
            Map<String, String> entries = new HashMap<>();
            entries.put(word.l, word.w);
            dict.put(word, entries);
        }
    }

    private Word find(Word word) {
        Word root = roots.get(word);
        if (root == null) return null;
        if (!word.equals(root)) roots.put(word, find(root));
        return roots.get(word);
    }

    public String get(String lang, String word, String toLang) {
        return get(new Word(lang, word), toLang);
    }

    public String get(Word word, String toLang) {
        Word root = find(word);
        if (root == null) return "";
        return dict.get(root).get(toLang);
    }

    public Map<Word, Word> getRoots() {
        return roots;
    }

    public Map<Word, Map<String, String>> getDict() {
        return dict;
    }

    enum Ciao {
        EN(new Word("EN", "Hello")),
        FR(new Word("FR", "Bonjour")),
        IT(new Word("IT", "Ciao")),
        CN(new Word("CN", "你好")),
        KR(new Word("KR", "안녕하세요")),
        JP(new Word("JP", "こんにちは")),
        GR(new Word("GR", "Hallo")),
        NL(new Word("NL", "Hallo")),
        ES(new Word("ES", "Hola"));
        final Word w;
        Ciao(Word w) {
            this.w = w;
        }
    }

    public static void main(String[] args) {
        Translator tl = new Translator();
        tl.add(Ciao.EN.w, Ciao.ES.w);
        tl.add(Ciao.FR.w, Ciao.ES.w);
        tl.add(Ciao.EN.w, Ciao.IT.w);
        tl.add(Ciao.EN.w, Ciao.NL.w);

        tl.add(Ciao.CN.w, Ciao.KR.w);
        tl.add(Ciao.KR.w, Ciao.JP.w);
        tl.add(Ciao.GR.w, Ciao.JP.w);
        tl.add(Ciao.GR.w, Ciao.FR.w);

        System.out.println("Translate Hola into Different Languages:");
        String wordToTranslate = "Hola";
        for (Ciao w : Ciao.values()) System.out.printf("%s -> %s: %s\n", wordToTranslate, w.name(), tl.get(Ciao.ES.w, w.name()));
        System.out.println();
        System.out.printf("dict  after Round1 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round1 queries: %s\n", tl.getRoots());

        System.out.println();
        System.out.println("Translate different Language words into French:");
        for (Ciao c : Ciao.values()) System.out.printf("%s: <- %s(%s)\n", tl.get(c.w, "FR"), c.w.w, c.w.l);

        System.out.println();
        System.out.printf("dict  after Round2 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round2 queries: %s\n", tl.getRoots());

        System.out.println();
        System.out.println("Random Translation:");
        Random rand = new Random();
        Ciao[] words = Ciao.values();
        for (Ciao c : words) {
            String toLang = words[rand.nextInt(words.length)].w.l;
            System.out.printf("%s(%s): -> %s(%s)\n", c.w.w, c.w.l, tl.get(c.w, toLang), toLang);
        }

        System.out.println();
        System.out.printf("dict  after Round3 queries: %s\n", tl.getDict());
        System.out.printf("roots after Round3 queries: %s\n", tl.getRoots());
    }
}


/*
All prints:
Translate Hola into Different Languages:
Hola -> EN: Hello
Hola -> FR: Bonjour
Hola -> IT: Ciao
Hola -> CN: 你好
Hola -> KR: 안녕하세요
Hola -> JP: こんにちは
Hola -> GR: Hallo
Hola -> NL: Hallo
Hola -> ES: Hola

dict  after Round1 queries: {Hallo-GR={JP=こんにちは, KR=안녕하세요, EN=Hello, GR=Hallo, CN=你好, IT=Ciao, FR=Bonjour, ES=Hola, NL=Hallo}}
roots after Round1 queries: {Bonjour-FR=Hallo-GR, 안녕하세요-KR=你好-CN, Ciao-IT=Bonjour-FR, 你好-CN=Hallo-GR, こんにちは-JP=你好-CN, Hola-ES=Hallo-GR, Hallo-NL=Bonjour-FR, Hello-EN=Hallo-GR, Hallo-GR=Hallo-GR}

Translate different Language words into French:
Bonjour: <- Hello(EN)
Bonjour: <- Bonjour(FR)
Bonjour: <- Ciao(IT)
Bonjour: <- 你好(CN)
Bonjour: <- 안녕하세요(KR)
Bonjour: <- こんにちは(JP)
Bonjour: <- Hallo(GR)
Bonjour: <- Hallo(NL)
Bonjour: <- Hola(ES)

dict  after Round2 queries: {Hallo-GR={JP=こんにちは, KR=안녕하세요, EN=Hello, GR=Hallo, CN=你好, IT=Ciao, FR=Bonjour, ES=Hola, NL=Hallo}}
roots after Round2 queries: {Bonjour-FR=Hallo-GR, 안녕하세요-KR=Hallo-GR, Ciao-IT=Hallo-GR, 你好-CN=Hallo-GR, こんにちは-JP=Hallo-GR, Hola-ES=Hallo-GR, Hallo-NL=Hallo-GR, Hello-EN=Hallo-GR, Hallo-GR=Hallo-GR}

Random Translation:
Hello(EN): -> Hallo(GR)
Bonjour(FR): -> 你好(CN)
Ciao(IT): -> Bonjour(FR)
你好(CN): -> 你好(CN)
안녕하세요(KR): -> Hallo(NL)
こんにちは(JP): -> 안녕하세요(KR)
Hallo(GR): -> Ciao(IT)
Hallo(NL): -> Bonjour(FR)
Hola(ES): -> こんにちは(JP)

dict  after Round3 queries: {Hallo-GR={JP=こんにちは, KR=안녕하세요, EN=Hello, GR=Hallo, CN=你好, IT=Ciao, FR=Bonjour, ES=Hola, NL=Hallo}}
roots after Round3 queries: {Bonjour-FR=Hallo-GR, 안녕하세요-KR=Hallo-GR, Ciao-IT=Hallo-GR, 你好-CN=Hallo-GR, こんにちは-JP=Hallo-GR, Hola-ES=Hallo-GR, Hallo-NL=Hallo-GR, Hello-EN=Hallo-GR, Hallo-GR=Hallo-GR}

 */