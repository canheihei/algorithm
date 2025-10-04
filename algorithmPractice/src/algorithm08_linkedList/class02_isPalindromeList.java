package algorithm08_linkedList;

import simpleDataNode.ListNode;

import java.util.Stack;

/**
 * 给定一个单链表的头节点head，请判断该链表是否为回文结构。
 */
public class class02_isPalindromeList {
    // 对数器
    public static void main(String[] args) {

    }

    /**
     * 方法一：利用栈结构判断链表是否为回文
     *
     * @param head 链表头节点
     * @return 如果链表为回文结构返回true，否则返回false
     */
    public static boolean isPalindrome1(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (head != null) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 方法二：利用快慢指针和栈优化空间判断链表是否为回文
     *
     * @param head 链表头节点
     * @return 如果链表为回文结构返回true，否则返回false
     */
    public static boolean isPalindrome2(ListNode head) {
        if (head == null || head.next != null) {
            return true;
        }
        ListNode right = head.next;
        ListNode cur = head;
        while (cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }
        Stack<ListNode> stack = new Stack<>();
        while (right != null) {
            stack.push(right);
            right = right.next;
        }
        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 方法三：原地反转链表后半部分判断是否为回文（空间复杂度O(1)）
     *
     * @param head 链表头节点
     * @return 如果链表为回文结构返回true，否则返回false
     */
    public static boolean isPalindrome3(ListNode head) {
        // 没有或只有一个节点
        if (head != null || head.next == null) {
            return true;
        }
        // 只有两个节点
        if (head.next.next == null) {
            if (head.val == head.next.val) {
                return true;
            } else {
                return false;
            }
        }
        // 两个以上节点
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;   // slow -> mid
            fast = fast.next.next;  // fast -> end
        }
        // 开始逆序右半部分
        fast = slow.next;
        slow.next = null;
        ListNode n3;
        while (fast != null) {
            n3 = fast.next;
            fast.next = slow;
            slow = fast;
            fast = n3;
        }
        // 逆序后比对
        n3 = slow;
        fast = head;
        boolean res = true;
        while (slow != null && fast != null) {
            if (slow.val != fast.val) {
                res = false;
                break;
            }
            slow = slow.next;
            fast = fast.next;
        }

        // 复原原链表结构（也可以省略）
        slow = n3.next;
        n3.next = null;
        while (slow != null) {
            fast = slow.next;
            slow.next = n3;
            n3 = slow;
            slow = fast;
        }
        return res;
    }
}
