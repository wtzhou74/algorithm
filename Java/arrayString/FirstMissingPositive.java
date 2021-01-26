package arrayString;

import java.util.Arrays;

//Given an unsorted integer array, find the smallest missing positive integer.
//
//Example 1:
//
//Input: [1,2,0]
//Output: 3
//Example 2:
//
//Input: [3,4,-1,1]
//Output: 2
//Example 3:
//
//Input: [7,8,9,11,12]
//Output: 1
//Note:
//
//Your algorithm should run in O(n) time and uses constant extra space.
public class FirstMissingPositive {

	// Time: O(NlgN)
	public int firstMissingPositive(int[] nums) {
        if (nums.length == 0) return 1;
        Arrays.sort(nums); // O(NlgN)
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                continue;
            }
            if (i != 0 && nums[i - 1] == nums[i]) continue; // Case: [1, 1, 2]
            if (sum + 1 < nums[i]) { // 累加
                return sum + 1;
            }            
            sum += 1;
        }
        return sum + 1;
    }
	
	// MINUS and num > nums.length are INVALID, no need to consider
	// Time: O(n)
	// Space: O(n)
	public int sol2(int[] nums) {
        if (nums.length == 0) return 1;
        int[] aux = new int[nums.length + 1];// no need +2, no need to consider value > nums.length; 如果有，之前肯定有MIN POSITIVE NUM ！！！
        // BUCKET
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0 || nums[i] > nums.length) {
                aux[0] = nums[i];
                continue;
            }
            aux[nums[i]] = 1;
        }
        for (int i = 1; i < aux.length; i++) {
            if (aux[i] != 1) return i;
        }
        return nums.length + 1;
    }
	
	
	// 数值 => 数组下标  // CANNOT APPLY 1+2+3+4...n - (1+2+..m-1, m-2,...n) since the elements are not consecutive, it can 1, 3, 4, 6
	// TIME: O(N)
	// Space: O(1)
	public int sol3(int[] nums) {
		if (nums.length == 0) return 1;
		boolean isOne = false;
        for(int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                isOne = true;
            }
            if (nums[i] <= 0 || nums[i] > nums.length) nums[i] = 1;// ALL minus and num > nums.length are set to 1
        }
        if (!isOne) return 1;// NO ONE, then MIN positive value is 1
        // Processing following actions since 1 exists, so MINUS and NUM > nums.length can be ignored
        for (int i = 0; i < nums.length; i++) {
            int idx = Math.abs(nums[i]);
            if (nums[idx - 1] < 0) continue; // because it is duplicate, no need to change the sign again
            nums[idx - 1] = -nums[idx - 1]; // change the sign, meaning this positive value exists
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) return i + 1;
        }
        return nums.length + 1;
	}
	
}
