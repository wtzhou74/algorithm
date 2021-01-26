package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

//Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.
//
//'?' Matches any single character.
//'*' Matches any sequence of characters (including the empty sequence).
//The matching should cover the entire input string (not partial).
//
//Note:
//
//s could be empty and contains only lowercase letters a-z.
//p could be empty and contains only lowercase letters a-z, and characters like ? or *.
//Example 1:
//
//Input:
//s = "aa"
//p = "a"
//Output: false
//Explanation: "a" does not match the entire string "aa".
//Example 2:
//
//Input:
//s = "aa"
//p = "*"
//Output: true
//Explanation: '*' matches any sequence.
//Example 3:
//
//Input:
//s = "cb"
//p = "?a"
//Output: false
//Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
//Example 4:
//
//Input:
//s = "adceb"
//p = "*a*b"
//Output: true
//Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
//Example 5:
//
//Input:
//s = "acdcb"
//p = "a*c?b"
//Output: false
public class WildcardMatch {

	// Time: O(min (s, p)); O(s lgP) for average case
	// Space: O(1)
	public boolean twoPointers(String s, String p) {
		if (s == null || p == null) {
			return false;
		}
		if (s.equals(p) || p.equals("*")) return true;
		int idxs = 0, idxp = 0, idx_match = -1, idxp_stat = -1;
		while (idxs < s.length()) {
			// s[i] and p[j] are same or p[j] is ?
			if (idxp < p.length() && (s.charAt(idxs) == p.charAt(idxp) 
					|| p.charAt(idxp) == '?')) {
				idxs++;
				idxp++;
			} else if (idxp < p.length() && p.charAt(idxp) == '*') {
				// assuming * matches EMPTY character
				idx_match = idxs;// current matched character from s
				// idxp_stat points to the current '*', in case we need 
				// go back to the current idx of '*' if current match solution failed
				idxp_stat = idxp;
				idxp++;
			} else if (idx_match == -1) { // no character matched (ELSE IF here, NOT if)
				// 之前已经做了是否匹配的判断，如果能到这里，说明没有字符能匹配
				return false;
			}else {
				// * matches at least one character
				// OR *-0-match solution failed, and now we need to go back to the "current" * to try 
				// * matches at least one character
				idxs = idx_match + 1;// since * matches one character, now we need to check x + 1th character
				idxp = idxp_stat + 1;// that is why we need +1 here
				idx_match = idxs;// reset current MATCHED character
			}
		}
		for (int i = idxp; i < p.length(); i++) {
			if (p.charAt(i) == '*') {
				idxp++;
				continue;
			}
		}
		return idxp == p.length() ? true : false;
	}
	
	// Applying the same strategy to this problem, see EditDistance.java
	// Time: O(SP) where s and p are lengths of the input string and the pattern correspondingly
	// Space: O(SP) to keep the MATRIX
	public boolean dynamicProgramSol(String s, String p) {
		int sLen = s.length();
		int pLen = p.length();
		
		// base case
		if (p.equals(s) || p.equals("*")) return true;
		if (p.isEmpty() || s.isEmpty()) return false;
		
		//init all matrix except [0][0] element as FALSE
		boolean[][] dp = new boolean[sLen + 1][pLen];
		dp[0][0] = true; // see DIAGRAM WildcardMatching.png
		
		for (int pidx = 1; pidx <= p.length(); pidx++) {
			if (p.charAt(pidx -1) == '*') {
				int sidx = 1;
				// matches as many as characters
				while (!dp[pidx - 1][sidx - 1] && sidx <= sLen) {
					sidx++;// find the first matched characters
				}
				dp[pidx][sidx - 1] = dp[pidx - 1][sidx - 1]; // see diagram WildcardMatching.png
				// for the remaining characters, '*' can matches all of them  (SUBPROBLEM solution)
				while (sidx < sLen - 1) {
					dp[pidx][sidx] = true;
					sidx++;
				}
			} else if (p.charAt(pidx - 1) == '?' ) {
				for (int sidx = 1; sidx <= sLen; sidx++) {
					dp[pidx][sidx] = dp[pidx - 1][sidx - 1];
				}
			} else {
				// neither '*' nor '?'
				for (int sidx = 1; sidx <= sLen; sidx++) {
					// dynamic programming is a process to POPULATE dp[][] (handle subproblem), all subproblem MUST BE solved.
					dp[pidx][sidx] = dp[pidx - 1][sidx - 1] && 
							(p.charAt(pidx - 1) == s.charAt(sidx - 1));
				}
			}
		}
		
		return dp[sLen][pLen];
	}
	
	// Straightforward solution => Recursion + Memo ==> Since it keeps comparing s[i] and p[j]
	// TLE 
	// 		for worst case: s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa..."; p = "*aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa...*";
	// Time: O(min(s, p));  O(2 ^ min(s, p/2)) for worst case because of * , the depth of tree
	public boolean isMatch(String s, String p) {
		
		if (s == null || p == null) return false;
		if (p.equals("*")) return true;
		if (s.equals(p)) return true;
		// clean up the string like "ac******b" which equals "ac*b" since * can match any sequence of characters
		// it can reduce the length of recursion
		StringBuilder sb = new StringBuilder();
		sb.append(p.charAt(0));
		for (int i = 1; i < p.length(); i++) {
			if (p.charAt(i - 1) == '*' && p.charAt(i) == '*')
				continue;
			else {
				sb.append(p.charAt(i));
			}
		}
		return helper(s, sb.toString(), new HashMap<>());
		
	}
	public boolean helper(String s, String p, Map<String, Boolean> memo) {
		if (p.equals(s)) return true;
		if (p.equals("*")) return true;
		//if (s.length() == 0 && p.length() == 0) return true;
		if (s.length() == 0 || p.length() == 0) return false;
		
//		if (s.length() == 1) {
//			if (p.equals("?")) return true;
//			else return false;
//		}
		//Pair pair = new Pair(s, p);
		if (memo.containsKey(s + "/" + p)) // WRONG S + P for s = "aaaaaa", p = "a*a??"
			return memo.get(s + "/" + p);
		
		if (s.charAt(0) == p.charAt(0) || p.charAt(0) == '?') {
			boolean s1 = helper(s.substring(1, s.length()), p.substring(1, p.length()), memo);
			memo.put(s + "/" + p, s1);
		} else if (p.charAt(0) == '*') {
			boolean s2 = helper(s, p.substring(1, p.length()), memo); // "*" matches no character
			boolean s3 = helper(s.substring(1, s.length()), p, memo); // "*" matches at least one character
			memo.put(s + "/" + p, s2 || s3);
		} else {
			memo.put(s + "/" + p, false);
		}
		return memo.get(s + "/" + p);
	}
	
	
	public static void main(String[] args) {
		boolean res = new WildcardMatch().twoPointers("aa", "a");
		System.out.println(res);
	}
	
}
