package recursionII.backtracking;

import java.util.HashMap;
import java.util.Map;

//Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.
//
//You have the following 3 operations permitted on a word:
//
//Insert a character
//Delete a character
//Replace a character
//Example 1:
//
//Input: word1 = "horse", word2 = "ros"
//Output: 3
//Explanation: 
//horse -> rorse (replace 'h' with 'r')
//rorse -> rose (remove 'r')
//rose -> ros (remove 'e')
//Example 2:
//
//Input: word1 = "intention", word2 = "execution"
//Output: 5
//Explanation: 
//intention -> inention (remove 't')
//inention -> enention (replace 'i' with 'e')
//enention -> exention (replace 'n' with 'x')
//exention -> exection (replace 'n' with 'c')
//exection -> execution (insert 'u')

public class EditDistance {

	// D[i][j] = edit distance of word1[1, i] to word2[1, j]
	// To get the D[i][j], there are 3 options, so, we should know D[i][j-1], D[i-1][j-1] and D[i-1][j] first.
	public int dynamicSol(String word1, String word2) {
		int[][] dp = new int[word1.length() + 1][word2.length() + 1];// NOTE: to add 1; since the first col/row is for the initialization purpose
		for (int i = 0; i <= word1.length(); i++) {
			dp[i][0] = i;
		}
		for (int i = 0; i <= word2.length(); i++) {
			dp[0][i] = i;
		}
		
		// Top down; see bottom-up sample in RegularExpressionMatch.java
		for (int i = 1; i <= word1.length(); i++) {
			for (int j = 1; j <= word2.length(); j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {//TRICK
					// if last character is same, then current replacement (dp[i-1][j-1])
					dp[i][j] = 1 + Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1] - 1);// 3 operations
				} else {
					dp[i][j] = 1 + Math.min(Math.min(dp[i][j - 1], dp[i - 1][j]), dp[i - 1][j - 1]);
				}
			}
		}
		System.out.println(dp[word1.length()][word2.length()]);
		return dp[word1.length()][word2.length()];
	}
	
    public int minDistance(String word1, String word2) {
        if (word1.equals(word2)) return 0;
        Map<String, Integer> memo = new HashMap<String, Integer>();
        int dist = backtracking(word1, word2, 0, memo);
        return dist;
    }
    
    // Using substring() to realize remove operation
    // Straightforward solution, but it is inefficient
    public int backtracking(String word1, String word2, int count, Map<String, Integer> memo) {
    	if (word1.length() == 0 && word2.length() == 0) 
    		return count;
    	
    	String key = word1 + "/" + word2;
    	if (memo.containsKey(key)) 
    		return memo.get(key);
    	
    	int dist; // local variable, each lay will have one copy of it
    	if (word1.length() > 0 && word2.length() > 0) {
    		// two characters are same
    		if (word1.charAt(0) == word2.charAt(0))
    			dist = backtracking(word1.substring(1), word2.substring(1), count, memo);
    		else {
    			// 3 options : replacement, insertion, deletion
    			// after each operation, the FIRST characters of two words are SAME
    			// deletion
    			int delDis = backtracking(word1.substring(1), word2, count, memo); // WRONG!!!!!!!!!!!backtracking(word1.substring(1), word2, COUNT + 1, memo);
    			// insertion
    			int inDis = backtracking(word1, word2.substring(1), count, memo); // in this case, the first char of word1 is the same as the first one of word2 after insertion
    			// replacement
    			int reDis = backtracking(word1.substring(1), word2.substring(1), count, memo);
    			
    			dist = Math.min(Math.min(delDis, inDis), reDis) + 1; // IT IS WRONG TO do "COUNT + 1" for each operation
    			// deletion, replacement and insertion are POSSIBLE operation for the final solution, we cannot add 1 for each operation
    			// we should add 1
    		}
    	} else {
    		// one word is empty, now we just need to remove all the remaining characters from non-empty string, each deletion is one operation (count + 1)
    		dist = count + Math.max(word1.length(), word2.length());
    	}
    	memo.put(key, dist);
    	return dist;
    	
    }
    
    public static void main(String[] args) {
    	new EditDistance().dynamicSol("horse", "ros");
    }
}
