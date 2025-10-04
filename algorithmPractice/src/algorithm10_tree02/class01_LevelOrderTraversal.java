package algorithm10_tree02;

import java.util.*;

import simpleDataNode.TreeNode;

// 二叉树的层序遍历（两种方法）
public class class01_LevelOrderTraversal {

	// 层序遍历方法一
	public static List<List<Integer>> levelOrder1(TreeNode root) {
		// 最终结果，每一层是一个 List<Integer>，ans.size()即为当前层数
		List<List<Integer>> ans = new ArrayList<>();

		// 如果 root 不为空，开始遍历
		if (root != null) {
			// 用于层序遍历的队列
			Queue<TreeNode> queue = new LinkedList<>();

			// 存储每个节点对应的层级（level）
			HashMap<TreeNode, Integer> level = new HashMap<>();

			// 将根节点加入队列，并设置其层级为 0
			queue.add(root);
			level.put(root, 0);

			// 队列不为空时，继续遍历
			while (!queue.isEmpty()) {
				// 取出当前节点
				TreeNode cur = queue.poll();

				// 获取当前节点的层级
				int curSize = level.get(cur);

				// 如果该层级还没有列表，就先创建一个新列表用来存储该层节点
				if (curSize == ans.size()) {
					ans.add(new ArrayList<>());
				}

				// 将当前节点的值加入对应层级的列表中
				ans.get(curSize).add(cur.val);

				if (cur.left != null) {
					// 设置其层级为当前层 + 1
					level.put(cur.left, curSize + 1);
					// 并且入队
					queue.add(cur.left);
				}

				// 如果右子节点存在，入队并设置其层级为当前层 + 1
				if (cur.right != null) {
					// 设置其层级为当前层 + 1
					level.put(cur.right, curSize + 1);
					// 并且入队
					queue.add(cur.right);
				}
			}
		}

		// 返回层序遍历结果
		return ans;
	}

	// 层序遍历方法二
	// 全局变量定义：左指针 l，右指针 r，以及静态队列数组 queue
	public static int l, r;
	public static int MAXN = 2001;
	public static TreeNode[] queue = new TreeNode[MAXN];

	// 使用数组模拟队列的层序遍历方法
	public static List<List<Integer>> levelOrder2(TreeNode root) {
		// 最终结果列表
		List<List<Integer>> ans = new ArrayList<>();

		// 如果根节点不为空，开始遍历
		if (root != null) {
			// 初始化队列头尾指针
			l = r = 0;

			// 根节点入队
			queue[r++] = root;

			// 队列不为空
			while (r > l) {
				// 当前层的节点数量
				int size = r - l;

				// 存放当前层的值
				ArrayList<Integer> arr = new ArrayList<>();

				// 遍历当前层的所有节点
				for (int i = 0; i < size; i++) {
					// 从 l 指针开始取元素（注意：这里 `i` 没用 l，需要修正）
					TreeNode cur = queue[l++];

					// 加入当前层的结果
					arr.add(cur.val);

					// 左右子节点入队
					if (cur.left != null) {
						queue[r++] = cur.left;
					}
					if (cur.right != null) {
						queue[r++] = cur.right;
					}
				}

				// 把当前层加入最终结果
				ans.add(arr);
			}
		}

		// 返回层序遍历结果
		return ans;
	}

}
