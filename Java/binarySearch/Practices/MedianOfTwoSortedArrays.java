package binarySearch.Practices;

public class MedianOfTwoSortedArrays {

	/**
	 * NOTE: 
	 * 		MEDIAN: it is used for dividing a set into TWO EQUAL LENGTH SUBSETS, that one subset is always GREATER than the other
	 * 	- The problem now is to FIND the PARTITION POINT
	 * 	- binary search: PARTITION one array, get two subset, then based on the length of each subset, get the PARTITION of the other array
	 * 				according to the MAXLEFTx, MINRIGHTx, MAXLEFTy and MINRIGHTy to get the MEDIAN value
	 * 
	 * Reference: https://www.youtube.com/watch?v=LPFhl65R7ww
	 * */
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		if (nums1.length == 0 && nums2.length == 0) return 0;
		// according to the following logic, LENGTH of nums1 should be less
		if (nums1.length > nums2.length) {
			return findMedianSortedArrays(nums2, nums1);
		}
		
        int left1 = 0, right1 = nums1.length;
        int total = nums1.length + nums2.length;
        
        while (left1 <= right1) {
            int partition1 = (left1 + right1) >>> 1;
            int partition2 = ((total + 1) >>> 1) - partition1;
            
            // should SET THE BOARD, or LINE 34 will outOfIndex, e.g. [1,3],[2]
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == nums1.length) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == nums2.length) ? Integer.MAX_VALUE : nums2[partition2];
            
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                if (total % 2 == 0) 
                    return (double) (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2;
                else return (double) Math.max(maxLeft1, maxLeft2);
            }
            if (maxLeft1 > minRight2) {
                right1 = partition1 - 1;
            } else left1 = partition1 + 1;
        }
        
        return 0;
    }
	
	public static void main(String[] args) {
		int[] nums1 = {3,4};
		int[] nums2 = {1,2};
		
		findMedianSortedArrays(nums1, nums2);
	}
}
