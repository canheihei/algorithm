package algorithm16_DP01;

/*
你有一堆贴纸（每张贴纸上写着一些小写字母），你可以使用贴纸上的字母去拼目标单词 target。
目标：用最少的贴纸把目标单词拼出来，如果拼不出来，返回 -1。
 */
public class class05 {
    public static int minStickers1(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null) {
            return 0;
        }
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        for (String firstSticker : stickers) {
            String restTarget = minus(firstSticker, target);
            if (restTarget.length() != target.length()) {
                min = Math.min(min, process1(stickers, restTarget));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    private static String minus(String firstSticker, String target) {
        char[] str1 = firstSticker.toCharArray();
        char[] str2 = target.toCharArray();

        char[] countWords = new char[26];
        for (char c1 : str1) {
            countWords[c1 - 'a']++;
        }
        for (char c2 : str2) {
            countWords[c2 - 'a']--;
        }

        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < 26; i++) {
            if (countWords[i] > 0) {
                for (int j = 0; j < countWords[i]; j++) {
                    builder.append((char) ('a' + i));
                }
            }
        }
        return builder.toString();
    }

    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] stickerSum = new int[N][26];

        for (int i = 0; i < N; i++) {
            char[] chars = stickers[i].toCharArray();
            for (char aChar : chars) {
                stickerSum[i][aChar - 'a']++;
            }
        }
        int ans = process2(stickerSum, target);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(int[][] stickerSum, String target) {
        if (target.length() == 0) {
            return 0;
        }
        // 统计出target的词频
        int[] targetSum = new int[26];
        char[] targetArr = target.toCharArray();
        for (char c : targetArr) {
            targetSum[c - 'a']++;
        }

        //第一个字符匹配才开始
        int N = stickerSum.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickerSum[i];
            // 包含目标卡片首字母才尝试
            if (sticker[targetSum[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetSum[j] > 0) {
                        int minu = targetSum[j] - sticker[j];
                        for (int k = 0; k < minu; minu++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                // 剩余待拼凑的目标字符
                String rest = builder.toString();
                min = Math.min(min, process2(stickerSum, rest));
            }
        }
        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }
}
