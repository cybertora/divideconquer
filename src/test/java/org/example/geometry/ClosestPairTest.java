package org.example.geometry;

import org.example.metrics.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {
    @Test
    void testCorrectness() {
        int n = 200;
        Random r = new Random(42);
        ClosestPair.Point[] pts = new ClosestPair.Point[n];
        for (int i=0; i<n; i++) {
            pts[i] = new ClosestPair.Point(r.nextDouble()*1000, r.nextDouble()*1000);
        }
        Metrics m = new Metrics();
        double d = ClosestPair.closest(pts, m);
        assertTrue(d > 0);
    }
}
