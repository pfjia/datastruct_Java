package ch3;


import java.beans.IntrospectionException;
import java.util.*;


/**
 * @author pfjia
 * @since 2017/11/15 20:21
 */
public class MyArrayList<AnyType> implements Iterable<AnyType> {
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 数据成员大小
     */
    private int size;
    private AnyType[] items;
    /**
     * 自构造以来对链表更改的次数
     */
    private int modCount = 0;


    public MyArrayList() {
        doClear();
    }


    public void clear() {
        doClear();
    }


    private void doClear() {
        size = 0;
        modCount++;
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
        if (size() == 0) {
            return;
        }
        System.arraycopy(old, 0, items, 0, size());
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
        modCount++;
    }

    public void addAll(Iterable<? extends AnyType> items) {
        Iterator<? extends AnyType> iterator = items.iterator();
        while (iterator.hasNext()) {
            add(iterator.next());
        }
    }


    public AnyType remove(int idx) {
        rangeCheck(idx);
        AnyType old = items[idx];
        System.arraycopy(items, idx + 1, items, idx, size - (idx + 1));
        size--;
        modCount++;
        return old;
    }


    private void rangeCheck(int idx) {
        MyArrayItr myArrayItr = new MyArrayItr();
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
        return new MyArrayItr();
    }

    public ListIterator<AnyType> listIterator() {
        return new MyArrayListItr();
    }


    public Iterator<AnyType> reverseIterator() {
        return new MyArrayListReverseIterator();
    }


    private class MyArrayItr implements Iterator<AnyType> {
        /**
         * (指向下一个将返回的值)
         */
        int cursor;
        int lastRet;
        int expectedModCount;

        public MyArrayItr() {
            cursor = 0;
            lastRet = -1;
            expectedModCount = MyArrayList.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return cursor < size();
        }

        /**
         * NOTE:执行此方法后,lastRet=cursor-1
         *
         * @return
         */
        @Override
        public AnyType next() {
            checkForComodification();
            //1.判断hasNext()
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            cursor++;
            return items[lastRet];
        }

        /**
         * NOTE:执行此方法后,lastRet=-1
         */
        @Override
        public void remove() {
            checkForComodification();
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * 若只有一个cursor属性(指向下一个将返回的值)
     * 即:next() 先返回,再cursor++
     * previous() 先cursor--,再返回
     * remove() (remove方法语义:删除刚刚返回的值)出现问题
     * (1)若next()后调用remove(),需删除cursor-1
     * (2)若previous()后调用remove(),需删除cursor
     * set() (set方法语义:设置刚刚返回的值) 出现问题
     * (1)若next()后调用set(),需设置 cursor-1
     * (2)若previous()后调用set(),需设置 cursor
     * <p>
     * 由于set(),remove()在next(),previous()后动作不一致,因此一个属性无法完成该功能
     */
    private class MyArrayListItr extends MyArrayItr implements ListIterator<AnyType> {
        @Override
        public boolean hasPrevious() {
            return cursor >= 0;
        }

        /**
         * NOTE:执行此方法后lastRet==cursor
         *
         * @return
         */
        @Override
        public AnyType previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            cursor--;
            return items[lastRet = cursor];
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
        public void set(AnyType anyType) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.set(lastRet, anyType);
        }

        /**
         * 将anyType添加到cursor当前的位置
         *
         * @param anyType
         */
        @Override
        public void add(AnyType anyType) {
            MyArrayList.this.add(cursor, anyType);
            cursor++;
            lastRet = -1;
        }
    }


    /**
     * 从后往前遍历的Iterator
     */
    private class MyArrayListReverseIterator implements Iterator<AnyType> {
        int cursor;
        int lastRet;
        int expectedModCount;

        public MyArrayListReverseIterator() {
            cursor = MyArrayList.this.size - 1;
            lastRet = -1;
            expectedModCount=MyArrayList.this.modCount;
        }

        @Override
        public boolean hasNext() {
            return cursor >= 0;
        }

        @Override
        public AnyType next() {
            checkForCoModification();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            cursor--;
            return items[lastRet];
        }

        /**
         * 由于是从后往前遍历,删除lastRet时无需修改cursor
         */
        @Override
        public void remove() {
            checkForCoModification();
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(lastRet);
            lastRet = -1;
        }

        public void checkForCoModification(){
            if (expectedModCount!=modCount){
                throw new ConcurrentModificationException();
            }
        }
    }

    public static void main(String[] args) {
        MyArrayList<Integer> integers = new MyArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(i);
        }

        Iterator<Integer> iterator = integers.reverseIterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
