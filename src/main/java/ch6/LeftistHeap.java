package ch6;


import exception.UnderflowException;


/**
 * @author pfjia
 * @since 2017/11/19 20:27
 */
public class LeftistHeap<E extends Comparable<? super E>> {
	private Node<E> root;


	public LeftistHeap() {
		root = null;
	}


	/**
	 * Merge rhs into the priority queue.
	 * rhs becomes empty. rhs must be different from this.
	 * @param rhs the other leftist heap
	 */
	public void merge(LeftistHeap<E> rhs) {
		if (this == rhs) {
			// Avoid aliasing problems
			return;
		}
		root = merge(root, rhs.root);
		rhs.makeEmpty();
	}


	/**
	 * Insert into the priority queue,maintaining heap order.
	 * @param x the item to insert
	 */
	public void insert(E x) {
		root = merge(new Node<E>(x), root);
	}


	public E findMin() {
	    if (isEmpty()){
	        throw new UnderflowException();
        }
		return root.element;
	}


	/**
	 * Remove the smallest item from the priority queue.
	 * @return the smallest item
	 * @throws exception.UnderflowException if this is empty
	 */
	public E deleteMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		E min = root.element;
		root = merge(root.left, root.right);
		return min;
	}


	public boolean isEmpty() {
		return root == null;
	}


	public void makeEmpty() {
		root = null;
	}


	/**
	 * Internal method to merge two roots.
	 * Deals with deviant cases and calls recursive merge1 
	 * @param h1
	 * @param h2
	 * @return
	 */
	private Node<E> merge(Node<E> h1, Node<E> h2) {
		if (h1 == null) {
			return h2;
		}
		if (h2 == null) {
			return h1;
		}
		if (h1.element.compareTo(h2.element) < 0) {
			return merge1(h1, h2);
		} else {
			return merge1(h2, h1);
		}
	}


	/**
	 * Internal method to merge two roots.
	 * Assumes trees are not empty, and h1's root contains smallest item.
	 * @param h1
	 * @param h2
	 * @return
	 */
	private Node<E> merge1(Node<E> h1, Node<E> h2) {
		if (h1.left == null) {
			h1.left = h2;
		} else {
			h1.right = merge(h1.right, h2);
			if (h1.left.npl < h1.right.npl) {
				swapChildren(h1);
			}
			h1.npl = h1.right.npl + 1;
		}
		return h1;
	}


	private void swapChildren(Node<E> t) {
		Node<E> tmp = t.left;
		t.left = t.right;
		t.right = tmp;
	}


	private static class Node<E> {
		private E element;
		private Node<E> left;
		private Node<E> right;
		private int npl;


		public Node(E element) {
			this(element, null, null);
		}


		public Node(E element, Node<E> left, Node<E> right) {
			this.element = element;
			this.left = left;
			this.right = right;
			this.npl = 0;
		}
	}
}
