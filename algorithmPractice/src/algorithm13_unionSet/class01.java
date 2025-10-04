package algorithm13_unionSet;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class class01 {
    public static void main(String[] args) {

    }

	public static class Node<V> {
		V value;

		public Node(V value) {
			this.value = value;
		}
	}

	public static class UnionSet<V> {
		HashMap<V,Node<V>> packNode;
		HashMap<Node<V>,Node<V>> representative;
		HashMap<Node<V>,Integer> sizeMap;

		public UnionSet(List<V> list) {
			packNode = new HashMap<>();
			representative = new HashMap<>();
			sizeMap = new HashMap<>();

			for(V node : list) {
				Node<V> tempackNode = new Node<V>(node);
				packNode.put(node,tempackNode);
				representative.put(tempackNode,tempackNode);
				sizeMap.put(tempackNode,1);
			}
		}

		public Node<V> findRepresentative(Node<V> node){
			Stack<Node<V>> path = new Stack<>();
			while(node != representative.get(node)) {
				path.push(node);
				node  = representative.get(node);
			}

			while(!path.isEmpty()) {
				representative.put(path.pop(),node);
			}

			return node;
		}

		public boolean isSame(V a, V b) {
			return findRepresentative(packNode.get(a)) == findRepresentative(packNode.get(b));
		}

		public void union (V node1, V node2) {
			Node<V> node1Repre = findRepresentative(packNode.get(node1));
			Node<V> node2Repre = findRepresentative(packNode.get(node2));
			if(node1Repre != node2Repre) {
				int size1 = sizeMap.get(node1Repre);
				int size2 = sizeMap.get(node2Repre);
				Node<V> big = size1 >= size2 ? node1Repre : node2Repre;
				Node<V> small = big == node1Repre ? node2Repre : node1Repre;

				representative.put(small,big);
				sizeMap.put(big,size1 + size2);
				sizeMap.remove(small);
			}
		}
	}
}
