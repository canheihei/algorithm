package algorithm19_DP03;

public class class02_KillMonster {

    public static double right1(int hp, int M, int times) {
        if (hp < 1 || M < 1 || times < 1) {
            return 0;
        }
        // 伤害组合数
        long all = (long) Math.pow(M + 1, times);
        long kill = process1(times, M, hp);
        return (double) ((double) kill / (double) all);
    }

    /**
     * @param hp    怪兽血量
     * @param M     每次伤害在[0~M]范围上
     * @param times 还有times次可以砍
     * @return 返回砍死的情况数
     */
    private static long process1(int times, int M, int hp) {
        // 没砍数了
        if (times == 0) {
            // 此时如果怪兽已经挂了就返回1生存指数，否则为0
            return hp <= 0 ? 1 : 0;
        }
        if (hp <= 0) {
            return (long) Math.pow(M + 1, times);
        }
        // 初始化生存指数
        long ways = 0;
        // 遍历每一次的可能伤害
        for (int i = 0; i <= M; i++) {
            ways += process1(times - 1, M, hp - i);
        }
        return ways;
    }

    // dp版本
    public static double dp1(int N, int M, int K) {
        if (N < 1 || M < 1 || K < 1) {
            return 0;
        }

        // 伤害组合数
        long all = (long) Math.pow(M + 1, K);
        long[][] dp = new long[K + 1][N + 1];
        dp[0][0] = 1;
        for (int times = 1; times <= K; times++) {
            dp[times][0] = (long) Math.pow(M + 1, times);
            for (int hp = 1; hp <= N; hp++) {
                long ways = 0;
                for (int i = 0; i <= M; i++) {
                    if (hp - i >= 0) {
                        ways += dp[times - 1][M];
                    } else {
                        ways += (long) Math.pow(M + 1, times - 1);
                    }
                }
                dp[times][hp] = ways;
            }
        }
        long kill = dp[K][N];

        return (double) ((double) kill / (double) all);
    }
}
