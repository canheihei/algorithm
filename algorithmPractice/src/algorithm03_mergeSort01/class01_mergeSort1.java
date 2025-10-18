package algorithm03_mergeSort01;

/**
 * 类说明: 归并排序实现（自顶向下递归版本）。
 */
public class class01_mergeSort1 {
    /**
     * 方法: mergeSort1
     * 说明: 归并排序的入口方法。对输入数组进行空值与长度检查，并启动对整个数组区间的递归排序过程。
     *
     * @param arr 待排序的整型数组
     * @return void 无返回值，排序结果就地写回到原数组
     */
    public static void mergeSort1(int[] arr) {
        if (arr != null && arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    /**
     * 方法: process
     * 说明: 递归分治方法。负责将当前区间 [L,R] 二分为两部分，递归排序左右子区间，然后调用合并方法合并有序子区间。
     *
     * @param arr 待排序的数组
     * @param L   当前处理区间的左边界（包含）
     * @param R   当前处理区间的右边界（包含）
     * @return void 无返回值，区间 [L,R] 将变为有序
     */
    private static void process(int[] arr, int L, int R) {
        // 终止条件
        if (L >= R) {
            return;
        }
        // 该区间中点
        int M = L + (R - L) >> 1;
        process(arr, L, M);
        process(arr, M + 1, R);
        merge(arr, L, M, R);
    }

    /**
     * 方法: merge
     * 说明: 合并方法。将已经分别有序的两个相邻子区间 [L,M] 和 [M+1,R] 合并为一个有序区间，使用辅助数组暂存并将结果写回原数组。
     *
     * @param arr 待合并的数组
     * @param L   左子区间起始索引（包含）
     * @param M   左子区间结束索引（包含），右子区间起始为 M+1
     * @param R   右子区间结束索引（包含）
     * @return void 无返回值，合并结果写回原数组的区间 [L,R]
     */
    private static void merge(int[] arr, int L, int M, int R) {
        int[] help = new int[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 依次比大小后剩余有序的肯定比已放入数组的大，直接追加
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }
        // L索引处更新
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }
}
