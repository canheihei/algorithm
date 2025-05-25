package algorithm12_tree02;

import java.util.ArrayList;
import java.util.List;


public class class04_EncodeNaryTreeToBinaryTree {
	public static class Node {
		public int val;
		public List<Node> children;

		public Node(){

		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, List<Node> _children){
			val = _val;
			children = _children;
		}
	}

	public static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode (int x){
			val = x;
		}
	}

	class Codec {
		//多叉树序列化为二叉树
		public TreeNode encode(Node root){
			if (root == null) {
				return null;
			}
			TreeNode head = new TreeNode(root.val);
			head.left = en(root.children);
			return head;
		}

		private TreeNode en(List<Node> children) {
			TreeNode cur = null;
			TreeNode head = null;
			for (Node child : children) {
				TreeNode tNode = new TreeNode(child.val);
				if(head == null){
					head = tNode;
				}else {
					head.right = tNode;
				}
				cur = tNode;
				cur.left = en(child.children);
			}
			return head;
		}

		//二叉树逆序列化为多叉树
		public List<Node> de(TreeNode root){
			List<Node> children = new ArrayList<>();
			while(root != null) {
				Node cur = new Node(root.val,de(root.left));
				children.add(cur);
				root = root.right;
			}
			return children;
		}
	}
}
