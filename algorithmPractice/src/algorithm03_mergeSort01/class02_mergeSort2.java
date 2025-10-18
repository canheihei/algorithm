package algorithm03_mergeSort01;

/**
 * 类说明: 自底向上（迭代）归并排序实现。
 * 该类提供对整型数组的原地升序排序，采用逐步合并子区间的方式，时间复杂度 O(N log N)，空间复杂度 O(N)（辅助数组）。
 */
public class class02_mergeSort2 {
    /**
     * 方法: mergeSort2
     * 说明: 归并排序的自底向上迭代实现。通过从长度为1的子段开始，逐步倍增合并长度为 mergeSize 的相邻子区间，
     * 直到整个数组被合并为有序。方法包含对空数组或长度小于2的校验，直接返回。
     *
     * @param arr 待排序的整型数组
     * @return void 无返回值，排序结果就地写回到原数组
     */
    public static void mergeSort2(int[] arr) {
        // 边界情况：数组为空，或只有0个、1个元素，无需排序
        if (arr == null || arr.length < 2) {
            return;
        }

        int N = arr.length;          // 数组总长度
        int mergeSize = 1;           // 当前每组的大小（初始为1：每个元素自己是一组）

        // 只要当前分组大小还没覆盖整个数组，就继续合并
        while (mergeSize < N) {
            int L = 0; // 每一轮从数组最左边开始处理

            // 遍历整个数组，每次处理两个相邻的“mergeSize”长度的子数组
            while (L < N) {
                // 第一个子数组的右边界（左闭右闭）
                int M = L + mergeSize - 1;

                // 如果第一个子数组已经超出数组范围，说明后面没东西可合并了，跳出
                if (M >= N) {
                    break;
                }

                // 第二个子数组的右边界（最多到数组最后一个位置）
                int R = Math.min(M + mergeSize, N - 1);

                // 合并 [L, M] 和 [M+1, R] 这两个已排序的子数组
                merge(arr, L, M, R);

                // 移动L到下一对子数组的起点
                L = R + 1;
            }

            // 防止 mergeSize 翻倍后溢出（比如 N=5，mergeSize=4，再翻倍=8，但没必要）
            // 实际上这行可以省略，因为 while(mergeSize < N) 会自然终止
            // 但加上更安全，避免极端情况下的无效循环
            if (mergeSize > N / 2) {
                break;
            }

            // 分组大小翻倍：1 → 2 → 4 → 8 → ...
            mergeSize <<= 1; // 等价于 mergeSize *= 2;
        }
    }

    /**
     * 方法: merge
     * 说明: 合并方法。将已经分别有序的两个相邻子区间 [L,M] 和 [M+1,R] 合并为一个有序区间，使用辅助数组暂存合并结果，
     * 保持稳定性，最后将合并后的结果写回原数组的区间 [L,R]。
     *
     * @param arr 待合并的整型数组
     * @param L   左子区间起始索引（包含）
     * @param M   左子区间结束索引（包含），右子区间起始为 M+1
     * @param R   右子区间结束索引（包含）
     * @return void 无返回值，合并结果写回原数组的区间 [L,R]
     */
    public static void merge(int[] arr, int L, int M, int R) {
        // 创建辅助数组，存放合并后的结果
        int[] help = new int[R - L + 1];
        int i = 0;           // help 数组的写入指针

        int p1 = L;          // 左子数组的读取指针（从 L 到 M）
        int p2 = M + 1;      // 右子数组的读取指针（从 M+1 到 R）

        // 两个指针都没越界时，比较大小，小的先放进 help
        while (p1 <= M && p2 <= R) {
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        // 如果左子数组还有剩余，全部拷贝过去
        while (p1 <= M) {
            help[i++] = arr[p1++];
        }

        // 如果右子数组还有剩余，全部拷贝过去
        while (p2 <= R) {
            help[i++] = arr[p2++];
        }

        // 把合并好的结果 copy 回原数组的 [L, R] 区间
        for (i = 0; i < help.length; i++) {
            arr[L + i] = help[i];
        }
    }
}
