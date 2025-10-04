package algorithm18_DP03;

/**
 * 题目：整数拆分（非递减拆分）
 * 给定一个正整数 n，将其拆分为若干个正整数之和，要求拆分后的序列是非递减的（即后面的数不能比前面的数小）。
 * 问有多少种不同的拆分方法？
 * 例如：n = 4 时，合法拆分有：
 * [4], [1,3], [2,2], [1,1,2], [1,1,1,1] → 共 5 种。
 */
public class class05_SplitNumber {
    /**
     * 方法一：暴力递归
     *
     * @param n 被拆分的正整数
     * @return 返回满足非递减条件的拆分方法总数
     */
    public static int ways(int n) {
        // 1.处理边界情况
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return process1(1, n);
    }

    /**
     * @param pre  上一个被拆出来的数是pre
     * @param rest 还剩rest要拆
     * @return 拆解的方法数
     */
    public static int process1(int pre, int rest) {
        if (rest == 0) {
            return 1;
        }
        if (pre > rest) {
            return 0;
        }
        // pre < rest
        int ways = 0;
        for (int first = pre; first <= rest; first++) {
            ways += process1(first, rest - first);
        }
        return ways;
    }

    /**
     * 方法二：记忆化搜索（自顶向下动态规划）
     *
     * @param n 被拆分的正整数
     * @return 返回满足非递减条件的拆分方法总数
     */
    public static int dp1(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            dp[i][i] = 1;
        }


        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                for (int first = pre; first <= rest; first++) {
                    ways += dp[first][rest - first];
                }
                dp[pre][rest] = ways;
            }
        }

        return dp[1][n];
    }

    /**
     * 方法三：动态规划（斜率优化版，利用状态转移方程优化内层循环）
     *
     * @param n 被拆分的正整数
     * @return 返回满足非递减条件的拆分方法总数
     */
    public static int dp2(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            dp[i][i] = 1;
        }


        for (int pre = n - 1; pre >= 1; pre--) {
            for (int rest = pre + 1; rest <= n; rest++) {
                int ways = 0;
                ways += dp[pre + 1][rest] + dp[pre][rest - pre];
                dp[pre][rest] = ways;
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {
        int test = 39;
        System.out.println(ways(test));
        System.out.println(dp1(test));
        System.out.println(dp2(test));
    }
}
