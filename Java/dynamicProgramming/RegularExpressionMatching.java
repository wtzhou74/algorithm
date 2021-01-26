package dynamicProgramming;

//Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*'.
//
//'.' Matches any single character.
//'*' Matches zero or more of the preceding element.
//The matching should cover the entire input string (not partial).
//
//Note:
//
//s could be empty and contains only lowercase letters a-z.
//p could be empty and contains only lowercase letters a-z, and characters like . or *.
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
//p = "a*"
//Output: true
//Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
//Example 3:
//
//Input:
//s = "ab"
//p = ".*"
//Output: true
//Explanation: ".*" means "zero or more (*) of any character (.)".
//Example 4:
//
//Input:
//s = "aab"
//p = "c*a*b"
//Output: true
//Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
//Example 5:
//
//Input:
//s = "mississippi"
//p = "mis*is*p*."
//Output: false

public class RegularExpressionMatching {

	// Consider preceding result => FirstMatch
	public boolean isMatch(String s, String p) {
		//return recursion(s, p);
		
		// return bottomUpDY(S, P);
		
		Result[][] memo = new Result[s.length() + 1][p.length() + 1]; // It can be replaced with Boolean[][] whose default value is NULL
		return recusionMemo(0, 0, s, p, memo);
		
		//return topDownDY(s, p);
	}
	
	public boolean topDownDY(String s, String p) {
		// dp[i][j] is the result of s[1:i] and p[1:j]
		boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
		dp[0][0] = true;
		// initialization
		for (int i = 1; i <= s.length(); i++) {
			dp[i][0] = false;
		}
		for (int j = 1; j <= p.length(); j++) {
			if (p.charAt(j - 1) == '*') {
				dp[0][j] = dp[0][j - 2];
			} else {
				dp[0][j] = false;
			}
		}
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 1; j <= p.length(); j++ ) {
				if (p.charAt(j - 1) != '.' && p.charAt(j - 1) != '*') {
					// NOTE!!!!
					// CURRENT situation, and preceding solution is dp[i-1][j-1], with that to calculate new dp[i][j]
					// This is why we need add extra 1 when initing the dp array to store the final results
					// Remember current dp is calculated based on previous dp and current situation
					if (s.charAt(i - 1) != p.charAt(j - 1)) { 
						dp[i][j] = false;
					} else {
						dp[i][j] = dp[i - 1][j - 1];
					}
				} else if(p.charAt(j - 1) == '.') {
					dp[i][j] = dp[i - 1][j - 1];
				} else if (j >= 2) { // because we need to consider *
					if (p.charAt(j - 2) == s.charAt(i - 1)) {//p.charAt(j - 1) == '*'
						// dp[i-1][j-2] to dp[i][j] means matching exact one preceding character
						// dp[i][j-2] to dp[i][j] means matching 0 preceding character
						// dp[i-1][j] to dp[i][j] means more than one preceding character
						dp[i][j] = dp[i - 1][j - 2] || dp[i][j - 2] || dp[i - 1][j]; // NOTE
					} else if (p.charAt(j - 2) != '.') {// p.charAt(i-2) != s.charAt(i-1)
						dp[i][j] = dp[i][j-2];// '*' matches 0 preceding character
					} else {//p.charAt(i-2) = '.'
						dp[i][j] = dp[i - 1][j - 2] || dp[i][j - 2] || dp[i - 1][j];
					}
				}
			}
		}
		return dp[s.length()][p.length()];
	}
	
	// Time: O(s * p)
	// Space: O(s * p)
	public boolean bootomUpDY(String s, String p) {
		// dynamic programming
		// bottom-up
		// dp[i][j] is the result of s[i:] and p[j:]
		boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];// starting from len, we need set a extra len+1 value
		dp[s.length()][p.length()] = true;
		for (int i = s.length(); i >= 0; i--) { // i + 1
			for (int j = s.length() - 1; j >= 0; j--) { // j + 2
				boolean firstMatch = i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');
				if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
					dp[i][j] = dp[i][j + 2] || (firstMatch && dp[i + 1][j]);
				} else {
					dp[i][j] = firstMatch && dp[i + 1][j + 1];
				}
			}
		}
		
		return dp[0][0];
	}
	// 3 situations: '.', '*' and specific characters. where '*' depends on preceding characters, so, we split first/remaining match
	public boolean recursion(String s, String p) {
		if (p.isEmpty()) {
            return s.isEmpty();
        }
        boolean firstMatch = s.length() > 0 && (s.charAt(0) == p.charAt(0) || p.charAt(0) == '.');
        // check the characters starting from index 1
        if (p.length() >= 2 && p.charAt(1) == '*') {
            return isMatch(s, p.substring(2)) || // * matches 0 characters
                firstMatch && isMatch(s.substring(1), p);// matches at least 1 character, "aaaa" / "a*"
        } else {
            return firstMatch && isMatch(s.substring(1), p.substring(1));
        }
	}
	
	enum Result {
		True,
		False
	}
	// Top Down DP
	public boolean recusionMemo(int i, int j, String s, String p, Result[][] memo) {
		if (memo[i][j] != null) {
			return memo[i][j] == Result.True; // s[1:i], p[1:j] has been calculated
		}
		
		boolean res;
		// base case
		if (j == p.length()) {
			res = i == s.length();
		} else {
			boolean firstMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
			if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
				res = (recusionMemo(i, j + 2, s, p, memo) 
						|| firstMatch && recusionMemo(i + 1, j, s, p, memo));
			} else {
				res = firstMatch && recusionMemo(i + 1, j + 1, s, p, memo);
			}
		}
		
		memo[i][j] = res ? Result.True : Result.False; // res is NOT the value of enum Result
		
		return res;
	}
	
	public static void main(String[] args) {
		boolean res = new RegularExpressionMatching().isMatch("aab", "c*a*b");
		System.out.println(res);
	}
}
