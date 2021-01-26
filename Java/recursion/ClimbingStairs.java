package recursion;

import java.util.HashMap;

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

public class ClimbingStairs {

	// TIME: O(2 n-square)
	// Space: O(n), because the depth of the recursion tree can go upto n.
	public int climbStairs(int n) {
//		if (n == 0) return 0;
//		if (n == 1) return 1;
		return climb_stairs(0, n);
	}
	
	// BRUTE FORCE SOLUTION => TLE => Consider MEMOIZATION
	// At every step, they can only climb 1 or 2 steps
	public int climb_stairs(int i, int n) {
		// BASE CASE.,   NOT 0 OR 1
		if (i > n) return 0;
		if (i == n) return 1;
		// RECURRENCE RELATION
		
		// ASSUMING the ith step, they can have either 1 or 2 steps option
		return climb_stairs(i + 1, n) + climb_stairs(i + 2, n);
	}
	
	// MEMOIZATION TO REDUCE REDUNTANT CALCULATION
	// Time: O(n) : reducing redundant calculation
	// Space: O(n)
	public int climbStairs_MEMO(int n) {
        HashMap<Integer, Integer> cache = new HashMap<>();
        return helper(0, n, cache);

    }
    public int helper(int i, int n, HashMap<Integer, Integer> cache){
        if (i > n) return 0;
        if (i == n) return 1;
        
        if (cache.containsKey(i)) return cache.get(i);
        int result = helper(i+1, n, cache) + helper(i + 2, n, cache);
        // key is I, not N, n is fixed, but i is changing over time.
        // also refer to : https://leetcode.com/explore/learn/card/recursion-i/255/recursion-memoization/1662/ for dynamic demo
        cache.put(i, result); 
        
        return result;
    }
    
    // DP Solution
	/*
	 * this problem can be broken into subproblems, and it contains the optimal
	 * substructure property i.e. its optimal solution can be constructed
	 * efficiently from optimal solutions of its subproblems, we can use dynamic
	 * programming to solve this problem.
	 * 
	 * ONE CAN REACH i^{th}step IN ONE OF TWO WAYS:
	 * 
	 * Taking a single step from (i-1)^{th} step.
	 * 
	 * Taking a step of 2 from (i-2)^{th} step.
	 * 
	 * So, the total number of ways to reach i^{th} is equal to sum of ways of
	 * reaching (i-1)^{th} step and ways of reaching (i-2)^{th}
	 * step.
	 * 
	 * Let dp[i]dp[i] denotes the number of ways to reach on i^{th} step:
	 * 
	 * dp[i]=dp[i-1]+dp[i-2]
	 */
    // Time: O(n)
    // Space: O(n)
    public int dynammicSol(int n) {
    	if (n == 1) return 1;
    	int[] dp = new int[n + 1];
    	// initialization
    	dp[1] = 1;
    	dp[2] = 2;
    	for (int i = 3; i <= n; i++) {
    		// solution of subproblem
    		// IT IS A FibonacciNumber PROBLEM
    		dp[i] = dp[i - 1] + dp[i - 2];
    	}
    	return dp[n];
    }
    
    // FibonacciNumeber solution
    //TIME: O(n)
    //Space: O(1)
    public int climbStairs_Fibonacci(int n) {
        if (n == 1) {
            return 1;
        }
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }
        return second;
    }
	
	public static void main(String[] args) {
		System.out.println(new ClimbingStairs().climbStairs(3));
	}
}
