package algorithm08_linkedList;


import simpleDataNode.ListNode;

/**
 * 题目：给定两个单链表的头节点 head1 和 head2，两个链表可能有环也可能无环。
 * 实现一个函数，如果两个链表相交，返回第一个相交的节点；如果不相交，返回 null。
 * 要求：时间复杂度 O(N)，额外空间复杂度 O(1)。
 */
public class class03_getIntersectNode {

    /**
     * main 方法：对数器测试入口
     *
     * @param args 程序运行参数数组
     *             此方法随机生成多种情况的链表（无环相交、无环不相交、入环点相同、入环点不同但相交、有环不相交）
     *             并调用 getIntersectNode 进行验证，打印测试结果。
     */
    public static void main(String[] args) {
        int testTimes = 10000;
        boolean success = true;

        for (int i = 0; i < testTimes; i++) {
            // 随机选取测试类型
            int type = (int) (Math.random() * 5);
            ListNode head1 = null;
            ListNode head2 = null;
            ListNode expected = null;

            switch (type) {
                case 0: // 无环相交
                    ListNode common1 = generateCommonPart(3);
                    head1 = generateListWithTail(common1, 5);
                    head2 = generateListWithTail(common1, 6);
                    expected = common1;
                    break;
                case 1: // 无环不相交
                    head1 = generateListWithTail(null, 5);
                    head2 = generateListWithTail(null, 7);
                    expected = null;
                    break;
                case 2: // 有环，入环点相同
                    ListNode loop = generateCycle(3); // 环长3
                    head1 = generateListWithTail(loop, 5);
                    head2 = generateListWithTail(loop, 6);
                    expected = loop;
                    break;
                case 3: // 有环，入环点不同但相交
                    ListNode sharedCycle = generateCycle(3);
                    ListNode loop1 = new ListNode(777);
                    loop1.next = sharedCycle;
                    ListNode loop2 = new ListNode(888);
                    loop2.next = sharedCycle;
                    head1 = generateListWithTail(loop1, 5);
                    head2 = generateListWithTail(loop2, 6);
                    expected = sharedCycle;
                    break;
                case 4: // 有环，不相交
                    ListNode cycle1 = generateCycle(3);
                    ListNode cycle2 = generateCycle(3);
                    head1 = generateListWithTail(cycle1, 5);
                    head2 = generateListWithTail(cycle2, 5);
                    expected = null;
                    break;
            }

            ListNode result = getIntersectListNode(head1, head2);
            if (result != expected) {
                System.out.println("Test Failed!");
                System.out.println("Expected: " + (expected != null ? expected.val : null));
                System.out.println("Actual  : " + (result != null ? result.val : null));
                success = false;
                break;
            }
        }

        System.out.println(success ? "All tests passed!" : "Some tests failed.");
    }

    /**
     * 将一段“前缀链表”连接到一个已有的“尾部结构”（可能是普通链表、环、或 null）上，从而构造出具有特定拓扑结构的链表。
     *
     * @param tail 指定的尾部节点（可以为 null，表示无公共尾部）
     * @param len  要生成的前置节点数（不包含 tail）
     * @return 返回生成的链表头节点；当 len 为 0 时直接返回 tail
     */
    private static ListNode generateListWithTail(ListNode tail, int len) {
        if (len == 0) {
            return tail;
        }
        ListNode head = new ListNode((int) (Math.random() * 100));
        ListNode cur = head;
        for (int i = 1; i < len; i++) {
            cur.next = new ListNode((int) (Math.random() * 100));
            cur = cur.next;
        }
        cur.next = tail;
        return head;
    }

    /**
     * 生成一个长度为 len 的公共部分链表（不成环）
     *
     * @param len 公共部分的节点数量
     * @return 返回生成的公共部分链表的头节点
     */
    private static ListNode generateCommonPart(int len) {
        ListNode head = new ListNode((int) (Math.random() * 100));
        ListNode cur = head;
        for (int i = 1; i < len; i++) {
            cur.next = new ListNode((int) (Math.random() * 100));
            cur = cur.next;
        }
        return head;
    }

    /**
     * 生成一个长度为 len 的环形链表，并返回入环点
     *
     * @param len 环的长度（入环点到再次回到入环点的节点数）
     * @return 返回环的入环点节点
     */
    private static ListNode generateCycle(int len) {
        ListNode head = new ListNode((int) (Math.random() * 100));
        ListNode cur = head;
        for (int i = 1; i < len; i++) {
            cur.next = new ListNode((int) (Math.random() * 100));
            cur = cur.next;
        }
        cur.next = head; // 自成环
        return head;
    }


    /**
     * 获取两个链表的第一个相交节点
     *
     * @param headA 链表 A 的头节点
     * @param headB 链表 B 的头节点
     * @return 如果两链表相交，返回第一个相交节点；否则返回 null
     * 说明：先判断各自是否有环，再根据无环/有环组合分别处理：
     * - 两者均无环：调用 noLoop
     * - 两者均有环：调用 bothLoop
     * - 一有一无：一定不相交，返回 null
     */
    public static ListNode getIntersectListNode(ListNode headA, ListNode headB) {
        // 任一链表为空,不可能相交，返回null
        if (headA == null || headB == null) {
            return null;
        }

        // 先判断两个链表各自会不会自己成环
        ListNode loop1 = getLoopListNode(headA);
        ListNode loop2 = getLoopListNode(headB);

        // 如果两个环各自都不成环
        if (loop1 == null && loop2 == null) {
            return noLoop(headA, headB);
        }

        // 如果两个都能各自成环，三种情况
        if (loop1 != null && loop2 != null) {
            return bothLoop(headA, headB, loop1, loop2);
        }

        // 如果一个有环，一个没环，一定不相交
        return null;
    }

    /**
     * 处理两个都有环的情况，返回第一个相交节点或 null
     *
     * @param headA 链表 A 的头节点
     * @param headB 链表 B 的头节点
     * @param loop1 链表 A 的入环节点
     * @param loop2 链表 B 的入环节点
     * @return 如果相交返回相交节点；否则返回 null
     * 说明：
     * - 若 loop1 == loop2，等同于在入环点之前判断相交，使用类似无环处理但以入环点作为终点；
     * - 若 loop1 != loop2，则在环中从 loop1 遍历一圈若遇到 loop2 则说明相交（可返回任一入环点），否则不相交。
     */
    private static ListNode bothLoop(ListNode headA, ListNode headB, ListNode loop1, ListNode loop2) {
        // 第一种情况：两个入环点相同（即是在环外相交的），
        // 和noLoop方法实现思路类似，但是在loop处中断
        if (loop1 == loop2) {
            ListNode cur1 = headA;
            ListNode cur2 = headB;
            // 长度差值
            int num = 0;
            while (cur1 != loop1) {
                num++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                num--;
                cur2 = cur2.next;
            }
            // 谁长谁是cur1，另一个则为cur2
            cur1 = num > 0 ? headA : headB;
            cur2 = cur1 == headA ? headB : headA;
            num = Math.abs(num);
            // 长链表先走长度差的步数
            while (num != 0) {
                cur1 = cur1.next;
                num--;
            }
            // 然后同步走找相交节点
            while (cur1 != cur2) {
                cur1 = cur1.next;
                cur2 = cur2.next;
            }

            return cur1;

        } else {
            // 第二种情况：入环点不同，可能在环中相交
            // 直接从某个入环节点开始遍历
            ListNode cur = loop1.next;
            while (cur != loop1) {
                // 如果两个成环节点相遇，说明在环中相交
                if (cur == loop2) {
                    return loop1;
                }
                cur = cur.next;
            }
            // 第三种情况，各自成环不相交
            return null;
        }

    }

    /**
     * 处理两个均无环链表的相交问题
     *
     * @param headA 链表 A 的头节点
     * @param headB 链表 B 的头节点
     * @return 如果相交返回第一个相交节点；否则返回 null
     * 说明：
     * - 先遍历两链表找尾节点并计算长度差，若尾节点不同则不相交；
     * - 否则将长链表先走长度差步，然后同步前进直到找到相同节点。
     */
    private static ListNode noLoop(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        ListNode cur1 = headA;
        ListNode cur2 = headB;
        // 定义计数器

        // 求出长度差,并且找到各自的尾部
        int num = 0;
        while (cur1.next != null) {
            num++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            num--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }

        // 哪个链表长就把cur1指向其头部，cur2指向另一个头部
        cur1 = num > 0 ? headA : headB;
        cur2 = cur1 == headA ? headB : headA;
        num = Math.abs(num);
        // 从头开始遍历，长的链表在长度差内肯定不会与另一个短的相交
        while (num != 0) {
            num--;
            cur1 = cur1.next;
        }
        // 长的走完长度差后，两者开始同步遍历找相交节点
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    /**
     * 使用 Floyd 判圈法找到链表的入环节点（若无环返回 null）
     *
     * @param head 链表头节点
     * @return 链表的入环节点（第一个进入环的节点）；若无环返回 null
     * 说明：
     * - 使用快慢指针寻找相遇点，若无相遇则无环；
     * - 相遇后将快指针置于头部，与慢指针同步移动，相遇点即为入环点。
     * - 数学上可以证明：从头到环入口的距离 a，等于从相遇点沿环走到环入口的距离（模环长）。因此两个指针同步走 a 步，必然在入口相遇
     */
    public static ListNode getLoopListNode(ListNode head) {
        // 如果这个链表只有少于3个节点（即自己不成环）
        // ????但是两个节点也能成环，只是用快慢指针会报错
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }

        // 定义快慢指针
        ListNode n1 = head.next;    //慢
        ListNode n2 = head.next.next;    //快

        // 第一阶段：寻找相遇点（判断是否有环）
        while (n1 != n2) {
            // 如果快指针走到空
            if (n2.next == null || n2.next.next == null) {
                return null;
            }
            // 快慢指针继续前进
            n1 = n1.next;
            n2 = n2.next.next;
        }
        // 第二阶段：从相遇点和头节点分别出发，寻找入环点
        n2 = head;
        while (n1 != n2) {
            n1 = n1.next;
            n2 = n2.next;
        }
        return n1;
    }
}
