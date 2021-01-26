package recursionII.backtracking;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

	// Time: O(n^n) without MEMO, the worst case is "aaaaaaa", and every PREFIX is present in WordDict
	// For Time Complexity Analysis: https://leetcode.com/problems/word-break/discuss/169383/The-Time-Complexity-of-The-Brute-Force-Method-Should-Be-O(2n)-and-Prove-It-Below
	// Time: O(n^2), if the substring() is O(n), then total is O(n^3)  => T(n) = T(n-1) + n = T(n-2) + (n-1) + n = ... = T(1) + 2 + 3 + ... + n = n(n+1)/2 => O(n^2)
	// Space: O(n), since the depth of recursion is n
	public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.size() == 1) {
            return s.equals(wordDict.get(0));
        }
        return backtracking(s, new HashSet(wordDict), 0, new Boolean[s.length()]); // list.contains has O(n) time
    }
    
    public boolean backtracking(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (s.length() == start) {
            return true;
        }
        if (memo[start] != null) return memo[start];
        for (int i = start + 1; i <= s.length(); i++) {
        	 // If false, move END to the next position, try the new word , THAT IS WHY WE SHOULD PUT backtracking in the IF conditional statement
        	// IFF the current substring and remain substring(backtracking) are both true, return true
            if (wordDict.contains(s.substring(start, i)) && backtracking(s, wordDict, i, memo)) {// Treat subproblem as a WHOLE
                memo[start] = true;
                return true; // backtracking has a required RETURN
                //return backtracking(s, wordDict, i);
                //i--; // NO, They are CONTINUOUS, RETRUN FALSE if current substring is not true, you CANNOT skip and jump to the next next character.
                		// Leetcode [le, lee, leet] le will be checked first, then lee, so if lee is not true, no need to track back to check le again
            }
        }
        memo[start] = false;
        return false;
    }
    
    
    // Dynamic Programming
    // Time: O(n^2)
    // Space: O(n)
    public boolean dp(String s, List<String> wordDict) {
    	if (wordDict.size() == 1) {
            return s.equals(wordDict.get(0));
        }
        boolean[] dp = new boolean[s.length() + 1];// each of them is the solution of substring [0, i]/SUBPROBLEM
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (wordDict.contains(s.substring(j, i)) && dp[j]) { // dp[j] && substring(j, i); dp[j] is a KNOWN solution of subproblem(0, j)
                    dp[i] = true; // solution of [0, j] && [j + 1, i]
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
