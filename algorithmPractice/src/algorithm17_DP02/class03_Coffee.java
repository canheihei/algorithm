package algorithm17_DP02;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 有一批咖啡机，每台咖啡机制作一杯咖啡需要的时间不同。
 * 现在有 n 个人依次去取咖啡（不并行抢占，同一时间谁先空闲就谁服务），每个人拿到咖啡后要清洗自己的杯子。
 * 清洗方式有两种：
 * 1.用一台只有一条流水线的洗杯机清洗，每次清洗耗时 a，且串行进行；
 * 2.自然风干，耗时 b（多个杯子可同时风干）。
 * 假设每个人一旦咖啡制作完成，就立刻决定其杯子是放入洗杯机排队清洗还是选择风干，
 * 决定后不可更改。要求安排每个杯子的清洗方式，使得所有杯子都变干(清洗或风干完成)的最早时间最小，返回该最短完成时间。
 * 给定：
 * 数组 arr：每台咖啡机制作一杯咖啡的耗时；
 * n：取咖啡的人数；
 * a：洗杯机洗一个杯子的耗时；
 * b：自然风干一个杯子的耗时。
 * 求最优策略下所有杯子都干的最早时间。
 */
public class class03_Coffee {
    /**
     * 咖啡机实体，描述一台机器当前可用时间点与制作一杯咖啡所需固定时间。
     * timePoint 表示该机器下一次可以开始制作的时间
     * workTime 表示制作一杯咖啡所需时间
     */
    public static class Machine {
        public int timePoint;
        public int workTime;

        public Machine(int t, int w) {
            timePoint = t;
            workTime = w;
        }
    }

    public static class MachineComparator implements Comparator<Machine> {
        /**
         * 咖啡机优先级比较器。
         * 按照“当前这台机器完成下一杯咖啡的时间 = timePoint + workTime”升序排序，
         * 使得每次都选择最早能完成下一杯的机器。
         *
         * @param o1 第一台咖啡机
         * @param o2 第二台咖啡机
         * @return 正、负或零以决定优先级顺序
         */
        @Override
        public int compare(Machine o1, Machine o2) {
            return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
        }
    }

    /**
     * 递归暴力过程（未做记忆化）：对 index 号杯子决策“洗”或“风干”。
     *
     * @param drinks   每个人拿到咖啡（杯子产生）的时间数组
     * @param a        洗杯机清洗时间
     * @param b        风干时间
     * @param index    当前处理的杯子下标
     * @param washline 洗杯机当前可用（空闲）时间线
     * @return 从 index 开始到结束所有杯子都干的最早完结时间
     */
    public static int process(int[] drinks, int a, int b, int index, int washline) {
        if (index == drinks.length) {
            return 0;
        }
        int selfClean1 = Math.max(drinks[index], washline) + a;
        int restClean1 = process(drinks, a, b, index + 1, washline);
        int p1 = Math.max(selfClean1, restClean1);

        int selfClean2 = drinks[index] + b;
        int restClean2 = process(drinks, a, b, index + 1, washline);
        int p2 = Math.max(selfClean2, restClean2);

        return Math.min(p1, p2);
    }

    /**
     * 主函数：计算最早让所有杯子干燥完成的时间（含洗或风干）。
     * 步骤：
     * 1. 用小根堆模拟 n 次取咖啡，得到每个人拿到咖啡的时间数组 drinks
     * 2. 对该完成时间数组做“洗 or 风干”决策的动态规划求最优
     *
     * @param arr 各咖啡机单杯制作时间
     * @param n   人数（需要制作的杯子数）
     * @param a   洗杯机清洗单杯耗时
     * @param b   自然风干单杯耗时
     * @return 所有杯子最终都干的最早时间
     */
    public static int minTime2(int[] arr, int n, int a, int b) {
        PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
        for (int j : arr) {
            heap.add(new Machine(0, j));
        }
        int[] drinks = new int[n];
        for (int i = 0; i < n; i++) {
            Machine cur = heap.poll();
            cur.timePoint += cur.workTime;
            // 第i个人喝完的时间
            drinks[i] = cur.timePoint;
            heap.add(cur);
        }
        return bestTimeDp(drinks, a, b);
    }

    /**
     * 动态规划求最优完成时间（对递归的 DP 改写）。
     * dp[index][free] 表示：处理到第 index 个杯子、洗杯机在时间 free 可用时，
     * 使 index..末尾 所有杯子都干的最早时间。
     * 状态转移：
     * 1. 选“洗”：selfClean1 = max(drinks[index], free) + wash
     * 递归到 dp[index+1][selfClean1]，结果为两者最大值
     * 2. 选“风干”：selfClean2 = drinks[index] + air
     * 递归到 dp[index+1][free]，取最大值
     * 取两种方案的较小者
     *
     * @param drinks 拿到咖啡的时间数组
     * @param wash   洗杯机清洗时间 a
     * @param air    风干时间 b
     * @return 所有杯子完成干燥的最短时间
     */
    public static int bestTimeDp(int[] drinks, int wash, int air) {
        int N = drinks.length;
        int maxFree = 0;
        for (int drink : drinks) {
            maxFree = Math.max(maxFree, drink) + wash;
        }
        int[][] dp = new int[N + 1][maxFree + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                int selfClean1 = Math.max(drinks[index], free) + wash;
                if (selfClean1 > maxFree) {
                    continue;
                }
                int restClean1 = dp[index + 1][selfClean1];
                int p1 = Math.max(selfClean1, restClean1);

                int selfClean2 = drinks[index] + air;
                int restClean2 = dp[index + 1][free];
                int p2 = Math.max(selfClean2, restClean2);
                dp[index][free] = Math.min(p1, p2);
            }
        }
        return dp[0][0];
    }
}
