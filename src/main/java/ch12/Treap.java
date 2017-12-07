package ch12;


import java.util.Random;

import exception.UnderflowException;


/**
 * 思路:
 * 1.满足二叉查找树的规则(通过旋转满足)
 * 2.以随机优先级排序
 * @author pfjia
 * @since 2017/11/23 22:31
 */
public class Treap<E extends Comparable<? super E>> {
	private TreapNode<E> root;
	private TreapNode<E> nullNode;


	public Treap() {
		nullNode = new TreapNode<E>(null);
		nullNode.left = nullNode.right = nullNode;
		nullNode.priority = Integer.MAX_VALUE;
		root = nullNode;
	}


	/**
	 * Insert into the tree.Does nothing if x is already present.
	 * @param x the item to insert
	 */
	public void insert(E x) {
		root = insert(x, root);
	}


	/**
	 * Remove from the tree.Does nothing if x is not found.
	 * @param x the item to remove
	 */
	public void remove(E x) {
		root = remove(x, root);
	}


	/**
	 * Find the smallest item in the tree.
	 * @return the smallest item
	 * @throws  exception.UnderflowException if this is empty.
	 */
	public E findMin() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		TreapNode<E> ptr = root;
		while (ptr.left != nullNode) {
			ptr = ptr.left;
		}
		return ptr.element;
	}


	public E findMax() {
		if (isEmpty()) {
			throw new UnderflowException();
		}
		TreapNode<E> ptr = root;
		while (ptr.right != nullNode) {
			ptr = ptr.right;
		}
		return ptr.element;
	}


	public boolean contains(E x) {
		TreapNode<E> current = root;
		// 哨兵
		nullNode.element = x;
		for (;;) {
			int compareResult = x.compareTo(current.element);
			if (compareResult < 0) {
				current = current.left;
			} else if (compareResult == 0) {
				return current != nullNode;
			} else {
				current = current.right;
			}
		}
	}


	public void makeEmpty() {
		root = nullNode;
	}


	public boolean isEmpty() {
		return root == nullNode;
	}


	public void printTree() {
		if (isEmpty()) {
			System.out.println("Empty tree");
		} else {
			printTree(root);
		}
	}


	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private TreapNode<E> insert(E x, TreapNode<E> t) {
		if (t == nullNode) {
			return new TreapNode<E>(x, nullNode, nullNode);
		}
		int compareResult = x.compareTo(t.element);

		if (compareResult < 0) {
			t.left = insert(x, t.left);
			if (t.left.priority < t.priority) {
				t = rotateWithLeftChild(t);
			}
		} else if (compareResult > 0) {
			t.right = insert(x, t.right);
			if (t.right.priority < t.priority) {
				t = rotateWithRightChild(t);
			}
		} else {
			// NOPE
		}
		return t;
	}


	private TreapNode<E> rotateWithLeftChild(TreapNode<E> t) {
		TreapNode<E> left = t.left;
		t.left = left.right;
		left.right = t;
		return left;
	}


	private TreapNode<E> rotateWithRightChild(TreapNode<E> t) {
		TreapNode<E> right = t.right;
		t.right = right.left;
		right.left = t;
		return right;
	}


	private void printTree(TreapNode<E> root) {

	}


	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private TreapNode<E> remove(E x, TreapNode<E> t) {
		if (t != nullNode) {
			int compareResult = x.compareTo(t.element);

			if (compareResult < 0) {
				t.left = remove(x, t.left);
			} else if (compareResult > 0) {
				t.right = remove(x, t.right);
			} else {
				// Match found
				if (t.left.priority < t.right.priority) {
					t = rotateWithLeftChild(t);
				} else {
					t = rotateWithRightChild(t);
				}
				if (t != nullNode) {
					// Continue on down
					t = remove(x, t);
				} else {
					// At a leaf
					t.left = nullNode;
				}
			}
		}
		return t;
	}


	private static class TreapNode<E> {
		private static Random random = new Random();
		private E element;
		private TreapNode<E> left;
		private TreapNode<E> right;
		private int priority;


		public TreapNode(E element) {
			this(element, null, null);
		}


		public TreapNode(E element, TreapNode<E> left, TreapNode<E> right) {
			this.element = element;
			this.left = left;
			this.right = right;
			this.priority = random.nextInt();
		}
	}
}
