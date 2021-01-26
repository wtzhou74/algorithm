package dynamicProgramming;

import java.util.Arrays;

public class NumberOfDiceRollsWithTargetSum {

	public int numRollsToTarget(int d, int f, int target) {
        if (d == 1 && f < target) return 0;
        int mod = (int)Math.pow(10, 9) + 7;
        
        // 子问题  ==> 只有1个dice，其对应不同情况下的target,2/3/4... ==> 采用2维数组
        int[][] dp = new int[d + 1][target + 1];
        // for (int i = 1; i <= target; i++) {
        //     dp[1][i] = 1;  // 不能直接赋值1， 是否是1 还要看 face跟target的关系
        // }
        dp[0][0] = 1; // 0个dice,target =0 就是 1种情况
        for (int n = 1; n <= d; n++) { // d个dice, 重复下面的情况
            for (int i = 0; i <= target; i++) { // 从 0 开始
                for (int face = 1; face <= f; face++) 
                    if (i >= face) { // ！！！！一定要判断
                        dp[n][i] += dp[n - 1][i - face]; // !!![n-1]，因为是选从第n-1个dice，到第n个dice,选face
                        dp[n][i] %= mod;
                    }
            }
        }
        
        return dp[d][target];
       
    }
	
	
	// recursive solution
	public int numRollsToTarget1(int d, int f, int target) {
        if (d == 1 && f < target) return 0;
        int mod = (int)Math.pow(10, 9) + 7;
        int[][] memo = new int[d + 1][target + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1); // 必须先都赋值 -1， 因为 0可以是结果的
        }
        return dp(d, f, target, memo);
    }
    
    int mod = (int)Math.pow(10, 9) + 7;
    public int dp(int d, int f, int target, int[][] memo) {
        if (d < 0  || target < 0) {
            return 0;
        }
        if (d == 0 && target < 0)
            return 0;
        if (d == 0 && target == 0)
            return 1;
        // if(d == 0 || target <= 0)
        //     return d == target ? 1: 0; 
        if (memo[d][target] != -1)
            return memo[d][target];
        int sum = 0;
        for (int j = 1; j <= f; j++) {
            int s = dp(d - 1, f, target - j, memo); // 从剩下 d - 1个dice找出和等于 target - j的solution
            sum = (sum + s) % mod; // sum += (s % mod)结果不对
        }
        memo[d][target] = sum;
        return sum;
    }
}
