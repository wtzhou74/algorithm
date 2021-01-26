package recursionII;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Given an array of size n, find the majority element. The majority element is the element that 
//appears more than ⌊ n/2 ⌋ times.
//
//You may assume that the array is non-empty and the majority element always exist in the array.
//
//Example 1:
//
//Input: [3,2,3]
//Output: 3
//Example 2:
//
//Input: [2,2,1,1,1,2,2]
//Output: 2
public class MajorityElement {

	// KEY here is that the number of the target element is MORE THAN n/2
	public int majorityElement(int[] nums) {
        Arrays.sort(nums);// TIME: O(nlogn), it will sort the array IN-PLACE
//        if (nums.length % 2 == 0) {// even number => the element is in LEFT / RIGHT side OR MID
//            int mid = nums.length / 2;
//            if (nums[mid] != nums[mid-1]) {
//                if (nums[0] == nums[mid-1]) return nums[0];
//                else if (nums[mid] == nums[nums.length - 1]) return nums[mid];
//            } else {
//                return nums[mid];
//            }
//        } else {// odd number
//            return nums[nums.length / 2];
//        }
        
        // SINCE the number of majority element is MORE THAN N/2 times
        return nums[nums.length /2]; // no matter whether odd or even
        // return -1;
    }
	
	
	public int mapSolution(int[] nums) {
		Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer count = map.putIfAbsent(nums[i], 1);
            if (count != null) map.put(nums[i], count+1);
            if (map.get(nums[i]) > nums.length / 2) {
                return nums[i];
            }
        }
        return -1;
	}
	
	// divide and conquer
	// TIME: O(nlgn)
	// Space: O(lgn) for recursion
	public int divideAndConquer(int[] nums) {
		return helper(nums, 0, nums.length - 1);
		
	}
	
	public int helper(int[] nums, int low, int high) {
		// BASE CASE
		if (low == high) {
			return nums[low]; // only one element
		}
		int mid = low + (high - low) / 2;
		// majority element of left side
		int leftMajorityElem = helper(nums, low, mid); // returns a "majority elem" for CURRENT sub-array
		// majority element of right side
		int rightMajorityElem = helper(nums, mid + 1, high);
		
		if (leftMajorityElem == rightMajorityElem) {
			return leftMajorityElem;
		}
		
		// Check the number its majority element for each side
		// For each majorityElem, find a "Winner", a real majority elem within CURRENT (SUB-)ARRAY for recursion (HIGH is high, NOT mid)
		int numLeft = checkMajorityElem(nums, leftMajorityElem, low, high);// returns number of LEFT MAJORITY ELEMENT
		int numRight = checkMajorityElem(nums, rightMajorityElem, low, high);
		
		return numLeft > numRight ? leftMajorityElem : rightMajorityElem;
		
	}
	
	// Find the number of majority Elem of each side
	public int checkMajorityElem(int[] nums, int majorityElem, int low, int high) {
		int count = 0;
		for (int i = low; i <= high; i++) {
			if (nums[i] == majorityElem) {
				count++;
			}
		}
		return count;
	}
}
