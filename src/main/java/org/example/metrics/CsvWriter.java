package org.example.metrics;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;

public class CsvWriter {
    private static final String DIR = "csv";
    private final Path file;

    public CsvWriter(String name) {
        try {
            Files.createDirectories(Path.of(DIR));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        String today = LocalDate.now().toString(); // YYYY-MM-DD
        this.file = Path.of(DIR, name + "-" + today + ".csv");
        ensureHeader();
    }

    private void ensureHeader() {
        if (!Files.exists(file)) {
            try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(file))) {
                pw.println("Algorithm,n,time_ns,comparisons,swaps,distanceCalcs,depth");
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public void appendRow(String algo, int n, Metrics m) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file.toFile(), true)))) {
            pw.printf("%s,%d,%d,%d,%d,%d,%d%n",
                    algo, n, m.getTimeNs(), m.getComparisons(), m.getSwaps(),
                    m.getDistanceCalcs(), m.getMaxDepth());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
