package algorithm16_DP01;

import java.util.HashMap;

/**
 * 给定一个字符串数组 stickers，每个元素代表一张贴纸，贴纸上是由小写字母组成的字符串。
 * 你可以任意使用某张贴纸多次。每次使用一张贴纸时，可以从上面抠下一些字母（顺序无关），
 * 用来拼接目标字符串 target 的某个尚未完成的部分。请返回拼出 target 所需的最少贴纸数量；
 * 如果无法拼出，返回 -1。
 * 要求：1 <= stickers.length <= 50；1 <= stickers[i].length, target.length <= 15；全部为小写字母。
 */
public class class05_StickersToSpellWord {
    /**
     * 计算使用给定贴纸数组拼出目标字符串所需的最少贴纸数（暴力递归版本）。
     * 无法拼出返回 -1。
     *
     * @param stickers 贴纸字符串数组，每张贴纸可重复使用
     * @param target   目标字符串
     * @return 最少贴纸数；若无法拼出返回 -1
     */
    public static int minStickers1(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.isEmpty()) {
            return 0;
        }
        int ans = process1(stickers, target);
        // 如果递归返回无解（Integer.MAX_VALUE），就输出 -1
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 暴力递归核心函数：返回拼出 target 所需的最少贴纸数。
     * 尝试枚举每一张贴纸作为本层使用的第一张，减少目标后递归剩余部分。
     *
     * @param stickers 贴纸数组
     * @param target   当前需要拼出的剩余目标字符串（按字典序排列形式出现）
     * @return 最少贴纸数；若无解返回 Integer.MAX_VALUE
     */
    public static int process1(String[] stickers, String target) {
        // 如果目标已经拼完了（为空），不需要贴纸
        if (target.isEmpty()) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        // 遍历每一张贴纸，尝试拿它来拼 target
        for (String first : stickers) {
            // 用这张贴纸覆盖 target，看看剩下的部分
            String rest = minus(target, first);
            // 如果 rest 比 target 短，说明这张贴纸确实帮上了忙
            if (rest.length() != target.length()) {
                // 递归计算剩余部分需要的贴纸数
                min = Math.min(min, process1(stickers, rest));
            }
        }
        // 如果 min 还是无穷大，说明没法拼(返回MAX_VALUE),否则，需要加上当前用掉的这张贴纸（+1）
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 用贴纸字符串 s2 去“抵消”目标字符串 s1 中的字符，返回未被覆盖的剩余字符串（按字典序）。
     *
     * @param s1 目标字符串
     * @param s2 当前尝试使用的一张贴纸
     * @return 减去贴纸后仍需拼出的部分（排序后）
     */
    public static String minus(String s1, String s2) {
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[] count = new int[26];
        // 统计 s1 的字母数量
        for (char cha : str1) {
            count[cha - 'a']++;
        }
        for (char cha : str2) {
            count[cha - 'a']--;
        }
        // 拼接剩下的字符
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) (i + 'a'));
                }
            }
        }
        return builder.toString();
    }

    /**
     * 计算最少贴纸数的优化版本：预处理每张贴纸的词频，减少重复字符统计开销（仍无记忆化）。
     *
     * @param stickers 贴纸字符串数组
     * @param target   目标字符串
     * @return 最少贴纸数；无法拼出返回 -1
     */
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        // counts[i][j] 表示第 i 张贴纸里各个字符出现的次数
        int[][] counts = new int[N][26];

        // 把每张贴纸转成词频表
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }

        // 词频表和目标字符串去玩
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 基于词频数组的递归：对当前剩余目标，尝试使用包含其首字符的贴纸以剪枝。
     * 不使用缓存，可能存在大量重复子问题。
     *
     * @param stickers 贴纸的词频数组，stickers[i][c] 表示第 i 张贴纸字符 c 的出现次数
     * @param t        当前剩余目标字符串
     * @return 最少贴纸数；若无解返回 Integer.MAX_VALUE
     */
    public static int process2(int[][] stickers, String t) {
        if (t.isEmpty()) {
            return 0;
        }

        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        // 统计目标词频
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        // 遍历卡片词频表
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            // 只有包含目标首字母的贴纸才尝试
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    /**
     * 计算最少贴纸数（记忆化版本）：在词频优化基础上加入缓存，避免重复计算。
     *
     * @param stickers 贴纸字符串数组
     * @param target   目标字符串
     * @return 最少贴纸数；无法拼出返回 -1
     */
    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        // 转换成词频表
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }

        // dp 缓存：key=目标字符串，value=最少贴纸数
        HashMap<String, Integer> dp = new HashMap<>();
        // 空字符串需要 0 张贴纸
        dp.put("", 0);
        // 词频表和目标字符串、缓存表去玩
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }


    /**
     * 记忆化搜索核心：利用 HashMap 缓存每个剩余目标字符串的最优结果。
     * 对每张可用贴纸尝试覆盖，递归计算剩余部分。
     *
     * @param stickers 贴纸词频数组
     * @param t        当前剩余目标字符串
     * @param dp       记忆化缓存：key 为剩余目标字符串，value 为最少贴纸数（或 Integer.MAX_VALUE 表示无解）
     * @return 最少贴纸数；若无解返回 Integer.MAX_VALUE
     */
    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
        // 如果之前算过，就直接用缓存
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        // 统计目标字符串的词频
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        // 遍历每张贴纸
        for (int i = 0; i < N; i++) {
            // 第i张贴纸对应的词频
            int[] sticker = stickers[i];
            // 优化：只有包含目标首字母的贴纸才尝试
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                // 用当前贴纸覆盖目标字符串
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        // 当前字符剩余数量 = 目标字符数 - 贴纸能提供的数
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            // 如果还剩，就加到 rest 里
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                // 剩余未覆盖的部分
                String rest = builder.toString();
                // 递归计算剩下的最少贴纸数
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        // 如果 min 有解，就加上当前这张贴纸
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        // 结果存缓存
        dp.put(t, ans);
        return ans;
    }
}
