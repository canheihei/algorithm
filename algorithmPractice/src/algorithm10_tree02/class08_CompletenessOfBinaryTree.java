package algorithm10_tree02;
import simpleDataNode.TreeNode;

public class class08_CompletenessOfBinaryTree {
	public static int MAXN = 101;

	public static TreeNode[] queue = new TreeNode[MAXN];

	public static int l, r;

	public static boolean isCompleteTree(TreeNode h){
		if(h == null){
			return true;
		}
		l = r = 0;
		queue[r++] = h;
		boolean leaf = false;
		while(r > l){
			h = queue[l++];
			if((h.left != null && h.right == null) || (leaf && (h.left != null) || h.right != null)){
				return false;
			}

			if(h.left != null){
				queue[r++] = h.left;
			}

			if(h.right != null){
				queue[r++] = h.right;
			}
			if (h.left == null || h.right == null) {
				leaf = true;
			}
		}

		return true;
	}
}
