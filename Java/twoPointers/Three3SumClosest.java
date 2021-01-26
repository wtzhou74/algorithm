package twoPointers;

import java.util.Arrays;

//Given an array nums of n integers and an integer target, find three integers in nums such that the sum is closest to target. Return the sum of the three integers. You may assume that each input would have exactly one solution.
//
//Example:
//
//Given array nums = [-1, 2, 1, -4], and target = 1.
//
//The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
public class Three3SumClosest {

	// aCLOSEST ==> 距离最短 ==> 绝对值， 所以不能简单判断  < target 就可以，比如 Three3SumSmaller.java
	// a因为 > target 一样可能会有个最短距离， ==> 只能排序后，根据 sum - target 的值判断距离远近
	// ==> a这块是 “距离”，需要 sum的绝对值， 所以不好 先 target - nums[i]，所以先sum,再 two-pointers，达到 O（N^2）
	public int threeSumClosest(int[] nums, int target) {
		if (nums.length < 3) return 0;
        Arrays.sort(nums);
        int res = nums[0] + nums[1] + nums[nums.length - 1];
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1, k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k]; //a要求绝对值，所以不好像 Three3SumSmaller.java先减法
                // a对不同sum，对应不同的distance，之后对不同的distance，做判断
                if (sum < target) {
                    j++; //a当前形成的一个距离 （在 target的左边）  ==> 距离需要看两边
                } else {
                    k--;//target的右边
                }
                // a对当前距离下
                if (Math.abs(sum - target) < Math.abs(res - target)) {
                    res = sum;
                }
                if (res == target) //a因为只有一个解，也只需要一个，所以这边要相等了就跳出，没必要浪费时间再往下
                    return res;
                
            }
        }
        return res;
    }
    
    
    
    public static void main(String[] args) {
    	new Three3SumClosest().threeSumClosest(new int[] {-1,  1, 2, -4}, 0);
    }
}
