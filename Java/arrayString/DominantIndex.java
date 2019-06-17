package arrayString;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * In a given integer array nums, there is always exactly one largest element.

Find whether the largest element in the array is AT LEAST TWICE as much as every other number in the array.

If it is, return the index of the largest element, otherwise return -1.
 * */
public class DominantIndex {

	// use too many extra objects => COMPLICATED
	// Find the largest number => Arrays.Sort()
	// Record the index => HashMap
	// Check whether it is at least twice of others => double elements of array, then sort
	// ==> COMPLICATED
	public static int dominantIndexSolution(int[] nums)
	{
		if (nums.length == 0 || nums.length > 50)
        {
            return -1;
        }
		// ONLY ONE ELEMENT
		if (nums.length == 1) return 0;
		Map<Integer, Integer> numMap = new HashMap<Integer, Integer>();
		int[] double2Nums = new int[nums.length];
		
		// find the largest number and its index
		for (int i = 0; i < nums.length; i++)
		{
			numMap.put(i, nums[i]);
			double2Nums[i] = 2 * nums[i];
		}
		Arrays.sort(nums);
		int largest = nums[nums.length - 1];
		Arrays.sort(double2Nums);
		// AT LEAST TWICE as much as every other number
		if (largest >= double2Nums[nums.length - 2])
		{
			int index = numMap.keySet()
			.stream()
			.filter(key -> largest == numMap.get(key))
			.findFirst().get();
			
			return index;
		}
		return -1;
	}
	
	
	// FIND THE LARGEST BY TRAVERSING THE ARRAY
	// CHECK WHETHER IT IS AT LEAST TWICE AS MUCH AS EVERY OTHER NUMBER
	// EXLCUDE ITSELF
	public static int optimizedSolution(int[] nums)
	{
		
		int largest = 0;
		int largestIndex = 0;
		if (nums.length == 0) return -1;
		if (nums.length == 1) return 0; // note the length is 1
		// find the largest number and its index
		for (int i = 0; i < nums.length; i++)
		{
			if (nums[i] > largest)
			{
				largest = nums[i];
				largestIndex = i;
			}
		}
		
		// check whether the largest number is at least twice of others
		// EXCLUDE ITSELF
		for (int i = 0; i < nums.length; i++) {
			if (largest < nums[i] * 2
					&& i != largestIndex) {
				return -1;
			}
		}
		return largestIndex;
	}
	
	
}
