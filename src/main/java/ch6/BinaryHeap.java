package ch6;


import exception.UnderflowException;


/**
 * 小根堆
 * @author pfjia
 * @since 2017/11/19 15:15
 */
public class BinaryHeap<E extends Comparable<? super E>> {
	private static final int DEFAULT_CAPACITY = 10;
	/**Number of elements in heap*/
	private int size;
	/**The heap array*/
	private E[] array;


	public BinaryHeap() {
		this(DEFAULT_CAPACITY);
	}


	/**
	 * Construct the binary heap
	 * @param capacity the capacity of the binary heap.
	 */
	@SuppressWarnings("unchecked")
	public BinaryHeap(int capacity) {
		this.array = (E[]) new Object[capacity + 1];
		size = 0;
	}


	/**
	 * Construct the binary heap with an given array of items.
	 * @param items given array
	 */
	@SuppressWarnings("unchecked")
	public BinaryHeap(E[] items) {
		this.array = (E[]) new Object[items.length + 1];
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null) {
				break;
			}
			this.array[i + 1] = items[i];
			size++;
		}
		buildHeap();
	}


	/**
	 * Insert into the priority queue,maintaining heap order.
	 * Duplicates are allowed
	 * @param x the item to insert.
	 */
	public void insert(E x) {
		if (size == array.length - 1) {
			enlargeArray(array.length * 2 + 1);
		}
		array[size++] = x;
		percolateUp(size);
	}


	/**
	 * Find the smallest item in the priority queue.
	 * @return the smallest item
	 * @throws  UnderflowException if queue is empty.
	 */
	public E findMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		return array[1];
	}


	/**
	 * Remove the smallest item from the priority queue.
	 * @return the smallest item
	 * @throws  UnderflowException if queue is empty.
	 */
	public E deleteMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		E min = findMin();
		array[1] = array[size];
		// speed up gc
		array[size] = null;
		size--;
		percolateDown(1);
		return min;
	}


	public boolean isEmpty() {
		return size == 0;
	}


	public void makeEmpty() {
		size = 0;
		// speed up gc
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
	}


	/**
	 * Internal method to percolate down in the heap.
	 * 将hole位置上的元素向下调整
	 * @param hole the index at which the percolate begins.
	 */
	private void percolateDown(int hole) {
		array[0] = array[hole];
		for (int child = 2 * hole; child <= size; child *= 2) {
			// 计算子树中的最小值
			if (child + 1 <= size
					&& array[child].compareTo(array[child + 1]) > 0) {
				child++;
			}
			if (array[0].compareTo(array[child]) > 0) {
				array[hole] = array[child];
				hole = child;
			} else {
				break;
			}
		}
		array[hole] = array[0];
	}


	private void percolateUp(int hole) {
		// array[0]=array[hole]:赋值array[0]做哨兵
		// 循环条件:array[0]<array[hole/2],即array[0]小于父节点的值
		// 增量表达式:找寻当前节点父节点
		for (array[0] = array[hole]; array[0]
				.compareTo(array[hole / 2]) < 0; hole = hole / 2) {
			array[hole] = array[hole / 2];
		}
		array[hole] = array[0];
	}


	/**
	 * Establish heap order property from an arbitrary arrangement of items. Runs in linear time
	 */
	private void buildHeap() {
		for (int i = size / 2; i > 0; i--) {
			percolateDown(i);
		}
	}


	@SuppressWarnings("unchecked")
	private void enlargeArray(int newSize) {
		E[] old = array;
		array = (E[]) new Object[newSize];
        System.arraycopy(old, 1, array, 1, size);
	}
}
