package algorithm19_DP03;

public class class01_BobDie {

    public static double livePossibility1(int row, int col, int k, int N, int M) {
        return (double) process(row, col, k, N, M) / Math.pow(4, k);
    }

    /**
     * 暴力递归版本
     *
     * @param row  所在格子行数
     * @param col  所在格子列数
     * @param rest 剩余可走步数
     * @param N    bob所在的行数
     * @param M    bob所在的列数
     * @return 生还点数
     */
    private static long process(int row, int col, int rest, int N, int M) {
        // 所在位置越界（died）
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        // 不能走了，但是没出界，生还
        if (rest == 0) {
            return 1;
        }
        // 各个方向走
        long up = process(row - 1, col, rest - 1, N, M);
        long down = process(row + 1, col, rest - 1, N, M);
        long left = process(row, col - 1, rest - 1, N, M);
        long right = process(row, col + 1, rest - 1, N, M);

        return up + down + left + right;
    }

    /**
     * dp版本
     *
     * @param row 所在格子行数
     * @param col 所在格子列数
     * @param k   剩余可走步数
     * @param N   bob所在的行数
     * @param M   bob所在的列数
     * @return 生还点数
     */
    public static double livePossibility2(int row, int col, int k, int N, int M) {
        // 可变参数分析
        long[][][] dp = new long[N][M][k + 1];
        // 根据尝试：第一层全为0
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }
        // 第n层依赖n-1层
        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    dp[r][c][rest] += pick(dp, N, M, r - 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r + 1, c, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c - 1, rest - 1);
                    dp[r][c][rest] += pick(dp, N, M, r, c + 1, rest - 1);
                }
            }
        }
        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    private static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        // 所在位置越界（died）
        if (r < 0 || r >= N || c < 0 || c >= M || rest < 0) {
            return 0;
        }
        return dp[r][c][rest];
    }
}
