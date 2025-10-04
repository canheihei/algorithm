package algorithm01_simpleSort;

import java.util.Arrays;
import java.util.Random;

/**
 * 选择排序：每轮从未排序部分选出最小值，放到已排序部分末尾
 * 时间复杂度：O(n²)
 * 空间复杂度：O(1)
 * 稳定性：不稳定
 */
public class class03_selectSort {
    // 对数器
    public static void main(String[] args) {
        int testTime = 100;
        int maxLength = 1000;
        int maxValue = 10000;
        Random rand = new Random();
        for (int i = 0; i < testTime; i++) {
            int[] arr1 = generateRandomArray(rand.nextInt(maxLength), maxValue);
            int[] arr2 = Arrays.copyOf(arr1, arr1.length);
            Arrays.sort(arr1);
            selectSort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                System.out.println(" 排序出错！");
                System.out.println(" 原数组: " + Arrays.toString(arr2));
                System.out.println(" 正确结果: " + Arrays.toString(arr1));
                return;
            }
        }
        System.out.println(" 恭喜！经过 " + testTime + " 次测试，你的排序算法正确！");
    }

    /**
     * 生成随机数组
     * @param arrLength 数组长度
     * @param maxValue 随机元素的取值范围为 [0, maxValue)
     * @return 返回生成的整型数组
     */
    private static int[] generateRandomArray(int arrLength, int maxValue) {
        int[] arr = new int[arrLength];
        Random rand = new Random();
        for (int i = 0; i < arrLength; i++) {
            arr[i] = rand.nextInt(maxValue);
        }
        return arr;
    }

    /**
     * 选择排序
     * @param arr 待排序数组；若为 null 或长度 < 2 则不处理
     */
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                minIndex = arr[j] < arr[minIndex] ? j : minIndex;
            }
            swap(arr, i, minIndex);
        }
    }

    /**
     * 交换数组中两个位置的值（使用按位异或实现，要求 i != j）
     * @param arr 目标数组
     * @param i 位置 i
     * @param j 位置 j
     */
    public static void swap(int[] arr, int i, int j) {
        if (i != j) {
            arr[i] = arr[i] ^ arr[j];
            arr[j] = arr[i] ^ arr[j];
            arr[i] = arr[i] ^ arr[j];
        }
    }
}
