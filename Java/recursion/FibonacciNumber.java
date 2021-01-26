package recursion;

import java.util.HashMap;

/*The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, 
 * such that each number is the sum of the two preceding ones, starting from 0 and 1. That is,

F(0) = 0,   F(1) = 1
F(N) = F(N - 1) + F(N - 2), for N > 1.
Given N, calculate F(N).

 

Example 1:

Input: 2
Output: 1
Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
Example 2:

Input: 3
Output: 2
Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.
Example 3:

Input: 4
Output: 3
Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.


Note:

0 ≤ N ≤ 30.
*/
public class FibonacciNumber {

	public int fib(int N) {
		// BASE CASE
		if (N == 0) return 0;
		if (N == 1) return 1;
		
		HashMap<Integer, Integer> cache = new HashMap<>();
		if (cache.containsKey(N)) {
			cache.get(N);
		}
		int result = fib(N - 1) + fib(N - 2);
		cache.put(N, result);
		
		// RECURRENCE RELATION
		// return fib(N - 1) + fib(N - 2);
		
		return result;
	}
	
	// ITERATIVE SOLUTION
	public int fibIterative(int N) {
		if (N == 0) return 0;
		if (N == 1) return 1;
		
		int R = 0; //F(0)
		int S = 1; //f(1)
		int T;
		for (int i = 1; i < N - 1; i++) {
			T = R + S; // F(2)
			R = S;
			S = T;
			
		}
		return S;
	}
	
	public static void main(String[] args) {
		System.out.println( new FibonacciNumber().fib(3));
	}
}
