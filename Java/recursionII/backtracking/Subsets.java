package recursionII.backtracking;

import java.util.ArrayList;
import java.util.List;

//Given a set of distinct integers, nums, return all possible subsets (the power set).
//
//Note: The solution set must not contain duplicate subsets.
//
//Example:
//
//Input: nums = [1,2,3]
//Output:
//[
//  [3],
//  [1],
//  [2],
//  [1,2,3],
//  [1,3],
//  [2,3],
//  [1,2],
//  []
//]
public class Subsets {

	public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        
        // Do multiple COMBINATIONs
        for (int i = 0; i <= nums.length; i++) {
            backtrack(nums, i, 0, new ArrayList<>(), res);
        }
        return res;
    }
    public void backtrack(int[] nums, int length, int i, List<Integer> group, List<List<Integer>> res) {
        if (length == group.size()) {
            res.add(new ArrayList<>(group));
            return;
        }
        for (; i < nums.length; i++) {
            group.add(nums[i]);
            // For each solution, i WONT back to 0, so, i goes with each solution
            // Compared with PERMUTATIONs, i can go back to 0
            backtrack(nums, length, i + 1, group, res);
            group.remove(group.size() - 1);
        }
    }
}
