package day02;

public class class01_printOddTimesNum2 {
	public static void main(int[] arr) {
		int erro = 0;
		int result1 = 1;
		int result2;
		for(int i = 0;i < arr.length;i++){
			erro ^= arr[i];
		}
		int rightOne = erro & (-erro);
		for(int i = 0;i < arr.length;i++){
			if((rightOne & arr[i]) != 0){
				 result1 ^= arr[i];
			}
			result2 = erro^result1;
			System.out.println(result1+""+ result2);
		}
	}
}
