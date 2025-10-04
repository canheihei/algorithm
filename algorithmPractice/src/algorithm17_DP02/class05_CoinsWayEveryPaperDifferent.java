package algorithm17_DP02;

/**
 * 给定一个长度为 N 的整型数组 arr，表示 N 张面值各不相同、
 * 且每张只能使用 0 次或 1 次的钞票。再给定一个目标金额 aim，
 * 求一共有多少种不同的选择方式，使得被选择的钞票面值之和恰好等于 aim。
 * 每张钞票只能取或不取（子集问题），顺序不重要，返回方案数。
 */
public class class05_CoinsWayEveryPaperDifferent {
    /**
     * 方法一: 递归入口
     * 题意: 计算使用数组中不重复的钞票(每张最多用一次)组成目标金额的不同方案数。
     * @param arr 整型数组，代表每张不同面值的钞票；长度为 N。
     * @param aim 目标金额；非负整数。
     * @return 返回一个整数，表示可以恰好凑出 aim 的不同子集方案数。
     */
    public static int coinWays(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * 方法二: 递归过程
     * 含义: 在当前位置 index，可选择 arr[index] 要或不要，每张钞票只有一次使用机会，统计能否用后续若干张恰好拼出剩余金额 rest 的方案数。
     * 递归结构:
     *   1. rest < 0: 剪枝，返回 0。
     *   2. index 到达末尾: 若 rest == 0 返回 1，否则返回 0。
     *   3. 普通位置: 方案 = 不要当前钞票 + 要当前钞票(剩余减去面值)。
     * @param arr 整型数组，表示不同面值钞票。
     * @param index 当前处理到的下标位置，取值范围 [0, arr.length]。
     * @param rest 还需要凑出的剩余金额，初始为 aim，过程中逐步减少。
     * @return 返回一个整数，表示从 index 开始能组成 rest 的方案数。
     */
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        } else {
            // 不要与要的情况
            return process(arr, index + 1, rest) + process(arr, index + 1, rest - arr[index]);
        }
    }

    /**
     * 方法三: 动态规划
     * 思路: 将递归转为自底向上的 DP。设 dp[index][rest] 表示使用子数组 arr[index..N-1] 能否凑出金额 rest 的方案数。
     * 递推:
     *   dp[index][rest] = dp[index+1][rest]                  (不要当前钞票)
     *                   + (rest - arr[index] >= 0 ? dp[index+1][rest - arr[index]] : 0) (要当前钞票)
     * 边界:
     *   dp[N][0] = 1 (无数可选且需求为 0，有 1 种空集合方案)
     *   dp[N][rest != 0] = 0
     * 目标: dp[0][aim]
     * 时间复杂度: O(N * aim)
     * 空间复杂度: O(N * aim)
     * @param arr 整型数组，表示不同面值钞票。
     * @param aim 目标金额，非负整数。
     * @return 返回一个整数，表示用这些钞票恰好组成 aim 的不同方案数。
     */
    public static int dp(int[] arr, int aim) {
        if (aim == 0) {
            return 1;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] >= 0 ? dp[index + 1][rest - arr[index]] : 0);
            }
        }
        return dp[0][aim];
    }
}
