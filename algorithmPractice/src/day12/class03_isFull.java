package day12;

public class class03_isFull {
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	public static class Info {
		int height;
		int nodes;
		Info(int h, int n){
			height = h;
			nodes = n;
		}

	}
	public boolean isFull(TreeNode root) {
		if(root == null) {
			return true;
		}
		Info all = process(root);
		return (1 << all.height) - 1 == all.nodes;
	}

	public static Info process(TreeNode root) {
		if(root == null) {
			return new Info(0,0);
		}
		Info leftInfo = process(root.left);
		Info rightInfo = process(root.right);
		int leftHeight = leftInfo.height;
		int rightHeight = rightInfo.height;
		int height = Math.max(leftHeight,rightHeight) + 1;
		int nodes = leftInfo.nodes + rightInfo.nodes + 1;
		return new Info(height,nodes);
	}
}
