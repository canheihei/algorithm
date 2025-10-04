package algorithm16_DP01;

/**
 * 给定一个整型数组表示一排纸牌的分值。两名玩家轮流从最左或最右拿一张牌，双方都采取最优策略。
 * 求先手最终能获得的最大分值。
 */
public class class02_CardsInline {
    // 对数器
    public static void main(String[] args) {
        // int[] arr = {5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7};
        int[] arr = {5, 7, 4, 5, 8, 1};
        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));
    }

    /**
     * 方法一: 暴力递归
     * 入口函数，返回先手在最优博弈下的最大得分。
     *
     * @param arr 纸牌分值数组
     * @return 先手最大得分
     */
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    //arr[L..R]，先手获得的最好分数返回

    /**
     * f1: 纯递归
     * 含义: 在子数组区间 [L,R] 上轮到“当前玩家”作为先手选择时, 能获得的最大得分。
     * 行为: 先手可选左或右，其后对手变成后手函数场景(g1)。
     *
     * @param arr 纸牌数组
     * @param L   左边界
     * @param R   右边界
     * @return 该区间先手能取得的最好分数
     */
    public static int f1(int[] arr, int L, int R) {
        // base case
        if (L == R) {
            return arr[L];
        }
        int p1 = arr[L] + g1(arr, L + 1, R);
        int p2 = arr[R] + g1(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    /**
     * g1: 纯递归
     * 含义: 在子数组区间 [L,R] 上轮到“当前玩家”作为后手(被动)时，最终(该玩家自身)能获得的分数。
     * 行为: 对手是先手，会给你留下一个较差的局面 -> 你得到的是对手选后剩余局面中较小的结果。
     *
     * @param arr 纸牌数组
     * @param L   左边界
     * @param R   右边界
     * @return 该区间后手能取得的分数
     */
    public static int g1(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int p1 = f1(arr, L + 1, R);
        int p2 = f1(arr, L, R - 1);
        return Math.min(p1, p2);
    }

    /**
     * 方法二: 记忆化搜索
     * 在暴力递归基础上使用两个缓存表避免重复计算。
     *
     * @param arr 纸牌数组
     * @return 先手最大得分
     */
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);

        return Math.max(first, second);
    }

    /**
     * f2: 记忆化版本的先手函数
     *
     * @param arr  纸牌数组
     * @param L    左边界
     * @param R    右边界
     * @param fmap 先手缓存表,fmap[L][R]表示f2(L,R)结果
     * @param gmap 后手缓存表
     * @return 区间 [L,R] 先手最优得分
     */
    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }
        int ans;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        fmap[L][R] = ans;
        return ans;
    }

    /**
     * g2: 记忆化版本的后手函数
     *
     * @param arr  纸牌数组
     * @param L    左边界
     * @param R    右边界
     * @param fmap 先手缓存表
     * @param gmap 后手缓存表,gmap[L][R]表示g2(L,R)结果
     * @return 区间 [L,R] 后手能取得的分值
     */
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans;
        if (L == R) {
            ans = 0;
        } else {
            int p1 = f2(arr, L + 1, R, fmap, gmap);
            int p2 = f2(arr, L, R - 1, fmap, gmap);
            ans = Math.min(p1, p2);
        }
        gmap[L][R] = ans;
        return ans;
    }

    /**
     * 方法三: 动态规划(表推导)
     * 自底向上填充:
     * 1. fmap[i][i] = arr[i]
     * 2. 按列(区间长度从小到大)扩展，使用已知更小区间结果。
     *
     * @param arr 纸牌数组
     * @return 先手最大得分
     */
    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i];
        }
        for (int startCol = 1; startCol < N; startCol++) {
            int L = 0;
            int R = startCol;
            while (R < N) {
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }
}
