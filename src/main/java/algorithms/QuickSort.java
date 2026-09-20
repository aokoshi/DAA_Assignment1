package algorithms;

public class QuickSort {

    public static void sort(int[] a, Metrics m) {
        if (a.length <= 1) {
            return;
        }
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int low, int high, Metrics m) {
        m.enterRecursion();
        while (low < high) {
            int[] bounds = Partition.partition(a, low, high, m);
            int lt = bounds[0];
            int gt = bounds[1];

            int leftSize = lt - low;
            int rightSize = high - gt;

            if (leftSize < rightSize) {
                sort(a, low, lt - 1, m);
                low = gt + 1;
            } else {
                sort(a, gt + 1, high, m);
                high = lt - 1;
            }
        }
        m.exitRecursion();
    }
}
