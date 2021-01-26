package recursionII.backtracking;

import java.util.ArrayList;
import java.util.List;

/*Given two integers n and k, return all possible combinations of k numbers out of 1 ... n.

Example:

Input: n = 4, k = 2
Output:
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]*/

public class Combinations {

	public List<List<Integer>> combine(int n, int k) {
		List<List<Integer>> res = new ArrayList<>();
		doCombination(n, k, 1, new ArrayList<>(), res);
		return res;
	}
	// Backtracking or DFS
	public void doCombination(int n, int k, int i, List<Integer> group, List<List<Integer>> res) {
		// BASE CASE: REACH THE GOAL
		if (0 == k) {
			// found a solution
			res.add(new ArrayList<>(group)); // FOR group.remove(); so, no need to create a new array list
			return;
		}
		// I for element candidate for each solution; k for solution candidate, so when k reachs the group.size, one solution found.
		// i wont start from 0 for each time, otherwise, that is PERMUTATION
		for (; i <= n - k + 1; i++) { // i++ for next round; ONCE RECURSIVE IS DONE, i WILL GO BACK FROM 5 TO 1
			group.add(i); // wont repeated, so no need to check valid
			doCombination(n, k - 1, i + 1, group, res); // i + 1 for adjacent NEXT (i is the value added to group) // do not k--, k is the CURRENT solution index
			group.remove(group.size() - 1); // abandon previous one ; IT WILL BE EXECUTED AFTER RETURN PREVIOUSLY
		}
	}
	
	public static void main(String[] args) {
		List<List<Integer>> res = new Combinations().combine(4, 2);
		System.out.println();
	}
}
