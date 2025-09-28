package org.example.sort;

import org.example.metrics.Metrics;
import java.util.Random;

public class QuickSort {
    private static final Random RND = new Random(42);

    public static void sort(int[] a, Metrics m) {
        long t0 = System.nanoTime();
        quick(a, 0, a.length-1, m);
        m.setTimeNs(System.nanoTime()-t0);
    }

    private static void quick(int[] a, int l, int r, Metrics m) {
        while (l < r) {
            m.enterRecursion();
            int i=l, j=r;
            int pivot = a[l + RND.nextInt(r-l+1)];
            while (i<=j) {
                while (a[i] < pivot) { i++; m.incComparisons(); }
                m.incComparisons();
                while (a[j] > pivot) { j--; m.incComparisons(); }
                m.incComparisons();
                if (i<=j) { swap(a,i,j,m); i++; j--; }
            }
            // Рекурсивно в меньшую часть, большая — циклом (bounded stack)
            if ((j-l) < (r-i)) {
                if (l < j) quick(a, l, j, m);
                l = i;
            } else {
                if (i < r) quick(a, i, r, m);
                r = j;
            }
            m.exitRecursion();
        }
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i==j) return;
        int t=a[i]; a[i]=a[j]; a[j]=t; m.incSwaps();
    }
}
