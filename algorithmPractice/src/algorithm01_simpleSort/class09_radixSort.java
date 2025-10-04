package algorithm01_simpleSort;

import java.util.Arrays;

/**
 * 基数排序：按位排序（从低位到高位），使用计数排序作为子过程
 * 时间复杂度：O(d * (n + k))，d 为位数，k 为基数（如10）
 * 空间复杂度：O(n + k)
 * 稳定性：稳定
 */
public class class09_radixSort {
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int max = Arrays.stream(arr).max().orElse(0);
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n];
        int[] count = new int[10];

        for (int num : arr) {
            count[(num / exp) % 10]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        System.arraycopy(output, 0, arr, 0, n);
    }
}
