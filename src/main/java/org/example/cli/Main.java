package org.example.cli;

import org.example.metrics.CsvWriter;
import org.example.metrics.Metrics;
import org.example.sort.MergeSort;
import org.example.sort.QuickSort;
import org.example.geometry.ClosestPair;  // Импортируем ClosestPair
import org.example.geometry.ClosestPair.Point;  // Импортируем Point

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Размеры данных для тестирования
        int[] sizes = {100, 500, 1000, 5000};

        // Создаём объект для записи данных в CSV
        CsvWriter csv = new CsvWriter("metrics"); // Имя файла будет metrics-YYYY-MM-DD.csv
        Metrics m = new Metrics();

        // Цикл для выполнения тестов с различными размерами данных
        for (int n : sizes) {
            System.out.println("Running test for size: " + n);

            // Запускаем каждый алгоритм с размерами n
            runAlgorithm("mergesort", n, m, csv);
            runAlgorithm("quicksort", n, m, csv);
            runAlgorithm("closest", n, m, csv); // Запуск для Closest Pair
        }
    }

    // Метод для выполнения алгоритма
    private static void runAlgorithm(String algo, int n, Metrics m, CsvWriter csv) {
        switch (algo.toLowerCase()) {
            case "mergesort":
                int[] a = new Random().ints(n, 0, 1_000_000).toArray();
                MergeSort.sort(a, m);
                break;
            case "quicksort":
                int[] b = new Random().ints(n, 0, 1_000_000).toArray();
                QuickSort.sort(b, m);
                break;
            case "closest":
                Point[] points = new Point[n];
                Random rand = new Random();
                // Заполняем массив точек случайными значениями
                for (int i = 0; i < n; i++) {
                    points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
                }
                double distance = ClosestPair.closest(points, m); // Вызываем алгоритм поиска ближайших точек
                break;
            default:
                System.out.println("Unknown algorithm: " + algo);
                return;
        }

        // Записываем результаты в CSV
        csv.appendRow(algo, n, m);

        // Выводим результат на консоль
        System.out.println(algo + " for n=" + n +
                " time=" + m.getTimeNs() + "ns comps=" + m.getComparisons() +
                " swaps=" + m.getSwaps() + " depth=" + m.getMaxDepth());
    }
}
