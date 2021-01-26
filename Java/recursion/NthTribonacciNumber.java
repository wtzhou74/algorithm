package recursion;

import java.util.HashMap;
import java.util.Map;

//The Tribonacci sequence Tn is defined as follows: 
//
//T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
//
//Given n, return the value of Tn.
//
// 
//
//Example 1:
//
//Input: n = 4
//Output: 4
//Explanation:
//T_3 = 0 + 1 + 1 = 2
//T_4 = 1 + 1 + 2 = 4
//Example 2:
//
//Input: n = 25
//Output: 1389537
// 
//
//Constraints:
//
//0 <= n <= 37
//The answer is guaranteed to fit within a 32-bit integer, ie. answer <= 2^31 - 1.
public class NthTribonacciNumber {

	// Time: O(38), n [0, 37]
	// Space: O(38)
	// DFS + MEMO
	Map<Integer, Integer> memo = new HashMap<>();
    public int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        if (memo.containsKey(n)) return memo.get(n);
        // a如果直接return, 就会 TLE
        //             10
        //     9,      8,         7
        //  [8,7,6], [7, 6, 5], [6,5,4]  // a做了很多重复计算， ===> memoization
        int sum = tribonacci(n - 3) + tribonacci(n - 2) + tribonacci(n - 1);
        memo.put(n, sum);
        return sum;
    }
    
    // ARRAY+DP ==> 1) 定义（一/二维）数组， 初始化  2） 定义关系， 填充数组值
    public int tribonaccidP(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        int[] tri = new int[n + 1];
        tri[0] = 0;
        tri[1] = 1;
        tri[2] = 1;
        for (int i = 3; i <= n; i++) {
            tri[i] = tri[i - 1] + tri[i - 2] + tri[i - 3];
        }
        return tri[n];
    }
}
