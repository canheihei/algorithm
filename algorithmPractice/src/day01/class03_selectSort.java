package day01;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class class03_selectSort {
	public static void main(String[] args) {
		int testTime = 100;
		int maxLength = 1000;
		int maxValue = 10000;
		Random rand = new Random();
		for (int i = 0; i < testTime; i++) {
			int arr1[] = generateRandomArray(rand.nextInt(maxLength),maxValue);
			int arr2[] = Arrays.copyOf(arr1,arr1.length);
			Arrays.sort(arr1);
			selectSort(arr2);
			if(!Arrays.equals(arr1, arr2)) {
				System.out.println(" 排序出错！");
				System.out.println(" 原数组: " + Arrays.toString(arr2));
				System.out.println(" 正确结果: " + Arrays.toString(arr1));
				return;
			}
		}
		System.out.println(" 恭喜！经过 " + testTime + " 次测试，你的排序算法正确！");
	}

	private static int[] generateRandomArray(int arrLength, int maxValue) {
		int arr[] = new int[arrLength];
		Random rand = new Random();
		for (int i = 0; i < arrLength; i++) {
			arr[i] = rand.nextInt(maxValue);
		}
		return arr;
	}

	public static void selectSort(int[] arr) {
		if(arr == null || arr.length < 2){
			return;
		}

		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			for(int j = i + 1; j < arr.length; j++){
				minIndex = arr[j] < arr[minIndex] ? j : minIndex;
			}
			swap(arr,i,minIndex);
		}
	}

	public static void swap (int[] arr, int i ,int j) {
		if(i != j) {
			arr[i] = arr[i] ^ arr[j];
			arr[j] = arr[i] ^ arr[j];
			arr[i] = arr[i] ^ arr[j];
		}
	}
}
