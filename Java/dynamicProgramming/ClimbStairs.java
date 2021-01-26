package dynamicProgramming;

import java.util.HashMap;
import java.util.Map;

//You are climbing a stair case. It takes n steps to reach to the top.
//
//Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
//
//Note: Given n will be a positive integer.
//
//Example 1:
//
//Input: 2
//Output: 2
//Explanation: There are two ways to climb to the top.
//1. 1 step + 1 step
//2. 2 steps
//Example 2:
//
//Input: 3
//Output: 3
//Explanation: There are three ways to climb to the top.
//1. 1 step + 1 step + 1 step
//2. 1 step + 2 steps
//3. 2 steps + 1 step


// HINT: To reach nth step, what could have been your previous steps? (Think about the step sizes)
public class ClimbStairs {

	public int climbStairs(int n) {
		 if (n == 1) return 1;
    	 int[] dp = new int[n + 1];
    	 dp[1] = 1;// when there is one step
    	 dp[2] = 2;// when there are two steps
    	 // (RECURSIVE) relation
    	 for (int i = 3; i <= n; i++) {
    	 	dp[i] = dp[i - 1] + dp[i - 2];
    	 }
    	 return dp[n];
    	 
    	 // 如果要用下面的这些，dp[0]一定赋值 1
//    	 dp[0] = 1;
//         dp[1] = 1;
//         //dp[2] = 2;
//         for (int step = 2; step <= n; step++) {
//             for (int j = 1; j <= 2; j++) {
//                 dp[step] += dp[step - j];
//             }
//         }
	}
	
	// DP: RECURSION + MEMOIZATION
	public int sol2(int n) {
		Map<Integer, Integer> cache = new HashMap<>();
		return helper(n, cache);
	}
	
	public int dp (int n, int[] memo) {
        if (n < 0) {
            return 0;
        }
        if (n == 0) {
            return 1;
        }
        if (memo[n] != 0)
            return memo[n];
        int ways = 0;
        int s1 = dp(n - 1, memo);
        int s2 = dp(n - 2, memo);
        ways = s1 + s2; // 把两种方式对应的dp值相加
        memo[n] = ways;
        return ways;
    }
	
	
	// WITHOUT MEMOIZATION == > TLE!!!
	public int helper(int n, Map<Integer, Integer> cache) {
		if (n == 1) {
            return 1;
        }
        if (n == 2) {return 2;}
        if (cache.containsKey(n)) return cache.get(n);
        int steps = helper(n - 2, cache) + helper(n - 1, cache);
        
        // int steps = helper(n - 2) + helper(n - 1); // WRONG, SET CACHE to each recursion !!!!!
        cache.put(n, steps);
        
        return steps;
        
        
	}
	
//  public int helper(int i, int n, HashMap<Integer, Integer> cache){
//  if (i > n) return 0;
//  if (i == n) return 1;
 
//  if (cache.containsKey(i)) return cache.get(i);
//  int result = helper(i+1, n, cache) + helper(i + 2, n, cache);
//  cache.put(i, result);
 
//  return result;
//}
}
