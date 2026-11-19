import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;

public class GenerateData {
    public static void main(String[] args) throws Exception {
        Path folder = Path.of("data");
        Files.createDirectories(folder);

        Random random = new Random(42);
        int[] sizes = {1000, 10000, 100000};

        for (int n : sizes) {
            int[] a = new int[n];

            for (int i = 0; i < n; i++) {
                a[i] = random.nextInt(100000);
            }
            save(folder.resolve("random_" + n + ".txt"), a);

            Arrays.sort(a);
            save(folder.resolve("ascending_" + n + ".txt"), a);

            for (int i = 0; i < n / 2; i++) {
                int j = n - 1 - i;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
            save(folder.resolve("descending_" + n + ".txt"), a);
        }

        System.out.println("Da tao 9 tep du lieu trong thu muc data.");
    }

    private static void save(Path file, int[] a) throws Exception {
        try (var writer = Files.newBufferedWriter(file)) {
            for (int value : a) {
                writer.write(Integer.toString(value));
                writer.newLine();
            }
        }
    }
}