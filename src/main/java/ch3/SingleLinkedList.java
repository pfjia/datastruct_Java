package ch3;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * 带头结点的单链表
 * 
 * @author pfjia
 * @since 2017/11/16 20:12
 */
public class SingleLinkedList<E> implements List<E> {
	private Node<E> head;
	private int size;


	public SingleLinkedList() {
		this.head = new Node<>();
	}


	@Override
	public int size() {
		return size;
	}


	@Override
	public boolean isEmpty() {
		return size == 0;
	}


	@Override
	public boolean contains(Object o) {
		return false;
	}


	@Override
	public Iterator<E> iterator() {
		return null;
	}


	@Override
	public Object[] toArray() {
		return new Object[0];
	}


	@Override
	public <T> T[] toArray(T[] a) {
		return null;
	}


	@Override
	public boolean add(E e) {
		return false;
	}


	@Override
	public boolean remove(Object o) {
		return false;
	}


	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}


	@Override
	public boolean addAll(Collection<? extends E> c) {
		return false;
	}


	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		return false;
	}


	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}


	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}


	@Override
	public void clear() {

	}


	@Override
	public E get(int index) {
		return null;
	}


	@Override
	public E set(int index, E element) {
		return null;
	}


	@Override
	public void add(int index, E element) {

	}


	public Node<E> getNode(int index) {
		return null;
	}


	@Override
	public E remove(int index) {
		return null;
	}


	@SuppressWarnings("Duplicates")
	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o == null) {
			for (Node<E> node = head.next; node != null; node = node.next) {
				if (node.data == null) {
					return index;
				}
				index++;
			}
		} else {
			for (Node<E> node = head.next; node != null; node = node.next) {
				if (o.equals(node.data)) {
					return index;
				}
				index++;
			}

		}
		return -1;
	}


	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}


	@Override
	public ListIterator<E> listIterator() {
		return null;
	}


	@Override
	public ListIterator<E> listIterator(int index) {
		return null;
	}


	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return null;
	}


	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
	}


	public static class Node<E> {
		private E data;
		private Node<E> next;


		public Node() {
			this(null, null);
		}


		public Node(E data, Node<E> next) {
			this.data = data;
			this.next = next;
		}
	}


	public class SingleLinkedListIterator<E> implements Iterator<E> {
		@Override
		public void remove() {

		}


		@Override
		public boolean hasNext() {
			return false;
		}


		@Override
		public E next() {
			return null;
		}
	}
}
