package algorithm17_DP01;

import java.util.HashMap;

public class class05_StickersToSpellWord {
    // 方法1：最暴力的递归解法
    public static int minStickers1(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null) {
            return 0;
        }
        int ans = process1(stickers, target);
        // 如果递归返回无解（Integer.MAX_VALUE），就输出 -1
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 递归函数：返回拼出 target 所需的最少贴纸数
    public static int process1(String[] stickers, String target) {
        // 如果目标已经拼完了（为空），不需要贴纸
        if (target.length() == 0) {
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

    // minus函数：用 s2 覆盖掉 s1，返回剩余的部分
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

    // 方法2：优化递归，用词频表来加速
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

    // 方法2递归实现（没加记忆化）
    public static int process2(int[][] stickers, String t) {
        if (t.length() == 0) {
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

    // 方法3：记忆化搜索，避免重复计算
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

    // 记忆化搜索递归函数
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
