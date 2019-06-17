package binarySearch.Practices;

/**
 * Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive), prove that 
 * at least one duplicate number must exist. Assume that there is only one duplicate number, find the duplicate one.
 * (Pigeonhole principle)
 * 
 * Note:
 * You must not modify the array (assume the array is read only).
 * You must use only constant, O(1) extra space.
 * Your runtime complexity should be less than O(n2).
 * There is only one duplicate number in the array, but it could be repeated more than once.
 * 
 * Reference to:
 * 		https://medium.com/solvingalgo/solving-algorithmic-problems-find-a-duplicate-in-an-array-3d9edad5ad41
 * **/
public class FindDuplicateNumber {

	public int findDuplicate(int[] nums) {
        if(nums.length == 0) return -1;
        // EACH INTEGER is BETWEEN 1 and N, due to this constraint, each value has its own corresponding index in the array
        // treated array as a linkedList
        // check cycle
        int slow = nums[0];
        int fast = nums[nums[0]];
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[nums[fast]];
        }
        
        // find the start point of cycle
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        
        return slow;
    }
}
