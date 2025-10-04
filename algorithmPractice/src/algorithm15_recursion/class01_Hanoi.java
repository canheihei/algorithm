package algorithm15_recursion;


import java.util.Stack;

/*
    A[起始柱A] -->|初始状态| B[叠放n个大小递减的圆盘]
    C[目标柱C] -->|规则| D[每次只能移动一个圆盘]
    E[辅助柱B] -->|约束| F[大盘不能压小盘]
 */
public class class01_Hanoi {

    public static void main(String[] args) {
        hanoi3(3); // 测试3层汉诺塔
    }

    // 方法一：理解递归的方法
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    private static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to right");
            return;
        }
        leftToMid(n - 1);
        System.out.println("move" + n + "from left to right");
        midToRight(n - 1);
    }

    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("move 1 from left to mid");
            return;
        }
        leftToRight(n - 1);
        System.out.println("move" + n + "from left to mid");
        rightToMid(n - 1);
    }

    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("move 1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println("move" + n + "from mid to right");
        leftToRight(n - 1);
    }

    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from mid to left");
            return;
        }
        midToRight(n - 1);
        System.out.println("move" + n + "from mid to right");
        rightToLeft(n - 1);
    }

    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to mid");
            return;
        }
        rightToLeft(n - 1);
        System.out.println("move" + n + "from right to left");
        leftToMid(n - 1);
    }

    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("move 1 from right to left");
            return;
        }
        rightToMid(n - 1);
        System.out.println("move" + n + "from right to mid");
        midToLeft(n - 1);
    }


    // 方法二：三柱法
    public static void hanoi2(int N, String from, String to, String other) {
        if (N == 1) {
            System.out.println("move" + N + "from" + from + "to" + to);
        } else {
            hanoi2(N - 1, from, other, to);
            System.out.println("move" + N + "from" + from + "to" + to);
            hanoi2(N - 1, other, to, from);
        }
    }


    // 方法三：递归改迭代的写法
    // 记录栈帧的辅助类
    private static class Record {
        boolean isFinished; // 标记当前阶段是否完成
        int diskNum;        // 当前要移动的盘子数
        String from;        // 起始柱
        String to;          // 目标柱
        String aux;         // 辅助柱

        public Record(boolean isFinished, int diskNum, String from, String to, String aux) {
            this.isFinished = isFinished;
            this.diskNum = diskNum;
            this.from = from;
            this.to = to;
            this.aux = aux;
        }
    }

    private static void hanoi3(int N) {
        if (N < 1) {
            return;
        }

        Stack<Record> stack = new Stack<>();
        stack.push(new Record(false, N, "left", "right", "mid"));

        while (!stack.isEmpty()) {
            Record cur = stack.pop();
            if (cur.diskNum == 1) {
                // 基础情况：直接移动最小盘子
                System.out.println("Move  1 from " + cur.from + " to " + cur.to);
                // 表示第一阶段完成
                if (!stack.isEmpty()) {
                    stack.peek().isFinished = true;
                }
            } else {
                if (!cur.isFinished) {
                    // 阶段1：处理n-1个盘子从from到aux
                    stack.push(cur);    // 重新压入当前任务（标记为已完成阶段1）
                    stack.push(new Record(false, cur.diskNum - 1, cur.from, cur.aux, cur.to));
                } else {
                    // 阶段2：移动当前最大盘子
                    System.out.println("Move  " + cur.diskNum + " from " + cur.from + " to " + cur.to);

                    // 阶段3：处理n-1个盘子从aux到to
                    stack.push(new Record(false, cur.diskNum - 1, cur.aux, cur.to, cur.from));
                }
            }
        }
    }

}
