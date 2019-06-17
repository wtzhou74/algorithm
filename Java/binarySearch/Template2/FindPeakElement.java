package binarySearch.Template2;

/**
 * A peak element is an element that is greater than its neighbors.
* Given an input array nums, where nums[i] does not equal nums[i+1], find a peak element and return its index.
* The array may contain multiple peaks, in that case return the index to ANY ONE OF the peaks is fine.
* You may imagine that nums[-1] = nums[n] = -8 (negative infinity).
 * */
public class FindPeakElement {

	// Just need to find one of them, so binary search, and the peak should be in the middle of an array
	// the elements increase and then decrease
    public int findPeakElementSol(int[] nums) {
        
//      if (nums.length == 1) return 0;
//      int left = 0, right = nums.length - 1;
     
//      while (left < right) {
//          int mid = (left + right) >>> 1;
         
//          if (nums[mid] < nums[mid + 1]) {
//              // the peak is behind mid
//              left = mid + 1;
//          } else right = mid;
//      }
     
//      return right;
     return helpFinder(nums, 0, nums.length - 1);
 }
 
    // recursive solution
	 public int helpFinder (int[] nums, int left, int right)
	 {
	     int mid = (left + right) >>> 1;
	     if (left >= right) return left;
	     if (nums[mid] < nums[mid + 1]) {
	         return helpFinder(nums, mid + 1, right);
	     } else return helpFinder(nums, left, mid);
	 }
}
