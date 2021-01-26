package recursionII.backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

//Remove the minimum number of invalid parentheses in order to make the input string valid. Return all possible results.
//
//Note: The input string may contain letters other than the parentheses ( and ).
//
//Example 1:
//
//Input: "()())()"
//Output: ["()()()", "(())()"]
//Example 2:
//
//Input: "(a)())()"
//Output: ["(a)()()", "(a())()"]
//Example 3:
//
//Input: ")("
//Output: [""]

public class RemoveInvalidParentheses {

	// Optimal Solution
	public List<String> removeInvalidParentheses(String s) {
		List<String> res = new ArrayList<>();
		char[] check = new char[] {'(', ')'};
		dfs(s, res, check, 0, 0);
		return res;
	}
	
	// TWO WAYS to judge a valid parentheses => 1) Counter; 2) Stack
	// Trick: 1) ((())))) ; 2) (((()), then starting from left to right with check array[')', '('] to get first abnormal [REVERSE first!!!!!!]
	public void dfs (String s, List<String> res, char[] check, int last_i, int last_j) {
		int count = 0;
		int i = last_i;
		// count >= 0 means having more '('
		while (i < s.length() && count >= 0) {
			// non '(' or ')' e.g. 'a' wont affect count
			if (s.charAt(i) == check[0]) 
				count++; // "("
			if (s.charAt(i) == check[1]) 
				count--; // ")"
			i++;
		}
		// ()) will get the first invalid character, since it will cause count < 0
		if (count >= 0) { // count >= 0 and i == s.length(), it means there are more '(', e.g. s = '(((()'
			// no extra ')' is detected. we now have to detect EXTRA "(", e.g. s = "((((()";
			// so, we need to start from right to left
			String reversed = new StringBuffer(s).reverse().toString();
			if (check[0] == '(') // check if it is reversed at FIRST TIME, e.g. "((()" => ")((("; so: "(())" still need to be reversed
				// "(((()" => )((((
				dfs(reversed, res, new char[] {')', '('}, 0, 0); // once all recursion finished,
				// they will get a result like '))((', which is the reversion of original valid parentheses "(())"
			// that is why below statement add REVERSED value
			else 
				res.add(reversed);
		} else {
			// count < 0; e.g. "())))", extra ")" was detected
			// NOTE: i is the position of FIRST abnormal
			i -= 1; // 'i - 1' is the index of abnormal ')' which makes count < 0
			// Try removing one at each position, skipping duplicates; e.g. "())"; removing first ')', or second, we got the same results
			for (int j = last_j; j <= i; j++) {
				if (s.charAt(j) == check[1] && // j == last_j , the "FIRST" start symbol is ")", e.g. ()()
						(j == last_j || s.charAt(j - 1) != check[1])) {//s.charAt(j - 1) != check[1] => j is the FIRST ")"
					// remove one abnormal by applying s.substring(0, j) + s.substring(j+1, s.length()) and then recursion
					dfs(s.substring(0, j) + s.substring(j+1, s.length()), res, check, i, j);
					// "(())))" after first round: i = 4, j = 2, s = "(()))" => last_i = 4, last_j = 2
					//				  second round: i = 4, j = 2, s = "(())"
					// j is the index of FIRST ")"
					// i is the index of FIRST abnormal
				}
			}
		}
		
	}
	
	
	public List<String> bfs(String s) {
		//List<String>> res = new ArrayList<>();
		Queue<String> queue = new LinkedList<>();
		List<String> strs = new ArrayList<>();
		Set<String> set = new HashSet<>();
		queue.offer(s);
		set.add(s);
		int max = Integer.MIN_VALUE;
		while (!queue.isEmpty()) {
			String t = queue.poll();
			if (isValid(t)) {
				max = Math.max(t.length(), max);
				strs.add(t);
				continue;// check next string
			}
			// remove character, to check EACH POTENTIAL string
			// int count = 0;
			if (t.length() < max) continue;
			for (int i = 0; i < t.length(); i++) {
				if (t.charAt(i) != '(' && t.charAt(i) != ')')
					continue;
				
				String temp = t.substring(0, i) + t.substring(i + 1, t.length()); // remove the char whose index is i
				if (set.contains(temp)) continue;
				set.add(temp);
				queue.offer(temp);// add each string to queue to be checked
				
			}
			
		}
		return strs;
	}
	
	public boolean isValid(String s) {
		if (s.length() == 0) return true;
		int count = 0;
		int i = 0;
		while (i < s.length()) {
			if (s.charAt(i) == '(') {
				count++;
			}
			if (s.charAt(i) == ')')
				count--;
			if (count < 0) return false;
			i++;
		}
		return count == 0 ? true : false;
	}
	public static void main(String[] args) {
		//new RemoveInvalidParentheses().removeInvalidParentheses("(())))");
		// new RemoveInvalidParentheses().removeInvalidParentheses("()");
		// new RemoveInvalidParentheses().removeInvalidParentheses(")(");
		new RemoveInvalidParentheses().removeInvalidParentheses("()())()");
	}
}
