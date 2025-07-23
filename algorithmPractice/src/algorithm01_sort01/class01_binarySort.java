package algorithm01_sort01;

// 二分法经典例子
// 给定一个有序数组arr，找到>=value的最左位置
public class class01_binarySort {
	public static void main(String[] args) {

	}

	// 二分查找
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

	// 局部最小值，即找到一个位置，使得它左边和右边的数都比它大，“V”结构
	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length  == 0) {
			return -1;
		}
		// 处理边界情况：单元素数组或首尾元素
		if (arr.length  == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length  - 1;
		}

		int left = 1;
		int right = arr.length  - 2;
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
