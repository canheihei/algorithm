package algorithm12_tree02;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import algorithm00_common.TreeNode;

// 树的最大宽度
public class class02_TreeMaxLevelNode {
	public static int MAXN = 3001;

	public static TreeNode[] nq = new TreeNode[MAXN];

	public static int[] iq = new int[MAXN];

	public static int l, r;

	// 方法1：使用 HashMap 记录每个节点所在的层数，来求最大宽度
	public static int maxWidthMap(TreeNode head){
		if(head == null){
			return 0;
		}
		// 使用队列进行层序遍历
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(head);
		// 记录每个节点所在的层数（key 为节点，value 为层数）
		HashMap<TreeNode,Integer> levelMap = new HashMap<>();
		// 根节点在第 1 层（起始层数从1开始）
		levelMap.put(head,1);
		int curLevel = 1;		// 当前层数
		int curLevelNodes = 0;	// 当前层节点数量
		int max = 0;			// 最大宽度
		while(!queue.isEmpty()){
			TreeNode cur = queue.poll();		// 当前处理的节点
			int curNodeLevel = levelMap.get(cur);		// 当前节点的层级
			// 判断是否仍在当前层
			if(curNodeLevel == curLevel){
				curLevelNodes++;
			}else{
				// 层数切换时更新最大宽度
				max = Math.max(max,curLevelNodes);
				curLevel++;		// 切换到下一层
				curLevelNodes = 1;	// 当前节点是新层的第一个
			}
			// 将左右孩子加入队列，并标记其层级（原层级加1）
			if(cur.left != null){
				queue.add(cur.left);
				levelMap.put(cur.left,curLevel + 1);

			}
			if(cur.right != null){
				queue.add(cur.right);
				levelMap.put(cur.right,curLevel + 1);
			}
		}
		// 最后一层可能没有触发更新，补一次 max 比较
		return Math.max(max,curLevelNodes);
	}

	// 方法2：不使用 HashMap，使用标记节点来判断层切换
	public static int maxWidthWithNoMap(TreeNode head){
		if(head == null){
			return 0;
		}
		int max = 0;
		int curLevelNodes = 0;
		Queue<TreeNode> queue = new LinkedList<>();
		queue.add(head);
		// curEnd 表示当前层的最右节点，nextEnd 表示下一层最右节点
		TreeNode curEnd = head;
		TreeNode nextEnd = null;
		while(!queue.isEmpty()){
			TreeNode cur = queue.poll();
			// 将左右孩子加入队列，并更新 nextEnd 为最新的孩子
			if(cur.left != null){
				queue.add(cur.left);
				nextEnd = cur.left;
			}
			if(cur.right != null){
				queue.add(cur.right);
				nextEnd = cur.right;
			}
			curLevelNodes++;		// 统计当前层的节点数
			// 到达当前层的末尾节点，准备切换层
			if(cur == curEnd){
				max = Math.max(max, curLevelNodes); // 更新最大宽度
				curLevelNodes = 0;                  // 重置当前层计数器
				curEnd = nextEnd;                   // 更新当前层的最右节点
			}
		}
		return max;
	}
}
