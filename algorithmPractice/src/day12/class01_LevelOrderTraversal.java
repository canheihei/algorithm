package day12;

import java.util.*;

public class class01_LevelOrderTraversal {
	public static class TreeNode {
		public int val;
		public TreeNode left;
		public TreeNode right;
	}

	// 层序遍历方法一
	public static List<List<Integer>> levelOrder1(TreeNode root) {
		List<List<Integer>> ans = new ArrayList<>();
		if(root != null){
			Queue<TreeNode> queue = new LinkedList<>();
			HashMap<TreeNode,Integer> level = new HashMap<>();
			queue.add(root);
			level.put(root,0);
			while(!queue.isEmpty()){
				TreeNode cur = queue.poll();
				int curSize = level.get(cur);
				if(curSize == ans.size()) {
					ans.add(new ArrayList<>());
				}
				ans.get(curSize).add(cur.val);

				if(cur.left != null) {
					level.put(cur.left,curSize + 1);
					queue.add(cur.left);
				}
				if(cur.right != null) {
					level.put(cur.right,curSize + 1);
					queue.add(cur.right);
				}
			}
		}
		return ans;
	}

	public static int l,r;
	public static int MAXN =2001;
	public static TreeNode[] queue = new TreeNode[MAXN];

	// 层序遍历方法二
	public static List<List<Integer>>levelOrder2(TreeNode root){
		List<List<Integer>> ans = new ArrayList<>();
		if(root != null){
			l = r = 0;
			queue[r++] = root;
			while(r > l){
				int size = r - l;
				ArrayList<Integer> arr = new ArrayList<>();
				for (int i = 0; i < size; i++) {
					TreeNode cur = queue[i];
					arr.add(cur.val);
					if(cur.left != null){
						queue[l++] = cur.left;
					}
					if(cur.right != null){
						queue[l++] = cur.right;
					}
				}
				ans.add(arr);
			}
		}
		return ans;
	}
}
