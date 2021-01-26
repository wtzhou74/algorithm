package arrayString;

//Given an array with n objects colored red, white or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white and blue.
//
//Here, we will use the integers 0, 1, and 2 to represent the color red, white, and blue respectively.
//
//Note: You are not suppose to use the library's sort function for this problem.
//
//Example:
//
//Input: [2,0,2,1,1,0]
//Output: [0,0,1,1,2,2]
//Follow up:
//
//A rather straight forward solution is a two-pass algorithm using counting sort.
//First, iterate the array counting number of 0's, 1's, and 2's, then overwrite array with total number of 0's, then 1's and followed by 2's.
//Could you come up with a one-pass algorithm using only constant space?


// LIMIT KINDs of ITEMs, e.g. BLUE, WHITE, RED
public class SortColors {

	// TWO-PASS algo using COUNT SORTING
	// KNOWN kinds of elements, e.g. 0, 1, 2
	public void sortColors(int[] nums) {
        if (nums.length == 0) return;
        int zeroCount = 0;
        int oneCount = 0;
        int twoCount = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroCount++;
            } else if (nums[i] == 1) {
                oneCount++;
            } else {
                twoCount++;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (zeroCount >= 1) {
                nums[i] = 0;
                zeroCount--;
            } else if (oneCount >= 1) {
                nums[i] = 1;
                oneCount--;
            } else {
                nums[i] = 2;
                twoCount--;
            }
        }
    }
	
	// MOVE 0 to FRONT, MOVE 2 to END of list
	public void onePass(int[] nums) {
		if (nums.length == 0) return;
//		int i = 0, j = nums.length - 1;
//        int zeroEnd = 0, twoStart = nums.length - 1;
//        while (i <= j) {// SHOULD check i==j, e.g. [2,0,1]
//            if (nums[i] == 0) {
//                int temp = nums[zeroEnd];
//                nums[zeroEnd] = nums[i];
//                nums[i] = temp;
//                zeroEnd++;
//                i++;
//                continue;
//            }
//            if (nums[i] == 2) {
//                int temp = nums[twoStart];
//                nums[twoStart] = nums[i];
//                nums[i] = temp;
//                twoStart--;
//                j--;
//                continue;
//            }
//            i++;
		if (nums.length == 0) return;
        int i = 0, j = nums.length - 1;
        int zeroIdx = -1;
        while (i <= j) {
            
            if (nums[i] == 0) {
                nums[i] = nums[++zeroIdx];
                nums[zeroIdx] = 0;
                i++; // i 有可能大于 zeroIdx 因为（中间）有1 的存在，所以我们要记录 0 的位置
            } else if (nums[i] == 2) {
                nums[i] = nums[j];
                nums[j] = 2;
                j--; // j始终记录最大值（2）的位置，其值就是最开始 2的位置
            } else i++;//对 1, 右移过去
        }
	}
}
