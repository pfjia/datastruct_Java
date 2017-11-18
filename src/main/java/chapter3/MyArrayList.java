package chapter3;


import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * @author pfjia
 * @since 2017/11/15 20:21
 */
public class MyArrayList<AnyType> implements Iterable<AnyType> {
	private static final int DEFAULT_CAPACITY = 10;

	private int size;
	private AnyType[] items;


	public MyArrayList() {
		doClear();
	}


	public void clear() {
		doClear();
	}


	private void doClear() {
		size = 0;
		ensureCapacity(DEFAULT_CAPACITY);
	}


	public int size() {
		return size;
	}


	public boolean isEmpty() {
		return size() == 0;
	}


	public void trimToSize() {

	}


	public AnyType get(int idx) {
		rangeCheck(idx);
		return items[idx];
	}


	public AnyType set(int idx, AnyType newVal) {
		rangeCheck(idx);
		AnyType old = items[idx];
		items[idx] = newVal;
		return old;
	}


	@SuppressWarnings("unchecked")
	private void ensureCapacity(int newCapacity) {
		if (newCapacity < size()) {
			return;
		}
		AnyType[] old = items;
		items = (AnyType[]) new Object[newCapacity];
		System.arraycopy(old,0,items,0,size());
	}


	public boolean add(AnyType x) {
		add(size(), x);
		return true;
	}


	public void add(int idx, AnyType x) {
		rangeCheckForAdd(idx);
		System.arraycopy(items, idx, items, idx + 1, size - idx);
		items[idx] = x;
		size++;
	}


	public AnyType remove(int idx) {
		rangeCheck(idx);
		AnyType old = items[idx];
		System.arraycopy(items, idx + 1, items, idx, size - (idx + 1));
		size--;
		return old;
	}


	private void rangeCheck(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
	}


	private void rangeCheckForAdd(int idx) {
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
	}


	@Override
	public Iterator<AnyType> iterator() {
		return new ArrayListIterator();
	}


	private class ArrayListIterator implements Iterator<AnyType> {
		private int current = 0;


		@Override
		public boolean hasNext() {
			return current < size();
		}


		@Override
		public void remove() {
			MyArrayList.this.remove(--current);
		}


		@Override
		public AnyType next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return items[current++];
		}
	}

}
