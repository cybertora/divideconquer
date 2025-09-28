package org.example.select;

import org.example.metrics.Metrics;
import java.util.Arrays;

public class DeterministicSelect {

    // Найти k-й (0-based) наименьший элемент
    public static int select(int[] a, int k, Metrics m) {
        long t0 = System.nanoTime();
        int ans = selectRec(a, 0, a.length - 1, k, m);
        m.setTimeNs(System.nanoTime() - t0);
        return ans;
    }

    private static int selectRec(int[] a, int l, int r, int k, Metrics m) {
        m.enterRecursion();
        if (l == r) {
            m.exitRecursion();
            return a[l];
        }

        int pivot = medianOfMedians(a, l, r, m);
        int pivotIndex = partition(a, l, r, pivot, m);

        int length = pivotIndex - l;
        if (k < length) {
            int res = selectRec(a, l, pivotIndex - 1, k, m);
            m.exitRecursion();
            return res;
        } else if (k > length) {
            int res = selectRec(a, pivotIndex + 1, r, k - length - 1, m);
            m.exitRecursion();
            return res;
        } else {
            m.exitRecursion();
            return pivot;
        }
    }

    private static int medianOfMedians(int[] a, int l, int r, Metrics m) {
        int n = r - l + 1;
        if (n < 5) {
            Arrays.sort(a, l, r + 1);
            return a[l + n / 2];
        }

        int numMedians = (int) Math.ceil(n / 5.0);
        int[] medians = new int[numMedians];
        for (int i = 0; i < numMedians; i++) {
            int subL = l + i * 5;
            int subR = Math.min(subL + 4, r);
            Arrays.sort(a, subL, subR + 1);
            medians[i] = a[subL + (subR - subL) / 2];
        }
        return medianOfMedians(medians, 0, numMedians - 1, m);
    }

    private static int partition(int[] a, int l, int r, int pivot, Metrics m) {
        int i = l, j = r;
        while (i <= j) {
            while (a[i] < pivot) { i++; m.incComparisons(); }
            m.incComparisons();
            while (a[j] > pivot) { j--; m.incComparisons(); }
            m.incComparisons();
            if (i <= j) {
                int tmp = a[i]; a[i] = a[j]; a[j] = tmp; m.incSwaps();
                i++; j--;
            }
        }
        return i - 1; // позиция pivot
    }
}
