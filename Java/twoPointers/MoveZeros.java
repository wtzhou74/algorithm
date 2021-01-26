package twoPointers;

public class MoveZeros {

	public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0
           || nums.length == 1) return;
        int i = 0, j = nums.length - 1;
        while (i < j) {
            if(nums[i] != 0) {
            	i++;
            	continue;
            }
            for (int k = i; k < j; k++) {
                nums[k] = nums[k + 1];
            }
            nums[j] = 0;
            j--;
        }
    }
	
	public void optimized(int[] nums) {
        if (nums == null || nums.length == 0
           || nums.length == 1) return;
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0)
                nums[index++] = nums[i];// 有0，那0肯定在非0前面，否则就不用换了， 所以此处非0的不会被不同于自己的值覆盖掉。而且这块 index <= i
        }
        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
	
	public static void main(String[] args) {
		new MoveZeros().moveZeroes(new int[] {0, 1, 0, 3, 12});
	}
}
