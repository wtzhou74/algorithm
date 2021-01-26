package dynamicProgramming;

import java.util.Arrays;

//You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
//
//Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount of money you can rob tonight without alerting the police.
//
//Example 1:
//
//Input: [1,2,3,1]
//Output: 4
//Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
//             Total amount you can rob = 1 + 3 = 4.
//Example 2:
//
//Input: [2,7,9,3,1]
//Output: 12
//Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
//             Total amount you can rob = 2 + 9 + 1 = 12.

// ===> SUM of DISCONTINUOUSE SUBSET => DP ==> maintain DP[i]
// ===> dp[0],dp[1]....dp[i] ===> when i, what is relation with PREVIOUS dp/step
public class HouseRobber {

	public int rob(int[] nums) {
        //int max = 0;
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        
        // STARTING TO MAINTAIN DP array
        int[] dp = new int[nums.length];
        dp[0] = nums[0]; // CHOICE when there is only ONE house
        dp[1] = Math.max(nums[0], nums[1]); // CHOICE when there are TWO house
        
        for (int i = 2; i < nums.length; i++) {
        	// dp[i - 2] + nums[i] ==> RESULTS when rob the ith house
        	dp[i] = Math.max(dp[i-2] + nums[i], dp[i - 1]); // CHOICE where there are I houses
        }
        return dp[nums.length - 1];
    }
   
	// Space: O(1)
	public int rob1(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        // int[] dp = new int[nums.length];
        // dp[0] = nums[0];
        // dp[1] = Math.max(nums[1], nums[0]);
        int prevMax = 0;
        int currMax = 0;
        for (int room = 0; room < nums.length; room++) {
            int temp = currMax;
            currMax = Math.max(prevMax + nums[room], temp);
            prevMax = temp;
        }
        return currMax;
    }
    
	// recursive solution
	public int rob3(int[] nums) {
        if (nums.length == 0) return 0;
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1); // 不能直接用默认值0， 因为 0 可以是结果的
        return dp(nums, 0, memo);
    }
    
    public int dp (int[] nums, int s, int[] memo) {
        if (s >= nums.length)
            return 0;
        if (memo[s] != -1) // 0判断的话，对本身current val = 0的就是出错， nums=[0,0,0,0,0]
            return memo[s];
        //根据两种不同的状态进行 递归
        int s1 = dp(nums, s + 2, memo) + nums[s];
        int s2 = dp(nums, s + 1, memo);
        memo[s] = Math.max(s1, s2);
        return memo[s];
    }
    public static void main(String[] args) {
    	new HouseRobber().rob(new int[] {1,2,3,1});
    }
}
