package sortingAndSearching;

public class WiggleSort {

	// The difference between WiggleSort and WiggleSortII is that the latter does not accept '='
	public void wiggleSort(int[] nums) {
        if (nums == null || nums.length == 0
            || nums.length == 1) return;
        // Arrays.sort(nums); // O(nlgn)
        // for (int i = 1; i < nums.length - 1; i += 2) {
        //     int temp = nums[i];
        //     nums[i] = nums[i + 1];
        //     nums[i + 1] = temp;
        // }
        int i = 0;
        while (i < nums.length - 1) {
            if (i % 2 == 0) {
                if (nums[i] > nums[i + 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                } else 
                    i++;
            } else {
                if (nums[i] < nums[i + 1]) {
                    int temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                } else 
                    i++;
            }
            
        }
    }
}
