package recursionII.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

//Given an unsorted array of integers, find the length of longest increasing subsequence.
//
//Example:
//
//Input: [10,9,2,5,3,7,101,18]
//Output: 4 
//Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4. 
//Note:
//
//There may be more than one LIS combination, it is only necessary for you to return the length.
//Your algorithm should run in O(n2) complexity.

//Follow up: Could you improve it to O(n log n) time complexity?

public class LongestIncreasingSubsequence {

	// WRONG SOLUTION
	public int lengthOfLIS0(int[] nums) {
        if (nums.length == 0) return 0;
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = 1;
            int pre = nums[i]; // You need to check ALL of pre where inx < j
            for (int j = i + 1; j < nums.length; j++) {
                
                if (nums[j] > pre) {
                    temp++;
                    pre = nums[j];
                }
            }
            res = Math.max(res, temp);
            
        }
        return res;
    }
	
	// Time: O(n**2)
	// Space: O(n)
	public int dpSol(int[] nums) {
		if (nums.length == 0) return 0;
		int[] dp = new int[nums.length];// the longest subsequence is the length of nums itself
		//dp[0] = 1; // here 0 is index (but treated as "CURRENT nums" which includes 1 element)
		// ALTERNATIVE: we can initialize ALL dp with 1, since the shortest is 1 which it is length of the num itself
		for (int i = 0; i < nums.length; i++) {
			dp[i] = 1;
		}
		int maxLen = 1; // NOTE: Since nums.length > 0, so the result should be initialized to 1
		for (int i = 1; i < nums.length; i++) {
			// check the sequence before i
			int tempLongest = 0;
			// The following SEARCH can be optimized with BINARY SEARCH in O(lgn)
			for (int j = 0; j < i; j++) {// since j starts from 0, that is why we need to set dp[0] = 1
				if (nums[i] > nums[j]) { // skip nums[i] < nums[j], since nums[k - 1] < nums[i], then they have to less than nums[j]
					// the longest of i is bigger than or equal to current dp[j];
					// "CURRENT" longest, it is not FINAL longest of j
					tempLongest = Math.max(tempLongest, dp[j]);
				} // tempLongest is the RESULT when nums[i] > nums[j], then when we calculate dp[i], we should do tempLongest + 1, adding nums[i] itself.
			}
			dp[i] = tempLongest + 1;
			maxLen = Math.max(maxLen, dp[i]);
		}
		return maxLen;
		
	}
	
	// Time: O(nlgn)
	// Space: O(n) : size of dp array
	// DP + BINARY Search to realize time complexity O(nlgn)  => Find the inserting point
	// [9, 12, 6, 7]  => dp[0] = [9] dp[1] = [9, 12], dp[3] = [6, 12]
	// Here we JUST NEED TO know the LENGTH of DP, so, find the INSERTION POINT
	// In practice, we DONOT SET dp[i] AS AN ARRAY, while ==> dp[0] = 6, dp[1] = 7 
	public int dpAndBS(int[] nums) {
        if (nums.length == 0) return 0;
        int res = 0;
        int[] dp = new int[nums.length];
        int i = 0;
        //dp[0] = nums[0];
        for (int num : nums) {
                // find the insertion point
        	int insertInx = binarySearch(dp, 0, i - 1, num);
            if (insertInx == res) { // NEED TO INSERT AN NEW ITEM to dp
                res++;
                i++;
            }
            if (insertInx < 0) {
                dp[0] = num;
            } else {                
                dp[insertInx] = num;// WE JUST KEEP THE USEFUL NUM SINCE WE JUST NEED TO CALCULATE THE LENGTH
            }
            
        }
        return res;
    }
    
	
	public int recursiveSol(int[] nums) {
		if (nums.length == 0) return 0;
		//helper(nums, 0, Integer.MIN_VALUE, 0); //TLE
		int[][] cache = new int[nums.length + 1][nums.length];// longest length of pre to current 
		for (int[] c : cache) {
			Arrays.fill(c, -1);
		}
		return helper(nums, 0, -1, cache);
        //return longest;
	}
	
	// TLE
	private int longest = 1;
    public void helper(int[] nums, int i, int pre, int currentLongest) {
        if (i == nums.length) {
        	longest = Math.max(longest, currentLongest);// find a longest one
        	// ONCE RETURN, the CURRENTLONGEST will be popped up, e.g. 4, 3, 2, 1
            return ;
        }
        //int currentLongest = 0;
        for (; i < nums.length; i++) {
            if (nums[i] > pre) {
                helper(nums, i + 1, nums[i], currentLongest + 1);
                //currentLongest = temp + 1;
            } else {
                helper(nums, i + 1, pre, currentLongest);
            }            
            
        }
        //longest = Math.max(longest, currentLongest); // SINCE this statement will be executed when FOR finished, at that time,
        // CURRENTLONGEST has all POPPED UP, its value here WILL BE THE LEAST ONE which is the FIRST value pushed into the stack before.
    }
    
    // HELPER + MEMO
    // SPLIT INTO TWO GROUPS (INCREASEING SUBSEQUENCE, AND non-increasing) [like Tree - LEFT/RIGHT]
    public int helper(int[] nums, int i, int preInd, int[][] cache) {
    	if (i == nums.length) return 0;
    	if (cache[preInd + 1][i] != -1) {
    		System.out.println((preInd + 1) + " " + i);
    		return cache[preInd + 1][i];
    	}
    	int taken = 0;
    	if (preInd < 0 || nums[i] > nums[preInd]) {
    		taken = helper(nums, i + 1, i, cache) + 1;
    	}
    	// process remaining elements (NOTE: it also CONTAINS increasing subsequence), and find the largest len
    	int notaken = helper(nums, i + 1, preInd, cache);
    	
    	cache[preInd + 1][i] = Math.max(taken, notaken);
    	return Math.max(taken, notaken);
    	
    }
    
	// FIND THE INSERTION POINT
    public int binarySearch(int[] dp, int i, int j, int num) {
        while (i <= j) {            
            int mid = i + (j - i) / 2;
            if (num == dp[mid]) return mid;
            if (num < dp[mid]) {
                j = mid - 1;
            } else {
                i = mid + 1;
            }
        }
        return i;
            
    }
    
    public int backtracking(int[] nums) {
    	if (nums.length == 0) return 0;
    	List<Integer> stack = new ArrayList<>();
        //stack.add(nums[0]);
        backtrackingHelper(nums, stack, 0);
        return longest;
    }
    
    public void backtrackingHelper(int[] nums, List<Integer> res, int i) {
    	if (i == nums.length) {
            longest = Math.max(res.size(), longest);
            return;
        }
    	// Since the number of remaining elements are less than the longest, no need to process.
    	// TLE occurs without this statment
    	if (res.size() + nums.length - i < longest) return;
    	int largecnt = 0;// LOCAL variable, EACH recursion will have a copy of largest
        for (; i < nums.length; i++) {// the "EXTERNAL" i will be ADDED by 1 when one solution FOUND, which means, the "INNER" i reaches to nums.length
            if (res.size() == 0 || nums[i] > res.get(res.size() - 1)) {
            	largecnt++;
                res.add(nums[i]);
                backtrackingHelper(nums, res, i + 1);
                res.remove(res.size() - 1); // THINK ABOUT STACK
            }
            
        }
        // There are no items after current one bigger than current value. 
        // i ISNOT EQUIVALENT TO nums.length, BUT we still need to keep this longest value
        if (largecnt == 0)  // no needed for this statment
        	longest = Math.max(res.size(), longest);
    }
    
	
	public static void main(String[] args) {
		// new LongestIncreasingSubsequence().dpSol(new int[] {10,9,2,5,3,4});
		//int larestInd = new LongestIncreasingSubsequence().recursiveSol(new int[] {3,5,6,2,5,4,19,5,6,7,12});
		//int larestInd = new LongestIncreasingSubsequence().recursiveSol(new int[] {2, 5, 3, 4, 6, 7});
		int larestInd = new LongestIncreasingSubsequence().backtracking(new int[] {10,9,2,5,3,7,101,18});
		System.out.println(larestInd);
	}
}
