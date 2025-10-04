package algorithm17_DP02;

/**
 * 题目描述:
 * 给定一个正整数数组 arr，表示一组可用的硬币面值(每种面值数量无限)。再给定一个非负整数 aim，
 * 问使用这些硬币(每种面值可使用任意张)有多少种不同的组合方式可以凑出刚好为 aim 的金额。
 * 说明:
 * 组合中硬币的顺序不计(即 {1,2,2} 与 {2,1,2} 视为同一种方式)。
 * 若无法凑出，结果为 0。
 * 若 aim == 0，结果为 1 (什么都不选)。
 * 数组元素均为正且不保证互不相同。
 */
public class class06_CoinsWayNoLimit {
    /**
     * 方法一: 递归暴力枚举
     * 功能: 计算使用数组中无限张硬币凑出指定金额的不同组合数量。
     * 思路: 依次决定每一种面值使用0张、1张、2张... 直到不能再用为止，递归到末尾检查是否刚好凑满。
     * 复杂度: 指数级，适合理解问题，不适合大数据。
     *
     * @param arr 硬币面值数组(正整数，可能存在重复面值)
     * @param aim 目标金额(非负)
     * @return 不同的组合数量(顺序无关)
     */
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 递归过程函数
     * 定义: 处理到 index 位置的面值时，剩余金额为 rest，返回可以完成凑出的组合数量。
     * 递归结束: index == arr.length 时，若 rest==0 返回1，否则返回0。
     * 枚举: 当前面值可用张数 zhang = 0..(rest / arr[index])，累加后续结果。
     *
     * @param arr   硬币面值数组
     * @param index 当前处理到的面值索引
     * @param rest  剩余需要凑的金额
     * @return 从当前状态出发的合法组合数量
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
            ways += process(arr, index + 1, rest - (zhang * arr[index]));
        }
        return ways;
    }

    /**
     * 方法二: 动态规划(三层循环版)
     * 功能: 将递归暴力改成自底向上的 DP，直接照搬枚举张数的逻辑。
     * 定义: dp[index][rest] 表示使用 index..N-1 的面值可以凑出 rest 的组合数量。
     * 转移: dp[index][rest] = Σ dp[index+1][rest - k*arr[index]]，k 满足 k*arr[index] <= rest。
     * 初始化: dp[N][0] = 1，其余 dp[N][rest>0] = 0。
     * 复杂度: O(N * aim * (aim / arr[i]))，仍可能较慢。
     *
     * @param arr 硬币面值数组
     * @param aim 目标金额
     * @return 不同的组合数量
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                    ways += dp[index + 1][rest - (zhang * arr[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    /**
     * 方法三: 动态规划优化(去除枚举张数)
     * 思路: 利用完全背包的优化递推:
     * dp[index][rest] = dp[index+1][rest] (不用当前面值) + dp[index][rest - arr[index]] (至少用一张当前面值)
     * 证明: 将原枚举拆分为“不用当前面值”与“用至少一张当前面值”两类，避免第三层循环。
     * 定义与初始化同方法二。
     * 复杂度: O(N * aim)；空间可继续优化为一维，但此处保留二维便于对照。
     *
     * @param arr 硬币面值数组
     * @param aim 目标金额
     * @return 不同的组合数量
     */
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }
}
