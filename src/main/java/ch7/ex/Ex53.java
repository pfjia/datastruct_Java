package ch7.ex;


import java.util.Arrays;


/**
 * @author pfjia
 * @since 2017/11/20 22:03
 */
public class Ex53 {

	public boolean hasSum(int[] a, int sum) {
		if (a == null) {
			return false;
		}
		for (int i1 : a) {
			for (int i2 : a) {
				if (i1 + i2 == sum) {
					return true;
				}
			}
		}
		return false;
	}


	public boolean hasSumBySort(int[] a, int sum) {
		Arrays.sort(a);
		int i = 0;
		int j = a.length - 1;
		while (i < a.length && j >= 0 && i <= j) {
			int tmpSum = a[i] + a[j];
			if (tmpSum == sum) {
				return true;
			} else if (tmpSum < sum) {
				i++;
			} else {
				j--;
			}
		}
		return false;
	}

    public static void main(String[] args) {
        System.out.println(Math.log(320)/Math.log(5));
    }
}

