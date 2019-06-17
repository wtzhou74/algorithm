package queueAndStack;

import java.util.Arrays;

public class PerfectSquare {

	public static int leastNumOfPerfectSquare(int n)
	{
		int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        for(int i = 1; i <= n; i++) {
            int maxSquare = (int) Math.sqrt(i);
            for(int j = 1; j <= maxSquare; j++)
                dp[i] = Math.min(dp[i], 1 + dp[i - (int)Math.pow(j, 2)]); 
        }
        
        return dp[n];
	}
}
