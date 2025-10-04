package algorithm17_DP02;

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个正整数数组 arr（数组中的每个元素代表一张货币的面值，所有值为正）。
 * 认为面值相同的货币没有区别（不可区分），再给定一个正整数 aim。
 * 问有多少种不同的组合方式可以恰好凑出 aim。
 * 例如: arr = {1,2,1,1,2,1,2}, aim = 4
 * 可行的方式有: 1+1+1+1、1+1+2、2+2，共 3 种，返回 3。
 */
public class class07_CoinsWaySameValueSamePapper {
    /**
     * 辅助信息结构:
     * coins[i] 表示第 i 种不同面值
     * zhangs[i] 表示该面值拥有的张数
     */
    public static class Info {
        public int[] coins;
        public int[] zhangs;

        public Info(int[] c, int[] z) {
            coins = c;
            zhangs = z;
        }
    }

    /**
     * 功能: 统计数组中每种面值出现的次数，转为面值数组与张数数组。
     *
     * @param arr 原始货币数组（每个元素代表一张货币的面值）
     * @return 包含不同面值及对应张数的 Info 结构
     */
    public static Info getInfo(int[] arr) {
        // 统计所有面值对应的张数（词频）
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int value : arr) {
            // 首次出现
            if (!counts.containsKey(value)) {
                counts.put(value, 1);
                // 第1+n次出现，对应键的值加一
            } else {
                counts.put(value, counts.get(value) + 1);
            }
        }
        // 统计出货币种类数量
        int N = counts.size();
        int[] coins = new int[N];
        int[] zhangs = new int[N];
        int index = 0;
        // 遍历键值对
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            coins[index] = entry.getKey();
            zhangs[index++] = entry.getValue();
        }
        return new Info(coins, zhangs);
    }

    /**
     * 方法一: 暴力递归
     *
     * @param arr 货币数组（面值可重复，表示多张）
     * @param aim 目标金额
     * @return 返回可以凑成 aim 的不同组合方式数量
     */
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.coins, info.zhangs, 0, aim);
    }

    /**
     * 递归过程函数
     *
     * @param coins  去重后的面值数组
     * @param zhangs 对应面值的张数限制数组
     * @param index  当前处理到的面值索引
     * @param rest   剩余还需凑的钱数
     * @return 从 index 开始使用后续面值凑出 rest 的方法数
     */
    public static int process(int[] coins, int[] zhangs, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }
        int ways = 0;
        // 各个数值货币拼凑后1.不超出rest并且2.张数也不超出的情况方法数
        for (int zhang = 0; zhang * coins[index] <= rest && zhang <= zhangs[index]; zhang++) {
            ways += process(coins, zhangs, index + 1, rest - (zhang * coins[index]));
        }
        return ways;
    }

    /**
     * 方法二: 动态规划（对应暴力递归的严格表结构，枚举张数）
     *
     * @param arr 原始货币数组
     * @param aim 目标金额
     * @return 可以凑成 aim 的组合总数
     */
    public static int dp1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ways = 0;
                for (int zhang = 0; zhang * coins[index] <= rest && zhang <= zhangs[index]; zhang++) {
                    ways += dp[index + 1][rest - (zhang * coins[index])];
                }
                dp[index][rest] = ways;
            }
        }
        return dp[0][aim];
    }

    /**
     * 方法三: 动态规划优化（利用窗口内加减法消除枚举张数）
     *
     * @param arr 原始货币数组
     * @param aim 目标金额
     * @return 可以凑成 aim 的组合总数（比 dp1 更优）
     */
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] zhangs = info.zhangs;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                if (rest - coins[index] * (zhangs[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - coins[index] * (zhangs[index] + 1)];
                }
            }
        }
        return dp[0][aim];
    }
}
