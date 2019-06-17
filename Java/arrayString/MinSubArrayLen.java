package arrayString;

import java.util.Arrays;

/**
 * Given an array of n positive integers and a positive integer s, 
 * find the minimal length of a CONTIGUOUS subarray of which the 
 * sum ≥ s. If there isn't one, return 0 instead.
 * */
public class MinSubArrayLen {

	public static int minSubArrayLenSol(int s, int[] nums) {
		int i = 0, j = 0, min = Integer.MAX_VALUE, sum = 0;
		
		while (j < nums.length) {
			//traverse each block
			while (sum < s) 
				sum += nums[j++];
			// for each block, get the minimum length
			while (sum >= s) {
				sum -= nums[i++];
				min = Math.min(min, j - i + 1);
			}
				
		}
		return min == Integer.MAX_VALUE ? 0 : min;
	}
	
}
