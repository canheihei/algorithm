package algorithm16_DP01;

/**
 * 给定一个只包含数字字符的字符串(可能包含字符'0')，按照映射规则 1->A, 2->B, ..., 26->Z，
 * 将该字符串转化为字母串的方式总数。'0' 不能被单独转换，只能作为 "10" 或 "20" 的一部分；
 * 若出现非法情况（如单独的'0'或"30"等），则该位置无效。求所有合法转换的种数。
 * 例如: "111" 可被转换为 (1,1,1)->"AAA", (11,1)->"KA", (1,11)->"AK"，共 3 种。
 */
public class class04_ConvertToLetterString {

    /**
     * 方法一: 递归入口
     * 功能: 对外提供接口，处理空串情况并启动递归过程。
     *
     * @param str 原始数字字符串
     * @return 合法转化方式的总数；若字符串为空返回0
     */
    public static int number(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        return process(str.toCharArray(), 0);
    }

    /**
     * 方法一-递归主体
     * 功能: 处理从位置 i 开始的子串，有多少种合法转换。
     * 递归逻辑:
     * 1. i 到达末尾，表示形成一种完整方案，返回1
     * 2. 若当前字符为 '0'，非法，返回0
     * 3. 尝试:
     * a) 单独使用 str[i]
     * b) 若 i 与 i+1 组成的两位数在 10~26，则再尝试跳两步
     *
     * @param str 已转为字符数组的数字串
     * @param i   当前处理的位置索引
     * @return 从 i 开始的合法转换方式数
     */
    public static int process(char[] str, int i) {
        if (i == str.length) {
            return 1;
        }
        // 0单独传递过来，说明没被消化掉，故无效
        if (str[i] == '0') {
            return 0;
        }
        // 一个数不管如何1-9都可以转为对应单个字母
        int ways = process(str, i + 1);
        // 两个数
        if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
            ways += process(str, i + 2);
        }
        return ways;
    }

    /**
     * 方法二: 动态规划
     * 功能: 采用自底向上DP消除重复计算。
     * 状态定义: dp[i] = 从位置 i 到末尾的合法转换方式数
     * 转移:
     * 若 str[i]=='0' -> dp[i]=0
     * 否则:
     * dp[i] = dp[i+1] (单字符)
     * 若 i+1 可与 i 组成 10~26 -> 加上 dp[i+2]
     * 初始化: dp[N]=1 表示越界后视为形成一种完整方案
     *
     * @param s 原始数字字符串
     * @return 合法转化方式总数
     */
    public static int dp(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            if (str[i] != '0') {
                int ways = dp[i + 1];
                if (i + 1 < str.length && (str[i] - '0') * 10 + str[i + 1] - '0' < 27) {
                    ways += dp[i + 2];
                }
                dp[i] = ways;
            }

        }

        return dp[0];
    }

    // 对数器
    public static void main(String[] args) {
        System.out.println(number("7210231231232031203123"));
        System.out.println(dp("7210231231232031203123"));
    }
}
