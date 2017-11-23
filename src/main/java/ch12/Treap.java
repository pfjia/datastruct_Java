package ch12;


import java.util.Random;


/**
 * @author pfjia
 * @since 2017/11/23 22:31
 */
public class Treap<E extends Comparable<? super E>> {
	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
	private TreapNode<E> insert(E x, TreapNode<E> t) {
		return null;
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
