package algorithm13_greed02;

import java.util.Random;

public class class04_minLight {
	public static void main(String[] args) {
		// 测试次数
		int testTimes = 1000;
		// 最大字符串长度
		int maxLength = 20;
		Random random = new Random();

		System.out.println("测试开始...");
		for (int i = 0; i < testTimes; i++) {
			// 生成随机测试用例
			int len = random.nextInt(maxLength) + 1;
			char[] chars = new char[len];
			for (int j = 0; j < len; j++) {
				chars[j] = random.nextBoolean() ? '.' : 'X';
			}
			String road = new String(chars);

			// 使用贪心算法
			int greedyResult = minLight(road);
			// 使用暴力方法
			int bruteForceResult = minLightBruteForce(road);

			// 比较结果
			if (greedyResult != bruteForceResult) {
				System.out.println("测试失败!");
				System.out.println("测试用例: " + road);
				System.out.println("贪心结果: " + greedyResult);
				System.out.println("暴力结果: " + bruteForceResult);
				return;
			}
		}
		System.out.println("测试通过!");
	}

	public static int minLight(String road) {
		// 将该字符串转为多字符数组
		char[] str = road.toCharArray();
		// 定义灯的数量
		int light = 0;
		// 定义指针
		int i = 0;
		// 遍历road
		while( i < str.length) {
			// 开始小贪
			// 如果当前是墙，无需照亮
			if(str[i] == 'X') {
				i++;
			}else{
				// 那么不管走哪个分支，灯数量都会要增加
				light++;
				// 边界条件：如果 i+1 已经是最后一个位置，则不需要再看后面
				if(i + 1==str.length){
					break;
				} else{
					// 如果 i+1 是墙，说明只有当前位置是空地，灯只照 i，跳过 i 和 i+1（墙）
					if(str[i + 1] == 'X'){
						i = i + 2;
					}else {
						// 所以可以跳过 i, i+1, i+2
						i = i + 3;
					}
				}
			}
		}
		// 返回需要的最小灯数
		return light;
	}

	// 暴力方法作为对数器
	public static int minLightBruteForce(String road) {
		char[] str = road.toCharArray();
		// 尝试所有可能的灯放置方案
		return process(str, 0, new boolean[str.length]);
	}

	// 递归函数，尝试每个位置放或不放灯
	private static int process(char[] str, int index, boolean[] lights) {
		// 如果已经处理完所有位置
		if (index == str.length) {
			// 检查是否所有的'.'都被照亮
			for (int i = 0; i < str.length; i++) {
				if (str[i] == '.') {
					// 检查当前位置或相邻位置是否有灯
					boolean illuminated = false;
					// 检查当前位置
					if (lights[i]) {
						illuminated = true;
					}
					// 检查左边位置
					if (i > 0 && lights[i - 1]) {
						illuminated = true;
					}
					// 检查右边位置
					if (i < str.length - 1 && lights[i + 1]) {
						illuminated = true;
					}
					// 如果没有被照亮，则当前方案无效
					if (!illuminated) {
						return Integer.MAX_VALUE;
					}
				}
			}
			// 计算使用的灯数
			int count = 0;
			for (boolean light : lights) {
				if (light) {
					count++;
				}
			}
			return count;
		}

		// 如果当前位置是墙，不能放灯
		if (str[index] == 'X') {
			return process(str, index + 1, lights);
		}

		// 尝试在当前位置不放灯
		int p1 = process(str, index + 1, lights);

		// 尝试在当前位置放灯
		lights[index] = true;
		int p2 = process(str, index + 1, lights);
		// 恢复状态，回溯
		lights[index] = false;

		return Math.min(p1, p2);
	}
}
