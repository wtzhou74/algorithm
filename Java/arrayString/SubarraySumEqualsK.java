package arrayString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubarraySumEqualsK {
	
	// RELATED: MinSubArrayLen.java

	// !!!!! SUM[i, j] = SUM[0, j] - SUM[0, i - 1] = k => sum[0, j] - k = sum[0, i - 1]
	
	public static int subArraySumK (int[] nums, int k) {
		int sum = 0, count = 0;
		Map<Integer, Integer> map = new HashMap<>();
		map.put(0, 1); // HAVE TO INITIALIZE CASE SUM = 0
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			// sum[0, j] - k = sum[0, i - 1] EXISTED
			if (map.containsKey(sum - k)) {
				count += map.get(sum - k);
			}
			map.put(sum, map.getOrDefault(sum, 0) + 1);
		}
		
		return count;
	}
	
	public static int distinctArrayPair(int[] nums, int k) {
		Arrays.sort(nums);
		int i = 0, j = nums.length - 1, count = 0;
		while (i < j) {
			if (nums[i] + nums[j] == k) {
				count++;
				i++;
				j--;
				while (nums[i] == nums[i-1]) i++;
				while (nums[j] == nums[j+1]) j--;
			} else if (nums[i] + nums[j] > k) {
				j--;
			} else i++;
		}
		return count;
	}
	
	public static void main(String[] args) {
		int[] nums = {7,6,6,3,9,3,5,1};
		subArraySumK(nums, 12);
		distinctArrayPair(nums, 12);
	}
	
}
