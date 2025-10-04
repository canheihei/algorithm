package algorithm01_simpleSort;

/**
 * 希尔排序：插入排序的改进版，按步长分组进行插入排序，逐步缩小步长至1
 * 时间复杂度：O(n log²n) ~ O(n²)（取决于步长序列）
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 */
public class class05_shellSort {
    public static void shellSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int n = arr.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = arr[i];
                int j = i;
                while (j >= gap && arr[j - gap] > key) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = key;
            }
        }
    }
}
