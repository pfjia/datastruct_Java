package ch3.ex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 给定两个已排序的表L1和L2,只使用基本的表操作编写计算L1∩L2的过程
 *
 * @author pfjia
 * @since 2017/12/7 20:09
 */
public class Ex4 {
    /**
     * @param l1
     * @param l2
     * @param <E>
     * @return L1∩L2
     */
    public <E extends Comparable<? super E>> List<E> intersection(
            List<? extends E> l1, List<? extends E> l2) {
        if (l1 == null || l1.size() == 0 || l2 == null || l2.size() == 0) {
            return Collections.emptyList();
        }
        List<E> result = new ArrayList<>();
        Iterator<? extends E> itr1 = l1.iterator();
        Iterator<? extends E> itr2 = l2.iterator();
        // get first item in each list
        E e1 = itr1.next();
        E e2 = itr2.next();
        // 若condition为hasNext()则跳出循环时iterator在最后一个元素,即最后一个元素不能进入while循环中
        // 若condition为e1!=null,while循环中配置一次hasNext()判断,这可以循环所有的元素
        while (e1!=null && e2!=null) {
            int compareResult = e1.compareTo(e2);
            if (compareResult == 0) {
                result.add(e1);
                e1 = itr1.hasNext()?itr1.next():null;
                e2 = itr2.hasNext()?itr2.next():null;
            } else if (compareResult < 0) {
                e1 = itr1.hasNext()?itr1.next():null;
            } else {
                e2 = itr2.hasNext()?itr2.next():null;
            }
        }
        return result;
    }

}
