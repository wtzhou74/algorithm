package oa.amazon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Given a list of positive integers nums and an int target, return indices of the two numbers such that they add up to a target - 30.
//
//Conditions:
//
//You will pick exactly 2 numbers.
//You cannot pick the same element twice.
//If you have muliple pairs, select the pair with the largest number.
//Example 1:
//
//Input: nums = [1, 10, 25, 35, 60], target = 90
//Output: [2, 3]
//Explanation:
//nums[2] + nums[3] = 25 + 35 = 60 = 90 - 30
//Example 2:
//
//Input: nums = [20, 50, 40, 25, 30, 10], target = 90
//Output: [1, 5]
//Explanation:
//nums[0] + nums[2] = 20 + 40 = 60 = 90 - 30
//nums[1] + nums[5] = 50 + 10 = 60 = 90 - 30
//You should return the pair with the largest number.

public class FindPairWithGivenSum {

	public List<Integer> findPair(List<Integer> nums, int target) {
		if (nums == null || nums.size() < 2) return new ArrayList<>();
		List<Integer> res = Arrays.asList(-1, -1);
		
		target -= 30;
		int max = Integer.MIN_VALUE;
		Map<Integer, Integer> map = new HashMap<>();
		for (int i = 0; i < nums.size(); i++) {
			// 把判断放在 for里面，one-pass处理完
			int diff = target - nums.get(i);
			// 对小的忽略，即便这个是solution,因为我们只保留大值的
			if ((nums.get(i) > max || diff > max) && map.containsKey(diff)) {
				res.set(1, i); // 用它的话， list一定要有初始值
				res.set(0, map.get(diff));
				max = Math.max(nums.get(i), diff);
			}
			
			map.put(nums.get(i), i);
		}
		
		// 用两个pass 完成
//		for (int i  = 0; i < nums.size(); i++) {
//			int diff = target - nums.get(i);
//			if ((nums.get(i) > max || diff > max) && map.containsKey(diff) && map.get(diff) != i) {
//				res.set(1, i); // 用它的话， list一定要有初始值
//				res.set(0, map.get(diff));
//				max = Math.max(nums.get(i), diff);
//			}
//		}
		
		// !!!这里要注意 对没能找到的case, 这里的返回值是 null, empty list 还是这里的 [-, -1]也可以
		return res;
	}
	
	public static void main(String[] args) {
		List<Integer> nums = Arrays.asList(0, 0);
		new FindPairWithGivenSum().findPair(nums, 30);
	}
}
