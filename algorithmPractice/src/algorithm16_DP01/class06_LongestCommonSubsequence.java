package algorithm16_DP01;

/**
 * 题目：计算两个字符串的最长公共子序列（LCS）长度。
 * 定义：在不改变字符相对次序的前提下，从两个字符串中各删除若干（或不删）字符后能得到的公共序列，长度最大者即为 LCS。
 * 说明：只返回长度，不需要返回具体的序列。
 */
public class class06_LongestCommonSubsequence {
    /**
     * 方法一：暴力递归
     * 思路：
     * 1. 从两个字符串的最后位置开始（i, j）尝试。
     * 2. 对于每个位置，可能性包括：
     * - 不要 str1[i]
     * - 不要 str2[j]
     * - 若 str1[i] == str2[j]，则把该字符纳入，并考察 (i-1, j-1)
     * 3. 利用递归穷举所有可能，取最大值。
     * 边界：
     * - 若某一侧到达首字符（i==0 或 j==0），需单独处理。
     * 缺点：存在大量重复子问题，时间复杂度呈指数级。
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 最长公共子序列长度
     */
    public static int longestCommonSubsequence1(String s1, String s2) {
        // 前置条件校验
        if (s1 == null || s2 == null || s1.isEmpty() || s2.isEmpty()) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process1(str1, str2, str1.length - 1, str2.length - 1);
    }

    /**
     * 递归过程函数（辅助函数）
     * 作用：返回 str1[0...i] 与 str2[0...j] 的最长公共子序列长度。
     * 逻辑：
     * 1. 明确 base case：
     * - (i==0 && j==0)：比较首字符是否相等
     * - (i==0)：判断 str1[0] 是否出现在 str2[0...j]
     * - (j==0)：判断 str2[0] 是否出现在 str1[0...i]
     * 2. 一般位置：
     * - p1：忽略 str1[i]
     * - p2：忽略 str2[j]
     * - p3：若字符相等则取 1 + process(i-1, j-1)
     * - 取三者最大值
     * 注意：当字符不等时，p3 设为极小值防止被选中。
     *
     * @param str1 字符数组形式的第一个字符串
     * @param str2 字符数组形式的第二个字符串
     * @param i    当前考察的 str1 的下标
     * @param j    当前考察的 str2 的下标
     * @return 最长公共子序列长度
     */
    private static int process1(char[] str1, char[] str2, int i, int j) {
        // 如果两个字符相等，最长公共子序列长度就是 1，否则是 0。
        if (i == 0 && j == 0) {
            return str1[i] == str2[i] ? 1 : 0;
            // 说明 str1 只剩第一个字符,那么看 str2 的前 j 个字符里有没有和 str1[0] 相等的。
        } else if (i == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, str2, i, j - 1);
            }
            // str2 只剩第一个字符，去 str1 的前 i 个里面找。
        } else if (j == 0) {
            if (str1[i] == str2[j]) {
                return 1;
            } else {
                return process1(str1, str2, i - 1, j);
            }
            // 一般情况（递归）
        } else {
            // 忽略 str1 的最后一个字符
            int p1 = process1(str1, str2, i - 1, j);
            // 忽略 str2 的最后一个字符
            int p2 = process1(str1, str2, i, j - 1);
            // 如果 str1[i] == str2[j]，那么这两个字符可以算作公共子序列的一部分
            int p3 = str1[i] == str2[j] ? (1 + process1(str1, str2, i - 1, j - 1)) : Integer.MIN_VALUE;
            // 取三者最大值作为结果
            return Math.max(p1, Math.max(p2, p3));
        }
    }

    /**
     * 方法二：动态规划
     * 思路：
     * 1. 定义 dp[i][j]：表示 str1[0...i] 与 str2[0...j] 的最长公共子序列长度。
     * 2. 初始化：
     * - 第一行：str1[0] 是否出现在 str2[0...j]
     * - 第一列：str2[0] 是否出现在 str1[0...i]
     * 3. 转移：
     * - 不要 str1[i]：dp[i-1][j]
     * - 不要 str2[j]：dp[i][j-1]
     * - 若 str1[i]==str2[j]：dp[i-1][j-1] + 1
     * - 三者取最大
     * 4. 答案：dp[N-1][M-1]
     * 时间复杂度：O(N*M)
     * 空间复杂度：O(N*M)
     * 可优化：可用滚动数组降为 O(min(N,M))
     *
     * @param s1 第一个字符串
     * @param s2 第二个字符串
     * @return 最长公共子序列长度
     */
    public static int longestCommonSubsequence2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int N = str1.length;
        int M = str2.length;
        int[][] dp = new int[N][M];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < M; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = str1[i] == str2[j] ? (1 + dp[i - 1][j - 1]) : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }


        return dp[N - 1][M - 1];
    }
}
