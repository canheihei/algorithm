package algorithm01_simpleSort;

import java.util.Arrays;
import java.util.Random;

/**
 * 插入排序：将当前元素插入到已排序部分的合适位置
 * 时间复杂度：O(n²)（最好 O(n)，已有序时）
 * 空间复杂度：O(1)
 * 稳定性：稳定
 */
public class class02_insertionSort {
    // 对数器
    public static void main(String[] args) {
        // 运行对数器
        int times = 1000;
        int maxLen = 50;
        int maxVal = 1000;
        if (runTester(times, maxLen, maxVal)) {
            System.out.println("所有测试通过");
        }

        // 演示一次排序
        int[] demo = {213, 1, 12, 11, 1223, 2131, 2213, 12231, 22131, 12231};
        insertionSort(demo);
        System.out.println("演示排序结果: " + Arrays.toString(demo));
    }

    /**
     * 插入排序:
     * 将当前位置元素向左扫描并插入到已排序区间的正确位置
     *
     * @param arr 待排序数组(原地修改)
     */
    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;
        for (int i = 1; i < arr.length; i++) {
            int cur = arr[i];
            int j = i - 1;
            // 向左移动大于 cur 的元素
            while (j >= 0 && arr[j] > cur) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = cur;
        }
    }

    private static void swap(Integer[] arr, Integer integer) {
        arr[integer] = arr[integer] ^ arr[integer - 1];
        arr[integer - 1] = arr[integer] ^ arr[integer - 1];
        arr[integer] = arr[integer] ^ arr[integer - 1];
    }

    /**
     * 对数器总控:
     * 随机生成多组数组，分别用自写排序与系统排序比对
     *
     * @param times  测试次数
     * @param maxLen 随机数组最大长度(实际长度为[0,maxLen])
     * @param maxVal 元素值范围为[-maxVal,maxVal]
     * @return 是否全部通过
     */
    public static boolean runTester(int times, int maxLen, int maxVal) {
        for (int i = 0; i < times; i++) {
            int[] arr1 = randomArray(maxLen, maxVal);
            int[] arr2 = copyArray(arr1);
            insertionSort(arr1);
            Arrays.sort(arr2);
            if (!isEqual(arr1, arr2)) {
                System.out.println("出错用例:");
                System.out.println("自写: " + Arrays.toString(arr1));
                System.out.println("标准: " + Arrays.toString(arr2));
                return false;
            }
        }
        return true;
    }

    /**
     * 生成随机数组
     *
     * @param maxLen 最大长度
     * @param maxVal 值域绝对值上限
     * @return 新数组
     */
    public static int[] randomArray(int maxLen, int maxVal) {
        Random r = new Random();
        int len = r.nextInt(maxLen + 1);
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = r.nextInt(maxVal + 1) - r.nextInt(maxVal + 1);
        }
        return arr;
    }

    /**
     * 拷贝数组
     *
     * @param arr 原数组
     * @return 副本
     */
    public static int[] copyArray(int[] arr) {
        if (arr == null) return null;
        return Arrays.copyOf(arr, arr.length);
    }

    /**
     * 判断两个数组是否元素一致
     *
     * @param a 数组A
     * @param b 数组B
     * @return 是否完全相同
     */
    public static boolean isEqual(int[] a, int[] b) {
        return Arrays.equals(a, b);
    }
}
