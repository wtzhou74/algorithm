package arrayString;

/**
 * Given an array of integers that is already sorted in ascending order, find two numbers such that they add up to a specific target number.
 * The function twoSum should return indices of the two numbers such that they add up to the target, where index1 must be less than index2.
 * Note:
 * Your returned answers (both index1 and index2) are not zero-based.
 * You may assume that each input would have exactly one solution and you may not use the same element twice.
 * */
public class TwoSumWithTarget {

	public static int[] twoSumSolution(int[] numbers, int target) {
		int leng = numbers.length;
        if (leng == 0
           || target < numbers[leng-1]) {
            return new int[2];
        }
        int[] result = new int[2];
        for (int i = leng - 1; i > 0; i--) {
        	
        	// minus numbers
            // if (numbers[i] > target) {
                // continue;
            // }
            for (int j = 0; j < i; j++) {
                if (numbers[i] + numbers[j] == target)
                {
                    result[0] = j;
                    result[1] = i;
                    return result;
                }
            }
        
        }
        return new int[2];
	}
	
	// two pointers MOVE TOGETHER in response to the sum result
	public static int[] twoSumOptimizedSol (int[] numbers, int target) {
		//the array is already SORTED in ascending order
		
		int len = numbers.length;
		if (len == 0) {
			return new int[2];
		}
		int[] result = new int[2];
		int i = 0, j = len - 1;
		while (i < j) {
			if (numbers[i] + numbers[j] > target) {
				j--;
			} else if (numbers[i] + numbers[j] < target) {
				i++;
			} else {
				// Returned answers are not ZERO-based.
				result[0] = i + 1;
				result[1] = j + 1;
				return result;
			}
		}
		
		return new int[2];
		
	}
}
