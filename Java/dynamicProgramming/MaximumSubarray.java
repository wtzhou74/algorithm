package dynamicProgramming;

//Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
//
//Example:
//
//Input: [-2,1,-3,4,-1,2,1,-5,4],
//Output: 6
//Explanation: [4,-1,2,1] has the largest sum = 6.
//Follow up:
//
//If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
public class MaximumSubarray {

	public int maxSubarray(int[] nums) {
		// Brute force
		 int max = nums[0];
	     for (int i = 0; i < nums.length; i++) {
	         int temp = nums[i];
	         
	         max = Math.max(temp, max);// check CURRENT node !!!!!
	         
	         for (int j = i + 1; j < nums.length; j++) {                
	             temp += nums[j];  
	             max = Math.max(temp, max);              
	         }
	     }
	     return max;
	}
	
	// Time: O(n)
	public int sol2(int[] nums) {
		int max = Integer.MIN_VALUE;
        int currMax = 0;
        for (int i = 0; i < nums.length; i++) {
            if (currMax + nums[i] < nums[i]) {
                currMax = nums[i];
            } else {
                currMax += nums[i];
            }
    
            max = Math.max(max, currMax);
        }
        return max;
	}
	
}
