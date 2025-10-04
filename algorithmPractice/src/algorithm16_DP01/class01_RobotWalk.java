package algorithm16_DP01;

// 自顶向下dp：记忆化搜索

/**
 * 在一条有 N 个连续位置（从 1 到 N 编号）的直线上，有一台机器人初始位于位置 start。机器人必须恰好走 K 步到达某个位置。
 * 走路规则为：
 * 1) 机器人每一步只能向左或向右移动 1 个位置；
 * 2) 机器人不能走出边界（即在位置 1 只能向右走，在位置 N 只能向左走）；
 * 3) 问：有多少种不同的走法，恰好用 K 步从 start 走到目标位置 aim？
 */
public class class01_RobotWalk {
    /**
     * 方法一，暴力递归法
     *
     * @param N     位置总数（N ≥ 2）
     * @param start 起始位置（1 ≤ start ≤ N）
     * @param aim   目标位置（1 ≤ aim ≤ N）
     * @param K     必须行走的总步数（K ≥ 0）
     * @return 返回一个整数，表示不同的合法走法数量。
     */
    public static int ways1(int N, int start, int aim, int K) {
        return process1(start, K, aim, N);
    }

    /**
     * 方法一递归过程
     *
     * @param cur  当前所在位置
     * @param rest 剩余步数
     * @param aim  目标位置
     * @param N    位置总数
     * @return 从cur出发，走rest步到aim的方法数
     */
    public static int process1(int cur, int rest, int aim, int N) {
        // 1.剩余步数为0时。如果当前在目标位置，就有一种方法，否则没有
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        // 2.当前位置在最左，只能向右走
        if (cur == 1) {
            return process1(2, rest - 1, aim, N);
        }
        // 3.当前位置在最右，只能向左走
        if (cur == N) {
            return process1(N - 1, rest - 1, aim, N);
        }
        // 4.否则向右或向左走
        else {
            return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
        }
    }

    /**
     * 方法二：傻缓存法
     *
     * @param N     位置总数
     * @param start 当前所在位置
     * @param aim   目标位置
     * @param K     必须行走的总步数
     * @return 返回一个整数，表示不同的合法走法数量。
     */
    public static int ways2(int N, int start, int aim, int K) {
        // 建立缓存
        int[][] dp = new int[N + 1][K + 1];
        // 初始化缓存
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                // -1表示未计算
                dp[i][j] = -1;
            }
        }
        return process2(start, K, aim, N, dp);
    }

    /**
     * 方法二过程
     *
     * @param cur  当前所在位置
     * @param rest 剩余步数
     * @param aim  目标位置
     * @param N    位置总数（N ≥ 2）
     * @param dp   缓存表
     * @return 从cur出发，走rest步到aim的方法数
     */
    public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        // 当前位置存在缓存，直接用
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        // 之前没有算过
        int ans;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process2(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = process2(N - 1, rest - 1, aim, N, dp);
        } else {
            ans = process2(cur - 1, rest - 1, aim, N, dp) + process2(cur + 1, rest - 1, aim, N, dp);
        }
        // 存入缓存
        dp[cur][rest] = ans;
        return ans;
    }

    /**
     * 方法三:动态规划
     *
     * @param N     位置总数
     * @param start 当前所在位置
     * @param aim   目标位置
     * @param K     必须行走的总步数
     * @return 返回一个整数，表示不同的合法走法数量。
     */
    public static int ways3(int N, int start, int aim, int K) {
        int[][] dp = new int[N + 1][K + 1];
        dp[aim][0] = 1;
        for (int rest = 1; rest <= K; rest++) {
            dp[1][rest] = dp[2][rest - 1];
            for (int cur = 2; cur < N; cur++) {
                dp[cur][rest] = dp[cur - 1][rest - 1] + dp[cur + 1][rest - 1];
            }
            dp[N][rest] = dp[N - 1][rest - 1];
        }
        return dp[start][K];
    }

    // 对数器
    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
