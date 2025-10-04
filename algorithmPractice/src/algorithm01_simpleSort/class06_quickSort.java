package algorithm01_simpleSort;

/**
 * 快速排序：分治法，选取基准值，将小于基准的放左边，大于的放右边，递归处理
 * 时间复杂度：平均 O(n log n)，最坏 O(n²)
 * 空间复杂度：O(log n)（递归栈）
 * 稳定性：不稳定
 */
public class class06_quickSort {

    public static void quickSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        quickSortHelper(arr, 0, arr.length - 1);
    }

    private static void quickSortHelper(int[] arr, int l, int r) {
        if (l >= r) return;
        int p = partition(arr, l, r);
        quickSortHelper(arr, l, p - 1);
        quickSortHelper(arr, p + 1, r);
    }

    private static int partition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int i = l;
        for (int j = l; j < r; j++) {
            if (arr[j] <= pivot) {
                swap(arr, i++, j);
            }
        }
        swap(arr, i, r);
        return i;
    }

    public static void swap(int[] arr, int i, int j) {
        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }
}
