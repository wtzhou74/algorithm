package twoPointers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//Given an array of integers and an integer k, you need to find the number of unique k-diff pairs in the array. Here a k-diff pair is defined as an integer pair (i, j), where i and j are both numbers in the array and their absolute difference is k.
//
//Example 1:
//Input: [3, 1, 4, 1, 5], k = 2
//Output: 2
//Explanation: There are two 2-diff pairs in the array, (1, 3) and (3, 5).
//Although we have two 1s in the input, we should only return the number of unique pairs.
//Example 2:
//Input:[1, 2, 3, 4, 5], k = 1
//Output: 4
//Explanation: There are four 1-diff pairs in the array, (1, 2), (2, 3), (3, 4) and (4, 5).
//Example 3:
//Input: [1, 3, 1, 5, 4], k = 0
//Output: 1
//Explanation: There is one 0-diff pair in the array, (1, 1).
//Note:
//The pairs (i, j) and (j, i) count as the same pair.
//The length of the array won't exceed 10,000.
//All the integers in the given input belong to the range: [-1e7, 1e7].
public class KDiffPairsInAnArray {

	public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length <= 1) return 0;
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length;) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] - nums[i] == k) {
                    count++;
                    // remove duplicates
                    while (i + 1 < nums.length && nums[i + 1] == nums[i]) {
                        i++;
                    }
                    break;
                    // a当前 j值是等于，由于排序，往右只能是大于或者等于，由于不能取重复，所以没必要再继续下去
                    // while (j + 1 < nums.length && 
                    //        nums[j + 1] == nums[j]) {
                    //     j++;
                    // }
                }
                //j++;
            }
            i++;
        }
        return count;
    }
	
	// Time: O(N)
	public int optSol1(int[] nums, int k) {
        if (nums == null || nums.length <= 1 || k < 0) return 0; // k must be larger than 0 since it is absolute value
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i); // no duplicates
        }
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int target = nums[i] + k;
            if (map.containsKey(target)
               && map.get(target) != i) {// 不能是数自己 [1, 1, 1]  k = 0
                map.remove(target);// remove duplicates , 对加法，其中一个数相同，另一数一定相同，所以此处要删掉已获取的值
                count++;
            }
        }
        return count;
        
        // Concise alternative
        // 这里的 K 是 diff的结果
//        if (nums == null || nums.length <= 1 || k < 0) return 0;
//        int count = 0;
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int num : nums) map.put(num, map.getOrDefault(num, 0) + 1); // K ==0 统计重复的 num
//        for (int n : map.keySet()) {
//            if (k == 0 && map.get(n) >= 2) {
//                count++;
//            }
//        }
//        if (k == 0) return count;
//        for (int n : map.keySet()) {
//            if (map.containsKey(n + k)) { // K 是相减的结果， n + k 的结果也必须在 map中， 这里不会重复，  n1 + k = n2, 但 n2 + k = n1 不成立的， 除非 K == 0
//                count++;
//            }
//        }
//        return count;
    }
	
	// Time: O(NlgN)
	public int twoPointers(int[] nums, int k) {
        if (nums == null || nums.length <= 1 || k < 0) return 0;
        Arrays.sort(nums); // O(NlgN)
        int i = 0, j = i + 1, count = 0;
        while (j < nums.length) { //[1,1,3,4,5]
            if (nums[j] - nums[i] == k) {
                count++;
                while (i + 1 < nums.length && nums[i + 1] == nums[i]) {
                    i++;
                }
                i++;
                j = i + 1;
            } else if (nums[j] - nums[i] > k) {
                i++;
                j = i + 1;
            } else {
                j++;
            }
        }
        return count;
    }
	
	public static void main(String[] args) {
		new KDiffPairsInAnArray().optSol1(new int[] {1, 1, 3, 4, 5},  2);
	}
}
