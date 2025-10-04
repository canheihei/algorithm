package algorithm18_DP03;

/**
 * 在N×N的棋盘上放置N个皇后，要求它们互不攻击（即任意两个皇后不能在同一行、同一列或同一对角线上）。
 * 求有多少种不同的摆放方法。
 */
public class class08_NQueens_notdp {
    /**
     * 方法一：递归回溯法
     *
     * @param n n*n棋盘有n个皇后
     * @return 返回合法的摆放方法数
     */
    public static int num1(int n) {
        if (n < 1) {
            return 0;
        }
        int[] record = new int[n];
        return process1(0, record, n);
    }

    /**
     * 递归处理每一行的皇后放置
     *
     * @param i      当前处理的行号
     * @param record 记录数组，record[x]=y表示第x行的皇后放在y列
     * @param n      棋盘大小
     * @return 返回从第i行开始后续的合法摆放方法数
     */
    //当前来到i行，一共是O~N-1行
    //在i行上放皇后，所有列都尝试
    //必须要保证跟之前所有的皇后不打架
    //int record record[x]=y 之前的第x行的皇后，放在了y列上
    //返回：不关心i以上发生了什么,i......后续有多少合法的方法数
    private static int process1(int i, int[] record, int n) {
        if (i == n) {
            return 1;
        }
        int res = 0;
        // i行的皇后，放哪一列呢？j列
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j;
                res += process1(i + 1, record, n);
            }
        }
        return res;
    }

    /**
     * 检查当前位置是否合法
     *
     * @param record 记录数组
     * @param i      当前行号
     * @param j      当前列号
     * @return 返回true表示当前位置合法，false表示不合法
     */
    public static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            // 共列或者共斜线
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(i - k)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 方法二：位运算优化版
     *
     * @param n n*n棋盘有n个皇后（1 <= n <= 32）
     * @return 返回合法的摆放方法数
     */
    // 位运算优化版
    public static int num2(int n) {
        // 32皇后算不了
        if (n < 1 || n > 32) {
            return 0;
        }
        // 如果你是13皇后问题，limit最右13个1，其他都是0
        int limit = n == 32 ? -1 : (1 << n) - 1;
        return process2(limit, 0, 0, 0);
    }

    /**
     * 位运算优化的递归处理
     *
     * @param limit       掩码，表示棋盘边界
     * @param colLim      列限制掩码
     * @param leftDiaLim  左下对角线限制掩码
     * @param rightDiaLim 右下对角线限制掩码
     * @return 返回在当前限制条件下的合法摆放方法数
     */
    //7皇后问题
    //limit :0....0 1 1 1 1 1 1 1
    //之前皇后的列影响：colLim
    //之前皇后的左下对角线影响：1eftDiaLim
    //之前皇后的右下对角线影响：rightDiaLim
    private static int process2(int limit, int colLim, int leftDiaLim, int rightDiaLim) {
        if (colLim == limit) {
            return 1;
        }
        // 或之后‘1’位置能放，‘0’不可以放
        int pos = limit & ((~colLim | leftDiaLim | rightDiaLim));
        int mostRightOne = 0;
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(limit, colLim | mostRightOne, (leftDiaLim | mostRightOne) << 1, (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }
}
