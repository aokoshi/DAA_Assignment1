package benchmark;

import algorithms.Metrics;
import algorithms.MergeSort;
import algorithms.QuickSelect;
import algorithms.QuickSort;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Locale;

public class BenchmarkRunner {

    private static final int[] SIZES = {1_000, 10_000, 100_000, 1_000_000};
    private static final String[] INPUTS = {"random", "sorted", "duplicates"};
    private static final String[] ALGORITHMS = {"MergeSort", "QuickSort", "QuickSelect"};
    private static final int RUNS = 5;

    private static final Random RANDOM = new Random();

    public static void main(String[] args) throws IOException {
        PrintWriter csv = new PrintWriter(new FileWriter("results.csv"));
        csv.println("algorithm,input,n,time_ms,comparisons,max_depth");

        System.out.printf("%-12s %-12s %10s %10s %14s %10s%n",
                "algorithm", "input", "n", "time_ms", "comparisons", "max_depth");
        System.out.println("-".repeat(74));

        for (String algorithm : ALGORITHMS) {
            for (String input : INPUTS) {
                for (int n : SIZES) {
                    runCase(algorithm, input, n, csv);
                }
            }
            System.out.println("-".repeat(74));
        }

        csv.close();
        System.out.println("Results are saved to results.csv");
    }

    private static void runCase(String algorithm, String input, int n, PrintWriter csv) {
        double[] times = new double[RUNS];
        long[] comparisons = new long[RUNS];
        int[] depths = new int[RUNS];

        for (int run = 0; run < RUNS; run++) {
            int[] data = generate(input, n);
            Metrics metrics = new Metrics();

            metrics.startTimer();
            execute(algorithm, data, metrics);
            metrics.stopTimer();

            times[run] = metrics.getTimeMs();
            comparisons[run] = metrics.getComparisons();
            depths[run] = metrics.getMaxDepth();
        }

        int median = medianIndex(times);

        System.out.printf("%-12s %-12s %10d %10.3f %14d %10d%n",
                algorithm, input, n, times[median], comparisons[median], depths[median]);
        csv.printf(Locale.US, "%s,%s,%d,%.3f,%d,%d%n",
                algorithm, input, n, times[median], comparisons[median], depths[median]);
    }

    private static void execute(String algorithm, int[] data, Metrics metrics) {
        switch (algorithm) {
            case "MergeSort" -> MergeSort.sort(data, metrics);
            case "QuickSort" -> QuickSort.sort(data, metrics);
            case "QuickSelect" -> QuickSelect.select(data, data.length / 2, metrics);
            default -> throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
    }

    private static int[] generate(String input, int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = switch (input) {
                case "sorted" -> i;
                case "duplicates" -> RANDOM.nextInt(10);
                default -> RANDOM.nextInt();
            };
        }
        return a;
    }

    private static int medianIndex(double[] times) {
        double[] sorted = times.clone();
        Arrays.sort(sorted);
        double median = sorted[times.length / 2];
        for (int i = 0; i < times.length; i++) {
            if (times[i] == median) {
                return i;
            }
        }
        return 0;
    }
}
