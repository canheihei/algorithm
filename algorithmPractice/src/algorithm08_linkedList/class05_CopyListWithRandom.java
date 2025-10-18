package algorithm08_linkedList;

import java.util.HashMap;

/**
 * 题目：复制带随机指针的链表
 * 给定一个链表，每个节点包含数值 value、后继指针 next，以及可指向任意节点或 null 的随机指针 rand。
 * 请在不破坏原链表的前提下，返回该链表的深拷贝头节点，使新链表的 value 相同，
 * 且 next、rand 的指向关系与原链表一致。边界情况包括：空链表、单节点、rand 指向自身等。
 */
public class class05_CopyListWithRandom {
    public static class Node {
        public int value;
        public Node next;
        public Node rand;

        public Node(int data) {
            this.value = data;
        }
    }

    /**
     * 方法一: 哈希表映射拷贝
     * 思路:
     * 1\. 首次遍历用 HashMap 建立「原节点 -> 新节点」的映射；
     * 2\. 二次遍历依据映射回填新节点的 `next` 与 `rand`。
     * 时间复杂度: O(N)
     * 空间复杂度: O(N)
     *
     * @param head 原链表头节点，可能为 `null`。
     * @return 返回深拷贝后的新链表头节点；若 `head` 为 `null` 则返回 `null`。
     */
    public static Node copyListWithRand1(Node head) {
        HashMap<Node, Node> map = new HashMap<Node, Node>();
        Node cur = head;
        while (cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }

    /**
     * 方法二: 原地交织节点拷贝
     * 思路:
     * 1\. 第一遍在每个原节点后插入其拷贝节点；
     * 2\. 第二遍利用原节点的 `rand`，为拷贝节点设置 `rand`（指向 `cur.rand.next`）；
     * 3\. 第三遍将交织链表拆分为原链表与新链表，并恢复原链表结构。
     * 时间复杂度: O(N)
     * 空间复杂度: O(1)
     *
     * @param head 原链表头节点，可能为 `null`。
     * @return 返回深拷贝后的新链表头节点；若 `head` 为 `null` 则返回 `null`。
     */
    public static Node copyListWithRand2(Node head) {
        if (head == null) {
            return null;
        }
        Node cur = head;
        Node next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = new Node(cur.value);
            cur.next.next = next;
            cur = next;
        }
        cur = head;
        Node curCopy = null;
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            curCopy.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }
        Node res = head.next;
        cur = head;
        while (cur != null) {
            next = cur.next.next;
            curCopy = cur.next;
            cur.next = next;
            curCopy.next = next != null ? next.next : null;
            cur = next;
        }
        return res;
    }
}
