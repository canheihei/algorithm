package algorithm08_linkedList;

import simpleDataNode.ListNode;

/**
 * 题目：给定单链表的头节点 head 和一个整数 pivot，请将链表调整为：
 * 所有节点值 < pivot 的节点在前，== pivot 的在中，> pivot 的在后。
 * 允许改变节点的 next 指针，不要求在各分区内保持原相对次序。
 * 返回调整后的链表头节点。
 */
public class class04_SmallerEqualBigger {
    /**
     * 方法一：数组 + 荷兰国旗划分
     * 思路：先遍历链表收集到数组，再对数组做三向划分，最后按新顺序重新串联 next。
     * 时间复杂度 O(N)，空间复杂度 O(N)。
     *
     * @param head  单链表头节点
     * @param pivot 基准值，用于将链表划分为三段（<、==、>）
     * @return 调整后链表的新头节点
     */
    public static ListNode listPartition1(ListNode head, int pivot) {
        // 0个节点
        if (head == null) {
            return head;
        }
        // 一个及一个以上节点
        ListNode cur = head;
        int i = 0;
        while (cur != null) {
            i++;
            cur = cur.next;
        }
        // 构造等长数组
        ListNode[] nodeArr = new ListNode[i];
        cur = head;
        for (i = 0; i != nodeArr.length; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        // 荷兰国旗划分
        arrPartition(nodeArr, pivot);
        // 重构链表
        for (i = 1; i != nodeArr.length; i++) {
            nodeArr[i - 1].next = nodeArr[i];
        }
        nodeArr[i - 1].next = null;
        return nodeArr[0];
    }

    /**
     * 工具函数：对节点数组做“荷兰国旗”三向划分
     * 划分结果满足：
     * [0 .. small]          区间内节点值 < pivot
     * [small+1 .. big-1]    区间内节点值 == pivot
     * [big .. end]          区间内节点值 > pivot
     * 仅重排数组中的引用，不创建新节点。
     *
     * @param nodeArr 以遍历顺序收集的链表节点数组
     * @param pivot   基准值
     */
    public static void arrPartition(ListNode[] nodeArr, int pivot) {
        int small = -1;
        int big = nodeArr.length;
        int index = 0;
        while (index != big) {
            if (nodeArr[index].val < pivot) {
                swap(nodeArr, ++small, index++);
            } else if (nodeArr[index].val == pivot) {
                index++;
            } else {
                swap(nodeArr, --big, index);
            }
        }
    }

    /**
     * 工具函数：交换数组中两个位置的节点引用
     *
     * @param nodeArr 节点数组
     * @param a       待交换的第一个位置
     * @param b       待交换的第二个位置
     */
    private static void swap(ListNode[] nodeArr, int a, int b) {
        ListNode tmp = nodeArr[a];
        nodeArr[a] = nodeArr[b];
        nodeArr[b] = tmp;
    }

    public static ListNode listPartition2(ListNode head, int pivot) {
        ListNode sH = null;
        ListNode sT = null;
        ListNode eH = null;
        ListNode eT = null;
        ListNode mH = null;
        ListNode mT = null;
        ListNode next = null;

        while (head != null) {
            next = head.next;
            head.next = null;
            if (head.val < pivot) {
                if (sH == null) {
                    sH = head;
                    sT = head;
                } else {
                    sT.next = head;
                    sT = head;
                }
            } else if (head.val == pivot) {
                if (eH == null) {
                    eH = head;
                    eT = head;
                } else {
                    eT.next = head;
                    eT = head;
                }
            } else {
                if (mH == null) {
                    mH = head;
                    mT = head;
                }
            }
            head = next;
        }
        // 构建整个链表
        if (sT != null) {
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }
        // 如果有小于区域
        if (sT != null) {
            sT.next = eH;
            eT = eT == null ? sT : eT;
        }
        //下一步，一定是需要用eT去接大于区域的头
        //有等于区域，eT->等于区域的尾结点
        //无等于区域，et->小区

        if (eT != null) {
            eT.next = mH;
        }
        return sH != null ? sH : (eH != null ? eH : mH);
    }
}
