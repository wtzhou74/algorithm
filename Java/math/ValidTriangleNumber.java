package math;

import java.util.Arrays;

//Given an array consists of non-negative integers, your task is to count the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.
//Example 1:
//Input: [2,2,3,4]
//Output: 3
//Explanation:
//Valid combinations are: 
//2,3,4 (using the first 2)
//2,3,4 (using the second 2)
//2,2,3
//Note:
//The length of the given array won't exceed 1000.
//The integers in the given array are in the range of [0, 1000].
public class ValidTriangleNumber {

	public int triangleNumber(int[] nums) {
        if (nums == null || nums.length < 3)
            return 0;
        int count = 0;
        Arrays.sort(nums);
        for (int i = nums.length - 1; i >= 2; i--) {
            int j = 0, k = i - 1;
            while (j < k) {
                if (nums[j] + nums[k] > nums[i]) {
                    count += k - j; //错误的如果只是 count++，会丢掉 k到j直接的数
                    k--;
                } else 
                    j++;
            }
        }
        return count;
    }
}
