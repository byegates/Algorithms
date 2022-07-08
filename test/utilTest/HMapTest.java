package utilTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HMapTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void size() {
        Map<Integer, Integer> map = new HMap<>();
        assertEquals(map.size(), 0);
        int n = 9;
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.put(i, i);
        assertEquals(map.size(), n);
    }

    @Test
    void isEmpty() {
        Map<Integer, Integer> map = new HMap<>();
        int n = 9;
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.put(i, i);
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.remove(i);
        assertTrue(map.isEmpty());
    }

    @Test
    void containsKey() {
    }

    @Test
    void containsValue() {
    }

    @Test
    void putAndGet3withUpdate() {
        Map<Integer, Integer> map = new HMap<>();
        int n = 9;
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.put(i, i);
        List<Integer> res = new ArrayList<>();
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) res.add(map.get(i));
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.put(i, i / 10);
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) res.add(map.get(i));
        assertEquals(res, Arrays.asList(-2147483648, -1647483648, -1147483648, -647483648, -147483648, 352516352, 852516352, 1352516352, 1852516352, -214748364, -164748364, -114748364, -64748364, -14748364, 35251635, 85251635, 135251635, 185251635));
    }

    @Test
    void putAndGet1null() { // test null as key, negative value as key (for get), and some other random values
        Map<Integer, Integer> map = new HMap<>();
        map.put(null, -88);
        for (int i = -2; i <= 2; i++) map.put(i, i);
        List<Integer> res = new ArrayList<>();
        for (Integer x : Arrays.asList(3, -88, -1, 2, 1, -2, null, 2, 9))
            res.add(map.get(x));
        assertEquals(res, Arrays.asList(null, null, -1, 2, 1, -2, -88, 2, null));
    }

    @Test
    void putAndGet2() {// put positive and negative values
        Map<Integer, Integer> map = new HMap<>();
        for (int i = -2; i <= 2; i++) map.put(i, i);
        List<Integer> res = new ArrayList<>();
        for (int i = -2; i <= 2; i++) res.add(map.get(i));
        assertEquals(res, Arrays.asList(-2, -1, 0, 1, 2));
    }

    @Test
    void remove() {
        Map<Integer, Integer> map = new HMap<>();
        int n = 9;
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.put(i, i);
        List<Integer> res = new ArrayList<>();
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) res.add(map.get(i));
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) map.remove(i);
        for (int i = Integer.MIN_VALUE, count = 0; count < n; i += 500_000_000, count++) res.add(map.get(i));
        assertEquals(res, Arrays.asList(-2147483648, -1647483648, -1147483648, -647483648, -147483648, 352516352, 852516352, 1352516352, 1852516352, null, null, null, null, null, null, null, null, null));
    }

    @Test
    void putAll() {
    }

    @Test
    void clear() {
    }

    @Test
    void keySet() {
    }

    @Test
    void values() {
    }

    @Test
    void entrySet() {
    }
}