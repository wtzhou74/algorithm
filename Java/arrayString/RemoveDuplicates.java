package arrayString;

/*
 * Remove duplicates from (sorted) array
 * Given a sorted array nums, remove the duplicates IN-PLACE such that each element appear only once and return the new length.
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 * **/
public class RemoveDuplicates {

	// The array is not sorted and it does not matter whether the result array is sorted
	public static int removeDuplicatesSol(int[] array){
		if (array.length == 0) {
			return 0;
		}
		int i = 0;
		int j = array.length - 1;
		while (i <= j)
		{
			for (int k = 0; k < i; k++) {
				if (array[k] == array[i]) {
					// move the item i to the j index
					int temp = array[i];
					array[i] = array[j];
					array[j] = temp;
					j--;
					// the next verification on out loop will start from current index
					i--;
					break;
				}
			}
			i++;
		}
		return i;
	}
	
	// for sorted array
	public static int removeDuplicatesWitinSortedArrary(int[] nums)
	{
		if (nums.length == 0) {
			return 0;
		}
		
		int i = 0, j = 1;
		while (j < nums.length) {
			if (nums[i] != nums[j]) {
				if (j - i == 1) {
					i++;
					j++;
				} else {
					int temp = nums[i + 1];
					nums[i+1] = nums[j];
					nums[j] = temp;
					i++;
					j++;
				}
			} else {
				j++;
			}
		}
		return i + 1;
	}
	
	// For sorted array
	public static int removeDuplicatesWithSortedOptimized(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		// i indicates the index of the last digit of subarray without duplicates
		int i = 0, j = 0;
		while (j < nums.length) {
			if (nums[i] != nums[j]) {
				// i++ should be executed before swoping data
				i++;
				// it does not matter what values are set beyond the returned length,
				// so, it does not need to store nums[i]
				nums[i] = nums[j];
			}
			j++;
		}
		return i+1; 
	}
}
