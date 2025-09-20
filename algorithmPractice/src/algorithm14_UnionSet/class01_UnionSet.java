package algorithm14_UnionSet;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class class01_UnionSet {
	public static void main(String[] args) {
		// 主方法，可用于测试并查集功能
	}

	// 包装节点类：用于包装原始数据
	public static class Node<V>{
		V value;  // 存储原始数据
		public Node(V value) {
			this.value = value;  // 构造函数初始化节点值
		}
	}

	// 并查集核心数据结构
	public static class UnionSet<V> {
		// 原始数据到包装节点的映射
		public HashMap<V,Node<V>> nodes;
		// 记录每个节点的父节点（用于查找代表节点）
		public HashMap<Node<V>,Node<V>> parents;
		// 记录每个代表节点对应的集合大小
		public HashMap<Node<V>,Integer> sizeMap;

		// 初始化并查集：传入数据列表
		public UnionSet(List<V> values){
			nodes = new HashMap<>();
			parents = new HashMap<>();
			sizeMap = new HashMap<>();
			// 为每个数据创建独立集合
			for(V cur : values){
				Node<V> node = new Node<>(cur);
				nodes.put(cur,node);    // 原始数据 -> 节点
				parents.put(node,node); // 初始时每个节点的父节点是自己
				sizeMap.put(node,1);    // 初始时每个集合大小为1
			}
		}

		// 查找根节点（带路径压缩优化）
		public Node<V> findFather(Node<V> cur){
			Stack<Node<V>> path = new Stack<>();  // 记录查找路径
			// 向上查找直到找到代表节点（父节点是自己的节点）
			while(cur != parents.get(cur)){
				path.push(cur);      // 记录路径
				cur = parents.get(cur); // 向上移动
			}
			// 路径压缩：将路径上所有节点直接指向代表节点
			while(!path.isEmpty()){
				parents.put(path.pop(),cur);
			}
			return cur;  // 返回代表节点
		}

		// 判断两个元素是否属于同一集合
		public boolean isSameSet(V a,V b){
			// 比较两个元素的代表节点是否相同
			return findFather(nodes.get(a)) == findFather(nodes.get(b));
		}

		// 合并两个元素所在的集合
		public void union(V a,V b){
			// 找到两个元素的代表节点
			Node<V> aHead = findFather(nodes.get(a));
			Node<V> bHead = findFather(nodes.get(b));

			// 如果代表节点不同才需要合并
			if(aHead != bHead){
				int aSetSize = sizeMap.get(aHead);  // 获取集合大小
				int bSetSize = sizeMap.get(bHead);

				// 确定较大和较小的集合
				Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
				Node<V> small = big == aHead ? bHead : aHead;

				// 小集合挂到大集合下
				parents.put(small,big);
				// 更新大集合的大小
				sizeMap.put(big,aSetSize+bSetSize);
				// 移除小集合的大小记录
				sizeMap.remove(small);
			}

		}
	}

}
