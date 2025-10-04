package algorithm17_DP02;

/**
 * https://leetcode.cn/problems/longest-palindromic-subsequence/
 * 最长回文子序列求解工具类。
 * 提供暴力递归与动态规划两种实现。
 */
public class class01_PalindromeSubsequence {
    /**
     * 方法一: 暴力递归入口
     * 功能: 计算字符串的最长回文子序列长度
     * 思路: 将字符串转为字符数组后调用递归函数处理区间 [0, n-1]
     * 边界: null 或空串返回 0
     * 时间复杂度: 指数级（最坏 O(4^n)）
     * 空间复杂度: 递归深度 O(n)
     *
     * @param s 原始输入字符串
     * @return 最长回文子序列长度
     */
    public static int lpsl(String s) {
        // 输入的串为空串或者为null
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] str = s.toCharArray();
        return f(str, 0, str.length - 1);
    }

    /**
     * 递归核心函数
     * 功能: 返回字符数组在闭区间 [L, R] 上的最长回文子序列长度
     * 设计: 枚举四种可能性（两端都不用 / 只用左 / 只用右 / 同时用左右且相等）
     * 剪枝: 当 L == R 时返回 1；当区间长度为 2 时直接判定
     * 参数约定: 只在 0 <= L <= R < n 条件下调用
     *
     * @param str 字符数组
     * @param L   当前考察的左边界
     * @param R   当前考察的右边界
     * @return 区间 [L, R] 的最长回文子序列长度
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
     * 方法二: 动态规划版本
     * 功能: 用二维 DP 表求解最长回文子序列长度
     * 定义: dp[i][j] 表示区间 [i, j] 的最长回文子序列长度
     * 初始化: 单个字符为 1；相邻两个字符根据是否相等赋 1 或 2
     * 转移:
     * dp[L][R] = max(dp[L][R-1], dp[L+1][R])
     * 若 str[L] == str[R] 再比较 2 + dp[L+1][R-1]
     * 填表顺序: L 从右往左, R 从 L+2 往右
     * 时间复杂度: O(n^2)
     * 空间复杂度: O(n^2)
     *
     * @param s 原始输入字符串
     * @return 最长回文子序列长度
     */
    public static int lpsl2(String s) {
        if (s == null || s.isEmpty()) {
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
