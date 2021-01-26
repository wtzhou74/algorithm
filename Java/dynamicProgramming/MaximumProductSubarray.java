package dynamicProgramming;

//Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.
//
//Example 1:
//
//Input: [2,3,-2,4]
//Output: 6
//Explanation: [2,3] has the largest product 6.
//Example 2:
//
//Input: [-2,0,-1]
//Output: 0
//Explanation: The result cannot be 2, because [-2,-1] is not a subarray.

public class MaximumProductSubarray {

	// dp   ===> NOTE: minus might change the SMALLEST to LARGEST or vice versa
	// the num itself can be the largest one  [-3, 0, 1, -2]
	public int dp(int[] nums) {
		int[] dpMax = new int[nums.length];
        int[] dpMin = new int[nums.length];
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
        	// The following can be optimized by checking POSITION/NEGAITVE first
        	// SO => Math.max(Math.max(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]), nums[i]);  ===> Math.max(dpMax[i - 1] * nums[i], nums[i])
            dpMax[i] = Math.max(Math.max(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]), nums[i]);
            dpMin[i] = Math.min(Math.min(dpMax[i - 1] * nums[i], dpMin[i - 1] * nums[i]), nums[i]);
            
            res = Math.max(res, dpMax[i]); // The LOCAL MAX might be the final result, so, we should check each MAX
        }
      //return Math.max(dpMax[nums.length - 1], dpMin[nums.length - 1]); // LOCAL MAX might be the largest one
        return res;
	}
	// DP2 
	public int dpUsingLessSpace(int[] nums) {
		int res = nums[0], max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > 0) {
                max = Math.max(max * nums[i], nums[i]);
                min = Math.min(min * nums[i], nums[i]);
            } else {
                int preMax = max;
                max = Math.max(min * nums[i], nums[i]); // multiple Min can get the MAX number since it is product
                min = Math.min(preMax * nums[i], nums[i]); // multiple MAX can get the MIN number
            }
            res = Math.max(res, max);
        }
        return res;	
	}
	
	// a trick solution
	public int sol(int[] nums) {
		int res = nums[0], prod = 1, n = nums.length;
        for (int i = 0; i < n; ++i) {
            res = Math.max(res, prod *= nums[i]);
            if (nums[i] == 0) prod = 1;// NOTE: should reset prod to 1 if current value is 0
        }
        prod = 1;
        for (int i = n - 1; i >= 0; --i) {
            res = Math.max(res, prod *= nums[i]);
            if (nums[i] == 0) prod = 1;
        }
        return res;
	}
	
	
	// Time: O(n^2)
	public int maxProduct(int[] nums) {
        if (nums.length == 0) return nums[0];
        int i = 0;
        int max = nums[0];
        while (i < nums.length) {
            int j = i + 1;
            int temp = nums[i];
            max = Math.max(temp, max);// NOTE: MAX can be the current value itself [-3, 0, 1, -2]
            while (j < nums.length) {
                if (temp * nums[j] >= temp) {
                    
                    temp *= nums[j]; 
                    max = Math.max(temp, max);
                    j++;
                } else {
                	temp *= nums[j];
                    //break; //[-2, 3, -4]
                }
                j++;
            }
            i++;
        }
        
        return max;
    }
	
	public static void main(String[] args ) {
		new MaximumProductSubarray().maxProduct(new int[] {-3, 0, 1, -2});
	}
}
