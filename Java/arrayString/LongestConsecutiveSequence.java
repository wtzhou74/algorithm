package arrayString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//Given an unsorted array of integers, find the length of the longest consecutive elements sequence.
//
//Your algorithm should run in O(n) complexity.
//
//Example:
//
//Input: [100, 4, 200, 1, 3, 2]
//Output: 4
//Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

public class LongestConsecutiveSequence {

	 // Time: O(NlgN)
	 public int longestConsecutive(int[] nums) {
	        if (nums.length == 0) return 0;
	        if (nums.length == 1) return 1;
	        Arrays.sort(nums); // O(NlgN)
	        int res = 1;
	        int temp = 1;
	        for (int i = 1; i < nums.length; i++) {
	            if (nums[i - 1] == nums[i]) continue;// for duplicates, e.g. [1, 1, 2] !!!, or can be place them into set to remove duplicates
	            if (nums[i - 1] + 1 == nums[i]) {
	                temp++;
	                continue;
	            }
	            res = Math.max(temp, res);    
	            temp = 1;
	        }
	        res = Math.max(temp, res); // [1, 2, 3], the previous max() wont be executed
	        
//	        int max = Integer.MIN_VALUE;
//	        for (int num : nums) {
//	            max = Math.max(num, max);
//	        }
//	        int[] aux = new int[max + 1];
//	        for (int i = 0; i < nums.length; i++) {
//	            aux[nums[i]] = 1; // nums[i] can be MINUS, while the INDEX CANNOT be MINUS
//	        }
//	        int res = 0;
//	        int temp = 0;
//	        for (int i = 0; i < aux.length; i++) {
//	            if (aux[i] == 1) temp++;
//	            else {
//	                res = Math.max(res, temp);
//	                temp = 0;
//	            }
//	        }
//	        res = Math.max(res, temp);
//	        return res;
	        return res;
	 }
	 
	 public int sol2(int[] nums) {
		 if (nums.length == 0) return 0;
	        if (nums.length == 1) return 1;
	        Set<Integer> set = new HashSet<Integer>();
	        for (int num : nums) {
	            set.add(num);
	        }
	        int res = 1;
	        for (int num : set) {
	            if (!set.contains(num - 1)) { // !!!!!! then WHILE will be seen as LINEAR, ONLY STARTING from the least num of a POTENTIAL consecutive sequence
	            	// TIME will be O(N^2) without this statement
	                int temp = num;
	                int max = 1;
	                while(set.contains(temp + 1)) { // contains() O(1)
 	                    max++;
	                    temp = temp + 1;
	                }
	                res = Math.max(res, max);
	            }            
	        }
	        return res;
	 }
}
