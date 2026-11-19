import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BenchmarkAll {
    public static void main(String[] args) throws Exception {
        int[] sizes = {1000, 10000, 100000};
        String[] types = {"random", "ascending", "descending"};
        List<Integer> cutoffs = new ArrayList<>(
                List.of(1, 4, 7, 10, 16, 24, 32, 64));
        Random order = new Random(42);

        Files.createDirectories(Path.of("results"));
        try (var out = Files.newBufferedWriter(Path.of("results", "full.csv"))) {
            out.write("file,n,cutoff,trial,time_ns\n");

            for (int n : sizes) {
                for (String type : types) {
                    String name = type + "_" + n + ".txt";
                    int[] input;
                    try (var lines = Files.lines(Path.of("data", name))) {
                        input = lines.mapToInt(Integer::parseInt).toArray();
                    }
                    if (input.length != n) {
                        throw new IllegalStateException("Sai so phan tu: " + name);
                    }

                    int[] expected = input.clone();
                    Arrays.sort(expected);

                    // 10 vong khoi dong, sau do ghi 10 vong do.
                    for (int round = 0; round < 20; round++) {
                        Collections.shuffle(cutoffs, order);

                        for (int cutoff : cutoffs) {
                            int[] a = input.clone();
                            int[] aux = new int[n];

                            long start = System.nanoTime();
                            MergeCutoff.sort(a, aux, 0, n - 1, cutoff);
                            long elapsed = System.nanoTime() - start;

                            if (!Arrays.equals(a, expected)) {
                                throw new AssertionError(
                                        "Sap xep sai: " + name + ", cutoff=" + cutoff);
                            }

                            if (round >= 10) {
                                out.write(name + "," + n + "," + cutoff + ","
                                        + (round - 9) + "," + elapsed + "\n");
                            }
                        }
                    }
                    System.out.println("Da do xong: " + name);
                }
            }
        }
        System.out.println("Da luu 720 lan do vao results/full.csv");
    }
}