package ch3.ex;


import java.util.*;


/**
 * @author pfjia
 * @since 2017/11/16 20:36
 */
public class Ex45 {

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


	/**
	 *
	 * @param l1
	 * @param l2
	 * @param <E>
	 * @return L1∪L2
	 */
	public <E extends Comparable<E>> List<E> union(List<? extends E> l1,
			List<? extends E> l2) {
		if (l1 == null || l1.size() == 0) {
			if (l2 == null) {
				return Collections.emptyList();
			} else {
				return new ArrayList<>(l2);
			}
		}
		if (l2 == null || l2.size() == 0) {
			return new ArrayList<>(l1);
		}
		List<E> result = new ArrayList<>();
		Iterator<? extends E> itr1 = l1.iterator();
		Iterator<? extends E> itr2 = l2.iterator();
		E e1 = itr1.next();
		E e2 = itr2.next();
		while (e1!=null&&e2!=null) {
			int compareResult = e1.compareTo(e2);
			if (compareResult == 0) {
				result.add(e1);
				e1 = itr1.hasNext()?itr1.next():null;
				e2 = itr2.hasNext()?itr2.next():null;
			} else if (compareResult < 0) {
				result.add(e1);
				e1 = itr1.hasNext()?itr1.next():null;
			} else {
				result.add(e2);
				e2 = itr2.hasNext()?itr2.next():null;
			}
		}
		while (e1!=null){
		    result.add(e1);
		    e1=itr1.hasNext()?itr1.next():null;
        }
		while (e2!=null){
		    result.add(e2);
		    e2=itr2.hasNext()?itr2.next():null;
        }
		return result;

	}


	public static void main(String[] args) {
		List<Integer> l1 = Arrays.asList(1, 4, 7, 8, 9);
		List<Integer> l2 = Arrays.asList(2, 3, 4, 8, 10);
		// List<Integer> result = new Ex45().intersection(l1, l2);
		List<Integer> result2 = new Ex45().union(l1, l2);
		// System.out.println(result);
		System.out.println(result2);
	}
}
