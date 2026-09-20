package algorithms;

public class InsertionSort {

    public static void sort(int[] a, Metrics m) {
        sort(a, 0, a.length - 1, m);
    }

    public static void sort(int[] a, int left, int right, Metrics m) {
        for (int i = left + 1; i <= right; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= left) {
                m.addComparison();
                if (a[j] <= key) {
                    break;
                }
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }
}
