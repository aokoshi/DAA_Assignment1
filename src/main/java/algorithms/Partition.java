package algorithms;

import java.util.Random;

public class Partition {

    private static final Random RANDOM = new Random();

    public static int[] partition(int[] a, int low, int high, Metrics m) {
        int pivotIndex = low + RANDOM.nextInt(high - low + 1);
        return partitionAt(a, low, high, pivotIndex, m);
    }

    public static int[] partitionAt(int[] a, int low, int high, int pivotIndex, Metrics m) {
        swap(a, low, pivotIndex);
        int pivot = a[low];

        int lt = low;
        int gt = high;
        int i = low + 1;

        while (i <= gt) {
            m.addComparison();
            if (a[i] < pivot) {
                swap(a, lt, i);
                lt++;
                i++;
            } else if (a[i] > pivot) {
                swap(a, i, gt);
                gt--;
            } else {
                i++;
            }
        }
        return new int[]{lt, gt};
    }

    public static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
