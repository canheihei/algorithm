package algorithm06;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public  class class01_MyHeap {
	public static class MyHeap<T>{
		public ArrayList<T> heap;
		public HashMap<T,Integer> indexMap;
		public int heapSize;
		public Comparator<? super T> comparator;

		public MyHeap(Comparator<? super T> com){
			heap=new ArrayList<>();
			indexMap = new HashMap<>();
			heapSize = 0;
			comparator=com;
		}

		public boolean isEmpty(){
			return heapSize == 0;
		}

		public int size(){
			return heapSize;
		}

		public boolean contains(T key){
			return indexMap.containsKey(key);
		}

		public void push(T value){
			heap.add(value);
			indexMap.put(value,heapSize);
			heapInsert(heapSize++);
		}

		public T pop(){
			T ans = heap.get(0);
			int end = heapSize-1;
			swap(0,end);
			heap.remove(end);
			indexMap.remove(ans);
			heapify(0,--heapSize);
			return ans;
		}

		public void resign(T value) {
			int valueIndex = indexMap.get(value);
			heapInsert(valueIndex);
			heapify(valueIndex,heapSize);
		}

		private void heapInsert(int index){
			while(comparator.compare(heap.get(index),heap.get((index-1) / 2))
					< 0){
				swap(index,(index-1) / 2);
				index = (index-1) / 2;
			}
		}

		private void swap(int i,int j){
			T o1 = heap.get(i);
			T o2 = heap.get(j);
			heap.set(i,o2);
			heap.set(j,o1);
			indexMap.put(o1,j);
			indexMap.put(o2,i);
		}

		private void heapify(int index,int heapSize){
			int left = index * 2 + 1;
			while (left < heapSize) {
				int largest = left + 1 < heapSize && (comparator.compare(heap.get(left + 1),heap.get(left)) < 0 ) ?
						left + 1 : left;
				largest = comparator.compare(heap.get(largest),heap.get(index)) < 0 ?
						largest : index;
				if(largest == index) {
					break;
				}
				swap(largest, index);
				index = largest;
				left = index * 2 + 1;
			}
		}
	}
}
