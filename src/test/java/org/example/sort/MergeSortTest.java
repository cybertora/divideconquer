package org.example.sort;

import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {
    @Test
    void randomCorrectness() {
        int n = 1000;
        int[] a = new Random(42).ints(n, -10000, 10000).toArray();
        int[] b = a.clone();
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        java.util.Arrays.sort(b);
        assertArrayEquals(b, a);
        assertTrue(m.getMaxDepth() > 0);
    }
}
