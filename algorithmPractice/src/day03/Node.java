package day03;

public class Node<Integer> {
	Integer data;
	Node<Integer> prev;
	Node<Integer> next;

	public Node(Integer data) {
		this.data  = data;
		this.prev  = null;
		this.next  = null;
	}
}