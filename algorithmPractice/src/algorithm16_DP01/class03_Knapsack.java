package algorithm16_DP01;

/**
 * 给定N个物品, 第i个物品重量为w[i], 价值为v[i]。给定一个最大承重为bag的背包,
 * 每个物品只能选择0次或1次。求在不超过最大承重的前提下, 可以获得的最大总价值。
 */
public class class03_Knapsack {
    /**
     * 方法一: 暴力递归入口
     *
     * @param w   物品重量数组
     * @param v   物品价值数组
     * @param bag 背包最大可承载重量
     * @return 最大可获得的总价值
     */
    public static int maxValue(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }

        // 尝试函数
        return process(w, v, 0, bag);
    }

    /**
     * 递归尝试函数
     * 含义: 在index位置及其之后的物品中自由选择, 当前还剩bag容量, 返回能获得的最大价值
     *
     * @param w     物品重量数组
     * @param v     物品价值数组
     * @param index 当前考虑到的物品索引
     * @param bag   剩余可用容量
     * @return 最大价值(若出现容量非法返回 - 1用于标记该路径无效)
     */
    public static int process(int[] w, int[] v, int index, int bag) {
        if (bag < 0) {
            return -1;
        }
        if (index == w.length) {
            return 0;
        }

        // 不要当前位置
        int p1 = process(w, v, index + 1, bag);

        // 要当前位置
        int p2 = 0;
        int next = process(w, v, index + 1, bag - w[index]);
        // 如果要当前位置，先判断当前位置合不合理
        if (next != -1) {
            // 合理再加
            p2 = v[index] + next;
        }

        return Math.max(p1, p2);
    }

    /**
     * 方法二: 动态规划(自底向上)
     * dp[index][rest]: 在[index..N)范围内自由选择, 剩余容量为rest时的最大价值
     *
     * @param w   物品重量数组
     * @param v   物品价值数组
     * @param bag 背包最大可承载重量
     * @return 最大可获得的总价值
     */
    public static int dp(int[] w, int[] v, int bag) {
        if (w == null || v == null || w.length != v.length || w.length == 0) {
            return 0;
        }
        int N = w.length;
        int[][] dp = new int[N + 1][bag + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= bag; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = 0;
                int next = rest - w[index] < 0 ? -1 : dp[index + 1][rest - w[index]];
                if (next != -1) {
                    p2 = v[index] + next;
                }
                dp[index][rest] = Math.max(p1, p2);

            }
        }

        return dp[0][bag];
    }

    // 对数器
    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7};
        int[] values = {5, 6, 3, 19};
        int bag = 11;

        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }

}
