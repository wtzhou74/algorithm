package math;

//Given an integer n, return the number of trailing zeroes in n!.
//
//Example 1:
//
//Input: 3
//Output: 0
//Explanation: 3! = 6, no trailing zero.
//Example 2:
//
//Input: 5
//Output: 1
//Explanation: 5! = 120, one trailing zero.
//Note: Your solution should be in logarithmic time complexity.

// NOTE: 
// 		2 * 5 = 10 generates one 0 ==> counting number of 5 since there are MORE 5 than 2
public class FactorialTrailingZeros {

	/**
	 * Figure out the RULE within the numbers
	 *  1) [....5....10....15....20....25...30....]  ==> 5 * (1, 2, 3, 4, 5)There are 30/5 = 5 SINGLE 5
	 *  2) => 5 * (1, 2, 3, 4, 5, 6)  => 5 * 5 (1)
	 * 
	 * Reference: https://blog.csdn.net/nwsuaf_uestc/article/details/78788932
	 * */
	public int trailingZeroesOpti(int n) {
		int count = 0;
		while (n >= 5) {
			count += n / 5;
			n /= 5;
		}
		return count;
	}
	// TLE - INPUT: 1808548329
	public int trailingZeroes(int n) {
		int count = 0;
        for (int i = 5; i <= n; i += 5) { // 5 steps each time
            if (i % 5 == 0) {
            	int temp = i;
                while(temp % 5 == 0) {
                    temp /= 5;
                    count++;
                }
            }
        }
        return count;
	}
//	public long trailingZeroes(int n) {
//        long res  = 1l;
//        res = facotorial(5);
//        return res;
//    }
    
	//RECURSIVE relation f(n) = n * f(n - 1)
    public long facotorial(int n) {
        if (n == 1) return 1;
        return n * facotorial(n - 1);
    }
    
    public static void main(String[] args) {
    	new FactorialTrailingZeros().trailingZeroesOpti(30);
    }
}
