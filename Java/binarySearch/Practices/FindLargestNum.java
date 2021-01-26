package binarySearch.Practices;

public class FindLargestNum {

	public int binarySearch(int[] nums, int i, int j) {
        //if (i == j) return nums[i];
		if(i == j) return i;
        int mid = i + (j - i) / 2;
        int left = binarySearch(nums, i, mid);
        int right = binarySearch(nums, mid + 1, j);
        //return Math.max(left, right); // return the MAX number, not index
        if (nums[left] > nums[right]) return left;
        else return right;
    }
	
	// or
	public int helper(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        int mid = left + (right - left) / 2;
        int left_max = helper(nums, left, mid);
        int right_max = helper(nums, mid + 1, right);
        if (left_max > right_max) return left_max;
        else  return right_max;
    }
}
