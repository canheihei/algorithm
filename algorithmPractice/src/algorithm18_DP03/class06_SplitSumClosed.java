package algorithm18_DP03;

/**
 * 给定一个整数数组 arr，将其分成两个子集，使得这两个子集的累加和尽可能接近。返回较小的那个子集的累加和。
 */
public class class06_SplitSumClosed {
    /**
     * 暴力递归法：计算将数组分成两个子集后，较小的子集的累加和
     *
     * @param arr 输入的整数数组
     * @return 返回较小的子集的累加和
     */
    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        // 计算累加和
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return process(arr, 0, sum >> 1);
    }

    /**
     * 递归辅助方法：从数组的某个位置开始选择数字，尽量接近目标值但不超出
     *
     * @param arr  输入的整数数组
     * @param i    当前处理的数组下标
     * @param rest 剩余的目标累加和
     * @return 返回不超过rest的最接近的累加和
     */
    private static int process(int[] arr, int i, int rest) {
        // 越界
        if (i == arr.length) {
            return 0;
            // 还有数
        } else {
            // 不使用i位置的数
            int p1 = process(arr, i + 1, rest);
            // 使用i位置的数
            int p2 = 0;
            // 判断是否超过rest
            if (arr[i] <= rest) {
                p2 = arr[i] + process(arr, i + 1, rest - arr[i]);
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 动态规划法：计算将数组分成两个子集后，较小的子集的累加和
     *
     * @param arr 输入的整数数组
     * @return 返回较小的子集的累加和
     */
    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int sum = 0;
        for (int add : arr) {
            sum += add;
        }
        int M = sum / 2;
        int[][] dp = new int[N + 1][M + 1];
        for (int i = 0; i <= M; i++) {
            dp[N][i] = 0;
        }
        for (int j = N - 1; j >= 0; j--) {
            for (int k = 0; k <= M; k++) {
                int p1 = dp[j + 1][M];
                int p2 = 0;
                if (arr[j] <= M) {
                    p2 = arr[j] + dp[j + 1][M - arr[j]];
                }
                dp[j][k] = Math.max(p1, p2);
            }
        }

        return dp[0][M];
    }
}
