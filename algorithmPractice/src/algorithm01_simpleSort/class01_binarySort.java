package algorithm01_simpleSort;

/**
 * 二分查找：在升序有序数组中查找目标值的下标。
 * 时间复杂度：O(log n)，空间复杂度：O(1)。
 */
public class class01_binarySort {
    // 对数器
    public static void main(String[] args) {
        int[] arr1 = {1, 3, 4, 6, 7, 8, 9, 12, 14, 15, 19};
        int[] arr2 = {4, 3, 2, 3, 4, 6, 7, 8, 9, 12, 14, 15, 19};
        int aim = 3;
        System.out.println(binarySearch(arr1, aim));
        System.out.println(getLessIndex(arr2));
    }

    /**
     * @param arr   升序有序整型数组（不可为空，否则行为未定义）
     * @param value 需要查找的目标值
     * @return 目标值所在的下标；若不存在返回 -1
     */
    public static int binarySearch(int[] arr, int value) {
        // 定义左边界
        int left = 0;
        // 定义右边界
        int right = arr.length - 1;
        while (left <= right) {
            // 定义中间位置
            int mid = (left + right) / 2;
            if (arr[mid] == value) {
                return mid;
            }
            // 如果中间位置的值小于value，说明value在mid的右边
            if (arr[mid] < value) {
                left = mid + 1;
            }
            // 如果中间位置的值大于value，说明value在mid的左边
            if (arr[mid] > value) {
                right = mid - 1;
            }
        }
        // 找不到返回-1
        return -1;
    }

    /**
     * 寻找数组中的任意一个局部最小值位置。
     * 局部最小值定义：arr[i] < arr[i-1] 且 arr[i] < arr[i+1]；
     * 边界情况：若 arr[0] < arr[1] 则 0 为局部最小；若 arr[n-1] < arr[n-2] 则 n-1 为局部最小。
     * 要求（经典前提）：相邻元素不相等，可保证一定存在局部最小。
     * 时间复杂度：O(log n)，空间复杂度：O(1)。
     *
     * @param arr 整型数组；若为 null 或长度为 0 返回 -1
     * @return 任意一个局部最小值的下标；不存在或非法输入返回 -1
     */
    public static int getLessIndex(int[] arr) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        // 处理边界情况：单元素数组或首尾元素
        if (arr.length == 1 || arr[0] < arr[1]) {
            return 0;
        }
        if (arr[arr.length - 1] < arr[arr.length - 2]) {
            return arr.length - 1;
        }

        int left = 1;
        int right = arr.length - 2;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            // 找到局部最小值
            if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
                return mid;
            }
            // 如果中间值比右边大，说明最小值在右侧
            else if (arr[mid] > arr[mid + 1]) {
                left = mid + 1;
            }
            // 否则在左侧
            else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
