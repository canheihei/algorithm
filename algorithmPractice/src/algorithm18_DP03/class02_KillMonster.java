package algorithm18_DP03;

/**
 * 给定一个怪兽的初始血量 N（或 hp），你可以攻击 K（或 times）次。
 * 每次攻击造成的伤害为一个等概率的整数，范围在 [0, M]。
 * 所有 (M+1)^K 种伤害序列等概率。求在 K 次攻击后怪兽被击杀（血量小于等于 0）的概率。
 */
public class class02_KillMonster {
    /**
     * 方法一: 递归暴力（概率接口）
     * 计算在给定攻击次数与每次攻击伤害范围下，击杀怪兽的概率。
     *
     * @param hp    怪兽初始血量（大于等于 1）
     * @param M     每次攻击伤害的最大值，实际伤害等概率落在 [0, M]
     * @param times 总共还能攻击的次数
     * @return 击杀怪兽的概率（double）
     */
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
     * 递归核心：返回在还剩 times 次攻击、当前怪兽剩余血量为 hp 时，
     * 能把怪兽击杀（血量降至 <=0）的所有伤害序列数量。
     * <p>
     * 递归含义:
     * 1. times == 0 时：若 hp <= 0 说明之前已杀死，返回 1；否则返回 0
     * 2. hp <= 0 且还有 times 次：说明已提前击杀，后续每次攻击(0~M)都无影响，
     * 剩余 (M+1)^{times} 种伤害序列全部算作“击杀”。
     * 3. 否则枚举当前一刀造成 i (0<=i<=M) 的伤害，累加子问题。
     *
     * @param times 还剩余的攻击次数
     * @param M     每次攻击伤害范围 [0, M]
     * @param hp    当前剩余血量
     * @return 当前状态下所有“击杀”序列的数量（long）
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

    /**
     * 方法二: 动态规划
     * 使用自底向上的 DP 统计在 K 次攻击内把血量为 N 的怪兽击杀的所有伤害序列数量，
     * 再除以总序列数 (M+1)^K 得到概率。
     * <p>
     * 状态定义:
     * dp[t][h] = 在还可以再攻击 t 次、怪兽当前血量为 h 时的“击杀”序列数量。
     * <p>
     * 初始化:
     * dp[0][0] = 1 （血量正好 <=0 视为击杀）
     * dp[0][h>0] = 0
     * <p>
     * 转移:
     * 若 h == 0：已被击杀，剩余 t 次攻击任意取值 => dp[t][0] = (M+1)^t
     * 若 h > 0：
     * dp[t][h] = Σ_{i=0..M}:
     * (h - i >= 0) ? dp[t-1][h - i]
     * : (M+1)^{t-1}   // 本次一刀已杀，后续自由
     * <p>
     * 注意：示例代码中的内部转移实现存在下标书写错误，应按上述逻辑修正。
     *
     * @param N 怪兽初始血量
     * @param M 每次攻击伤害范围 [0, M]
     * @param K 总攻击次数
     * @return 击杀概率（double）
     */
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
