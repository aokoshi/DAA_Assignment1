package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {

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

            MergeSort.sort(a, metrics);

            assertArrayEquals(expected, a);
        }
    }

    @Test
    void sortsEmptyArray() {
        int[] a = {};
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{}, a);
    }

    @Test
    void sortsOneElement() {
        int[] a = {42};
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{42}, a);
    }

    @Test
    void sortsArrayOfEqualElements() {
        int[] a = {7, 7, 7, 7, 7, 7};
        MergeSort.sort(a, metrics);
        assertArrayEquals(new int[]{7, 7, 7, 7, 7, 7}, a);
    }

    @Test
    void sortsAlreadySortedArray() {
        int[] a = new int[1000];
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        int[] expected = a.clone();

        MergeSort.sort(a, metrics);

        assertArrayEquals(expected, a);
    }
}
