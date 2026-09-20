package algorithms;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static double closest(Point[] points, Metrics m) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }
        Point[] byX = points.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));

        Point[] buffer = new Point[points.length];   // helper array for the merge by y
        Point[] strip = new Point[points.length];    // points near the middle line
        return closest(byX, buffer, strip, 0, points.length - 1, m);
    }

    private static double closest(Point[] a, Point[] buffer, Point[] strip,
                                  int left, int right, Metrics m) {
        m.enterRecursion();
        double best;

        if (right - left + 1 <= 3) {
            best = bruteForce(a, left, right, m);
            sortByY(a, left, right);
        } else {
            int mid = left + (right - left) / 2;
            double middleX = a[mid].x;

            double leftBest = closest(a, buffer, strip, left, mid, m);
            double rightBest = closest(a, buffer, strip, mid + 1, right, m);
            best = Math.min(leftBest, rightBest);

            mergeByY(a, buffer, left, mid, right);

            int size = 0;
            for (int i = left; i <= right; i++) {
                if (Math.abs(a[i].x - middleX) < best) {
                    strip[size] = a[i];
                    size++;
                }
            }

            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size && j <= i + 7; j++) {
                    if (strip[j].y - strip[i].y >= best) {
                        break;
                    }
                    m.addComparison();
                    best = Math.min(best, distance(strip[i], strip[j]));
                }
            }
        }

        m.exitRecursion();
        return best;
    }

    public static double bruteForce(Point[] points, Metrics m) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least two points are required");
        }
        return bruteForce(points, 0, points.length - 1, m);
    }

    private static double bruteForce(Point[] a, int left, int right, Metrics m) {
        double best = Double.POSITIVE_INFINITY;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                m.addComparison();
                best = Math.min(best, distance(a[i], a[j]));
            }
        }
        return best;
    }

    private static double distance(Point p, Point q) {
        double dx = p.x - q.x;
        double dy = p.y - q.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private static void sortByY(Point[] a, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            Point key = a[i];
            int j = i - 1;
            while (j >= left && a[j].y > key.y) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private static void mergeByY(Point[] a, Point[] buffer, int left, int mid, int right) {
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
            } else if (buffer[j].y < buffer[i].y) {
                a[k] = buffer[j++];
            } else {
                a[k] = buffer[i++];
            }
        }
    }
}
