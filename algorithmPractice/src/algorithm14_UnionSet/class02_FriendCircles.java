package algorithm14_UnionSet;


// 有 n 个城市，其中一些彼此相连，另一些没有相连。
// 如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
// 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
public class class02_FriendCircles {
	public static void main(String[] args) {

	}


	public static int findCircleNum(int[][] M) {
		// 获取
		int n = M.length;
		// 初始化并查集
		UnionFind unionFind = new UnionFind(n);
		// 遍历矩阵
		for (int i = 0; i < n; i++) {
			// 遍历上三角矩阵
			for (int j = 0; j < n; j++) {
				// 如果是朋友关系，合并两个集合
				if (M[i][j] == 1) {
					unionFind.union(i, j);
				}
			}
		}
		return unionFind.sets();
	}

	// 并查集类
	public static class UnionFind {
		// 记录每个节点的父节点
		private int[] parent;
		// 记录每个集合的大小（每个样本大小）
		// size[i] = k ,如果i是代表节点，size[i]才有意义，否则无意义
		private int[] size;
		// 辅助结构，用来扁平化路径上的节点
		private int[] help;
		// 记录集合的数量
		private int sets;
		// 初始化并查集
		public UnionFind(int N) {
			parent = new int[N];
			size = new int[N];
			help = new int[N];
			sets = N;
			// 初始化每个节点的父节点为自己，每个集合大小为1
			for (int i = 0; i < N; i++) {
				parent[i] = i;
				size[i] = 1;
			}
		}

		// 找该节点的代表节点
		// 期间会把路径上的所有节点都扁平化
		private int find(int i){
			//定义一个栈的指针
			int hi = 0;
			// 如果i不是代表节点，就一直向上找
			while (parent[i] != i) {
				help[hi++] = i;
				i = parent[i];
			}// 此时i就是代表节点
			// 路劲上的所有节点扁平化
			for(hi--;hi>=0;hi--){
				parent[help[hi]] = i;
			}
			return i;
		}

		// 合并两个集合
		public void union(int i, int j) {
			// 找到两个节点的代表节点
			int fi = find(i);
			int fj = find(j);
			// 如果两个节点不在同一个集合中，就合并
			if (fi != fj) {
				// 小集合挂到大集合上
				if (size[fi] >= size[fj]) {
					parent[fj] = fi;
					size[fi] += size[fj];
				} else {
					parent[fi] = fj;
					size[fj] += size[fi];
				}
				// 集合数量减一
				sets--;
			}
		}
		public int sets(){
			return sets;
		}
	}
}
