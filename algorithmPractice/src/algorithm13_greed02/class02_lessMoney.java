package algorithm13_greed02;


import java.util.PriorityQueue;

// 金条切割花费最小问题，本质就是哈夫曼编码问题
public class class02_lessMoney {
	public static void main(String[] args) {

	}

	// 方法一：暴力对数器=======================================================
	public static int lessMoney1(int[] arr) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		return process(arr,0);
	}

	public static int process(int[] arr, int pre) {
		if(arr.length == 1) {
			return pre;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				ans = Math.min(ans, process(copyAndMergeTwo(arr,i,j),pre + arr[i] + arr[j]));
			}
		}
		return ans;
	}

	// 将i和j位置的数合成新数后合并至原数组生成新集合
	private static int[] copyAndMergeTwo(int[] arr, int i, int j) {
		// 新数组，长度减少1
		int[] newArr = new int[arr.length - 1];
		int index = 0;

		// 遍历原数组，将除 i 和 j 外的元素放入新数组
		for (int k = 0; k < arr.length; k++) {
			if (k != i && k != j) {
				newArr[index++] = arr[k];
			}
		}

		// 将 i 和 j 合并后的新值放入新数组
		// index此时正好是最后一个位置
		newArr[index] = arr[i] + arr[j];

		return newArr;
	}


	// 方法二：小根堆解决=======================================================
	public static int lessMoney2(int[] arr) {
		// 定义小根堆
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		//放入堆中
		for (int i = 0; i < arr.length; i++) {
			pq.add(arr[i]);
		}
		// 总消费
		int sum = 0;
		// 当前要切的块大小
		int cur = 0;
		// 当堆中至少有两个元素
		while (pq.size() > 1) {
			// 拿出两个最小的数值作为目标切割
			cur = pq.poll() + pq.poll();
			// 计算此次切割花销
			sum += cur;
			// 切完继续入小根堆
			pq.add(cur);
		}
		return sum;
	}
}
