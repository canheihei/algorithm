package algorithm12_greed02;

import java.util.Comparator;
import java.util.PriorityQueue;

// 项目安排利润最大化问题（IPO）
// 在初始资金 W 的条件下，从多个项目中最多选择 K 个项目，
// 每个项目有启动所需资金 Capital[i] 和完成后利润 Profits[i]，
// 要求选择的项目在启动时资金充足，并使最终资金最大。
public class class03_IPO {
	public static void main(String[] args) {

	}

	public static int findMaximizedCapital(int K,int W, int[] Profits, int[] Capital) {
		// 小根堆：按项目所需启动资金 c 从小到大排序
		PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
		// 大根堆：按项目利润 p 从大到小排序
		PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
		// 所有项目先加入 minCostQ，等待资金满足后再考虑是否执行
		for (int i = 0; i < Profits.length; i++) {
			minCostQ.add(new Program(Profits[i],Capital[i]));
		}
		// 最多做 K 个项目
		for (int i = 0; i < K; i++) {
			// 把所有当前能做的项目（资金 W 足够的）移入 maxProfitQ（按利润排序）
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			// 如果没有可以做的项目，提前结束，返回当前资金
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			// 选择利润最大的项目做，资金增加
			W += maxProfitQ.poll().p;
		}
		// 返回最终资金
		return W;
	}

	// 定义项目结构体，p 表示利润，c 表示启动资金
	public static class Program {
		public int p;
		public int c;
		public Program(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	// 小根堆比较器：按启动资金升序
	public static class MinCostComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.c - o2.c;
		}
	}

	// 大根堆比较器：按利润降序
	public static class MaxProfitComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o2.p - o1.p;
		}
	}
}
