package algorithms;

public class Metrics {

    private long comparisons;
    private int currentDepth;
    private int maxDepth;
    private long startTime;
    private long elapsedNanos;

    public void addComparison() {
        comparisons++;
    }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public void exitRecursion() {
        currentDepth--;
    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        elapsedNanos = System.nanoTime() - startTime;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public double getTimeMs() {
        return elapsedNanos / 1_000_000.0;
    }
}
