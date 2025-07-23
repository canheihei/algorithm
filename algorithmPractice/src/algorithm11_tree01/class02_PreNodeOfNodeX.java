package algorithm11_tree01;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import algorithm00_common.TreeNode;

// X先序遍历之前的节点定义为集合A,
// X后序遍历之后的节点定义为集合B,
// 则 A∩B得到的解释是且仅是X的祖先节点
public class class02_PreNodeOfNodeX {
	public static void main(String[] args) {
		Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3));
		Set<Integer> set2 = new HashSet<>(Arrays.asList(3, 4, 5));

		// 计算并集
		Set<Integer> union = new HashSet<>(set1);
		union.addAll(set2); // union = [1, 2, 3, 4, 5]
		System.out.println(union);

		// 计算交集
		Set<Integer> diff = new HashSet<>(set1);
		diff.removeAll(set2); // diff = [1, 2]
		System.out.println(diff);
	}
	public static TreeNode findPreNodeOfNodeX(TreeNode node) {
		

		return findPreNodeOfNodeX(node.left);
	}
}
