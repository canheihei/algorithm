package algorithm08_linkedList;

import java.util.Random;

/**
 * 单链表相关的快慢指针练习与工具方法集合。
 * 包含4类“中点 / 中点前一个”查找策略
 */
public class class01_slowandfastPointer {
    // 对数器
    public static void main(String[] args) {

    }

    public static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    /**
     * 功能：返回链表的“中点或上中点”。
     * 定义：
     * - 若链表长度为奇数，返回正中间节点；
     * - 若链表长度为偶数，返回两个中间节点中靠前（上方）的那个。
     * 举例：1→2→3→4→5→6 返回节点3；1→2→3→4→5 返回节点3。
     * 思路：使用快慢指针，fast 每次走2步，slow 每次走1步；根据初始指针位置控制落点。
     * 边界：
     * - 长度 < 3 时直接返回 head。
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 中点（奇数）或上中点（偶数）
     */
    public static Node midOrUpMidNode(Node head) {
        //如果链表不存在或者链表只有一个或者两个节点，返回head即可
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        //存在三个及以上节点
        Node slow = head.next;
        Node fast = head.next.next;
        while (fast.next != null || fast.next.next != null) {
            // slow走一步，fast走两步
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 功能：返回链表的“中点或下中点”。
     * 定义：
     * - 若链表长度为奇数，返回正中间节点；
     * - 若链表长度为偶数，返回两个中间节点中靠后（下方）的那个。
     * 举例：1→2→3→4→5→6 返回节点4；1→2→3→4→5 返回节点3。
     * 思路：快指针起点位置不同于上一个函数，确保偶数时 slow 落在靠后节点。
     * 边界：
     * - 长度为0或1：返回 head。
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 中点（奇数）或下中点（偶数）
     */
    public static Node midOrDownNode(Node head) {
        //如果链表不存在或者链表只有一个节点，返回head即可
        if (head == null || head.next == null) {
            return head;
        }

        // 链表有两个节点
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 功能：返回“中点前一个”或“上中点前一个”。
     * 定义：
     * - 奇数长度：返回中点的前一个；
     * - 偶数长度：返回上中点的前一个。
     * 举例：1→2→3→4→5→6 返回节点2（上中点3的前一个）；1→2→3→4→5 返回节点2（中点3的前一个）。
     * 边界：
     * - 长度 < 3：直接返回 head（题目约定策略）。
     * 思路：调整 fast 初始位置，使 slow 停在目标前一个。
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 目标节点；长度过短按约定返回 head
     */
    public static Node midOrUpMidPreNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    /**
     * 功能：返回“中点前一个”或“下中点前一个”。
     * 定义：
     * - 奇数长度：返回中点前一个；
     * - 偶数长度：返回下中点前一个。
     * 举例：1→2→3→4→5→6 返回节点3（下中点4的前一个）；1→2→3→4→5 返回节点2（中点3的前一个）。
     * 边界：
     * - 长度 0 或 1：返回 null（无前驱）；
     * - 长度 2：返回 head（第一个的前驱视为 null，按策略返回第一个）。
     * 思路：快指针起点与循环条件配合，确保 slow 最终位置正确。
     * 时间复杂度：O(N)
     * 空间复杂度：O(1)
     *
     * @param head 链表头节点
     * @return 目标节点；无合法前驱时返回 null
     */
    public static Node midOrDownMidPreNode(Node head) {
        //和上中点很像，但是边缘条件不一样，两个节点返回的是null而不是head
        if (head == null || head.next == null) {
            return null;
        }
        if (head.next.next == null) {
            return head;
        }
        Node slow = head;
        Node fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }


    /**
     * 生成一个随机单链表。
     * 长度范围：[1, maxSize]；节点值范围：[0, maxValue)
     *
     * @param maxSize  最大长度（正整数）
     * @param maxValue 节点值上限（不含）
     * @return 随机链表头节点
     */
    public static Node generateRandomLinkedList(int maxSize, int maxValue) {
        Random random = new Random();
        int size = random.nextInt(maxSize) + 1;    // 链表长度范围为 [1, maxSize]
        Node head = new Node(random.nextInt(maxValue));
        Node cur = head;
        for (int i = 0; i < size; i++) {
            cur.next = new Node(random.nextInt(maxValue));
            cur = cur.next;
        }
        return head;
    }

    /**
     * 打印链表所有节点值，以空格分隔，末尾换行。
     *
     * @param head 链表头节点；若为 null 打印空行
     */
    public static void printLinkedList(Node head) {
        Node cur = head;
        while (cur != null) {
            System.out.print(cur.value + " ");
            cur = cur.next;
        }
        System.out.println();
    }

    /**
     * 计算链表长度。
     *
     * @param head 链表头节点
     * @return 节点总数；空链表返回0
     */
    public static int getLinkedListLength(Node head) {
        int length = 0;
        Node cur = head;
        while (cur != null) {
            length++;
            cur = cur.next;
        }
        return length;
    }

    /**
     * 对数器：验证 midOrUpMidNode 的返回是否正确。
     * 思路：
     * - 遍历收集节点到数组；
     * - 依据长度 L：
     * * L < 3 期望就是 head；
     * * L 为奇数：期望 index = L/2；
     * * L 为偶数：期望 index = (L/2) - 1（上中点）
     * - 比较传入的 midOrUpMid 是否为期望节点。
     *
     * @param head       原链表头
     * @param midOrUpMid 被测函数返回的节点
     * @return true 表示正确；false 表示错误
     */
    public static boolean isMidOrUpMidNodeCorrect(Node head, Node midOrUpMid) {


        return false;
    }
}
