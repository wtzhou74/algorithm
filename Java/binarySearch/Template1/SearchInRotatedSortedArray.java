package binarySearch.Template1;

public class SearchInRotatedSortedArray {
	// FIGURE OUT HOW TO FIND SORTED SIDE (comparing MID and LEFT)
	// SORTED, ROTATED without duplicates : Split by MID, ALWAYS has one half SORTED number, so the problems becomes to 
	// find which side is sorted under which condition, see below. Then for each sorted side, handle the elements iteratively or recursively
	// RIGHT SIDE SORTED:  mid < right  (6 7 0 [1] 2 4 5)
	// LEFT SIDE SORTED: mid > right  (2 4 5 [6] 7 0 1)
	public static int searchRotatedSol (int[] nums, int target) {
		
		int left = 0, right = nums.length - 1;
		
		while (left <= right) {
            int mid = (left + right) >>> 1;
            if (nums[mid] == target) return mid;
            // right half sorted
            if (nums[mid] < nums[right]) {
                if (target > nums[mid] && target <= nums[right])
                    left = mid + 1;
                else  right = mid -1;
            } else // left half sorted
                {
                    if (target < nums[mid] && target >= nums[left]) {
                        right = mid - 1;
                    } else left = mid + 1;
                }
        }
        
        return -1;
        
        // recursion
        //return findHelper(nums, target, 0, nums.length - 1);
			
		}
	
	// find recursively
	public int findHelper(int[] nums, int target, int left, int right)
    {
        if (left > right) return -1;
        int mid = (left + right) >>> 1;
        if (nums[mid] == target) return mid;
        // right half sorted
        if (nums[mid] < nums[right]) {
            if (target > nums[mid] && target <= nums[right]) return findHelper (nums, target, mid + 1, right);
            else return findHelper (nums, target, left, mid - 1);
        } else {
            // left half sorted
            if (target >= nums[left] && target < nums[mid]) {
                return findHelper (nums, target, left, mid - 1);
            } else return findHelper (nums, target, mid + 1, right);
        }
    }
	
	// duplicates:  3 1 2 [3] 3 3 3 ,  3 3 3 [3] 1 2 3
	// Base on previous analysis, MID might equal END, 
	// so, beside above process, we should process the case MID=END
	public int searchSortedRotatedWithDuplicates(int[] nums, int target)
	{
		int left = 0, right = nums.length - 1;
		while (left <= right) {
			int mid = left + (right - left) / 2;
			if (nums[mid] == target) return mid;
			// right side sorted
			if (nums[mid] < nums[right]) {
				if (target > nums[mid] && target <= nums[right])
					left = mid + 1;
				else right = mid - 1;
			} else if (nums[mid] > nums[right]) {// left side sorted
				if (target < nums[mid] && target >= nums[left])
					right = mid - 1;
				else 
					left = mid + 1;
			}
			else { // HANDLE DUPLICATES
				right--;
			}
		}
		
		return -1;
	}
}
