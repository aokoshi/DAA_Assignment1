package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QuickSelectTest {

    private final Metrics metrics = new Metrics();
    private final Random random = new Random();

    @Test
    void returnsTheSameElementAsSortedArray() {
        for (int test = 0; test < 100; test++) {
            int[] a = new int[random.nextInt(200) + 1];
            for (int i = 0; i < a.length; i++) {
                a[i] = random.nextInt(1000);
            }
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int k = random.nextInt(a.length);

            assertEquals(sorted[k], QuickSelect.select(a, k, metrics));
        }
    }

    @Test
    void worksOnOneElement() {
        assertEquals(42, QuickSelect.select(new int[]{42}, 0, metrics));
    }

    @Test
    void worksOnEqualElements() {
        int[] a = {7, 7, 7, 7, 7};
        assertEquals(7, QuickSelect.select(a, 3, metrics));
    }

    @Test
    void throwsOnEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{}, 0, metrics));
    }

    @Test
    void throwsWhenKIsOutOfRange() {
        int[] a = {1, 2, 3};
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, -1, metrics));
        assertThrows(IllegalArgumentException.class, () -> QuickSelect.select(a, 3, metrics));
    }
}
