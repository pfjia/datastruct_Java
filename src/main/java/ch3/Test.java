package ch3;


import static java.util.stream.Collectors.toList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;


/**
 * @author pfjia
 * @since 2017/7/12 0012 21:49
 */
public class Test {
	public static void main(String[] args) {
		List<Integer> list = IntStream.range(0, 100500).boxed()
				.collect(toList());
		Iterator<Integer> iterator = list.iterator();
		printList(iterator);
	}


	public static <E> void printList(Iterator<E> iterator) {
		while (true) {
			if (!iterator.hasNext()) {
				return;
			}
			System.out.println(iterator.next());
		}
	}


	private static void f() {
		List<Integer> list = IntStream.rangeClosed(0, 6).boxed()
				.collect(toList());
		Iterator<Integer> iterator = list.iterator();
		while (iterator.hasNext()) {
			Integer i = iterator.next();
			iterator.remove();
		}
	}
}
