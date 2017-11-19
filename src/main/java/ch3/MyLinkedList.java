package ch3;


import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @author pfjia
 * @since 2017/11/15 20:46
 */
public class MyLinkedList<E> implements Iterable<E> {


	/**数据成员大小*/
	private int size;
	/**自构造以来对链表所做改变的次数*/
	private int modCount = 0;
	/**头结点*/
	private Node<E> beginMarker;
	/**尾结点*/
	private Node<E> endMarker;


	public MyLinkedList() {
		doClear();
	}


	public void clear() {
		doClear();
	}


	private void doClear() {
		beginMarker = new Node<>(null, null, null);
		endMarker = new Node<>(null, beginMarker, null);
		beginMarker.next = endMarker;
		size = 0;
		modCount++;
	}


	public boolean contains(E x) {
		return index(x) >= 0;
	}


	@SuppressWarnings("Duplicates")
	public int index(E e) {
		int index = 0;
		if (e == null) {
			for (Node<E> node = beginMarker.next; node != null; node = node.next) {
				if (node.data == null) {
					return index;
				}
				index++;
			}
		} else {
			for (Node<E> node = beginMarker.next; node != null; node = node.next) {
				if (e.equals(node.data)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}


	public int size() {
		return size;
	}


	public boolean isEmpty() {
		return size() == 0;
	}


	public boolean add(E x) {
		add(size(), x);
		return true;
	}


	public void add(int idx, E x) {
		addBefore(getNode(idx), x);
	}


	public E get(int idx) {
		return getNode(idx).data;
	}


	public E set(int idx, E newVal) {
		Node<E> node = getNode(idx);
		E oldVal = node.data;
		node.data = newVal;
		return oldVal;
	}


	/**
	 * Adds an item to this collection, at specified position p.
	 * Items at or after that position are slid on position higher.
	 * @param p Node to add before
	 * @param x an object
	 */
	private void addBefore(Node<E> p, E x) {
		Node<E> newNode = new Node<>(x, p.prev, p);
		p.prev.next = newNode;
		p.prev = newNode;
		size++;
		modCount++;
	}


	/**
	 * Removes the object contained in Node p.
	 * @param p the Node containing the object
	 * @return the item was removed from the collection
	 */
	private E remove(Node<E> p) {
		p.prev.next = p.next;
		p.next.prev = p.prev;
		size--;
		modCount++;
		return p.data;
	}


	/**
	 * Gets the Node at position idx,which must range from o to size()-1.
	 * @param idx index to search at
	 * @return internal node corresponding to idx
	 * @throws IndexOutOfBoundsException if idx is not between 0 and size() -1 ,inclusive.
	 */
	private Node<E> getNode(int idx) {
		return getNode(idx, 0, size() - 1);
	}


	/**
	 * Gets the Node at position idx,which must range form lower to upper.
	 * @param idx index to search at
	 * @param lower lowest valid index
	 * @param upper upper highest valid index.
	 * @return internal node corresponding to idx.
	 * @throws IndexOutOfBoundsException if idx if not between lower and upper,inclusive.
	 */
	private Node<E> getNode(int idx, int lower, int upper) {
		if (idx < lower || idx > upper) {
			throw new IndexOutOfBoundsException();
		}
		Node<E> p;
		// 从前往后搜索
		if (idx < size() / 2) {
			p = beginMarker.next;
			for (int i = 0; i < idx; i++) {
				p = p.next;
			}
		}
		// 从后往前搜索
		else {
			p = endMarker;// NOTE:p=endMarker,即p指向第size个元素(即最后一个元素后一位)
			for (int i = size(); i > idx; i--) {
				p = p.prev;
			}

		}
		return p;
	}


	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator();
	}


	private static class Node<E> {
		public E data;
		public Node<E> prev;
		public Node<E> next;


		public Node(E data, Node<E> prev, Node<E> next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}


	private class LinkedListIterator implements Iterator<E> {
		private Node<E> current;
		private int expectedModCount;
		private boolean okToRemove;


		public LinkedListIterator() {
			this.current = beginMarker.next;
			expectedModCount = modCount;
			okToRemove = false;
		}


		@Override
		public boolean hasNext() {
			return current != endMarker;
		}


		@Override
		public void remove() {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!okToRemove) {
				throw new IllegalStateException();
			}
			MyLinkedList.this.remove(current.prev);
			expectedModCount++;
			okToRemove = false;
		}


		@Override
		public E next() {
			if (expectedModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			E nextItem = current.data;
			current = current.next;
			okToRemove = true;
			return nextItem;
		}
	}
}
