package algorithms;

public class DeterministicSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k must be in [0, " + (a.length - 1) + "], but was " + k);
        }
        int[] copy = a.clone();
        int index = selectIndex(copy, 0, copy.length - 1, k, m);
        return copy[index];
    }

    private static int selectIndex(int[] a, int low, int high, int k, Metrics m) {
        m.enterRecursion();
        int result;

        if (low == high) {
            result = low;
        } else {
            int pivotIndex = medianOfMedians(a, low, high, m);
            int[] bounds = Partition.partitionAt(a, low, high, pivotIndex, m);
            int lt = bounds[0];
            int gt = bounds[1];

            if (k < lt) {
                result = selectIndex(a, low, lt - 1, k, m);
            } else if (k > gt) {
                result = selectIndex(a, gt + 1, high, k, m);
            } else {
                result = k;
            }
        }

        m.exitRecursion();
        return result;
    }

    private static int medianOfMedians(int[] a, int low, int high, Metrics m) {
        int n = high - low + 1;
        if (n <= 5) {
            InsertionSort.sort(a, low, high, m);
            return low + n / 2;
        }

        int groups = 0;
        for (int start = low; start <= high; start += 5) {
            int end = Math.min(start + 4, high);
            InsertionSort.sort(a, start, end, m);
            int medianIndex = start + (end - start) / 2;
            Partition.swap(a, low + groups, medianIndex);
            groups++;
        }

        return selectIndex(a, low, low + groups - 1, low + groups / 2, m);
    }
}
