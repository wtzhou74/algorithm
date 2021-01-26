package math;

import java.util.HashMap;
import java.util.Map;

//Given an array of integers and an integer k, you need to find the total number of continuous subarrays whose sum equals to k.
//
//Example 1:
//
//Input:nums = [1,1,1], k = 2
//Output: 2
// 
//
//Constraints:
//
//The length of the array is in range [1, 20,000].
//The range of numbers in the array is [-1000, 1000] and the range of the integer k is [-1e7, 1e7].
public class SubarraySumEqualsK {

	// brute force， 就是两层for loop， 得到一个个子区间，对每个区间再判断有多少个符合的 array  O(n^3)
	// 或者利用累加和，相当于3sum  ==> O(n^2)
	public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return 0;
        int count = 0;
        int[] sum = new int[nums.length + 1];
        //sum[0] = 0;
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1]; // 利用累加和，sum[0] = 0;
        }
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j <= nums.length; j++) {
                if (sum[j] - sum[i] == k) {
                    count++;
                }
            }
        }
        return count;
    }
	
	
	// 把累加和sum【i】 放入map, 借助当前的累加和 sum【curr】 - k 看是否在 map中以及在的话有几个决定在这区间有几个符合的subarray
	public int subarraySum1(int[] nums, int k) {
        if (nums == null || nums.length == 0)
            return 0;
        int count = 0;
        
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1); // 这一点一定要加上，否则如果  currSum - k == preSum, 就会漏掉，因为 0没有值
        				// 或者在下面 加一个判断 - sum == k  then count++;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int diff = map.getOrDefault(sum - k, 0);
            count += diff;
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
