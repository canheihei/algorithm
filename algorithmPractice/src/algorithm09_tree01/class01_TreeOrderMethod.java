package algorithm09_tree01;
import simpleDataNode.TreeNode;

import java.util.Stack;

// 二叉树的遍历方式
public class class01_TreeOrderMethod {
	public static void main(String[] args) {

	}

	// 递归方式先序
	public static void preOrder(TreeNode root) {
		if (root == null) {
			return;
		}
		System.out.print(root.val + " ");
		preOrder(root.left);
		preOrder(root.right);
	}

	// 非递归方式先序
	public static void preOrder2(TreeNode root) {
		if (root == null) {
			return;
		}
		// 定义栈
		Stack<TreeNode> stack = new Stack<>();
		// 将根节点放入栈中
		stack.push(root);
		//出入栈操作，进行非递归先序遍历
		while (!stack.isEmpty()) {
			TreeNode node = stack.pop();
			//根
			System.out.print(node.val + " ");
			//先右节点入栈，出栈时才能保证先序的根左右顺序（FILO)
			if (node.left != null) {
				stack.push(node.right);
			}
			if (node.right != null) {
				stack.push(node.left);
			}
		}

	}
}
