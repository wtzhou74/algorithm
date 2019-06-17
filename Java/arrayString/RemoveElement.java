package arrayString;

import java.util.Arrays;

//SIMILAR AS REMOVE ELEMENT
/*
 * Given an array nums and a value val, remove all instances of that value in-place and return the new length.
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
 * 
 * Clarification:
 * Confused why the returned value is an integer but your answer is an array?
 * Note that the input array is passed in by reference, which means modification 
 * to the input array will be known to the caller as well.
 * **/
public class RemoveElement {

	public static int removeElementSol(int[] nums, int val) {
		int len = nums.length;
        if (len == 0) {
			return 0;
		}
		int j = len - 1;
		int i = 0;
        if (len == 1 && nums[0] == val) {
            return 0;
        } else if (len == 1 && nums[0] != val) return 1;
        
        //!!!!!!!!!!!!!!!
        //Moving strategy
		while (i < j) {
			if (nums[i] == val && nums[j] == val) {
				//i++;
				if (i != j) j--;
				//if (i == j) return i;
				continue;
			}
			if (nums[i] == val && nums[j] != val) {
				nums[i] = nums[j];
				nums[j] = val;
				i++;
				//if (i == j) return i;
				// else j--;
				continue;
			}
			if (nums[i] != val && nums[j] == val) {
				i++;
				//if (i == j) return i;
				if (i != j) continue;
			}
            if (nums[i] != val && nums[j] != val) {
            	i++;
            	//if (i == j) return j + 1;
            	continue;
            }
		}
		
		if (nums[i] == val) return i;
		else return i + 1;
	}
	
	public static int removeElementOptimizedSol(int[] nums, int val) {
		int len = nums.length;
        if (len == 0) {
			return 0;
		}
        int i = 0;
        int j = 0;
        
        while (i < nums.length) {
        	if (nums[i] == val) {
        		i++;
        	} else {
        		// MOVING STRATEGY
        		nums[j] = nums[i];
        		i++;
        		j++;
        	}
        }
        
        return j;
	}
}
