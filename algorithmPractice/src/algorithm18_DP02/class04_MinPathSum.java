package algorithm18_DP02;

public class class04_MinPathSum {
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

    // compress version dp
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
        return arr[col-1];
    }
}
