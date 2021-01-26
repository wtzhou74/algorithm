package dynamicProgramming;

// Detailed explanation: https://www.cnblogs.com/grandyang/p/5006441.html
public class BurstBalloons {

	// checking ALL possible pairs, time = N * (n- 1) *... = O(n^n)
	public int maxCoins(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int[][] memo = new int[nums.length + 1][nums.length + 1]; // memo[i][j] 打爆区间 [i,j] 中的所有气球能得到的最多金币
        return dp(0, nums.length - 1, nums, memo);
    }
    
	// dp array
	// Time: 0(N^3); N^2 (left, right) pairs and O(N) to find the value of one of them
	// Space: O(N^2) dp size
	public int dpArray(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        
        int n = nums.length;
        int[] new_nums = new int[n + 2];
        new_nums[0] = new_nums[n + 1] = 1;
        for (int i = 0; i < nums.length; i++) {
            new_nums[i + 1] = nums[i]; 
        }
        
        int[][] dp = new int[n + 2][n + 2];
//        for (int i = 1; i <= n; i++) {
//            for (int left = 1; left <= n - i + 1; left++) {
//                int right = left + i - 1;
//                for (int j = left; j <= right; j++) {
//                    dp[left][right] = Math.max(dp[left][right], dp[left][j - 1] +
//                        dp[j + 1][right] + new_nums[left - 1] * new_nums[j] * new_nums[right + 1]);
//                }
//            }
//        }
        
        // FIND THE PAIRS OF (LEFT, RIGHT)
        for (int left = n; left >= 1; left--) {
        	for (int right = left; right < n + 1; right++) {
        		for (int i = left; i <= right; i++) {
        			dp[left][right] = Math.max(dp[left][right], dp[left][i - 1] + dp[i + 1][right] + new_nums[left - 1] * new_nums[i] * new_nums[right + 1]);
        		} 
        	}
        }
        //   for (int len = 1; len <= n; ++len) {
        //     for (int i = 1; i <= n - len + 1; ++i) {
        //         int j = i + len - 1;
        //         for (int k = i; k <= j; ++k) {
        //             dp[i][j] = max(dp[i][j], nums[i - 1] * nums[k] * nums[j + 1] + dp[i][k - 1] + dp[k + 1][j]);
        //         }
        //     }
        // }
        
        return dp[1][n]; // dp[1][n] means the maximum coins achieved by bursting all balloons between(1, n)
    }
	
	// recursion + memo
	// Time: O(n^2 * n) = O(n^3)
	// Space: O(n^2)
    public int dp(int left, int right, int[] nums, int[][] memo) {
        if (left > right) {
            return 0; // no elem can be added
        }
        if (memo[left][right] > 0) {
            return memo[left][right];
        }
        int max = Integer.MIN_VALUE;
        for (int i = left; i <= right; i++) {
        	//an 用小区间去更新大区间，然后才能去更新大区间, i是[left, right]之间
        	// nums[left - 1] * nums[i] * nums[right + 1] 因为 dp[left, right] 表示 打爆区间这个区间所有期间得到的金币，所以，这区间的所有气球都打完了，只能打 left - 1, right + 1
            int res = dp(left, i - 1, nums, memo) + // [left, i -1]
                getNum(left - 1, nums) * nums[i] * getNum(right + 1, nums) +
                dp(i + 1, right, nums, memo); // [i + 1, right]
            max = Math.max(max, res);
        }
        memo[left][right] = max;
        return max;
    }
    
    public int getNum (int idx, int[] nums) {
        if (idx < 0 || idx > nums.length - 1) return 1;
        else return nums[idx];
    }
    
    public static void main(String[] args) {
    	int[] nums = new int[] {3, 1, 5, 8};
    	new BurstBalloons().dpArray(nums);
    }
}
