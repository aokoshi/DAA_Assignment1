package benchmark;

import algorithms.DeterministicSelect;
import algorithms.Metrics;
import algorithms.QuickSelect;

import java.util.Random;

public class SelectComparison {

    private static final int[] SIZES = {10_000, 100_000, 1_000_000};
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        System.out.printf("%-20s %-8s %10s %10s %14s%n",
                "algorithm", "input", "n", "time_ms", "comparisons");
        System.out.println("-".repeat(66));

        for (String input : new String[]{"random", "sorted"}) {
            for (int n : SIZES) {
                int[] data = generate(input, n);
                int k = n / 2;
                measure("QuickSelect", input, data, k);
                measure("DeterministicSelect", input, data, k);
            }
        }
    }

    private static void measure(String algorithm, String input, int[] data, int k) {
        Metrics metrics = new Metrics();

        metrics.startTimer();
        if (algorithm.equals("QuickSelect")) {
            QuickSelect.select(data, k, metrics);
        } else {
            DeterministicSelect.select(data, k, metrics);
        }
        metrics.stopTimer();

        System.out.printf("%-20s %-8s %10d %10.3f %14d%n",
                algorithm, input, data.length, metrics.getTimeMs(), metrics.getComparisons());
    }

    private static int[] generate(String input, int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = input.equals("sorted") ? i : RANDOM.nextInt();
        }
        return a;
    }
}
