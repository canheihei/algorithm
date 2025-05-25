package algorithm10;


/*给定两个可能有环也可能无环的单链表，头节点head1和head2。
请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。如果不相交，返回null
【要求】
如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)。*/
public class class01_getIntersectNode {
	// 定义链表节点类
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// 对数器测试
	public static void main(String[] args) {
		int testTimes = 10000;
		boolean success = true;

		for (int i = 0; i < testTimes; i++) {
			// 随机选取测试类型
			int type = (int)(Math.random() * 5);
			Node head1 = null, head2 = null;
			Node expected = null;

			switch (type) {
				case 0: // 无环相交
					Node common1 = generateCommonPart(3);
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
					Node loop = generateCycle(3); // 环长3
					head1 = generateListWithTail(loop, 5);
					head2 = generateListWithTail(loop, 6);
					expected = loop;
					break;
				case 3: // 有环，入环点不同但相交
					Node sharedCycle = generateCycle(3);
					Node loop1 = new Node(777);
					loop1.next = sharedCycle;
					Node loop2 = new Node(888);
					loop2.next = sharedCycle;
					head1 = generateListWithTail(loop1, 5);
					head2 = generateListWithTail(loop2, 6);
					expected = sharedCycle;
					break;
				case 4: // 有环，不相交
					Node cycle1 = generateCycle(3);
					Node cycle2 = generateCycle(3);
					head1 = generateListWithTail(cycle1, 5);
					head2 = generateListWithTail(cycle2, 5);
					expected = null;
					break;
			}

			Node result = class01_getIntersectNode.getIntersectNode(head1, head2);
			if (result != expected) {
				System.out.println("Test Failed!");
				System.out.println("Expected: " + (expected != null ? expected.value : null));
				System.out.println("Actual  : " + (result != null ? result.value : null));
				success = false;
				break;
			}
		}

		System.out.println(success ? "All tests passed!" : "Some tests failed.");
	}

	// 生成尾部为 common 的链表
	private static Node generateListWithTail(Node tail, int len) {
		if (len == 0) {
			return tail;
		}
		Node head = new Node((int)(Math.random() * 100));
		Node cur = head;
		for (int i = 1; i < len; i++) {
			cur.next = new Node((int)(Math.random() * 100));
			cur = cur.next;
		}
		cur.next = tail;
		return head;
	}

	// 生成公共部分链表
	private static Node generateCommonPart(int len) {
		Node head = new Node((int)(Math.random() * 100));
		Node cur = head;
		for (int i = 1; i < len; i++) {
			cur.next = new Node((int)(Math.random() * 100));
			cur = cur.next;
		}
		return head;
	}

	// 生成一个指定长度的环链表（返回入环点）
	private static Node generateCycle(int len) {
		Node head = new Node((int)(Math.random() * 100));
		Node cur = head;
		for (int i = 1; i < len; i++) {
			cur.next = new Node((int)(Math.random() * 100));
			cur = cur.next;
		}
		cur.next = head; // 自成环
		return head;
	}


	public static Node getIntersectNode(Node headA, Node headB) {
		// 任一链表为空,不可能相交，返回null
		if (headA == null || headB == null) {
			return null;
		}

		// 先判断两个链表各自会不会自己成环
		Node loop1 = getLoopNode(headA);
		Node loop2 = getLoopNode(headB);
		// 如果两个环各自都不成环
		if (loop1 == null && loop2 == null) {
			return noLoop(headA,headB);
		}

		// 如果两个都能各自成环，三种情况
		if(loop1 != null && loop2 != null) {
			return bothLoop(headA,headB,loop1,loop2);
		}

		// 如果一个有环，一个没环，一定不相交
		return null;
	}

	private static Node bothLoop(Node headA, Node headB, Node loop1, Node loop2) {
		// 第一种情况：两个入环点相同（即是在环外相交的），
		// 和noLoop方法实现思路类似，但是在loop处中断
		if(loop1 == loop2) {
			Node cur1 = headA;
			Node cur2 = headB;
			// 长度差值
			int num = 0;
			while(cur1 != loop1) {
				num++;
				cur1 = cur1.next;
			}
			while(cur2 != loop2) {
				num--;
				cur2 = cur2.next;
			}
			// 谁长谁是cur1，另一个则为cur2
			cur1 = num > 0 ? headA : headB;
			cur2 = cur1 == headA ? headB : headA;
			num = Math.abs(num);
			// 长链表先走长度差的步数
			while(num != 0){
				cur1 = cur1.next;
				num--;
			}
			// 然后同步走找相交节点
			while(cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}

			return cur1;

		}else{
			// 第二种情况：入环点不同，可能在环中相交
			// 直接从某个入环节点开始遍历
			Node cur = loop1.next;
			while(cur != loop1) {
				// 如果两个成环节点相遇，说明在环中相交
				if(cur == loop2) {
					return loop1;
				}
				cur = cur.next;
			}
			// 第三种情况，各自成环不相交
			return null;
		}

	}

	// 如果各自不成环，那要么一定共享后续部分造成相交，要么不相交
	private static Node noLoop(Node headA, Node headB) {
		if(headA == null || headB == null) {
			return null;
		}
		Node cur1 = headA;
		Node cur2 = headB;
		// 定义计数器

		// 求出长度差,并且找到各自的尾部
		int num = 0;
		while(cur1.next != null) {
			num++;
			cur1 = cur1.next;
		}
		while(cur2.next != null) {
			num--;
			cur2 = cur2.next;
		}
		if(cur1 != cur2) {
			return null;
		}

		// 哪个链表长就把cur1指向其头部，cur2指向另一个头部
		cur1 = num > 0 ? headA : headB;
		cur2 = cur1 == headA ? headB : headA;
		num = Math.abs(num);
		// 从头开始遍历，长的链表在长度差内肯定不会与另一个短的相交
		while(num != 0){
			num--;
			cur1 = cur1.next;
		}
		// 长的走完长度差后，两者开始同步遍历找相交节点
		while(cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	/*如果有限状态机、迭代函数或者链表上存在环，
	那么在某个环上以不同速度前进的2个指针必定会在某个时刻相遇。
	同时显然地，如果从同一个起点(即使这个起点不在某个环上)
	同时开始以不同速度前进的2个指针最终相遇，那么可以判定存在一个环，
	且可以求出2者相遇处所在的环的起点与长度。*/
	// Floyd判圈法
	public static Node getLoopNode(Node head) {
		// 如果这个链表只有少于3个节点（即自己不成环）
		// ????但是两个节点也能成环，只是用快慢指针会报错
		if(head == null || head.next == null || head.next.next == null){
			return null;
		}

		// 定义快慢指针
		Node n1 = head.next;	//慢
		Node n2 = head.next.next;	//快

		// 第一阶段：寻找相遇点（判断是否有环）
		while(n1 != n2){
			// 如果快指针走到空
			if(n2.next == null || n2.next.next == null){
				return null;
			}
			// 快慢指针继续前进
			n1 = n1.next;
			n2 = n2.next.next;
		}
		// 第二阶段：从相遇点和头节点分别出发，寻找入环点
		n2 = head;
		while(n1 != n2){
			n1 = n1.next;
			n2 = n2.next;
		}
		return n1;
	}
}
