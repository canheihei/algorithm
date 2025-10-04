package algorithm17_DP02;

/**
 * 在一个大小为 10×9（横坐标 0~9，纵坐标 0~8 ）的中国象棋棋盘上，
 * 象棋“马”从起点 (0,0) 出发，目标是用恰好 k 步跳到指定位置 (a,b)。
 * 马的走法与国际象棋的 Knight 相同：一共 8 种“日”字形跳法。
 * 请计算共有多少种不同的合法跳法。若无法到达，返回 0。
 */
public class class02_HorseJump {

    /**
     * 入口方法：计算马从 (0,0) 出发，恰好跳 k 步到达 (a,b) 的不同走法数量（暴力递归版本）。
     *
     * @param a 目标位置的横坐标（0\<=a\<=9）
     * @param b 目标位置的纵坐标（0\<=b\<=8）
     * @param k 必须恰好跳的总步数
     * @return 不同合法跳法的数量
     */
    public static int jump(int a, int b, int k) {
        return process(0, 0, k, a, b);
    }

    /**
     * 递归过程：当前位于 (x,y)，还剩 rest 步要跳，问在剩余这些步里恰好跳到 (a,b) 的走法数量。
     * 使用“逆向思维”：求从 (x,y) 走 rest 步到 (a,b) 的方案。
     *
     * @param x    当前所在横坐标
     * @param y    当前所在纵坐标
     * @param rest 剩余还需要跳的步数
     * @param a    目标横坐标
     * @param b    目标纵坐标
     * @return 从 (x,y) 用 rest 步到达 (a,b) 的方案数
     */
    public static int process(int x, int y, int rest, int a, int b) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (rest == 0) {
            return (x == a && y == b) ? 1 : 0;
        }
        int ways = process(x + 2, y + 1, rest - 1, a, b);
        ways += process(x + 1, y + 2, rest - 1, a, b);
        ways += process(x - 1, y + 2, rest - 1, a, b);
        ways += process(x - 2, y + 1, rest - 1, a, b);
        ways += process(x - 2, y - 1, rest - 1, a, b);
        ways += process(x - 1, y - 2, rest - 1, a, b);
        ways += process(x + 1, y - 2, rest - 1, a, b);
        ways += process(x + 2, y - 1, rest - 1, a, b);

        return ways;
    }


    /**
     * 动态规划版本：自底向上填表，dp[x][y][rest] 表示从 (x,y) 出发，用 rest 步跳到 (a,b) 的方案数。
     * 初始条件：dp[a][b][0] = 1（从目标到目标 0 步 1 种方式），然后利用所有反向马步累加。
     *
     * @param a 目标位置横坐标
     * @param b 目标位置纵坐标
     * @param k 必须恰好跳的总步数
     * @return 从 (0,0) 出发恰好 k 步到达 (a,b) 的方案数
     */
    public static int dp(int a, int b, int k) {
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest <= k; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    /**
     * 安全取值辅助函数：返回 dp[x][y][rest]，若 (x,y) 越界则返回 0。
     *
     * @param dp   三维动态规划数组
     * @param x    横坐标
     * @param y    纵坐标
     * @param rest 剩余步数维度索引
     * @return 合法则返回对应状态方案数；越界则返回 0
     */
    public static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[0][0][rest];
    }
}
