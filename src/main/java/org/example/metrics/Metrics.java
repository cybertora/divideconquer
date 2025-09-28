package org.example.metrics;

public class Metrics {
    private long timeNs;
    private long comparisons;
    private long swaps;
    private long distanceCalcs;
    private int maxDepth;

    private int currentDepth;

    public void incComparisons() { comparisons++; }
    public void incSwaps() { swaps++; }
    public void incDistanceCalcs() { distanceCalcs++; }

    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }
    public void exitRecursion() { currentDepth--; }

    public void setTimeNs(long t) { this.timeNs = t; }

    // getters
    public long getTimeNs() { return timeNs; }
    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getDistanceCalcs() { return distanceCalcs; }
    public int getMaxDepth() { return maxDepth; }
}
