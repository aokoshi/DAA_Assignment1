package algorithms;

public class MergeSort {

    private static final int CUTOFF = 15;

    public static void sort(int[] a, Metrics m) {
        if (a.length <= 1) {
            return;
        }
        int[] buffer = new int[a.length];
        sort(a, buffer, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] buffer, int left, int right, Metrics m) {
        m.enterRecursion();
        if (right - left + 1 <= CUTOFF) {
            InsertionSort.sort(a, left, right, m);
        } else {
            int mid = left + (right - left) / 2;
            sort(a, buffer, left, mid, m);
            sort(a, buffer, mid + 1, right, m);
            merge(a, buffer, left, mid, right, m);
        }
        m.exitRecursion();
    }

    private static void merge(int[] a, int[] buffer, int left, int mid, int right, Metrics m) {
        for (int i = left; i <= right; i++) {
            buffer[i] = a[i];
        }

        int i = left;
        int j = mid + 1;

        for (int k = left; k <= right; k++) {
            if (i > mid) {
                a[k] = buffer[j++];
            } else if (j > right) {
                a[k] = buffer[i++];
            } else {
                m.addComparison();
                if (buffer[j] < buffer[i]) {
                    a[k] = buffer[j++];
                } else {
                    a[k] = buffer[i++];
                }
            }
        }
    }
}
