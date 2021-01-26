package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

public class DecodeWays {

	// Similar to ClimbStairs.java
	public int dp(String s) {
		if (s.length() == 0 || s.charAt(0) == '0') return 0;
		if (s.length() == 1) return 1;
        // Map<Integer, Integer> memo = new HashMap<>();
        // return helper(0, s, memo);
        
        //return count;
        int[] dp = new int[s.length() + 1];
        dp[0] = 1; // one digit
        dp[1] = s.charAt(0) == '0' ? 0 : 1; // has two digits
        for (int i = 2; i <= s.length(); i++) { // i here is used for right of substring(), so, i should reach to len
            // Two options
            int oneDigit = Integer.parseInt(s.substring((i - 1), i));// i can reach to the length
            int twoDigits = Integer.parseInt(s.substring((i - 2), i));
            
            // option 1
            if (oneDigit > 0 && oneDigit < 10) {
                dp[i] += dp[i - 1];
            }
            // option 2
            if (twoDigits >= 10 && twoDigits <= 26) {
                dp[i] += dp[i - 2];
            }
           
        }
         return dp[s.length()];
	}
	public int numDecodings(String s) {
		if (s.length() == 0) return 0;
		if (s.length() == 1) return 1;
		Map<Integer, Integer> memo = new HashMap<>();
        return helper(0, s, memo);
        //return count;
    }
	
	// recursion + memo
    public int helper(int i, String s, Map<Integer, Integer> memo) {
    	if (i <= s.length() - 1 && s.charAt(i) == '0') return 0; // "10" => 1, 0 is NOT a solution
        if (i >= s.length() - 1) {
            //count++;
            return 1; // find a solution
        }
        if (memo.containsKey(i))
            return memo.get(i);
        
        int way = 0;
    	if (s.length() >= 2) {
        	if (i < s.length() - 1 && 
        			Integer.parseInt(s.substring(i, i + 2)) <= 26) {
    			way = helper(i + 2, s, memo);
			} 
        }
        
        way += helper(i + 1, s, memo);
        memo.put(i, way);
        
        return way;
    }
    
    public static void main(String[] args) {
    	int res = new DecodeWays().numDecodings("226");
    	System.out.println(res);
    }
}
