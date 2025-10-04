package algorithm10_tree02;
import simpleDataNode.TreeNode;

// 套路判断是不是完全二叉树
public class class09_IsCBT2 {
	public static void main(String[] args) {

	}

	// 主函数
	public static boolean isCBT(TreeNode head) {
		return process(head).isCBT;
	}

	// 套路基本规则信息
	public static class Info {
		// 是否是满二叉树
		Boolean isFull;
		// 是否是完全二叉树
		Boolean isCBT;
		// 树的高度
		int height;
		// 全参构造函数
		Info(int height, Boolean isFull, Boolean isCBT) {
			this.isFull = isFull;
			this.isCBT = isCBT;
			this.height = height;
		}
	}

	public static Info process(TreeNode head) {
		// 如果为空，则判断为是完全二叉树
		if (head == null) {
			return new Info(0, true, true);
		}
		// 获取左子树信息
		Info leftInfo = process(head.left);
		// 获取右子树信息
		Info rightInfo = process(head.right);
		// 获取高度
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		// 判断是否为满二叉树
		Boolean isFull = leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height;
		// 定义完全二叉树标志
		Boolean isCBT = false;
		//一共四个情况
		//1.左右子树都为满二叉树并且高度相等
		if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
			isCBT = true;
		}
		//2.左子树为完全二叉树，右子数为满二叉树，并且左子树高度等于右子数高度+1
		if (leftInfo.isCBT && rightInfo.isFull && leftInfo.height == rightInfo.height+1) {
			isCBT = true;
		}
		//3.左右子树都为满二叉树，并且左子树高度等于右子数高度+1
		if(leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height+1) {
			isCBT = true;
		}
		//4.左子树为满二叉树，右子树为完全二叉树，并且左子树高度等于右子数高度
		if(leftInfo.isFull && rightInfo.isCBT && leftInfo.height == rightInfo.height) {
			isCBT = true;
		}
		return new Info(height, isFull, isCBT);
	}
}
