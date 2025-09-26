package algorithm19_DP03;

public class class03_MinCoinsNoLimit {
    /**
     * @param arr 货币数组（可以重复选）
     * @param aim 需要凑够的金额
     * @return 凑够金额满足的最小张数
     */
    public static int minCoins(int[] arr, int aim) {
        return process(arr, 0, aim);
    }

    /**
     * @param arr   面值数组
     * @param index arr[index....]面值，每种面值张数自由选
     * @param rest  搞出rest这么多钱
     * @return 最小张数
     */
    // 递归函数：从arr[index...]中任意选择货币，凑出rest金额，返回最小张数
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

    // 记忆化搜索
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

    // 严格表依赖（斜率优化）
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

    // 生成随机数组
    public static int[] randomArray(int N, int maxValue) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    // 打印数组
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
