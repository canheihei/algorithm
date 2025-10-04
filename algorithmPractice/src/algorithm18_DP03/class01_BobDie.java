package algorithm18_DP03;

/**
 * 在一个大小为 N 行 M 列的网格上，Bob 初始位于坐标 (row, col)。
 * 他可以在每一步等概率(1/4)向上、下、左、右移动一格。如果某一步走出网格边界则立即死亡；
 * 若在恰好走完 k 步后仍在网格内则视为存活。请计算 Bob 存活的概率。
 */
public class class01_BobDie {

    /**
     * 方法一: 暴力递归求存活概率
     *
     * @param row 初始所在行
     * @param col 初始所在列
     * @param k   总共可走步数
     * @param N   网格行数
     * @param M   网格列数
     * @return 返回一个 double，表示在走完 k 步后仍留在网格内的概率
     */
    public static double livePossibility1(int row, int col, int k, int N, int M) {
        return (double) process(row, col, k, N, M) / Math.pow(4, k);
    }

    /**
     * 递归过程函数(暴力尝试)
     *
     * @param row  当前所在行
     * @param col  当前所在列
     * @param rest 剩余可走步数
     * @param N    网格行数
     * @param M    网格列数
     * @return 返回从当前状态出发的所有“仍存活”路径条数
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
     * 方法二: 动态规划求存活概率
     *
     * @param row 初始所在行
     * @param col 初始所在列
     * @param k   总共可走步数
     * @param N   网格行数
     * @param M   网格列数
     * @return 返回一个 double，表示在走完 k 步后仍留在网格内的概率
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

    /**
     * DP辅助取值函数
     *
     * @param dp   三维数组: dp[r][c][rest] 表示在(r,c)位置还剩 rest 步时的存活路径条数
     * @param N    网格行数
     * @param M    网格列数
     * @param r    目标行
     * @param c    目标列
     * @param rest 剩余步数
     * @return 若越界或 rest<0 返回 0，否则返回对应 dp 值
     */
    private static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        // 所在位置越界（died）
        if (r < 0 || r >= N || c < 0 || c >= M || rest < 0) {
            return 0;
        }
        return dp[r][c][rest];
    }
}
