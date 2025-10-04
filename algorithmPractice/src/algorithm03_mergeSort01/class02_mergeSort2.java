package algorithm03_mergeSort01;

public class class02_mergeSort2 {
	public static void mergeSort2(int[] arr){
		if(arr!=null && arr.length<2){
			return;
		}
			int N = arr.length;
			int mergeSize = 1;

			while(mergeSize<N){
				int L = 0;
				while(L<N){
					int M = L+mergeSize-1;
					if(M>=N){
						break;
					}
					int R = Math.min(M+mergeSize,N-1);
					merge(arr,L,M,R);
					L = R+1;
				}
				if(mergeSize>N/2){
					break;
				}
				mergeSize <<= 1;
			}
	}

	public static void merge(int[] arr, int L, int M, int R) {
		int[] help = new int[R - L + 1];
		int i = 0;
		int p1 = L;
		int p2 = M + 1;
		while (p1 <= M && p2 <= R) {
			help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
		}
		// 要么p1越界了，要么p2越界了
		while (p1 <= M) {
			help[i++] = arr[p1++];
		}
		while (p2 <= R) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[L + i] = help[i];
		}
	}
}
