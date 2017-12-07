package ch1.ex;


/**
 * @author pfjia
 * @version v4
 * @since 2017/12/7 8:38
 */
public class Swap {

	public static <E> void swap(E[] arr, int i, int j) {
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


	public static void swap(char[] arr, int i, int j) {
		if (arr == null) {
			return;
		}
		if (i >= arr.length || j >= arr.length) {
			return;
		}
		char temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
