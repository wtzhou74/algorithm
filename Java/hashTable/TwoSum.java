package hashTable;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

	public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> minusResultMap = new HashMap<Integer, Integer>();
        int[] result = new int[2];
		for (int j = 0; j < nums.length; j++) {
			if (minusResultMap.containsKey(target - nums[j])) {
				result[0] = minusResultMap.get(target - nums[j]);
				result[1] = j;
                break;
			}
            minusResultMap.put(nums[j], j);
		}
        return result;
    }
}
