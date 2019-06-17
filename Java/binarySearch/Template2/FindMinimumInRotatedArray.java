package binarySearch.Template2;

// FIND the pivot
// 1 2 3 4 5 6 =rotated=> 4 5 6 1 2 3
public class FindMinimumInRotatedArray {
	// typical BINARY SEARCH in template 2
	// 1. termination : left == right
	// 2. need check IMMEDIATE NEIGHBORS
	public int findPivotNumber (int[] nums)
	{
		if (nums.length == 1) return nums[0];
	    int left = 0, right = nums.length - 1, last = nums.length - 1;
	    while (left < right) {
	        int mid = (left + right) >>> 1;
	        // Comparing with LAST element
	        if (nums[mid] > nums[last]) {
	            // the minimum number is behind mid
	            left = mid + 1;
	        } else right = mid;
	    }
	    
	    return nums[left];
	}
	
}
