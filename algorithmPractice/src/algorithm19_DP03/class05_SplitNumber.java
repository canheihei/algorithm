package algorithm19_DP03;

// 后面的数不能比前面的数小
public class class05_SplitNumber {
    /**
     * 暴力递归版
     *
     * @param n 被分裂的正整数
     * @return 可分裂的方法数
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

    // 记忆化搜索版本
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

    // 严格表依赖斜率优化版本
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
