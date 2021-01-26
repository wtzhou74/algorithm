package arrayString;

import java.util.Arrays;

//Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].
//
//Example:
//
//Input:  [1,2,3,4]
//Output: [24,12,8,6]
//Note: Please solve it without division and in O(n).
//
//Follow up:
//Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)
public class ProductOfArrayExceptSelf {

	// Division solution, but it is not allowed
	public int[] productExceptSelf(int[] nums) {
		if (nums.length == 1) return new int[] {nums[0]};
        int[] res = new int[nums.length];
        int total = 1;
        int zeroCount = 0;
        for (int item : nums) {
            if (item == 0) {
                zeroCount++;
                continue;
            }
            total *= item;
        }
        if (zeroCount >= 2) return res;
        if (zeroCount == 1) {
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == 0) {
                    res[i] = total;
                    return res;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
                res[i] = total / nums[i];
        }
        return res;
	}
	
	// LEFT * RIGHT
	// TIME: O(N)
	// Space: O(N)
	public int[] sol2(int[] nums) {
		if (nums.length == 1) return new int[] {nums[0]};
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);
//        int k = -1;
//        int temp = 1;
//        for (int i = 0; i < nums.length; i++) {
//            if (k == -1) {
//                temp *= 1;
//                left[0] = 1;
//                k++;
//                continue;
//            }             
//            temp *= nums[k];
//            left[i] = temp;
//            k++;
//            
//        }
//        k = nums.length;
//        temp = 1;
//        for (int i = nums.length - 1; i >= 0; i--) {
//            if (k == nums.length) {
//                right[nums.length - 1] = 1;
//                k--;
//                continue;
//            }            
//            temp *= nums[k];
//            right[i] = temp;
//            k--;
//        }
        // OPTIMIZE CODE   ===> left[i - 1] already CONTAINs the PRODUCT of the left of 'i - 1', so simply multiplying it with nums[i - 1] to get LEFT[i]
        left[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            left[i] = left[i - 1] * nums[i - 1];
            
        }
        right[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            right[i] = right[i + 1] * nums[i + 1];
        }
        for (int i = 0; i < nums.length - 1; i++) {
            left[i] = right[i] * left[i];   
        }
        return left;
	}
	
	// Space: O(1)
	public int[] sol3(int[] nums) {
		if (nums.length == 1) return new int[] {nums[0]};
        int[] res = new int[nums.length];
        Arrays.fill(res, 1);
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
            
        }
        int temp = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
        	temp *= nums[i + 1];
            res[i] = res[i] * temp;
            //res[i] = res[i + 1] * nums[i + 1]; // WRONG
        }
        return res;
	}
	public static void main(String[] args) {
		new ProductOfArrayExceptSelf().sol3(new int[] {1, 2, 3, 4});
	}
}
