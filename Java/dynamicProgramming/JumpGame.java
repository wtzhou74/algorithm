package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

//Given an array of non-negative integers, you are initially positioned at the first index of the array.
//
//Each element in the array represents your maximum jump length at that position.
//
//Determine if you are able to reach the last index.
//
//Example 1:
//
//Input: [2,3,1,1,4]
//Output: true
//Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
//Example 2:
//
//Input: [3,2,1,0,4]
//Output: false
//Explanation: You will always arrive at index 3 no matter what. Its maximum
//             jump length is 0, which makes it impossible to reach the last index.


// IT IS REACHABLE from i to j  ==> The KEY here is whether i - j <= nums[j]  !!!!!!!!!
public class JumpGame {

	public boolean dp1(int[] nums) {
        if (nums.length == 1) return true;
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        for (int i = 1; i < nums.length; i++) {
            //for (int j = 1; j < nums[i]; j++) {
            for (int j = 0; j < i; j++) { // In order to GET previous STEP, there are multiple STEP CHOICE for each i
                //dp[i] = dp[i - 1] & j;
                if (dp[j] && (i - j) <= nums[j]) { // It means that it is REACHABLE from J TO I
                    dp[i] = true; // 往回找看能不能找到一个点能跳到 i 的， 能则判断下一个点； 前提是  dp[j] 得能到达
                    break; // 不用再看其他情况了
                }
                // else dp[i] = false;  //不对，因为我们还要看其他情况， 直到 j < i
            }
        }
        return dp[nums.length - 1];
	}
	
	// Based on the RELATION i - j <= nums[j] or vice versa.
	// Iterative solution
	public boolean iterativeSol(int[] nums) {
//		int start = 0;
//        for (int i = 1; i < nums.length; i++) {
//            if (nums[i] == 0) {continue;}
//            if (i - start <= nums[start]) {
//                start = i;
//            }
//            // } else {
//            //     return false; // You should also CHECK privous START, see [5, [1, 0, 1], 1] ==> Go to DP
//            // }
//        }
//        return start == nums.length - 1 || nums[start] >= (nums.length - 1 - start);
		
		// Since it is complicated to start from 0 UNLESS DP, then starting from tail
		// ALWAYS CHECK THE "LAST" one is reachable till 0
		int lastReachable = nums.length - 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            if (lastReachable - i <= nums[i]) {
                lastReachable = i;
            } // else, i--, check next one // Comparing with STARTing from 0, it is easy to check "PREVIOUS" one
        }
        return lastReachable == 0;
		
	}
	
	// 这题本质是个 Greedy算法，因为这里只考虑是否能达到最后，所以每次遍历i的时候，看最大的到达位置在哪
	public boolean canJumpGreedy(int[] nums) {
        if (nums.length <= 1)
            return true;
        int i = 0;
        int maxPos = 0;
        while (i < nums.length) {
            if (i > maxPos || maxPos >= nums.length - 1) // i > maxPos，说明最大跳也跳不到 i的位置了，即过不去
                break;
            maxPos = Math.max(maxPos, nums[i] + i);
            i++;
        }
        return maxPos >= nums.length - 1;
    }
	
	// TLE
	public boolean canJump(int[] nums) {
        if (nums.length == 1) return true;
        
        return helper(nums, 0);
    }
    private Map<String, Boolean> cache = new HashMap<>();
    // Backtracking
    public boolean helper(int[] nums, int i) {
    	// recursion的过程即是找solution的一个完整过程（解决一个个subproblem）
        if (i == nums.length - 1) return true;
        if (nums[i] == 0) return false;
        int step = 1; // Each of CANDIDATE for a solution, so for each nums[i], step will be reset to 1
        while (step <= nums[i]) {
            boolean isReachable = false;
            if (cache.containsKey(String.valueOf(i) + "-" + String.valueOf(step))) {
                isReachable = cache.get(String.valueOf(i) + "-" + String.valueOf(step));
            } else {
                isReachable = helper(nums, i + step);
                cache.put(String.valueOf(i) + "-" + String.valueOf(step), isReachable);
            }
            if (isReachable) return true;
            step++;
        }
        return false;
    }
	
	public static void main(String[] args) {
		new JumpGame().canJump(new int[] {1,1,2,2,0,1,1});
	}
}
