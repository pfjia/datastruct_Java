package ch7;


import exception.UnderflowException;


/**
 * @author pfjia
 * @since 2017/11/20 20:43
 */
public class Sort {
	public static <E extends Comparable<? super E>> void insertSort(E[] a) {
		E tmp;
		for (int i = 1; i < a.length; i++) {
			tmp = a[i];
			int j;
			// 当前与tmp比较的元素位置
			for (j = i - 1; j >= 0 && a[j].compareTo(tmp) > 0; j--) {
				a[j + 1] = a[j];
			}
			a[j + 1] = tmp;
		}
	}


	/**
	 * ShellSort, using Shell's (poor) increments.
	 * @param a an array of Comparable items
	 * @param <E>
	 */
	public static <E extends Comparable<? super E>> void shellSort(E[] a) {
		if (a == null) {
			return;
		}
		// int increment=a.length/2;
		for (int increment = a.length / 2; increment >= 1; increment /= 2) {
			E tmp;
			for (int i = increment; i < a.length; i++) {
				tmp = a[i];
				int j;
				for (j = i - increment; j >= 0
						&& a[j].compareTo(tmp) > 0; j -= increment) {
					a[j + increment] = a[j];
				}
				a[j + increment] = tmp;
			}
		}
	}


	/**
	 * Internal method for heapsort.
	 * @param i the index of an item in the heap.
	 * @return the index of the left child.
	 */
	private static int leftChild(int i) {
		return i * 2 + 1;
	}


	/**
	 * Standard heapsort
	 * @param a an array of Comparable items
	 * @param <E>
	 */
	public static <E extends Comparable<? super E>> void heapSort(E[] a) {
		// 1.建立大根堆
		for (int i = a.length / 2 - 1; i >= 0; i--) {
			percolateDown(a, i, a.length);
		}
		// 2.交换后下滤
		for (int i = a.length - 1; i >= 1; i--) {
			E tmp = a[0];
			a[0] = a[i];
			a[i] = tmp;
			percolateDown(a, 0, i);
		}
	}


	/**
	 * Internal method for heapsort that is used in deleteMax and buildHeap. 
	 * @param a an array of Comparable items
	 * @param hole the position from which to percolate down.
	 * @param n the logic size of heap
	 * @param <E>
	 */
	private static <E extends Comparable<? super E>> void percolateDown(E[] a,
			int hole, int n) {
		if (n >= a.length && hole > n) {
			throw new UnderflowException();
		}
		E tmp = a[hole];
		int child;
		for (child = leftChild(hole); child < n; child=leftChild(hole)) {
			// 找到孩子中的较大值
			if (child < n && a[child].compareTo(a[child + 1]) < 0) {
				child++;
			}
			//
			if (a[child].compareTo(a[hole]) > 0) {
				a[hole] = a[child];
				hole = child;
			} else {
				break;
			}
		}
		a[hole] = tmp;
	}
}
