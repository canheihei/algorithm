package algorithm18_DP02;

// https://leetcode.cn/problems/longest-palindromic-subsequence/
public class class01_PalindromeSubsequence {
    /**
     * @param s 输入串
     * @return 最长回文字串长度
     */
    public static int lpsl(String s) {
        // 输入的串为空串或者为null
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return f(str, 0, str.length - 1);
    }

    /**
     * @param str 输入串对应的字符数组
     * @param L   左边界
     * @param R   右边界
     * @return str[L..R]最长回文子序列长度返回
     */
    private static int f(char[] str, int L, int R) {
        //base case
        if (L == R) {
            return 1;
        }
        // 如果只有两个元素了，相等那么回文长度为2，否则为其中任意一个，即为1
        if (L == R - 1) {
            return str[L] == str[R] ? 2 : 1;
        }
        // 不可能 L 不可能 R str[L..R] -> str[L+1...R-1]
        int p1 = f(str, L + 1, R - 1);
        // 可能 L 不可能 R str[L..R-1] -> str[L...R-1]
        int p2 = f(str, L, R - 1);
        // 不可能 L 可能 R str[L+1..R] -> str[L+1...R]
        int p3 = f(str, L + 1, R);
        // 可能 L 可能 R str[L..R] -> str[L+1...R-1]
        // 不为 str[L...R]是因为下次递归就死循环了
        int p4 = str[L] != str[R] ? 0 : (f(str, L + 1, R - 1) + 2);
        return Math.max(Math.max(p1, p2), Math.max(p3, p4));
    }

    /**
     * dp版本
     * @param s
     * @return
     */
    public static int lpsl2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        // L>R 不可能出现
        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }
        for (int L = N - 3; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                // 压缩后优化版本
                dp[L][R] = Math.max(dp[L][R - 1], dp[L + 1][R]);
                if (str[L] == str[R]) {
                    dp[L][R] = Math.max(dp[L][R], 2 + dp[L + 1][R - 1]);
                }
                  // 未压缩版本
//                int p1 = dp[L + 1][R - 1];
//                int p2 = dp[L][R - 1];
//                int p3 = dp[L + 1][R];
//                int p4 = str[L] != str[R] ? 0 : (f(str, L + 1, R - 1) + 2);
//                dp[L][R] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
            }
        }
        return dp[0][N - 1];
    }
}
