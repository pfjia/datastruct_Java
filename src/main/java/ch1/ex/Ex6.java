package ch1.ex;


/**
 * 显示String str中的字符的所有排列.
 * @author pfjia
 * @version v4
 * @since 2017/12/7 8:31
 */
public class Ex6 {

	public void permute(String str) {
		permute(str.toCharArray(), 0, str.length() - 1);
	}


	public void permute(char[] str, int low, int high) {
		if (low == high) {
			// 在终止条件时打印整个排列(排列的顺序在递归时存储在数组中)
			for (int i = 0; i <= high; i++) {
				System.out.print(str[i]);
			}
			System.out.println();
		}
		for (int i = low; i <= high; i++) {
			// 若在for循环中每次打印一个元素需要保存[0,low-1]的顺序,将其存放在数组中进行传递即可
			Swap.swap(str, low, i);
			permute(str, low + 1, high);
			Swap.swap(str, low, i);

			// 在递归调用前后各调用一次swap(),保证下一次循环时str保持原样
		}
	}

	public static void main(String[] args) {
		new Ex6().permute("abc".toCharArray(), 0, 2);
	}
}
