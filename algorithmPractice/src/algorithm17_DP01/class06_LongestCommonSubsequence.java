package algorithm17_DP01;

// 最长公共子序列
public class class06_LongestCommonSubsequence {
    // 暴力递归版
    public static int longestCommonSubsequence1(String s1, String s2) {
        // 前置条件校验
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process1(str1, str2, str1.length - 1, str2.length - 1);
    }

    // str1[0...i]与str2[0...j]最长公共子序列多长?
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

    // dp版
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
