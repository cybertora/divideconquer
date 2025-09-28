package org.example.geometry;

import org.example.metrics.Metrics;
import java.util.*;

public class ClosestPair {
    public static class Point {
        public double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double closest(Point[] pts, Metrics m) {
        long t0 = System.nanoTime();
        Point[] sortedX = pts.clone();
        Arrays.sort(sortedX, Comparator.comparingDouble(p -> p.x));
        double ans = closestRec(sortedX, m);
        m.setTimeNs(System.nanoTime() - t0);
        return ans;
    }

    private static double closestRec(Point[] pts, Metrics m) {
        m.enterRecursion();
        int n = pts.length;
        if (n <= 3) {
            double d = brute(pts, m);
            m.exitRecursion();
            return d;
        }

        int mid = n / 2;
        Point midPoint = pts[mid];

        double dl = closestRec(Arrays.copyOfRange(pts, 0, mid), m);
        double dr = closestRec(Arrays.copyOfRange(pts, mid, n), m);
        double d = Math.min(dl, dr);

        List<Point> strip = new ArrayList<>();
        for (Point p : pts) {
            if (Math.abs(p.x - midPoint.x) < d) strip.add(p);
        }
        strip.sort(Comparator.comparingDouble(p -> p.y));

        double minStrip = d;
        for (int i = 0; i < strip.size(); i++) {
            for (int j = i+1; j < strip.size() && (strip.get(j).y - strip.get(i).y) < minStrip; j++) {
                double dist = dist(strip.get(i), strip.get(j), m);
                if (dist < minStrip) minStrip = dist;
            }
        }

        m.exitRecursion();
        return Math.min(d, minStrip);
    }

    private static double brute(Point[] pts, Metrics m) {
        double min = Double.MAX_VALUE;
        for (int i=0;i<pts.length;i++) {
            for (int j=i+1;j<pts.length;j++) {
                double dist = dist(pts[i], pts[j], m);
                if (dist < min) min = dist;
            }
        }
        return min;
    }

    private static double dist(Point a, Point b, Metrics m) {
        m.incDistanceCalcs();
        return Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y));
    }
}
