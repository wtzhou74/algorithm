package recursionII.backtracking;

import java.util.ArrayList;
import java.util.List;

//Given a collection of distinct integers, return all possible permutations.
//
//Example:
//
//Input: [1,2,3]
//Output:
//[
//  [1,2,3],
//  [1,3,2],
//  [2,1,3],
//  [2,3,1],
//  [3,1,2],
//  [3,2,1]
//]
public class Permutations {

	public List<List<Integer>> permute(int[] nums) {
        if (nums.length == 0) {
            return new ArrayList<>();
        }
        List<List<Integer>> res = new ArrayList<>();
        backtrack(nums, new ArrayList<Integer>(), res);
        return res;
    }
	
	public void backtrack (int[] nums, List<Integer> sol, List<List<Integer>> res) {
		// BASE CASE
	    if (sol.size() == nums.length) {
	        res.add(new ArrayList<>(sol)); // FIND A SOLUTION sol, THEN ADD IT TO RES
	        return;
	    }
	    
	    // BACKTRACK
	    for (int i = 0; i < nums.length; i++) {
	    	 // CONSTRAINS
	    	if (!sol.contains(nums[i])) {// each time, i starts from 0
		        sol.add(nums[i]);
		        // meet the constraint then backtrack next
		        backtrack(nums, sol, res);// ONE STEP TO THE FINAL SOLUTION
		        sol.remove(sol.size() - 1);
	    	}
	    }
	}
	
	public static void main(String[] args) {
		int[] nums = new int[] {1, 2, 3};
		List<List<Integer>> res = new Permutations().permute(nums);
		System.out.println();
	}
}

