package day03;


class LinkedListStack<Integer>{
	private Node<Integer> head;

	public void push(Integer data) {
		Node<Integer> newNode = new Node<>(data);
		if (head != null) {
			newNode.next = head;
			head.prev = newNode;
		}
		head = newNode;
	}

	public Integer pop() {
		if(head==null){
			System.out.println("Stack is empty");
			return null;
		}else{
			Integer data = head.data;
			head=head.prev;
			head.next=null;
			return data;
		}
	}
}




