package arrayString;

public class RemoveZeros {

	public static void removeZerosSol(int[] nums) {
		if (nums.length == 0) {
			return;
		}
		int i = 0;// indicate the first zero-element
		for (int j = 0; j < nums.length; j++) {
			if (nums[j] != 0) {
				int temp = nums[i];
				nums[i] = nums[j];
				//nums[j] = 0;
				nums[j] = temp;//for length = 1 and the item is not 0
				i++;// notice the meaning of index i; refer to the RemoveDuplicates()
			}
		}
	}
	
	public static void removeZerosOptimized(int[] nums) {
		int i = 0;
		for (int j = 0; j < nums.length; j++) {
			if (nums[j] != 0) {
				nums[i] = nums[j];
				i++; // Do not do items exchanging now
			}
		}
		// set zero
		while (i < nums.length) {
			nums[i] = 0;
			i++;
		}
	}
}
