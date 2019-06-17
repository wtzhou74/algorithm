package binarySearch.Template3;
//Given an array of integers nums sorted
// in ascending order, find the starting and ending position of a given target value.
// Split it into two Template 1
public class SearchForARange {

	public int[] searchForRange (int[] nums, int target) {
		int[] result = {-1, -1};
        if (nums.length == 0) return result;
        //if (nums.length == 1) {return new result[]{0, 0};}
        int left = 0, right = nums.length - 1;
        int start = -1, end = -1;
        // apply Template 1 to find the LEFT/RIHGT MOST target position
        while (left < right) {
            int mid = (left + right) >>> 1; // mid always biased towards LEFT
            if (nums[mid] < target) left = mid + 1;
            else right = mid;
        }
        if (nums[left] != target) return result;
        else start = left;
        
        right = nums.length - 1;
        while (left < right) {
            int mid = ((left + right) >>> 1) +1;//!!!!! make the mid biased towards right otherwise the search range might not move
            if (nums[mid] > target) right = mid - 1;
            else left = mid;// here tells why the above mid should ADD + 1. here LEFT == MID, So give [1, 2], the MID without adding 1
                            // also 1 which equals LEFT
        }
        end = left;
        
        result[0] = start;
        result[1] = end;
        
        return result;
	}
}
