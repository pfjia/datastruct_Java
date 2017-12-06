package ch1.ex;


import java.util.Arrays;
import java.util.Random;


/**
 * 编写一个程序解决选择问题.令K=N/2,画出表格显示程序对于N种不同的值的运行时间
 * @author pfjia
 * @since 2017/12/6 21:22
 */
public class Ex1 {


	/**
	 * 思路:排序后输出第k个位置上的元素
	 * 
	 * @param arr
	 * @param k 输出第k个最大者
	 * @param <E> 
	 * @return
	 */
	public <E extends Comparable<? super E>> E sortThenSelect(E[] arr, int k) {
		bubbleSort(arr);
		return arr[arr.length - k];
	}


	/**
	 * 冒泡排序k趟,获取第k个最大值
	 * @param <E>
	 * @return
	 */
	public <E extends Comparable<? super E>> E bubbleSelect(E[] arr, int k) {
		// 从后往前,递减排序
		for (int i = 0; i < k; i++) {
			for (int j = arr.length - 1; j >= i + 1; j--) {
				if (arr[j].compareTo(arr[j - 1]) > 0) {
					swap(arr, j, j - 1);
				}
			}
		}
		return arr[k-1];
	}


	/**
	 * 冒泡排序(升序)
	 * @param arr 待排序数组
	 * @param <E> 
	 */
	public <E extends Comparable<? super E>> void bubbleSort(E[] arr) {
		if (arr == null || arr.length == 1) {
			return;
		}
		// 从前往后,升序排序
		// n-1躺排序
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = 0; j <= arr.length - i - 2; j++) {
				if (arr[j].compareTo(arr[j + 1]) > 0) {
					swap(arr, j, j + 1);
				}
			}
		}
	}


	private <E> void swap(E[] arr, int i, int j) {
		if (arr == null) {
			return;
		}
		if (i >= arr.length || j >= arr.length) {
			return;
		}
		E temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}


	public static void main(String[] args) {
		Integer[] arr = new Integer[87];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Random().nextInt(548);
		}
		System.out.println(Arrays.toString(arr));
		int k = new Ex1().bubbleSelect(arr, 7);
		System.out.println(k);
		System.out.println(Arrays.toString(arr));
	}
}
