package algorithm17_DP01;

// 自顶向下dp：记忆化搜索
public class class01_RobotWalk {
    // 方法一，暴力递归法
    public static int ways1(int N, int start, int aim, int K) {
        return process1(start, K, aim, N);
    }

    /**
     * @param cur  当前所在位置
     * @param rest 剩余步数
     * @param aim  目标位置
     * @param N    位置总数
     * @return 从cur出发，走rest步到aim的方法数
     */
    // step1:暴力递归
    public static int process1(int cur, int rest, int aim, int N) {
        // basecase:剩余步数为0.如果当前在目标位置，就有一种方法，否则没有
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        // 当前位置在最左，只能向右走
        if (cur == 1) {
            return process1(2, rest - 1, aim, N);
        }
        // 当前位置在最右，只能向左走
        if (cur == N) {
            return process1(N - 1, rest - 1, aim, N);
        }
        // 否则向右或向左走
        else {
            return process1(cur - 1, rest - 1, aim, N) + process1(cur + 1, rest - 1, aim, N);
        }
    }

    // 方法二：傻缓存法
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

    // step2:傻缓存法
    public static int process2(int cur, int rest, int aim, int N, int[][] dp) {
        // 当前位置存在缓存，直接用
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        // 之前没有算过
        int ans = 0;
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

    // 方法三:动态规划
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

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
