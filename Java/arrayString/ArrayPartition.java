package arrayString;

import java.util.Arrays;

/*
 * Given an array of 2n integers, your task is to group these integers into n pairs of integer, say (a1, b1), 
 * (a2, b2), ..., (an, bn) which makes sum of min(ai, bi) for all i from 1 to n as large as possible.
 * 
 * Note:
 * n is a positive integer, which is in the range of [1, 10000].
 * All the integers in the array will be in the range of [-10000, 10000].
 * **/
public class ArrayPartition {

	public static int arrayPartitionSolution(int[] sums) {
		Arrays.sort(sums);
		int sum = 0;
		for (int i = 0; i < sums.length; i += 2) {
			sum += sums[i];
		}
		return sum;
	}
	
	// less time complexity
	// implement it without SORT()
	public static int arrayPartitionOptimizedSolution(int[] nums) {
		
		// Set OFFSET correctly.
		int[] indexes = new int[200001];
		// Replace sort with placing elements on specific index where the original elements will be sorted automatically
		for (int num : nums) {
			// MUST use ++ operator to handle duplicate elements. Then -- will be used to get original value
			indexes[num + 10000]++;
		}
		
		int sum = 0;
		boolean odd = true;
		for (int i = 0; i < indexes.length; i++) {
			
			// MUST use while for duplicate elements saved in same indexes
			while (indexes[i] > 0) {
				if (odd) {
					sum += i - 10000;
					//odd = !odd;
				}
				odd = !odd;
				indexes[i]--;
			}
			//odd = !odd;
			//indexes[i]--;
		}
		
		return sum;
	}
}
