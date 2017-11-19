package ch6;


import exception.UnderflowException;


/**
 * @author pfjia
 * @since 2017/11/19 21:20
 */
public class BinomialQueue<E extends Comparable<? super E>> {
	private static final int DEFAULT_TREES = 1;
	/** items in priority queue*/
	private int size;
	/**An array of tree roots*/
	private Node<E>[] trees;


	public BinomialQueue() {
		trees = new Node[DEFAULT_TREES];
		makeEmpty();
	}


	@SuppressWarnings("unchecked")
	public BinomialQueue(E item) {
		trees = new Node[1];
		trees[0] = new Node<>(item);
	}


	/**
	 * Merge rhs into the priority queue.
	 * rhs becomes empty. rhs must be different from this.
	 * @param rhs the other binomial queue.
	 */
	public void merge(BinomialQueue<E> rhs) {
		if (this == rhs) {
			// Avoid aliasing problems
			return;
		}
		size += rhs.size;

		if (size > capacity()) {
			int maxLength = Math.max(trees.length, rhs.trees.length);
			expandTrees(maxLength + 1);
		}

		// 由trees[i]与rhs.trees[i] combine而来
		Node<E> carry = null;

		for (int i = 0, j = 1; j <= size; i++, j *= 2) {
			Node<E> t1 = trees[i];
			Node<E> t2 = i < rhs.trees.length ? rhs.trees[i] : null;
			int whichCase = t1 == null ? 0 : 1;
			whichCase += t2 == null ? 0 : 2;
			whichCase += carry == null ? 0 : 4;

			switch (whichCase) {
			// No trees
			case 0:
				break;
			// Only this
			case 1:
				break;
			// only rhs
			case 2:
				trees[i] = t2;
				rhs.trees[i] = null;
				break;
			// this and rhs
			case 3:
				carry = combineTrees(t1, t2);
				trees[i] = rhs.trees[i] = null;
				break;
			// only carry
			case 4:
				trees[i] = carry;
				carry = null;
				break;
			// this and carry
			case 5:
				carry = combineTrees(t1, carry);
				trees[i] = null;
				break;
			// rhs and carry
			case 6:
				carry = combineTrees(t2, carry);
				rhs.trees[i] = null;
				break;
			// this and rhs and carry
			case 7:
				carry = combineTrees(t2, carry);
				rhs.trees[i] = null;
				break;
			default:
				break;
			}
		}
		rhs.makeEmpty();
	}


	public void insert(E x) {
		merge(new BinomialQueue<>(x));
	}


	public E findMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		int minIndex = findMinIndex();
		return trees[minIndex].element;
	}


	/**
	 * Remove the smallest item from the priority queue.
	 * @return the smallest item.
	 * @throws UnderflowException if this is empty.
	 */
	public E deleteMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		int minIndex = findMinIndex();
		E minItem = trees[minIndex].element;
		Node<E> deletedTree = trees[minIndex].leftChild;

		// Construct H''
		BinomialQueue<E> deletedQueue = new BinomialQueue<>();
		deletedQueue.expandTrees(minIndex + 1);
		deletedQueue.size = 1 << minIndex - 1;
		for (int j = minIndex - 1; j >= 0; j--) {
			deletedQueue.trees[j] = deletedTree;
			deletedTree = deletedTree.nextSibling;
			deletedQueue.trees[j].nextSibling = null;
		}

		// Construct H'(this --> H')
		trees[minIndex] = null;
		size -= deletedQueue.size + 1;
		merge(deletedQueue);
		return minItem;
	}


	public boolean isEmpty() {
		return size == 0;
	}


	public void makeEmpty() {
		size = 0;
		for (int i = 0; i < trees.length; i++) {
			trees[i] = null;
		}
	}


	@SuppressWarnings("unchecked")
	private void expandTrees(int newNumTrees) {
		if (newNumTrees <= trees.length) {
			return;
		}
		Node<E>[] old = trees;
		trees = new Node[newNumTrees];
		System.arraycopy(old, 0, trees, 0, old.length);
	}


	/**
	 * 
	 * @param t1
	 * @param t2
	 * @return the result of merging equal-sized t1 and t2
	 */
	private Node<E> combineTrees(Node<E> t1, Node<E> t2) {
		if (t1.element.compareTo(t2.element) > 0) {
			return combineTrees(t2, t1);
		}
		t2.nextSibling = t1.leftChild;
		t1.leftChild = t2;
		return t1;
	}


	private int capacity() {
		return 1 << trees.length - 1;
	}


	private int findMinIndex() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		int minIndex = -1;
		for (int i = 0; i < trees.length; i++) {
			if (trees[i] != null) {
				if (minIndex == -1 || trees[minIndex].element
						.compareTo(trees[i].element) > 0) {
					minIndex = i;
				}
			}
		}
		return minIndex;
	}


	private static class Node<E> {
		private E element;
		private Node<E> leftChild;
		private Node<E> nextSibling;


		public Node(E element) {
			this(element, null, null);
		}


		public Node(E element, Node<E> leftChild, Node<E> nextSibling) {
			this.element = element;
			this.leftChild = leftChild;
			this.nextSibling = nextSibling;
		}
	}
}
