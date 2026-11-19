import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Benchmark {
    public static void main(String[] args) throws Exception {
        Path file = Path.of("data", "random_10000.txt");
        int[] input;
        try (var lines = Files.lines(file)) {
            input = lines.mapToInt(Integer::parseInt).toArray();
        }

        int[] expected = input.clone();
        Arrays.sort(expected);

        Files.createDirectories(Path.of("results"));
        Path csv = Path.of("results", "pilot.csv");

        try (var out = Files.newBufferedWriter(csv)) {
            out.write("file,n,cutoff,trial,time_ns\n");

            // 10 vong khoi dong, sau do 10 vong ghi ket qua.
            for (int round = 0; round < 20; round++) {
                int[] order = (round % 2 == 0)
                        ? new int[] {7, 10} : new int[] {10, 7};

                for (int cutoff : order) {
                    int[] a = input.clone();
                    int[] aux = new int[a.length];

                    long start = System.nanoTime();
                    MergeCutoff.sort(a, aux, 0, a.length - 1, cutoff);
                    long elapsed = System.nanoTime() - start;

                    if (!Arrays.equals(a, expected)) {
                        throw new AssertionError("Sai ket qua voi CUTOFF = " + cutoff);
                    }

                    if (round >= 10) {
                        out.write(file.getFileName() + "," + a.length + ","
                                + cutoff + "," + (round - 9) + "," + elapsed + "\n");
                    }
                }
            }
        }
        System.out.println("Da luu 20 lan do vao results/pilot.csv");
    }
}