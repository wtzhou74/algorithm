package arrayString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Given an array, rotate the array to the right by k steps, where k is non-negative.
 * */
public class RotateArray {

	public static void rotateArraySol(int k, int[] nums) {
		
		
		//Note: rotation - circulation
		if (k % nums.length == 0) {
			return;
		}
		k = k % nums.length;
		List<Integer> temp = new ArrayList<Integer>();
		int j = nums.length - 1;
		for (int i = 0; i < k; i++) {
			temp.add(nums[j--]);
		}
		int m = nums.length - 1;
		for (int i = nums.length - k - 1; i >= 0; i--) {
			nums[m] = nums[i];
			m--;
		
		}
		int tempLen = temp.size();
		for (int i = 0; i < k; i++) {
			nums[i] = temp.get(tempLen - 1);
			tempLen--;
		}
		
		return;
	}
	
	// High time complexity for lots of step moving
	public static void rotateArraySol2(int k, int[] nums) {
		if (k % nums.length == 0) {
			return;
		}
		k = k % nums.length;
		int j = nums.length - 1;
		// move one position to right for each step
		for (int i = 0; i < k; i++) {
			int temp = nums[j];
			int m = nums.length - 2;
			while (m >= 0) {
				nums[m + 1] = nums[m];
				m--;
			}
			nums[0] = temp;
		}
		return;
	}
	
	// high time complexity
	// convert it to List
	public static void rotateArraySol3(int k, int[] nums) {
		if (k % nums.length == 0) {
			return;
		}
		k = k % nums.length;
		List<Integer> initial = new ArrayList<Integer>();
		for (int i : nums) {
			initial.add(i);
		}
		
		// split it into two blocks
		List<Integer> part1 = initial.subList(0, nums.length - k);
		List<Integer> part2 = initial.subList(nums.length - k, nums.length);
		
		part2.addAll(part1);
		
		for (int i = 0; i < part2.size(); i++) {
			nums[i] = part2.get(i);
		}
	}
	
	public static void rotateArraySolOPtimized(int k, int[] nums) {
		if (k % nums.length == 0) {
			return;
		}
		k = k % nums.length;
		reverse(nums, 0, nums.length - k - 1);
		reverse(nums, nums.length - k, nums.length - 1);
		reverse(nums, 0, nums.length - 1);
		
	}
	
	// reverse each block
	public static void reverse(int[] nums, int start, int end) {
		while (start < end) {
			int temp = nums[end];
			nums[end] = nums[start];
			nums[start] = temp;
			start++;
			end--;
		}
	}
}
