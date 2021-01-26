package recursionII.unfoldRecursion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

For example, given n = 3, a solution set is:

[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]*/

public class GenerateParentheses {

	public List<String> generateParenthesis(int n) {
		List<String> res = new ArrayList<>();
		if (n == 1) {
			res.add("()");
			return res;
		}
		placeCharacter("", 0, 0, n, res);
		return res;
		
		
	}
	// BACKTRACKING
	// Keeping track of the number of opening and closing brackets
	public void placeCharacter(String s, int open, int close, int n, List<String> res) {
		if (s.length() == n) {
			res.add(s);
			return;
		}
		// The character will be added only WHEN WE KNOW IT IS VALID. So NO CHECK needed, which is different from solution 2 below !!!!!!!!
		if (open < n) {
			placeCharacter(s + "(", open + 1, close, n, res);
		}
		if (close < open) { // Ensure that the ) comes after (
			placeCharacter(s + ")", open, close + 1, n, res);
		}
	}
	
	public List<String> solution2(int n) {
		List<String> res = new ArrayList<>();
		generateAll(new char[2 * n], 0, res);
		return res;
	}
	
	// Generate ALL sequence and then check validity
	public void generateAll(char[] curr, int pos, List<String> res) {
		if (curr.length == pos) {
			// get one combination, then check if it is valid
			if (isValid(curr)) {
				res.add(new String(curr));
			}
		}
		// THE FOLLOWING PROCESS IS KIND OF LIKE TREE -> LEFT, RIGHT child !!!!!!!!
		
		// add character "(" to curr
		curr[pos] = ')';
		generateAll(curr, pos + 1, res);  // "PROCESS LEFT CHILD"
		// add character "(" to curr 
		curr[pos] = '(';
		generateAll(curr, pos + 1, res);  // "PROCESS RIGHT CHILD"
	}
	
	// Check if a sequence is a well-formed parentheses
	public boolean isValid(char[] curr) {
		int i = 0;
		int balance = 0;
		while (i < curr.length) {
			if (curr[i] == '(') balance++;
			else balance--;
			if (balance < 0) return false;
		}
		return balance == 0;
	}
}
