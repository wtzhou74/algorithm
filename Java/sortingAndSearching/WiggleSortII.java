package sortingAndSearching;

import java.util.Arrays;

//Given an unsorted array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
//
//Example 1:
//
//Input: nums = [1, 5, 1, 1, 6, 4]
//Output: One possible answer is [1, 4, 1, 5, 1, 6].
//Example 2:
//
//Input: nums = [1, 3, 2, 2, 3, 1]
//Output: One possible answer is [2, 3, 1, 3, 1, 2].
//Note:
//You may assume all input has valid answer.
//
//Follow Up:
//Can you do it in O(n) time and/or in-place with O(1) extra space?

public class WiggleSortII {

	public void wiggleSort(int[] nums) {
//        if (nums == null || nums.length == 0 || nums.length == 1) return;
//        int i = 0, j = i + 1;
//        while (i < nums.length && j < nums.length) {
//            if (i % 2 == 0) {
//                while (j < nums.length && nums[i] >= nums[j]) {
//                    j++;
//                }                
//            } else {
//                while (j < nums.length && nums[i] <= nums[j]) {
//                    j++;
//                }
//            }
//            if (j >= nums.length) {
//                int temp = nums[i];
//                nums[i] = nums[nums.length - 1];
//                nums[nums.length - 1] = temp;
//            } else if (j > i + 1) {
//                int temp = nums[i + 1];
//                nums[i + 1] = nums[j];
//                nums[j] = temp;
//            }
//            i++;
//            j = i + 1;
//        }
        
		if (nums == null || nums.length == 0 || nums.length == 1) return;
        Arrays.sort(nums);
        int i = (nums.length - 1) / 2, j = nums.length - 1;
        
        int[] aux = new int[nums.length];
        int k = 0;
        while (i >= 0 || j > (nums.length - 1) / 2) { // NOTE: Starting from left-most of both half !!!!! [4,5,5,6] will be wrong if starting from the first of each half
            if (k % 2 == 0)
                aux[k] = nums[i--];
            else
                aux[k] = nums[j--];
            k++;
        }
        for (int m = 0; m < aux.length; m++) {
            nums[m] = aux[m];
        }
		
        System.out.println("");
    
    }
	
	public static void main(String[] args) {
		new WiggleSortII().wiggleSort(new int[] {4,5,5,6});
	}
}
