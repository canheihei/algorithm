package algorithm11_greed01;

import java.util.*;

// 拼接最小字典序的字符串
public class class01_lowestString {
	public static void main(String[] args) {
		int arrLen = 6;      // 每个字符串数组最多6个元素
		int strlen = 5;      // 每个字符串最大长度为5
		int testTimes = 100000; // 测试次数
		String base = "abcde";  // 字符构成范围

		System.out.println("测试开始...");

		for (int i = 0; i < testTimes; i++) {
			String[] arr1 = generateRandomStringArray(arrLen, strlen, base);
			String[] arr2 = Arrays.copyOf(arr1, arr1.length);

			String ans1 = lowestString1(arr1); // 暴力解法
			String ans2 = lowestString2(arr2); // 贪心解法

			if (!ans1.equals(ans2)) {
				System.out.println("出错了！");
				System.out.println("arr: " + Arrays.toString(arr1));
				System.out.println("暴力解: " + ans1);
				System.out.println("贪心解: " + ans2);
				return;
			}
		}

		System.out.println("测试结束，所有样例通过！");
	}

	// 生成一个随机字符串数组
	public static String[] generateRandomStringArray(int arrLen, int strMaxLen, String base) {
		int len = (int) (Math.random() * arrLen) + 1; // 随机数组长度 [1, arrLen]
		String[] arr = new String[len];
		for (int i = 0; i < len; i++) {
			arr[i] = generateRandomString(strMaxLen, base);
		}
		return arr;
	}

	// 生成一个最大长度为strMaxLen的随机字符串
	public static String generateRandomString(int strMaxLen, String base) {
		int len = (int) (Math.random() * strMaxLen) + 1; // 随机字符串长度 [1, strMaxLen]
		char[] chars = new char[len];
		for (int i = 0; i < len; i++) {
			chars[i] = base.charAt((int) (Math.random() * base.length()));
		}
		return new String(chars);
	}


	/**
	 * PriorityQueue → 堆结构 → 快速找最值 → 可重复 → 无序遍历；
	 * TreeSet → 红黑树结构 → 有序集合 → 自动去重 → 有序遍历。
	 */
	// 方法一：用作对数器的暴力解法
	public static String lowestString1(String[] strs) {
		if (strs == null || strs.length == 0){
			return null;
		}
		TreeSet<String> ans = process(strs);
		return ans.size() == 0 ? "" : ans.first();
	}

	// 返回字符串数组 strs 中所有字符串的全排列，返回所有可能的结果
	public static TreeSet<String> process(String[] strs) {
		// 创建用于存储所有排列结果的列表
		TreeSet<String> ans = new TreeSet<>();

		// 边界条件：空数组，返回空列表
		if (strs.length == 0) {
			ans.add("");
			return ans;
		}

		// 遍历当前字符串数组的每一个元素，尝试将它作为排列的第一个字符
		for (int i = 0; i < strs.length; i++) {
			// 当前作为第一个位置的字符串
			String first = strs[i];

			// 构造一个新数组，去除下标为 i 的元素，相当于递归时传入剩下的字符串
			String[] nexts = removeIndexString(strs, i);

			// 递归求解剩下字符串的所有全排列
			TreeSet<String> next = process(nexts);

			// 将当前字符串 first 与剩下字符串的每一种排列组合，拼接后加入结果列表
			for (String cur : next) {
				ans.add(first + cur);
			}
		}

		return ans;
	}


	// 数组删除指定索引的元素
	public static String[] removeIndexString(String[] arr, int index) {
		int N = arr.length;
		String[] ans = new String[N-1];
		int ansIndex = 0;
		for (int i = 0; i < N; i++) {
			if(i != index){
				ans[ansIndex++] = arr[i];
			}
		}
		return ans;
	}

	// 方法二,利用传递性解决=================================================================
	// 前置知识：字符串拼接具有传递性，无非就是字母变数字，类比十六进制的表示
	public static String lowestString2(String[] strs) {
		// 毋庸置疑
		if (strs == null || strs.length == 0) {
			return "";
		}

		Arrays.sort(strs, new MyComparator());
		String res = "";

		// 排序完后，直接全部拼接即为字典序最小的字符串
		for (int i = 0; i < strs.length; i++) {
			res += strs[i];
		}
		return res;
	}

	// 自定义比较器，a-b<0,那么a排前面（字典序升序），a-b>0,那么a排后面（字典序降序）
	public static class MyComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			//a+b为str1，b+a为str2
			// 拼接后，谁的字符串字典序小，谁就放前面
			return (a+b).compareTo(b+a);   // str1 - str2 < 0
		}
	}
}
