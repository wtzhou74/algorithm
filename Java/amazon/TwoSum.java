package amazon;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Given an array of integers, return indices of the two numbers such that they add up to a specific target.
//
//You may assume that each input would have exactly one solution, and you may not use the same element twice.
//
//Example:
//
//Given nums = [2, 7, 11, 15], target = 9,
//
//Because nums[0] + nums[1] = 2 + 7 = 9,
//return [0, 1].

public class TwoSum {

	
	public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return new int[0];
        int[] res = new int[2]; 
        //Arrays.sort(nums);// 不可以，否则修改了顺序，不好找回原来的idx
        // 前提是 只有一个solution, 所以有重复key（nums[i]）也没关系，因为这个key不会是solution, 否则就有重复了
        // 另外对只有一个solution的，假设如果有重复的key,且这个key对应的是idx是solution，map也只会保存一个，但按此题意不会有这样的case
        Map<Integer, Integer> map = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {  
        	// One pass, 否则放到另外一个 for, two-pass, 没必要
            int diff = target - nums[i];
            if (map.containsKey(diff)) {
                res[0] = map.get(diff);
                res[1] = i;
                return res;
            }
            map.put(nums[i], i);
        }
        // two-pass
        // for (int i = 0; i < nums.length; i++) {
        //     int diff = target - nums[i];
        //     if (map.containsKey(diff) && map.get(diff) != i) {
        //         res[0] = i;
        //         res[1] = map.get(diff);
        //         return res;
        //     }
        // }
        return res;
    }
}
