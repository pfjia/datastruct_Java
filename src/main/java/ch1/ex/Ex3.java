package ch1.ex;


/**
 * 只使用处理I/O的printDigit方法,编写一种方法以输出在任意double型量(可以是负的)
 * @author pfjia
 * @since 2017/12/6 21:54
 */
public class Ex3 {

	public void printDigit(int i) {
		if (i >= 10 || i < 0) {
			throw new IllegalArgumentException("i必须是个位数");
		}
		System.out.print(i);
	}


	public void printDouble(double d) {
		// 正负号
		if (d < 0) {
			System.out.print("-");
			d = -d;
		}
		// 整数
		long digit = (long) d;
		if (digit < 1) {
			System.out.print("0");
		} else {
			printDigit((int) digit);
		}
		// 小数点
		System.out.print(".");
		// 小数
		double decimal = d - digit;
		printDoub(decimal);
	}


	private void printDoub(double num) {
		if (num > 1) {
			// 不可遗漏long
			printDoub((long) (num / 10));
			// 不可遗漏long
			System.out.print((long) (num % 10));
		} else if (num > 0 && num < 1) {
			long digit = (long) (num * 10);
			System.out.print(digit);
			printDoub(num * 10 - digit);
		} else if (num == 1) {
			System.out.print("1");
		}
	}


	public static void main(String[] args) {
		new Ex3().printDouble(-0.4875453123);
	}
}
