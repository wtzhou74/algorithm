package amazon;

import java.util.Arrays;

//Given an array of non-negative integers, you are initially positioned at the first index of the array.
//
//Each element in the array represents your maximum jump length at that position.
//
//Your goal is to reach the last index in the minimum number of jumps.
//
//Example:
//
//Input: [2,3,1,1,4]
//Output: 2
//Explanation: The minimum number of jumps to reach the last index is 2.
//    Jump 1 step from index 0 to 1, then 3 steps to the last index.
//Note:
//
//You can assume that you can always reach the last index.
public class JumpGameII {

	// recursive + memo ==> TLE 这里
	// 实际对于Jump Game, 是个Greedy问题，只关心能否到达，而不在乎比如还剩下几步需要走，那么只关心能否到
	//	， 那就每次最大的判断（greedy）, 看在当前位置，最大能跑到哪，对能到的，那最少要几步， ===> 看下面的 Greedy算法
	public int jump(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        int[] memo = new int[nums.length];
        // for (int i = 0; i < nums.length; i++) {
        //     Arrays.fill(nums[i], -1);
        // }
        Arrays.fill(memo, -1);
        return dp(nums, 0, memo);
    }
    
    //int min = Integer.MAX_VALUE;
    public int dp (int[] nums, int s, int[] memo) {
        if (s == nums.length - 1) {
            return 0;
        }
        if (s >= nums.length)
            return -1;
        
        if (memo[s] != -1)
            return memo[s];
        
        //int jump = 0;
        int min = Integer.MAX_VALUE; // 针对每个子问题的 MIN， 所以要放在这里，不能设置为全局的，这是一个有return的递归
        for (int i = 1; i <= nums[s]; i++) {
        	if (i + s >= nums.length) break;
            int len = dp(nums, s + i, memo);
            if (len == -1 || len == Integer.MAX_VALUE) // 这里某个位置上可能是0 （[4,2, 0, 3]）
                continue;
            else {
                min = Math.min(min, len + 1);
                memo[s] = min;
            }
                
        }
        //min = Math.min(jump, min);
        //memo[s] = min;
        return min;
    }
    
    
    public int jumpGreedy(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        //if (nums.length == 1) return 0;
        int jump = 0;
        int currMaxPos = 0; // 当前从某个位置过来能到的最远的地方
        int preMax = 0; // 之前最远的地方，如果 i 到了之前的这个最远的地方，我们还没到终点，说明至少得重这里再jump++一次
        				// 而在所谓的最远之前肯定能到的，因为nums[i]表示跳的距离是 [1, nums[i]]
        for (int i = 0; i < nums.length - 1; i++) { 
            // if (maxPos < i || maxPos >= nums.length - 1)
            //     break;
            currMaxPos = Math.max(currMaxPos, nums[i] + i);
            // 如果i已经到了之前的最大的这里了，说明需要再jump一次了，同时这次jump的最远距离被currMaxPos更新
            if (preMax == i) { // == i 需要 +1， 那上面 for 就不用到 len - 1了，需要的最后一个jump这边加上了
            				// 同时这里 len - 1 是处理只有一个元素的时候 [2]; 如果这里一定要 ==len-1, 前面要单独处理
            				// 当 length == 1; return 0;
                jump++;
                preMax = currMaxPos;
                if (currMaxPos >= nums.length - 1) // 最大已经到终点了，不用判断了
                						// 这里不能放在外面，因为 preMax==i 要+1才能这么做， [2,3,1,1,4],
                						// 要等到 i = 2是才可以break, 否则jump=1,不对
                    break;
            }
            
        }
        return jump;
    }
    
    public static void main(String[] args) {
    	new JumpGameII().jump(new int[] {2,3,0,1,4});
    }
}
