package ch3.ex;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * @author pfjia
 * @since 2017/11/16 19:54
 */
public class Ex1 {

	/**
	 * 打印list中在indexs位置上的元素
     *
	 * @param list 升序排列 
	 * @param indexs 包含n个索引,升序排列
	 */
	public void printLots(List<Integer> list, List<Integer> indexs) {
		Iterator<Integer> listItr = list.iterator();
		Iterator<Integer> indexItr = indexs.iterator();
        int index=0;
		while (indexItr.hasNext()) {
		    int targetIndex=indexItr.next();
			while (listItr.hasNext()) {
			    int val=listItr.next();
			    index++;
				if (targetIndex == index) {
					System.out.println(val);
					break;
				}
			}
		}
	}

    public static void main(String[] args) {
        List<Integer> list= IntStream.range(0,100).boxed().collect(Collectors.toList());
        List<Integer>indexs= Arrays.asList(1,2,4,6);
        new Ex1().printLots(list,indexs);
    }
}
