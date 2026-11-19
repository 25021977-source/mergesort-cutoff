import java.util.Arrays;

public class MergeCutoff {
    private static void insertionSort(int[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            for (int j = i; j > lo; j--) {
                if (a[j] < a[j - 1]) {
                    int temp = a[j];
                    a[j] = a[j - 1];
                    a[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }
private static void merge(int[] a, int[] aux, int lo, int mid, int hi) {
    for (int k = lo; k <= hi; k++) {
        aux[k] = a[k];
    }

    int i = lo;       // Phần tử đang xét ở nửa trái
    int j = mid + 1;  // Phần tử đang xét ở nửa phải

    for (int k = lo; k <= hi; k++) {
        if (i > mid) {
            a[k] = aux[j++];
        } else if (j > hi) {
            a[k] = aux[i++];
        } else if (aux[j] < aux[i]) {
            a[k] = aux[j++];
        } else {
            a[k] = aux[i++];
        }
    }
}
 static void sort(int[] a, int[] aux, int lo, int hi, int cutoff) {
    if (hi <= lo) return;

    if (hi - lo + 1 <= cutoff) {
        insertionSort(a, lo, hi);
        return;
    }

    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid, cutoff);
    sort(a, aux, mid + 1, hi, cutoff);
    merge(a, aux, lo, mid, hi);
}
    public static void main(String[] args) {
      int[][] cases = {
        {},
        {5},
        {7, 6, 5, 4, 3, 2, 1},              // 7 phần tử
        {8, 3, 7, 1, 6, 2, 5, 4},           // 8 phần tử
        {4, -1, 4, 0, 8, 2, 5, 1, 9, 3},    // 10 phần tử
        {4, -1, 4, 0, 8, 2, 5, 1, 9, 3, 7}  // 11 phần tử
      };

      for (int cutoff : new int[] {7, 10}) {
         for (int[] input : cases) {
            int[] a = input.clone();
            int[] expected = input.clone();
            Arrays.sort(expected);

            int[] aux = new int[a.length];
            sort(a, aux, 0, a.length - 1, cutoff);

            if (!Arrays.equals(a, expected)) {
               throw new AssertionError("Sai o cutoff = " + cutoff
                     + ", input = " + Arrays.toString(input));
            }
         }
         System.out.println("CUTOFF = " + cutoff + ": dung ca 6 ca");
    } 
}
}