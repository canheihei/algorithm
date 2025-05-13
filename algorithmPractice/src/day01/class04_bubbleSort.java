package day01;

import java.util.Random;

public class class04_bubbleSort {
	public static void main(String[] args) {
		int testTime = 1000;
		int maxLength = 1000;
		int maxValue = 10000;
		Random rand = new Random();

	}

	public static void bubbleSort(int[] arr) {
		if(arr == null || arr.length < 2) {
			return;
		}
		for (int i = arr.length; i > 0; i--) {
			for(int j = 0; j < i; j++) {
				if(arr[j] > arr[j + 1]) {
					swap(arr,j,j+1);
				}
			}
		}
	}

	public static void swap (int[] arr, int i ,int j) {
		if (i != j) {
			arr[i] = arr[i] ^ arr[j];
			arr[j] = arr[i] ^ arr[j];
			arr[i] = arr[i] ^ arr[j];
		}
	}
}
