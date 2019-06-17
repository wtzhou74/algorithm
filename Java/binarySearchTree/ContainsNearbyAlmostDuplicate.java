package binarySearchTree;

import java.util.TreeSet;

/**
 * Given an array of integers, find out whether there are two distinct indices i and j 
 * in the array such that the absolute difference between
 * nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
 * */
public class ContainsNearbyAlmostDuplicate {

	/**
	 * A WINDOW OF SIZE K
	 * */
	public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        
        if (nums == null || nums.length == 0) {
            return false;
        }
        // BINARY SEARCH
         TreeSet<Long> treeSet = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            long num = nums[i];// in case OVERFLOW
            // the greatest number which is less than or equals num[i] + t
            Long floor = treeSet.floor(num + t);// RIGHT
            // the least number which is greater than or equals num[i] - t
            Long ceil = treeSet.ceiling(num - t);//LEFT
            if ((floor != null && floor >= nums[i]) 
                || (ceil != null && ceil <= nums[i])) {
                return true;
            }
            
            treeSet.add((long)nums[i]);// add one element
            // The absolute difference between INDICES is at most K.
            if (i >= k) {
                treeSet.remove((long)nums[i - k]);// remove [0...(i-k)]
            }
        }
        return false;
    }
}
