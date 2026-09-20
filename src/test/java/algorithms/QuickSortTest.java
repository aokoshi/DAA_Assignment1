package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuickSortTest {

    private final Metrics metrics = new Metrics();
    private final Random random = new Random();

    @Test
    void sortsRandomArraysLikeArraysSort() {
        for (int test = 0; test < 100; test++) {
            int[] a = new int[random.nextInt(200) + 1];
            for (int i = 0; i < a.length; i++) {
                a[i] = random.nextInt(1000);
            }
            int[] expected = a.clone();
            Arrays.sort(expected);

            QuickSort.sort(a, metrics);

            assertArrayEquals(expected, a);
        }
    }

    @Test
    void sortsEmptyArray() {
        int[] a = {};
        QuickSort.sort(a, metrics);
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void sortsOneElement() {
        int[] a = {42};
        QuickSort.sort(a, metrics);
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void sortsArrayOfEqualElements() {
        int[] a = new int[10_000];
        Arrays.fill(a, 5);
        int[] expected = a.clone();

        QuickSort.sort(a, metrics);

        assertArrayEquals(expected, a);
    }

    @Test
    void depthOnSortedArrayIsBounded() {
        int n = 100_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i;
        }
        Metrics m = new Metrics();

        QuickSort.sort(a, m);

        int limit = (int) (2 * (Math.log(n) / Math.log(2)));
        assertTrue(m.getMaxDepth() <= limit,
                "maxDepth = " + m.getMaxDepth() + ", limit = " + limit);
    }
}
