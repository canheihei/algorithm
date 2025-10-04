package simpleDataNode;

public class TwoWayNode<Integer> {
	public Integer data;
	public TwoWayNode<Integer> prev;
	public TwoWayNode<Integer> next;

	public TwoWayNode(Integer data) {
		this.data  = data;
		this.prev  = null;
		this.next  = null;
	}
}