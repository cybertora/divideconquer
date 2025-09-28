Divide & Conquer Algorithms – Assignment 1
Project Overview

This project implements and compares four classic divide-and-conquer algorithms:

MergeSort

QuickSort (randomized pivot)

Deterministic Select (Median of Medians)

Closest Pair of Points in 2D

For each algorithm, I track metrics such as:

Execution time (nanoseconds)

Number of comparisons

Number of swaps

Number of distance calculations (for geometry)

Maximum recursion depth

All runs append results into CSV files inside the csv/ folder. These CSVs are later opened in Excel to make plots.

Architecture Notes

I created a Metrics class that counts comparisons, swaps, recursion depth, and distance calculations.

Depth is measured by calling enterRecursion() and exitRecursion() at the start/end of recursive calls.

All metrics are written into a CSV file using the CsvWriter class. Each run adds a new line, so data grows over time.

The CLI (Main.java) takes arguments <algo> <n> where algo = mergesort | quicksort | select | closest.

Example:

java -jar target/divideconquer-1.0-SNAPSHOT-shaded.jar mergesort 1000

Recurrence Analysis

MergeSort

Recurrence: T(n) = 2T(n/2) + Θ(n)

Master Theorem (Case 2) ⇒ Θ(n log n).

Depth grows like O(log n).

QuickSort (random pivot, average case)

Recurrence: T(n) = T(i) + T(n-i-1) + Θ(n) where i is random.

Expected ⇒ Θ(n log n).

Depth average O(log n), but can be O(n) in the worst case.

Deterministic Select (Median of Medians)

Recurrence: T(n) = T(n/5) + T(7n/10) + Θ(n)

By intuition (Akra–Bazzi): Θ(n).

Depth is O(log n) even in worst case.

Closest Pair of Points

Recurrence: T(n) = 2T(n/2) + Θ(n)

Same as MergeSort ⇒ Θ(n log n).

Depth = O(log n).

Plots

I opened the CSV data in Excel and made scatter plots:

Time vs n

X-axis: input size n

Y-axis: execution time in nanoseconds

Each algorithm is a separate series

Depth vs n

X-axis: n

Y-axis: recursion depth

From these graphs:

MergeSort and QuickSort show near-linear-log growth.

Deterministic Select is slower on small n due to large constants, but scales linearly.

Closest Pair follows n log n, but with higher constant factors.

Constant-Factor Effects

Even though asymptotic curves match theory, there are practical differences:

Cache misses: For large n, arrays no longer fit in L1/L2 cache, so time jumps.

Garbage Collection (GC): In Java, object allocation can trigger GC pauses, making times noisy.

Cutoffs: Using insertion sort for small subproblems improves MergeSort performance.

Pivot choice: Random pivot in QuickSort smooths performance but adds RNG overhead.

Summary

The measured results generally align with theoretical complexity (MergeSort, QuickSort, and Closest Pair all ~n log n, Select ~n).

Small mismatches are due to constant factors (cache, GC, extra function calls).

Writing results into CSV and plotting gave a clear picture of how theory translates into practice.

Overall, this assignment helped me understand not just Big-O, but also how real systems affect algorithm performance.


Written by Rassul Tokatov, SE-2419
