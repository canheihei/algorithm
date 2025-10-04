package algorithm17_DP02;

/**
 * 给定一个包含非负整数的二维矩阵 m，从左上角 (0,0) 出发，
 * 只能向右或向下移动，到达右下角 (m[row-1][col-1])。
 * 请返回一条路径的最小路径和。
 */
public class class04_MinPathSum {
    /**
     * 方法一: 二维动态规划（DP）
     * 使用一个与原矩阵同尺寸的 dp 数组，dp[i][j] 表示到达位置 (i,j) 的最小路径和。
     * 转移方程: dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + m[i][j]。
     * 边界: 第一行只能从左到右累加，第一列只能从上到下累加。
     * 时间复杂度: O(row * col)
     * 空间复杂度: O(row * col)
     * 若 m 为 null 或行/列为空，返回 0。
     *
     * @param m 非负整数矩阵，m[i][j] 表示该格子的代价
     * @return 从左上到右下的最小路径和，若输入非法返回 0
     */
    public static int minPathSum1(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = m[0][0];
        for (int i = 0; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + m[i][0];
        }
        for (int j = 1; j < col; j++) {
            dp[0][j] = dp[0][j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
            }
        }
        // return the last
        return dp[row - 1][col - 1];
    }

    /**
     * 方法二: 一维数组空间压缩动态规划
     * 利用一维数组 arr[j] 代替二维 dp，arr[j] 表示当前行位置 j 的最小路径和。
     * 更新顺序: 行优先；对每一行内，从左到右更新。
     * 转移: arr[j] = min(arr[j], arr[j-1]) + m[i][j]（其中 arr[j] 仍是上一行的 dp[i-1][j]，arr[j-1] 是当前行已更新的 dp[i][j-1]）。
     * 时间复杂度: O(row * col)
     * 空间复杂度: O(col)
     * 若 m 为 null 或行/列为空，返回 0。
     *
     * @param m 非负整数矩阵，m[i][j] 表示该格子的代价
     * @return 从左上到右下的最小路径和，若输入非法返回 0
     */
    public static int minPathSum2(int[][] m) {
        if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
            return 0;
        }
        int row = m.length;
        int col = m[0].length;
        int[] arr = new int[col];
        arr[0] = m[0][0];
        // init arr
        for (int j = 1; j < col; j++) {
            arr[j] = arr[j - 1] + m[0][j];
        }
        for (int i = 1; i < row; i++) {
            arr[0] += m[i][0];
            for (int j = 1; j < col; j++) {
                arr[j] = Math.min(arr[j - 1], arr[j]) + m[i][j];
            }
        }
        return arr[col - 1];
    }
}
