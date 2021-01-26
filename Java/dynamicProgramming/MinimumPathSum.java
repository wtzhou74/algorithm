package dynamicProgramming;

//Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right which minimizes the sum of all numbers along its path.
//
//Note: You can only move either down or right at any point in time.
//
//Example:
//
//Input:
//[
//  [1,3,1],
//  [1,5,1],
//  [4,2,1]
//]
//Output: 7
//Explanation: Because the path 1→3→1→1→1 minimizes the sum.
public class MinimumPathSum {

	// 跟 MinCostClimbingStairs.java 类似，只是这里变为2维， ===> 那就把 cell [i][j] 看成一整个单元， ==> 构造 dp[][]数组
	// 同样， 根据题意 ==> 没走一步两种选择， 往右，往下  ===> 所以，对当前cell， 看上面两种情况哪个过来cost更小，取最小
	//			====> 得出relation   dp[i][j] = Math.min([i-1][j], [i][j-1]) + cell[i][j]
	// 初始化的时候， 只有一行，只有一列的情况，DP所有值都可以得出
	// refer to: MinimumPathSum.jpg
	public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int[][] dp = new int[grid.length][grid[0].length];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < grid.length; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0]; // 第一行， 即从第一行的第一个出发，从右走每个得dp值
        }
        for (int i = 1; i < grid[0].length; i++) { // 第一列，每一个下来的的dp值
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < grid.length; i++) {
            for (int j = 1; j < grid[0].length; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], 
                                    dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[grid.length - 1][grid[0].length - 1];
    }
	
	
	// recursive solution
	public int minPathSum1(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;
        int[][] memo = new int[grid.length][grid[0].length];
        // for (int i = 0; i < grid.length; i++) {
        //     for (int j = 0; j < grid[0].length; j++) {
        //         memo[i][j] = -1;
        //     }
        // }
        return dp(grid, 0, 0, memo);
    }
    
	
	// recursive 写法
    public int dp(int[][] grid, int i, int j, int [][] memo) {
        // if (i < 0 || i >= grid.length 
        //     || j < 0 || j >= grid[0].length) // 指定了方向，从left-corn开始，不会出现 < 0
        if (i >= grid.length || j >= grid[0].length)
            return Integer.MAX_VALUE; // 我们是取min，所以出界直接返回最大，就不会被取上
        if (i == grid.length - 1 && j == grid[0].length - 1)
            return grid[i][j]; // 到最后一个点，直接返回当前值
        if (memo[i][j] != 0)
            return memo[i][j];
        int min = grid[i][j]; // 从当前值开始，往下/往右走
        // int down = dp(grid, i + 1, j, memo) + grid[i][j]; // 不要先 + ，否则会溢出，因为前面递归往回的时候，return MAX
        int down = dp(grid, i + 1, j, memo);
        int right = dp(grid, i, j + 1, memo);
        
        memo[i][j] = Math.min(down, right) + min;//先取两者小的再加当前值，防止溢出
        
        return memo[i][j];
        
    }
}
