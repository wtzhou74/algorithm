package queueAndStack;

public class TargetSum {

	/**
	 *  Dynamic Programming !!! 
	 *  				  sum[p] - sum[n] = target
	 *  sum[p] + sum[n] + sum[p] - sum[n] = target + sum[p] + sum[n]
	 *  					   2 * sum[p] = target + sum[nums]
	 * */
	public static int targetSum(int[] nums, int s)
	{
		int sum = 0;
		for (int num : nums)
		{
			sum += num;
		}
		// Based on the formula above
		if (sum < s || (sum + s) % 2 > 0) {
			return 0;
		} else {
			return subsetSum(nums, (s + sum) >>> 1);
		}
		// the problem have been converted to subset sum problem
	}
	
	public static int subsetSum(int[] nums, int s)
	{
		// find sum[p] equals (target - sum)/2 
		int[] dp = new int[s + 1]; 
        dp[0] = 1;
        for (int n : nums)
            for (int i = s; i >= n; i--)
                dp[i] += dp[i - n]; 
        return dp[s];
		
	}
	
	// recursion - DFS
	private static int count;
	public static int dfsSol(int[] nums, int s)
	{
		if (nums.length == 0) return 0;
		count = 0;
		DFS(nums, s, 0, 0);
		return count;
		
	}
	
	public static void DFS(int[] nums, int s, int cur, int sum)
	{
		// finish condition
		if (cur == nums.length)
		{
			if (sum == s)
			{
				count++;
			}
			return;
		}
		
		// Cannot use cur++ ??
		// two situations, refer to nums of island having four situation.
		DFS(nums, s, cur + 1, sum + nums[cur]);
		DFS(nums, s, cur + 1, sum - nums[cur]);
		
	}
}
