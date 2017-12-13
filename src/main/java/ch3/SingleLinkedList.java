package ch3;


import java.util.*;


/**
 * 带头结点的单链表
 *
 * @author pfjia
 * @since 2017/11/16 20:12
 */
public class SingleLinkedList<E extends Comparable<? super E>> implements List<E> {
    private Node<E> head;
    private int size;


    public SingleLinkedList() {
        this.head = new Node<>();
        size = 0;
    }


    @Override
    public int size() {
        return size;
    }


    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param o 判断x是否存在该链表中
     * @return true 如果链表中包含o
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }


    @Override
    public Iterator<E> iterator() {
        return new SingleLinkedListIterator();
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

    /**
     * 删除p的下一个结点
     *
     * @param p
     */
    private boolean removeNextNode(Node<E> p) {
        Node<E> next = p.next;
        if (next != null) {
            p.next = next.next;
            return true;
        }
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

    /**
     * @param o
     * @return 若o在链表中, 返回o之前的那个节点;若o不在链表中,返回最后一个节点
     */
    private Node<E> prevNodeIndexOf(E o) {
        Node<E> node;
        if (o == null) {
            for (node = head; node.next != null; node = node.next) {
                if (node.next.data == null) {
                    return node;
                }
            }
        } else {
            for (node = head; node.next != null; node = node.next) {
                int compareResult = o.compareTo(node.next.data);
                if (compareResult == 0) {
                    return node;
                }
            }
        }
        //此时node为最后一个节点
        return node;
    }


    private Node<E> prevNodeIndexOfOrdered(E o) {
        Node<E> node;
        if (o == null) {
            for (node = head; node.next != null; node = node.next) {
                if (node.next.data == null) {
                    return node;
                }
            }
        } else {
            for (node = head; node.next != null; node = node.next) {
                int compareResult = o.compareTo(node.next.data);
                //修改为<=0时return
                if (compareResult <= 0) {
                    return node;
                }
            }
        }
        //此时node为最后一个节点
        return node;
    }

    /**
     * 若x不在链表中,以有序的方式插入x
     *
     * @param x 判断是否插入的值
     * @return true x不存在链表中,false x存在链表中
     */
    public boolean addIfAbsentOrdered(E x) {
        Node<E> prev = prevNodeIndexOfOrdered(x);
        if (prev.next != null && prev.next.data.compareTo(x) == 0) {
            return false;
        } else if (prev.next == null || prev.next.data.compareTo(x) > 0) {
            prev.next = new Node<>(x, prev.next);
            size++;
            return true;
        }
        throw new IllegalStateException();
    }

    public boolean addIfAbsent(E x) {
        Node<E> p = prevNodeIndexOf(x);
        if (p.next == null) {
            p.next = new Node<>(x);
            size++;
            return true;
        } else {
            return false;
        }
    }

    public boolean removeIfPresent(E x) {
        Node<E> p = prevNodeIndexOf(x);
        if (p.next == null) {
            return false;
        } else {
            removeNextNode(p);
            size--;
            return true;
        }
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

    @Override
    public String toString() {
        Iterator<E> iterator = iterator();
        if (!iterator.hasNext()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (; ; ) {
            E e = iterator.next();
            sb.append(e);
            if (!iterator.hasNext()) {
                return sb.append("]").toString();
            }
            sb.append(",");
        }
    }


    /**
     * 单链表结点
     *
     * @param <E> 链表中元素类型
     */
    public static class Node<E> {
        private E data;
        private Node<E> next;


        public Node() {
            this(null, null);
        }

        public Node(E data) {
            this(data, null);
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return "data:[" + data + "]";
        }
    }


    /**
     * NOTE:
     * 1.在next()中必须先判断是否hasNext()
     * 2.必须保存上一次返回的值的标记,否则无法调用remove删除上一次返回的值
     * ArrayList lastRet
     * LinkedList lastReturned
     * MyArrayList --current
     * MyLinkedList current.prev
     */
    private class SingleLinkedListIterator implements Iterator<E> {
        private Node<E> beforeNext;
        private Node<E> beforeLastReturned;

        public SingleLinkedListIterator() {
            this.beforeNext = head;
        }

        @Override
        public boolean hasNext() {
            return beforeNext.next != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E data = beforeNext.next.data;
            beforeLastReturned = beforeNext;
            beforeNext = beforeNext.next;
            return data;
        }

        @Override
        public void remove() {
            if (beforeLastReturned == null) {
                throw new IllegalStateException();
            }
            if (beforeLastReturned.next == beforeNext) {
                beforeNext = beforeLastReturned;
            }
            removeNextNode(beforeLastReturned);
            beforeLastReturned = null;
        }
    }


    public static void main(String[] args) {
        SingleLinkedList<Integer> list=new SingleLinkedList<>();
        for (int i=10;i>0;i=i-2){
            list.addIfAbsentOrdered(i);
        }
        list.addIfAbsentOrdered(5);
        System.out.println(list);
    }
}
