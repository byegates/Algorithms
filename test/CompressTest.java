import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompressTest {

    String tc1 = "aaaaaaaaaccccmmittttttttttbbbbbbbbbccccccc";
    String tc2 = "aufdnqpmhkndmvaodknreakgpkpwxxilggi";
    String tc3 = "abcddddddddddddddddddddabc";
    String tc4 = "hhhhhhhhhhhhhhhhhhhhhxxxxxxxxxxxxxxaaaaaaaaaddddffffooooooooooooll";
    String tc5 = "abcddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddabc";
    String decomp1 = "a13b21c0d2e11f13";
    String decompRes1 = "aaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbbddeeeeeeeeeeefffffffffffff";
    String decomp2 = "a0";
    String decompRes2 = "";
    String decomp3 = "a5b0c0d0e0";
    String decompRes3 = "aaaaa";
    String decomp4 = "a15b0c0d0e15";
    String decompRes4 = "aaaaaaaaaaaaaaaeeeeeeeeeeeeeee";
    String decomp5 = "a2b2c2e2";
    String decompRes5 = "aabbccee";
    String decomp6 = "a0b21c2d3e4";
    String decompRes6 = "bbbbbbbbbbbbbbbbbbbbbccdddeeee";
    String decomp7 = "e4d3c2b21a0";
    String decompRes7 = "eeeedddccbbbbbbbbbbbbbbbbbbbbb";
    String decomp8 = "ap2lec3n";
    String decompRes8 = "applecccn";
    
    @Test
    void tc1() {assertEquals(tc1, Decompress.decompress(Compress.compress(tc1, true)));}
    @Test
    void tc2() {assertEquals(tc2, Decompress.decompress(Compress.compress(tc2, true)));}
    @Test
    void tc3() {assertEquals(tc3, Decompress.decompress(Compress.compress(tc3, true)));}
    @Test
    void tc4() {assertEquals(tc4, Decompress.decompress(Compress.compress(tc4, true)));}
    @Test
    void tc5() {assertEquals(tc5, Decompress.decompress(Compress.compress(tc5, true)));}
    @Test
    void decomp1() {assertEquals(decompRes1, Decompress.decompress(decomp1));}
    @Test
    void decomp2() {assertEquals(decompRes2, Decompress.decompress(decomp2));}
    @Test
    void decomp3() {assertEquals(decompRes3, Decompress.decompress(decomp3));}
    @Test
    void decomp4() {assertEquals(decompRes4, Decompress.decompress(decomp4));}
    @Test
    void decomp5() {assertEquals(decompRes5, Decompress.decompress(decomp5));}
    @Test
    void decomp6() {assertEquals(decompRes6, Decompress.decompress(decomp6));}
    @Test
    void decomp7() {assertEquals(decompRes7, Decompress.decompress(decomp7));}
    @Test
    void decomp8() {assertEquals(decompRes8, Decompress.decompress(decomp8, false));}
}
