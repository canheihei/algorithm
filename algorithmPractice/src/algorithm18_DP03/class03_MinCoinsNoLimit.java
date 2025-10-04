package algorithm18_DP03;

/**
 * 给定一个正整数数组 arr，表示若干种可用的货币面值，每种面值的张数可以无限次使用。
 * 再给定一个非负整数 aim，求使用这些面值之和恰好凑成 aim 时所需的最少纸币张数。
 * 如果无法凑出 aim，返回 Integer.MAX_VALUE（表示无解）。
 */
public class class03_MinCoinsNoLimit {
    /**
     * 功能：暴力递归入口。给定面值数组（每种面值可重复使用）与目标金额，返回凑成目标所需的最少张数。
     * 思路：调用递归过程，从索引0开始向后尝试所有面值张数的组合。
     *
     * @param arr 货币面值数组（正整数，每种面值可使用无限张）
     * @param aim 目标金额（非负整数）
     * @return 最少张数；若无法凑出则返回 Integer.MAX_VALUE
     */
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * 功能：递归尝试。在 arr[index...] 范围内任意使用各面值若干张，求凑出 rest 金额的最少张数。
     * 递归终止：index 到达数组末尾时，rest==0 返回0，否则返回 Integer.MAX_VALUE。
     * 枚举：当前面值可用张数 zhang=0..(rest/arr[index])，对剩余金额递归求解并取最小。
     *
     * @param arr   面值数组
     * @param index 当前处理的面值位置（可选择 arr[index...] 的面值）
     * @param rest  还需凑出的剩余金额
     * @return 最少张数；无解返回 Integer.MAX_VALUE
     */
    private static int process(int[] arr, int index, int rest) {
        // 如果已经没有货币可选
        if (index == arr.length) {
            // 如果正好凑够，返回0张；否则返回无穷大（表示无解）
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        } else {
            int ans = Integer.MAX_VALUE;
            // 枚举当前面值可以用多少张（0~最多能用的张数）
            for (int zhang = 0; zhang * arr[index] <= rest; zhang++) {
                // 递归尝试下一个面值
                int next = process(arr, index + 1, rest - zhang * arr[index]);
                // 如果后续有解，更新最小张数
                if (next != Integer.MAX_VALUE) {
                    ans = Math.min(ans, zhang + next);
                }
            }
            return ans;
        }
    }

    /**
     * 功能：自底向上动态规划（枚举张数版）。等价于暴力递归的记忆化展开。
     * 状态：dp[i][r] 表示在使用 arr[i...] 的面值时凑出金额 r 的最少张数。
     * 转移：枚举使用当前面值 0..k 张（k * arr[i] <= r），取最小。
     * 复杂度：时间 O(N * aim * (aim / 最小面值))，空间 O(N * aim)。
     *
     * @param arr 面值数组
     * @param aim 目标金额
     * @return 最少张数；无解返回 Integer.MAX_VALUE
     */
    public static int dp1(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int index = arr.length;
        int[][] dp = new int[index + 1][aim + 1];
        dp[index][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[index][j] = Integer.MAX_VALUE;
        }
        for (int col = index - 1; col >= 0; col--) {
            for (int row = 0; row <= aim; row++) {
                int ans = Integer.MAX_VALUE;
                for (int zhang = 0; zhang * arr[col] <= row; zhang++) {
                    // 递归尝试下一个面值
                    int next = dp[col + 1][row - zhang * arr[col]];
                    // 如果后续有解，更新最小张数
                    if (next != Integer.MAX_VALUE) {
                        ans = Math.min(ans, zhang + next);
                    }
                    dp[col][row] = ans;
                }
            }
        }
        return dp[0][aim];
    }

    /**
     * 功能：优化后的动态规划（完全背包型转移）。利用：
     * dp[i][r] = min( dp[i+1][r] (不用当前面值),  dp[i][r - arr[i]] + 1 (再用一张当前面值) )
     * 说明：消除了枚举张数的循环，相当于斜率/滚动利用，体现"当前行复用当前行左侧结果"的完全背包思想。
     * 复杂度：时间 O(N * aim)，空间 O(N * aim)（可继续优化为一维）。
     *
     * @param arr 面值数组
     * @param aim 目标金额
     * @return 最少张数；无解返回 Integer.MAX_VALUE
     */
    public static int dp2(int[] arr, int aim) {
        if (aim == 0) {
            return 0;
        }
        int index = arr.length;
        int[][] dp = new int[index + 1][aim + 1];
        dp[index][0] = 0;
        for (int j = 1; j <= aim; j++) {
            dp[index][j] = Integer.MAX_VALUE;
        }
        for (int col = index - 1; col >= 0; col--) {
            for (int row = 0; row <= aim; row++) {
                // 优化部分
                dp[col][row] = dp[col + 1][row];
                // 首先保证在表里（无越界），然后保证有效
                if (row - arr[col] >= 0 && dp[col][row - arr[col]] != Integer.MAX_VALUE) {
                    dp[col][row] = Math.min(dp[col][row], dp[col][row - arr[col]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    // 对数器
    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int N = (int) (Math.random() * maxLen);
            int[] arr = randomArray(N, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                printArray(arr);
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

    /**
     * 功能：生成长度为 N 的随机面值数组，每个面值范围为 [1, maxValue]。
     *
     * @param N        数组长度
     * @param maxValue 面值最大值
     * @return 随机生成的面值数组
     */
    public static int[] randomArray(int N, int maxValue) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    /**
     * 功能：打印整型数组内容，元素以空格分隔。
     *
     * @param arr 待打印的数组
     */
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
