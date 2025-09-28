package org.example.select;

import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {
    @Test
    void testCorrectness() {
        int n = 200;
        int[] a = new Random(42).ints(n, -1000, 1000).toArray();
        int[] b = a.clone();
        Arrays.sort(b);

        int k = n / 2; // медиана
        Metrics m = new Metrics();
        int res = DeterministicSelect.select(a, k, m);

        assertEquals(b[k], res);
    }
}
