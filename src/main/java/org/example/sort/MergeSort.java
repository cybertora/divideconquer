package org.example.sort;

import org.example.metrics.Metrics;
import java.util.Arrays;

public class MergeSort {

    private static final int CUTOFF = 32; // на малых n вставками

    public static void sort(int[] a, Metrics mt) {
        long t0 = System.nanoTime();
        int[] buf = Arrays.copyOf(a, a.length);
        mergeSort(a, 0, a.length, buf, mt);
        mt.setTimeNs(System.nanoTime() - t0);
    }

    private static void mergeSort(int[] a, int l, int r, int[] buf, Metrics mt) {
        mt.enterRecursion();
        int n = r - l;
        if (n <= 1) {
            mt.exitRecursion(); return;
        }
        if (n <= CUTOFF) {
            insertion(a, l, r, mt);
            mt.exitRecursion(); return;
        }
        int mid = l + (n >> 1);
        mergeSort(a, l, mid, buf, mt);
        mergeSort(a, mid, r, buf, mt);
        merge(a, l, mid, r, buf, mt);
        mt.exitRecursion();
    }

    private static void insertion(int[] a, int l, int r, Metrics mt) {
        for (int i = l+1; i < r; i++) {
            int x = a[i], j = i-1;
            while (j >= l) {
                mt.incComparisons();
                if (a[j] <= x) break;
                a[j+1] = a[j]; mt.incSwaps();
                j--;
            }
            a[j+1] = x;
        }
    }

    private static void merge(int[] a, int l, int m, int r, int[] buf, Metrics mt) {
        int i=l, j=m, k=0;
        while (i<m && j<r) {
            mt.incComparisons();
            if (a[i] <= a[j]) buf[k++] = a[i++];
            else              buf[k++] = a[j++];
        }
        while (i<m) buf[k++] = a[i++];
        while (j<r) buf[k++] = a[j++];
        // copy back
        for (int t=0; t<k; t++) { a[l+t] = buf[t]; mt.incSwaps(); }
    }
}
