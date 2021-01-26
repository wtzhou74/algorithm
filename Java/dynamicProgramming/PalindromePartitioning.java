package dynamicProgramming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Given a string s, partition s such that every substring of the partition is a palindrome.
//
//Return all possible palindrome partitioning of s.
//
//Example:
//
//Input: "aab"
//Output:
//[
//  ["aa","b"],
//  ["a","a","b"]
//]

public class PalindromePartitioning {
	
	// refer to LongestPalindromicSubstring.java
	public List<List<String>> dpSol(String s) {
		if (s.length() == 0) return new ArrayList<>();
		List<List<String>> res = new ArrayList<>();
		boolean[][] dp = new boolean[s.length()][s.length()];//representing whether s[i:j] is a palindrome
		// build dp TABLE (2-D array)
		for (int i = s.length() - 1; i >= 0; i--) {
			dp[i][i] = true; // the letter itself is a palindrome
			for (int j = i + 1; j < s.length(); j++) {
				if (s.charAt(i) == s.charAt(j)) {
					if (j - i == 1) {
						dp[i][j] = true;//aa
					} else {
						dp[i][j] = dp[i + 1][j - 1];//since s[i] == s[j]; See: LongestPalindromicSubstring.java
					}
				}
			}
		}
		
		// now we got dp array, backtracking the table to find a continuous sequence of
		// start/end pairs(no overlapping or skipping indices) that are palindromes for the entire String length
		backtracking(s, dp, 0, 0, res, new ArrayList<>());
		return res;
	}
	
	public void backtracking(String s, boolean[][] dp, int start, int end,
			List<List<String>> res, List<String> items) {
		if (end >= s.length()) {
			if (start == end) {
				res.add(new ArrayList<>(items));
			}
			return;
		}
		
		// if palindrome, partition here
		if (dp[start][end]) {
			items.add(s.substring(start, end + 1));// end is the partition point
			backtracking(s, dp, end + 1, end + 1, res, items);
			items.remove(items.size() - 1);
		}
		// either way, choose not to partition here and continue building item // // CANNOT place it in the ELSE statement
		backtracking(s, dp, start, end + 1, res, items);// end + 1 ==> this is why we need use nested IF statement
		
	}
	
	// Backtracking solution
	public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();//global var which belongs to whole recursive process
        helper(s, res, new ArrayList<>(), 0);
        return res;
    }
    
    public void helper(String s, List<List<String>> res, List<String> items, int start) {
//    	if (start == s.length()) {
//            res.add(new ArrayList<>(items));
//            return;
//        }
//        for (int i = start; i < s.length(); i++) {
//            if (isPalindrome(s.substring(start, i + 1))) {
//                items.add(s.substring(start, i + 1));
//                helper(s, res, items, i + 1);
//                items.remove(items.size() - 1);
//            }
//        }
    	
        if (start == s.length()) {
            res.add(new ArrayList<>(items));
            return;
        }
        // local var, each level has a copy of local var and will be popped out when recursively (N-ary tree)
        StringBuilder sb = new StringBuilder(); 
        for (int i = start; i < s.length(); i++) {// each level has a start, then from start to get a substring       
            sb.append(s.charAt(i));
            if (!isPalindrome(sb.toString())) continue;
            else {
                items.add(sb.toString());
                helper(s, res, items, i + 1);
                items.remove(items.size() - 1);
            }
        }
    }
    
    public boolean isPalindrome(String s) {
        if (s.length() == 1) return true;
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    
    public static void main(String[] args) {
    	new PalindromePartitioning().partition("aab");
    }
}
