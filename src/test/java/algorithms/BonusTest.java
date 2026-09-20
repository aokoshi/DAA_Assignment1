package algorithms;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BonusTest {

    private final Metrics metrics = new Metrics();
    private final Random random = new Random();

    @Test
    void deterministicSelectReturnsTheSameElementAsSortedArray() {
        for (int test = 0; test < 100; test++) {
            int[] a = new int[random.nextInt(200) + 1];
            for (int i = 0; i < a.length; i++) {
                a[i] = random.nextInt(1000);
            }
            int[] sorted = a.clone();
            Arrays.sort(sorted);
            int k = random.nextInt(a.length);

            assertEquals(sorted[k], DeterministicSelect.select(a, k, metrics));
        }
    }

    @Test
    void closestPairEqualsBruteForce() {
        for (int test = 0; test < 20; test++) {
            Point[] points = new Point[random.nextInt(300) + 2];
            for (int i = 0; i < points.length; i++) {
                points[i] = new Point(random.nextDouble() * 1000, random.nextDouble() * 1000);
            }

            double expected = ClosestPair.bruteForce(points, metrics);
            double actual = ClosestPair.closest(points, metrics);

            assertEquals(expected, actual, 1e-9);
        }
    }
}
