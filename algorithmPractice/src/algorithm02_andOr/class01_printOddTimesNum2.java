package algorithm02_andOr;

public class class01_printOddTimesNum2 {

	// 这里 main 方法参数为 int[] arr，表示输入的整数数组
	public static void main(int[] arr) {
		// erro 用来异或所有元素，最终结果是两个出现奇数次数字的异或值
		int erro = 0;
		// result1 用来存储分组后其中一个出现奇数次的数字，初始化为1（可任意值）
		int result1 = 1;
		// result2 用来存储另一个出现奇数次的数字（后面计算）
		int result2;

		// 第一次遍历：将数组所有元素异或起来，得到两个奇数次数字异或的结果,偶数次的数字异或为0,0^任何数=任何数
		for (int i = 0; i < arr.length; i++) {
			erro ^= arr[i];
		}

		// 找到 erro 二进制中最右侧为1的位
		// 该位表示两个奇数次数字在该位上不同，用于将数组元素分成两组
		int rightOne = erro & (-erro);

		// 第二次遍历：根据 rightOne 将数组元素分为两组，分别异或得到两个数字
		for (int i = 0; i < arr.length; i++) {
			// 判断当前元素在 rightOne 位置是否为1
			if ((rightOne & arr[i]) != 0) {
				// 如果是，将其异或到 result1 中
				result1 ^= arr[i];
			}
			// 计算另一个数字：两个奇数次数字异或值与 result1 异或得到另一个数字
			result2 = erro ^ result1;
			// 每遍历一个元素都会打印两个数字，这样打印太多次，不合理
			// 这个打印语句建议放到 for 外面执行，只打印一次
			System.out.println(result1 + " " + result2);
		}
	}
}
