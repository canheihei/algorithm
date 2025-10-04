package algorithm18_DP03;

/**
 * 给定一个正整数数组 arr，将其划分为两个子集，要求两个子集的元素个数之差不超过 1（即尽量均分元素个数），
 * 在满足该条件的前提下，使得两个子集的累加和尽可能接近。返回其中一个子集的最大可能累加和（该和不超过总和的一半）。
 */
public class class07_SplitSumClosedSizeHalf {
    /**
     * 方法一：暴力递归（正确但效率低）
     *
     * @param arr 输入的正整数数组
     * @return 返回在满足子集大小为 floor(n/2) 或 ceil(n/2) 的前提下，子集累加和不超过总和一半的最大值
     */
    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += sum;
        }
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else {
            return Math.max(process(arr, 0, arr.length / 2, sum / 2), process(arr, 0, arr.length / 2 + 1, sum / 2));
        }
    }

    /**
     * 辅助递归函数：在 arr[i...] 中恰好选择 picks 个数，且累加和不超过 rest 的前提下，能获得的最大累加和
     *
     * @param arr   原始数组
     * @param i     当前考虑的起始索引（从 i 到末尾可选）
     * @param picks 必须恰好选择的元素个数
     * @param rest  当前允许的最大累加和（不能超过该值）
     * @return 返回满足条件的最大累加和；若无法选出 picks 个数，则返回 -1
     */
    public static int process(int[] arr, int i, int picks, int rest) {
        // 没有数挑了
        if (i == arr.length) {
            return picks == 0 ? 0 : -1;
        } else {
            int p1 = process(arr, i + 1, picks, rest);
            int p2 = -1;
            int next = -1;
            if (arr[i] <= rest) {
                next = process(arr, i + 1, picks - 1, rest - arr[i]);
            }
            if (next != -1) {
                p2 = arr[i] + next;
            }
            return Math.max(p1, p2);
        }
    }

    /**
     * 方法二：动态规划（三维 DP 表）
     *
     * @param arr 输入的正整数数组
     * @return 返回在满足子集大小为 floor(n/2) 或 ceil(n/2) 的前提下，子集累加和不超过总和一半的最大值
     */
    public static int dp1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        sum /= 2;
        // 构造dp表
        int N = arr.length;
        int M = (N + 1) / 2;
        int[][][] dp = new int[N + 1][M + 1][sum + 1];

        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= M; j++) {
                for (int k = 0; k <= sum; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        for (int rest = 0; rest <= sum; rest++) {
            dp[N][0][rest] = 0;
        }
        for (int i = N - 1; i >= 0; i--) {
            for (int picks = 0; picks <= M; picks++) {
                for (int rest = 0; rest <= sum; rest++) {
                    int p1 = dp[i + 1][picks][rest];

                    int p2 = -1;
                    int next = -1;
                    if (picks - 1 >= 0 && arr[i] <= rest) {
                        next = dp[i + 1][picks - 1][rest - arr[i]];
                    }
                    if (next != -1) {
                        p2 = arr[i] + next;
                    }
                    dp[i][picks][rest] = Math.max(p1, p2);
                }
            }
        }
        if ((arr.length & 1) == 0) {
            return dp[0][arr.length / 2][sum];
        } else {
            return Math.max(dp[0][arr.length / 2][sum], dp[0][(arr.length / 2) + 1][sum]);
        }
    }
}
