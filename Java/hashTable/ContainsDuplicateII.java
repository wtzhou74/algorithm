package hashTable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array 
 * such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
 * 
 * KIND OF SIMILIAR TO ContainsNearbyAlmostDuplicate.java (a window of K)
 * */
public class ContainsDuplicateII {

	// HASHSET SOLUTION - since need to find DUPLICATION
	public boolean containsNearbyDuplicate(int[] nums, int k) 
    {
        if(nums.length > 1000) return false;
        Set<Integer> set = new HashSet<Integer>();
        for(int i=0; i<nums.length;i++){
        	// keep the number of items within (1, k)
            if(i>k) set.remove(nums[i-k-1]);
            // if there are duplication within K numbers, return true
            if(!set.add(nums[i])) return true;// add() return false if the item exist.
        }        
        return false;
    }
	
	// HASHTABLE - since we need to process BOTH index and value
	public boolean containsNearbyDuplicateSol (int[] nums, int k) {
		if (nums == null || nums.length == 0) return false;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if (Math.abs(i - map.get(nums[i]) ) <= k) {
                    return true;
                } else {
                    // in case there duplications after i, so replace the old value with new one
                    map.put(nums[i], i);
                }
            } else map.put(nums[i], i);
        }
        return false;
	}
	
	public static void main(String[] args) {
		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		System.out.println(set.add(1));
	}
}
