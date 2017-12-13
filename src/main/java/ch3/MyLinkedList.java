package ch3;


import java.util.*;


/**
 * 带头结点和尾节点的双向链表(非循环双链表)
 *
 * @author pfjia
 * @since 2017/11/15 20:46
 */
public class MyLinkedList<E> implements Iterable<E> {


    /**
     * 数据成员大小
     */
    private int size;
    /**
     * 自构造以来对链表所做改变的次数
     */
    private int modCount = 0;
    /**
     * 头结点
     */
    private Node<E> beginMarker;
    /**
     * 尾结点
     */
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
        return addLast(x);
    }


    public void add(int idx, E x) {
        addBefore(getNode(idx, 0, size()), x);
    }


    public boolean addFirst(E x) {
        add(0, x);
        return true;
    }

    public boolean addLast(E x) {
        add(size(), x);
        return true;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size() - 1);
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size() - 1);
    }


    public E get(int idx) {
        checkElementIndex(idx);
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
     *
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
     * 将特定list中的值加入到this的末尾
     *
     * @param linkedList 一个MyLinkedList
     */
    public void addAll(MyLinkedList<E> linkedList) {
        //验证linkedList和this是否是同一个对象
        if (this == linkedList) {
            return;
        }
        //连接linkedList的第一个节点
        this.endMarker.prev.next = linkedList.beginMarker.next;
        linkedList.beginMarker.next.prev = endMarker.prev;
        //连接linkedList的最后一个节点
        linkedList.endMarker.prev.next = this.endMarker;
        this.endMarker.prev = linkedList.endMarker.prev;
        //修改linkedList
        linkedList.beginMarker.next = linkedList.endMarker;
        linkedList.endMarker.prev = linkedList.beginMarker;
    }

    /**
     * Removes the object contained in Node p.
     *
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


    public E remove(int idx) {
        return remove(getNode(idx));
    }

    public boolean remove(Object obj) {
        if (obj == null) {
            for (Node<E> p = beginMarker.next; p != endMarker; p = p.next) {
                if (p.data == null) {
                    remove(p);
                    return true;
                }
            }
        } else {
            for (Node<E> p = beginMarker.next; p != endMarker; p = p.next) {
                if (obj.equals(p.data)) {
                    remove(p);
                    return true;
                }
            }
        }
        return false;
    }


    public void removeAll(Iterable<? extends E> items) {
        Iterator<? extends E> iterator = items.iterator();
        while (iterator.hasNext()) {
            remove(iterator.next());
        }
    }


    /**
     * Gets the Node at position idx,which must range from o to size()-1.
     *
     * @param idx index to search at
     * @return internal node corresponding to idx
     * @throws IndexOutOfBoundsException if idx is not between 0 and size() -1 ,inclusive.
     */
    private Node<E> getNode(int idx) {
        return getNode(idx, 0, size() - 1);
    }


    /**
     * Gets the Node at position idx,which must range form lower to upper.
     *
     * @param idx   index to search at
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
            // NOTE:p=endMarker,即p指向第size个元素(即最后一个元素后一位)
            p = endMarker;
            for (int i = size(); i > idx; i--) {
                p = p.prev;
            }

        }
        return p;
    }


    @Override
    public Iterator<E> iterator() {
        return new MyLinkedIterator();
    }

    /**
     * 检查元素索引是否超出链表范围
     *
     * @param idx 被检查的索引值
     */
    private void checkElementIndex(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new IndexOutOfBoundsException(outOfBoundMsg(idx));
        }
    }

    private String outOfBoundMsg(int idx) {
        return "Index: " + idx + ", Size: " + size();
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


    private class MyLinkedIterator implements Iterator<E> {
        Node<E> cursor;
        Node<E> lastRet;
        int expectedModCount;


        public MyLinkedIterator() {
            this.cursor = beginMarker.next;
            lastRet = beginMarker;
            expectedModCount = modCount;
        }


        @Override
        public boolean hasNext() {
            return cursor != endMarker;
        }


        @Override
        public void remove() {
            checkForComodification();
            if (lastRet == beginMarker) {
                throw new IllegalStateException();
            }
            MyLinkedList.this.remove(cursor);
            expectedModCount++;
            lastRet = beginMarker;
        }


        @Override
        public E next() {
            checkForComodification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            cursor = cursor.next;
            return lastRet.data;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class MyLinkedListIterator extends MyLinkedIterator implements ListIterator<E> {
        @Override
        public boolean hasPrevious() {
            return cursor != beginMarker.next;
        }

        @Override
        public E previous() {
            checkForComodification();
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            cursor = cursor.prev;
            lastRet = cursor;
            return lastRet.data;
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            if (lastRet == beginMarker) {
                throw new IllegalStateException();
            }
            checkForComodification();
            lastRet.data = e;
        }

        /**
         * Adds an item to this collection, at specified position cursor.
         * Items at or after that position are slid on position higher.
         *
         * @param e an object
         */
        @Override
        public void add(E e) {
            checkForComodification();
            lastRet = beginMarker;
            addBefore(cursor, e);
            expectedModCount++;
        }
    }

    @Override
    public String toString() {
        Iterator<E> it = iterator();
        if (!it.hasNext()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (; ; ) {
            E e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext()) {
                return sb.append(']').toString();
            }
            sb.append(',').append(' ');
        }
    }

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        list.addFirst(0);
        list.addFirst(1);
        list.addLast(2);
        list.getFirst();
        list.getLast();
        list.removeLast();
        list.removeFirst();
        System.out.println();
    }
}
