package day01;


public class class02_insertionSort {
	public static void main(String[] args) {
		Integer[] arr = new Integer[10];
		arr[0] = 1;
		arr[1] = 213;
		arr[2] = 12;
		arr[3] = 11;
		arr[4] = 1223;
		arr[5] = 2131;
		arr[6] = 2213;
		arr[7] = 12231;
		arr[8] = 22131;
		arr[9] = 12231;
		insertionSort(arr);
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}

	static void insertionSort(Integer[] arr){
		if(arr == null || arr.length==0){
			return ;
		}
		for(int i = 1;i < arr.length;i++){
			for(int j = i; j > 0;j--){
				if(arr[j]<arr[j-1]){
					swap(arr,j);
				}
			}
		}
	}

	private static void swap(Integer[] arr, Integer integer) {
		arr[integer] = arr[integer]^arr[integer-1];
		arr[integer-1] = arr[integer]^arr[integer-1];
		arr[integer] = arr[integer]^arr[integer-1];
	}
}
