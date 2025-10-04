package algorithm12_greed02;

import java.util.Arrays;
import java.util.Random;

// 会议室宣讲安排问题
public class class01_bestArrange {
	public static void main(String[] args) {
		int programSize = 12;
		int timeMax = 20;
		int timeTimes = 1000000;
		for (int i = 0; i < timeTimes; i++) {
			Program[] programs = generatePrograms(programSize,timeMax);
			if(bestArrange1(programs) != bestArrange2(programs)) {
				System.out.println("omg");
			}
		}
		System.out.println("finish");
	}

	// 随机生成 programSize 个会议，每个会议的开始时间和结束时间在 [0, timeMax] 范围内，且开始 < 结束
	public static Program[] generatePrograms(int programSize, int timeMax) {
		Random random = new Random();
		Program[] programs = new Program[programSize];
		int count = 0;

		while (count < programSize) {
			// [0, timeMax - 1]
			int start = random.nextInt(timeMax);
			// 保证 end > start
			int end = start + 1 + random.nextInt(timeMax - start);
			programs[count++] = new Program(start, end);
		}

		return programs;
	}


	// 会议信息类
	public static class Program {
		// 开始时间
		public int start;
		// 结束时间
		public int end;

		public Program(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	// 方法一：暴力对数器===========================================================
	public static int bestArrange1(Program[] programs) {
		// 如果会议列表为空，毋庸置疑
		if (programs == null || programs.length == 0) {
			return 0;
		}
		return process(programs,0,0);
	}

	public static int process(Program[] programs, int done, int timeLine) {
		// 没有会议可以安排
		if(programs.length == 0){
			// 没有会议可选，返回已安排数量
			return done;
		}
		int max = done;
		for (int i = 0; i < programs.length; i++) {
			if(timeLine <= programs[i].start) {
				// 如果当前会议 i 的开始时间 >= 当前时间线，可以选择参加
				Program[] next = copyButExcept(programs,i);
				// 尝试参加 i 号会议，递归求后续最优方案
				max = Math.max(max,process(next,done+1,programs[i].end));
			}
			// 否则，跳过 i 号会议，尝试其他会议（即继续循环）
		}
		return max;
	}

	public static Program[] copyButExcept(Program[] programs, int i) {
		Program[] ans = new Program[programs.length - 1];
		int index = 0;
		for(int k = 0; k < programs.length; k++) {
			if(k != i){
				ans[index++] = programs[k];
			}
		}
		return ans;
	}

	// 方法二===========================================================

	public static int bestArrange2(Program[] programs) {
		// 按照结束时间升序排序会议
		Arrays.sort(programs, (o1, o2) -> o1.end - o2.end);
		// 记录当前安排的会议最晚结束时间
		int timeLine = 0;
		// 记录安排的会议场数
		int result = 0;
		// 遍历每个会议
		for (int i = 0; i < programs.length; i++) {
			// 将最早开始的并且能安排到当前会议后的会议安排上
			if(timeLine <= programs[i].start) {
				result++;
				timeLine = programs[i].end;
			}
		}
		return result;
	}
}
