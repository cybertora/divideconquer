package org.example.cli;

import org.example.metrics.CsvWriter;
import org.example.metrics.Metrics;
import org.example.sort.MergeSort;
import org.example.sort.QuickSort;
// import org.example.select.Select;
// import org.example.closest.ClosestPair;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java -jar app.jar <algo> <n>");
            System.out.println("algo: mergesort | quicksort | select | closest");
            System.exit(1);
        }
        String algo = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);

        CsvWriter csv = new CsvWriter("metrics"); // csv/metrics-YYYY-MM-DD.csv
        Metrics m = new Metrics();

        switch (algo) {
            case "mergesort" -> {
                int[] a = new Random().ints(n, 0, 1_000_000).toArray();
                MergeSort.sort(a, m);
            }
            case "quicksort" -> {
                int[] a = new Random().ints(n, 0, 1_000_000).toArray();
                QuickSort.sort(a, m);
            }
            // case "select" -> { ... Select.select(a, k, m); }
            // case "closest" -> { ... double d = ClosestPair.closest(points, m); }
            default -> {
                System.out.println("Unknown algo: " + algo);
                System.exit(2);
            }
        }
        csv.appendRow(algo, n, m);
        System.out.println("Done: " + algo + " n=" + n +
                " time=" + m.getTimeNs() + "ns comps=" + m.getComparisons() +
                " swaps=" + m.getSwaps() + " depth=" + m.getMaxDepth());
    }
}
