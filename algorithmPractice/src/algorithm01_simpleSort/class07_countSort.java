package algorithm01_simpleSort;

import java.util.Arrays;

/**
 * 计数排序：适用于整数且范围不大的情况，统计每个值出现次数后还原
 * 时间复杂度：O(n + k)，k 为数据范围
 * 空间复杂度：O(k)
 * 稳定性：稳定
 */
public class class07_countSort {
    public static void countingSort(int[] arr) {
        if (arr == null || arr.length < 2) return;
        int max = Arrays.stream(arr).max().orElse(0);
        int min = Arrays.stream(arr).min().orElse(0);
        int range = max - min + 1;
        int[] count = new int[range];
        for (int num : arr) {
            count[num - min]++;
        }
        int index = 0;
        for (int i = 0; i < range; i++) {
            while (count[i]-- > 0) {
                arr[index++] = i + min;
            }
        }
    }
}
