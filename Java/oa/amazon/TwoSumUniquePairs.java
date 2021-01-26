package oa.amazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Given an int array nums and an int target, find how many unique pairs in the array such that their sum is equal to target. Return the number of pairs.
//
//Example 1:
//
//Input: nums = [1, 1, 2, 45, 46, 46], target = 47
//Output: 2
//Explanation:
//1 + 46 = 47
//2 + 45 = 47
//Example 2:
//
//Input: nums = [1, 1], target = 2
//Output: 1
//Explanation:
//1 + 1 = 2
//Example 3:
//
//Input: nums = [1, 5, 1, 5], target = 6
//Output: 1
//Explanation:
//[1, 5] and [5, 1] are considered the same.

public class TwoSumUniquePairs {

	// Time: O(n)
	public int numOfUniquePairs(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		List<int[]> pairs = new ArrayList<>();
		int count = 0;
		for (int i = 0; i < nums.length; i++) {
			if (!map.containsKey(nums[i])) // 统计每个数出现的次数
				map.put(nums[i], 1);
			else
				map.put(nums[i], map.get(nums[i]) + 1);
		}
		
		// 这边map的key,保证了有多对 [3,5],[3,5], [3,3],[3,3], 只会判断一次，因为map中的key是唯一的！！！！
		for (int key : map.keySet()) {
		//for (int i = 0; i < nums.length; i++) {
			int diff = target - key;
			// m < n, for (m, n) and (n, m) are same pair for the same target
			// [3, 5] [5, 3] 是一样的，所以这边用 "<" 只取一边
			if (diff < key && map.containsKey(diff)) {
				pairs.add(new int[] {diff, key});
				count++;
			}
			// if there are same
			// 对于相同的，该key必要对应多个idx才可以，
			// 这边map的key,保证了有多对 [3,5],[3,5], [3,3],[3,3], 只会判断一次，因为map中的key是唯一的！！！！
			if (diff == key && map.get(diff) > 1) {
				pairs.add(new int[] {diff, key});
				count++;
			}
		}
		
		System.out.println(count);
		return count;
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {4, 3, 3, 3, 3, 4};
		new TwoSumUniquePairs().numOfUniquePairs(nums, 6);
	}
}
