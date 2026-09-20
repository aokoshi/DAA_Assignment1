package algorithms;

public class QuickSelect {

    public static int select(int[] a, int k, Metrics m) {
        if (a == null || a.length == 0) {
            throw new IllegalArgumentException("Array must not be empty");
        }
        if (k < 0 || k >= a.length) {
            throw new IllegalArgumentException(
                    "k must be in [0, " + (a.length - 1) + "], but was " + k);
        }
        int[] copy = a.clone();
        return select(copy, 0, copy.length - 1, k, m);
    }

    private static int select(int[] a, int low, int high, int k, Metrics m) {
        m.enterRecursion();
        int result;

        if (low == high) {
            result = a[low];
        } else {
            int[] bounds = Partition.partition(a, low, high, m);
            int lt = bounds[0];
            int gt = bounds[1];

            if (k < lt) {
                result = select(a, low, lt - 1, k, m);
            } else if (k > gt) {
                result = select(a, gt + 1, high, k, m);
            } else {
                result = a[k];
            }
        }

        m.exitRecursion();
        return result;
    }
}
