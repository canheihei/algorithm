package algorithm03;


import algorithm00_common.TwoWayNode;

class dequeneStack<Integer>{
	private TwoWayNode<Integer> head;

	public void push(Integer data) {
		TwoWayNode<Integer> newTwoWayNode = new TwoWayNode<>(data);
		if (head != null) {
			newTwoWayNode.next = head;
			head.prev = newTwoWayNode;
		}
		head = newTwoWayNode;
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