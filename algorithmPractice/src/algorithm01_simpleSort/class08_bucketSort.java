package algorithm01_simpleSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 桶排序：将数据分到有限数量的桶中，每个桶内排序（如插入排序），再合并
 * 时间复杂度：平均 O(n + k)，最坏 O(n²)
 * 空间复杂度：O(n + k)
 * 稳定性：稳定（取决于桶内排序算法）
 */
public class class08_bucketSort {
    public static void bucketSort(float[] arr) {
        if (arr == null || arr.length < 2) return;
        int n = arr.length;
        @SuppressWarnings("unchecked")
        List<Float>[] buckets = new List[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 将元素分配到桶中（假设元素在 [0,1) 区间）
        for (float num : arr) {
            int idx = (int) (num * n);
            buckets[idx].add(num);
        }

        // 对每个桶排序
        for (List<Float> bucket : buckets) {
            Collections.sort(bucket);
        }

        // 合并结果
        int index = 0;
        for (List<Float> bucket : buckets) {
            for (float num : bucket) {
                arr[index++] = num;
            }
        }
    }
}
