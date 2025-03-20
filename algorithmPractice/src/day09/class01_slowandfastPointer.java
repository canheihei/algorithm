package day09;

import day03.Node;

import java.util.Random;

public class class01_slowandfastPointer {
	public static void main(String[] args) {

	}

	public static class Node{
		int value;
		Node next;
		public Node(int value){
			this.value = value;
			this.next = null;
		}
	}

	// 输入链表头节点，奇数长度返回中点，偶数长度返回上中点
	// 比如1->2->3->4->5->6,返回3->4中的3
	public static Node midOrUpMidNode(Node head) {
		//如果链表不存在或者链表只有一个或者两个节点，返回head即可
		if(head == null || head.next == null || head.next.next == null){
			return head;
		}
		//存在三个及以上节点
		Node slow = head.next;
		Node fast = head.next.next;
		while(fast.next != null || fast.next.next != null){
			// slow走一步，fast速度为slow两倍，即两步
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	// 输入链表头节点，奇数长度返回中点，偶数长度返回下中点
	// 比如1->2->3->4->5->6,返回3->4中的4
	public static Node midOrDownNode(Node head){
		//如果链表不存在或者链表只有一个节点，返回head即可
		if(head == null || head.next == null){
			return head;
		}

		Node slow = head;
		Node fast = head.next;
		while(fast.next != null && fast.next.next != null){
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

	// 输入链表头节点，奇数长度返回中点，偶数长度返回上中点前一个
	// 比如1->2->3->4->5->6,返回2->3中的2
	public static Node midOrUpMidPreNode(Node head){
		if(head == null || head.next == null || head.next.next == null){
			return head;
		}
		Node slow = head;
		Node fast = head.next.next;
		while(fast.next != null && fast.next.next != null){
			slow = slow.next;
			fast = fast.next.next;
		}
		return slow;
	}

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


	//生成随机链表
	public static Node generateRandomLinkedList(int maxSize,int maxValue){
		Random random = new Random();
		int size = random.nextInt(maxSize) + 1;	// 链表长度范围为 [1, maxSize]
		Node head = new Node(random.nextInt(maxValue));
		Node cur = head;
		for (int i = 0; i < size; i++) {
			cur.next = new Node(random.nextInt(maxValue));
			cur = cur.next;
		}
		return head;
	}

	public static void printLinkedList(Node head){
		Node cur = head;

	}
}
