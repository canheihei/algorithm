package algorithm10_tree02;
import simpleDataNode.TreeNode;

import java.util.HashMap;
import java.util.HashSet;

// 找到A和B的最近公共祖先（最近的交汇点）
public class class10_lowestAncestor {
	// 基础递归信息
	public static class Info {
		// 是否发现a
		public boolean findA;
		// 是否发现b
		public boolean findB;
		// 最近祖先节点
		public TreeNode ans;

		Info(boolean findA, boolean findB, TreeNode ans) {
			this.findA = findA;
			this.findB = findB;
			this.ans = ans;
		}
	}

	// 主函数
	public static TreeNode lowestAncestor2(TreeNode root, TreeNode p, TreeNode q) {
		return process(root,p,q).ans;
	}

	public static Info process(TreeNode root, TreeNode a, TreeNode b) {
		// 为空的话毋庸置疑
		if(root == null) {
			return new Info(false, false, null);
		}
		// 递归一般都后序遍历
		// 找左信息
		Info leftInfo = process(root.left,a,b);
		// 找右信息
		Info rightInfo = process(root.right,a,b);
		// 如果a节点就是根节点，或者左右任意一棵子树找到了a
		boolean findA = (root == a) || leftInfo.findA || rightInfo.findA;
		// 如果b节点就是根节点，或者左右任意一棵子树找到了b
		boolean findB = (root == b) || leftInfo.findB || rightInfo.findB;

		TreeNode ans = null;
		if(leftInfo.ans != null) {
			ans = leftInfo.ans;
		}else if(rightInfo.ans != null) {
			ans = rightInfo.ans;
		} else {
			if(findA && findB) {
				ans = root;
			}
		}

		return new Info(findA, findB, ans);
	}

	// 充当对数器的解法
	public static TreeNode lowestAncestor1(TreeNode head, TreeNode o1, TreeNode o2) {
		// 如果树为空，不存在
		if(head == null) {
			return null;
		}

		// key的父节点是value
		HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
		// 根节点没有父亲
		parentMap.put(head, null);
		// 生成所有节点的父节点表
		fillParentMap(head,parentMap);
		// 存放每个节点的所有祖先
		HashSet<TreeNode> o1Set = new HashSet<>();
		// 从o1开始找他的所有祖先
		TreeNode cur = o1;
		o1Set.add(cur);
		// 放入所有当前节点的所有祖先
		while(parentMap.get(cur) != null) {
			// 将当前节点变为父亲节点
			cur = parentMap.get(cur);
			o1Set.add(cur);
		}

		cur = o2;
		while(parentMap.get(cur) != null) {
			cur = parentMap.get(cur);
		}
		return cur;
	}

	private static void fillParentMap(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
		if(head.left != null) {
			parentMap.put(head.left, head);
			fillParentMap(head.left, parentMap);
		}

		if(head.right != null) {
			parentMap.put(head.right, head);
			fillParentMap(head.right, parentMap);
		}
	}
}
