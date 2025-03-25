package day12;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class class02_TreeMaxLevelNode {
	public static class TreeNode{
		public int val ;
		public TreeNode left;
		public TreeNode right;
	}

	public static int MAXN = 3001;

	public static TreeNode[] nq = new TreeNode[MAXN];

	public static int[] iq = new int[MAXN];

	public static int l, r;

	public static int maxWidthMap(TreeNode head){
		if(head == null){
			return 0;
		}
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(head);
		HashMap<TreeNode,Integer> levelMap = new HashMap<>();
		levelMap.put(head,1);
		int curLevel = 1;
		int curLevelNodes = 0;
		int max = 0;
		while(!queue.isEmpty()){
			TreeNode cur = queue.poll();
			int curNodeLevel = levelMap.get(cur);
			if(curNodeLevel == curLevel){
				curLevelNodes++;
			}else{
				max = Math.max(max,curLevelNodes);
				curLevel++;
				curLevelNodes = 1;
			}
			if(cur.left != null){
				queue.add(cur.left);
				levelMap.put(cur.left,curLevel + 1);

			}
			if(cur.right != null){
				queue.add(cur.right);
				levelMap.put(cur.right,curLevel + 1);
			}
		}
		return Math.max(max,curLevelNodes);
	}

	public static int maxWidthWithNoMap(TreeNode head){
		if(head == null){
			return 0;
		}
		int max = 0;
		int curLevelNodes = 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(head);
		TreeNode curEnd = head;
		TreeNode nextEnd = null;
		while(!queue.isEmpty()){
			TreeNode cur = queue.poll();
			if(cur.left != null){
				queue.add(cur.left);
				nextEnd = cur.left;
			}
			if(cur.right != null){
				queue.add(cur.right);
				nextEnd = cur.right;
			}
			curLevelNodes++;
			if(cur == curEnd){
				max = Math.max(max,curLevelNodes);
				curLevelNodes = 0;
				curEnd = nextEnd;
			}
		}
		return max;
	}
}
