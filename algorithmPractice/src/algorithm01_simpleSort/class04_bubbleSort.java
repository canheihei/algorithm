package algorithm01_simpleSort;

import java.util.Random;

/**
 * 冒泡排序：相邻元素两两比较，大者后移，每轮将最大值“冒泡”到末尾
 * 时间复杂度：O(n²)
 * 空间复杂度：O(1)
 * 稳定性：稳定
 */
public class class04_bubbleSort {
    public static void main(String[] args) {
        int testTime = 1000;
        int maxLength = 1000;
        int maxValue = 10000;
        Random rand = new Random();

    }

    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public static void swap(int[] arr, int i, int j) {
        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }
}
