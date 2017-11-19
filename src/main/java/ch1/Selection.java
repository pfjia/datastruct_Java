package ch1;

import java.util.Arrays;

/**
 * @author pfjia
 * @since 2017/7/12 0012 20:33
 */
public class Selection {

	/**
	 * 冒泡排序
	 *
	 * 选择nums中第k个最大者
	 *
	 * @param nums
	 */
	public static int[] bubbleSort(int[] nums) {
		int n = nums.length;
		// 外循环[0,n-2],一共n-1次循环,选择n-1个较大值,剩余一个即为最小值
		/*
		 * 外循环控制循环次数
		 */
		for (int i = 0; i < n - 1; i++) {
			/*
			 * 内循环控制比较的index
			 */
			for (int j = 0; j < n - i - 1; j++) {
				if (nums[j] > nums[j + 1]) {
					int tmp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = tmp;
				}
			}
		}
		return nums;
	}

	public static int[] bubbleReverseSort(int[] nums) {
		// 递减排序
		int len = nums.length;
		for (int i = 0; i < len - 1; i++) {
			// 每次循环找出一个最大值,放置到nums[len-i-1]上
			for (int j = 0; j < len - i - 1; j++) {
				// 稳定排序
				if (nums[j] < nums[j + 1]) {
					// 交换
					int tmp = nums[j];
					nums[j] = nums[j + 1];
					nums[j + 1] = tmp;
				}
			}
		}
		return nums;
	}

	public static int[] selectionSort(int[] nums) {
		int n = nums.length;
		/*
		 * 循环次数,n-1次
		 */
		for (int i = 0; i < n - 1; i++) {
			// 每次选择最小值
			int minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (nums[minIndex] > nums[j]) {
					minIndex = j;
				}
			}
			int tmp = nums[minIndex];
			nums[minIndex] = nums[i];
			nums[i] = tmp;
		}
		return nums;
	}

	public static int[] selectionReverseSort(int[] nums) {
		int n = nums.length;

		for (int i = 0; i < n - 1; i++) {
			int maxIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (nums[maxIndex] < nums[j]) {
					maxIndex = j;
				}
			}
			int tmp = nums[i];
			nums[i] = nums[maxIndex];
			nums[maxIndex] = tmp;
		}
		return nums;
	}

	public static void main(String[] args) {
		int[] nums = { 1, 6, 2, 4, 7, 8, 9, 4, 5 };
		System.out.println(Arrays.toString(bubbleSort(nums)));
		System.out.println(Arrays.toString(bubbleReverseSort(nums)));
		System.out.println(Arrays.toString(selectionSort(nums)));
		System.out.println(Arrays.toString(selectionReverseSort(nums)));
	}
}
