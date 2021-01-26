package dynamicProgramming;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

//Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
//
//Example 1:
//
//Input: n = 12
//Output: 3 
//Explanation: 12 = 4 + 4 + 4.
//Example 2:
//
//Input: n = 13
//Output: 2
//Explanation: 13 = 4 + 9.
public class PerfectSquare {

	// Dynamic Solution
	// sub-problem:  1...n, 对应每个数时， 其最小数目的平方的和等于 i
	//		==> dp[i] 表示 当为 i 时， 最小数目的平方的和等于 i
	public int numSquares(int n) {
		int[] dp = new int[n + 1];
		Arrays.fill(dp, n+1); // initial dp array, because we are going to do min()
		dp[0] = 0;
		for (int i = 1; i <= n; i++) {
			// start to calculate the minimum number of squares for each i
			for (int j = 1; j <= i * i; j++) {
				// dp[i - j * j] can reach to dp[i] by doing j*j ==> This is one jump + 1
				dp[i] = Math.min(dp[i - j * j] + 1, dp[i]);
				
//				int add = dp(n, sum + i * i, i + 1) + 1;
//		        int skip = dp(n, sum, i + 1);  !!!!!! WRONG TO DEFINE THE SUB-PROBLEM
			}
		}
		
		return dp[n];
	}
	
	
	// SQUARE ==> N-ary tree ===> BFS,  refer to PerfectSquares.PNG
	public int bfs(int n) {
		Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);
        //int size = 1;
        int level = 1;
        while (!queue.isEmpty()) {
        	int size = queue.size();
            while (size > 0) {
            	int num = queue.poll();
            	size--;
            	int i = 1;
                while (i * i <= num) {
                    if (i * i == num) return level;
                    queue.offer(num - i * i); // EACH level has ONLY one square 12 = 4-4-4, so cannot use num % (i * i)
                    i++;
                }
            }           
            level++;
        }
        return -1;
	}
	
	
	// recursion
	public int recursion(int n) {
        int res  = n, i = 2;
        while (i * i <= n) {
            int currNum = n / (i * i), remain = n % (i * i); // REMAIN cannot be calculated with n - (i * i), currNum get the "total" number of i*i
            res = Math.min(currNum + recursion(remain), res);
            i++;
        }
        
        return res;
    }
	
	// recursion in Python for reference
//	def numSquares(self, n):
//        square_nums = [i**2 for i in range(1, int(math.sqrt(n))+1)]
//
//        def minNumSquares(k):
//            """ recursive solution """
//            # bottom cases: find a square number
//            if k in square_nums:
//                return 1
//            min_num = float('inf')
//
//            # Find the minimal value among all possible solutions
//            for square in square_nums:
//                if k < square:
//                    break
//                new_num = minNumSquares(k-square) + 1
//                min_num = min(min_num, new_num)
//            return min_num
//
//        return minNumSquares(n)
	public static void main(String args[]) {
		int num = new PerfectSquare().bfs(13);
		System.out.println(num);
	}
}
