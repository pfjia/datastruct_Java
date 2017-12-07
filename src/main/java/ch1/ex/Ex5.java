package ch1.ex;


import java.util.Random;


/**
 * 编写一种递归方法,它返回数N的二进制中表示中1的个数.利用这样的事实:如果N是奇数,那么其1的个数等于N/2的二进制表示中1的个数加1
 * @author pfjia
 * @version v4
 * @since 2017/12/7 8:17
 */
public class Ex5 {

	public int ones(int n) {
		if (n < 2) {
			return n;
		}
		return ones(n / 2) + (n & 1);
	}


	/**
	 * @param n  判断该数的奇偶性
	 * @return true 如果n是奇数
	 */
	private boolean isOdd(int n) {
		return (n & 1) == 1;
	}


	public static void main(String[] args) {
		int[] arr = new int[15];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Random().nextInt(500);
		}
		for (int i : arr) {
			System.out.println("原始数据:" + i + ",二进制中1的个数:" + new Ex5().ones(i));
		}
	}
}
